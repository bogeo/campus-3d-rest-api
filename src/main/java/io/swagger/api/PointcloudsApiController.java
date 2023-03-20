package io.swagger.api;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Min;

import io.swagger.api.impl.Database;
import io.swagger.api.impl.Geoserver;
import io.swagger.api.impl.PythonExecutor;
import io.swagger.api.impl.PointcloudManager;
import io.swagger.api.impl.db.model.*;
import io.swagger.api.impl.db.query.QueryMetadata;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.model.PointcloudMetadata;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-12-08T12:14:47.659Z[GMT]")
@RestController
public class PointcloudsApiController implements PointcloudsApi {

    private static final Logger log = LoggerFactory.getLogger(PointcloudsApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    private ApplicationContext context;

    @org.springframework.beans.factory.annotation.Autowired
    public PointcloudsApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Void> addPointcloud(@Parameter(description = "file detail") @Valid @RequestPart("file") MultipartFile file,
                                              @Parameter(in = ParameterIn.DEFAULT, description = "", required=true,schema=@Schema()) @RequestParam(value="pointcloudId", required=true)  Integer pointcloudId,
                                              @Parameter(in = ParameterIn.DEFAULT, description = "", required=true,schema=@Schema()) @RequestParam(value="metadata", required=true)  String metadata) {
        log.info("start POST command");
        String accept = request.getHeader("Accept");
        PointcloudManager pcManager = context.getBean(PointcloudManager.class);
        pcManager.generatePaths();
        log.info("generated tempdir path: " + pcManager.getTempDirPath());
        log.info("generated tempdir path: " + pcManager.getPythonScriptPostPath());
        log.info("generated tempdir path: " + pcManager.getPythonScriptGetPath());
        log.info("generated tempdir path: " + pcManager.getPythonScriptDeletePath());
        pcManager.setFile(file);
        pcManager.setFileName(pointcloudId);
        
        try {

            pcManager.deleteTempPointcloudFiles();
			PointcloudMetadata metadataObj = objectMapper.readValue(metadata, PointcloudMetadata.class);

            log.info("save Pointcloud File local");
            pcManager.savePointcloudFile();
            metadataObj.setName(file.getOriginalFilename().split("\\.")[0]);
            Database db = context.getBean(Database.class);

            log.info("check if id already exists");
            QueryMetadata queryMetadata = new QueryMetadata(db, pointcloudId);
            Boolean idExists = queryMetadata.checkIdExistence();

            if(idExists == false){
                log.info("id " + pointcloudId + " is free");

                log.info("run python commands");
                Geoserver geoserver = context.getBean(Geoserver.class);
                PythonExecutor pythonExecutor = new PythonExecutor(pcManager, db, geoserver);
                metadataObj = pythonExecutor.executePdalPostCommands(metadataObj, pointcloudId);

                log.info("save metadata to database");
                queryMetadata.setMetadataObj(metadataObj);
                queryMetadata.insertMetadata();

                pcManager.deleteTempPointcloudFiles();
                log.info("end POST command");

            }

            else{
                return new ResponseEntity("Pointcloud with id " + pointcloudId + " is already in use", HttpStatus.BAD_REQUEST);
            }


		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
            e.printStackTrace();
        }
        return new ResponseEntity("Pointcloud with id " + pointcloudId + " added", HttpStatus.OK);
    }

    public ResponseEntity<Void> deletePointcloudById(@Min(1)@Parameter(in = ParameterIn.PATH, description = "The pointcloud ID", required=true, schema=@Schema(allowableValues={  }, minimum="1")) @PathVariable("id") Integer id) {

        log.info("start DELETE{id} command");
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json") || accept.contains("*/*")) {
            try {
                Database db = context.getBean(Database.class);
                Geoserver geoserver = context.getBean(Geoserver.class);

                log.info("check if id exists");
                QueryMetadata queryMetadata = new QueryMetadata(db, id);
                Boolean idExists = queryMetadata.checkIdExistence();

                if(idExists){
                    log.info("id " + id + "exists");
                    Connection conn = queryMetadata.getConn();

                    // select metadata table of id
                    List<Metadata> metadataDatasets = Metadata.execSelectAllStatement(conn, "where mid = " + id.toString());

                    log.info("delete metadata");
                    if(metadataDatasets.size() > 0){

                        Metadata dataset = metadataDatasets.get(0);

                        // check how often foreign keys are used
                        Integer expressionId = dataset.getExpressionId();
                        Integer datasourceId = dataset.getDataSourceId();
                        Integer projectionId = dataset.getProjectionId();

                        Integer expressionIdCount = dataset.execSelectCountStatement(conn, "expressionid", expressionId);
                        Integer datasourceIdCount = dataset.execSelectCountStatement(conn, "datasourceid", datasourceId);
                        Integer projectionIdCount = dataset.execSelectCountStatement(conn, "projectionid", projectionId);

                        // if foreign key is only used one time, delete row from foreign table
                        if(expressionIdCount == 1){
                            Expression.execDeleteStatement(conn, expressionId);
                        }
                        if(datasourceIdCount == 1){
                            DataSource.execDeleteStatement(conn, datasourceId);
                        }
                        if(projectionIdCount == 1){
                            Projection.execDeleteStatement(conn, projectionId);
                        }

                        // delete row from metadata table
                        dataset.execDeleteStatement(conn, id);
                        Pointcloud.execDeleteStatement(conn, id);
                    }

                    log.info("delete data from database and geoserver via python commands");
                    PointcloudManager pcManager = context.getBean(PointcloudManager.class);
                    pcManager.generatePaths();
                    PythonExecutor pythonExecutor = new PythonExecutor(pcManager, geoserver);
                    String response = pythonExecutor.executeGeoserverDeleteCommand(id);

                    log.info("end DELETE{id} command");

                    return new ResponseEntity(response, HttpStatus.OK);

                }

                else{
                    return new ResponseEntity("Pointcloud with id " + id + " is already in use", HttpStatus.BAD_REQUEST);
                }


            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return new ResponseEntity("Wrong header 'accept'", HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Resource> getPointcloudById(@Min(1)@Parameter(in = ParameterIn.PATH, description = "The pointcloud ID.", required=true, schema=@Schema(allowableValues={  }, minimum="1")) @PathVariable("id") Integer id) {

        log.info("start GET{id} command");
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/octet-stream") || accept.contains("*/*")) {
            try {
                PointcloudManager pcManager = context.getBean(PointcloudManager.class);
                pcManager.generatePaths();
                log.info(pcManager.getTempDirPath());
                Database db = context.getBean(Database.class);

                log.info("check if id " + id + " exists");
                QueryMetadata queryMetadata = new QueryMetadata(db, id);
                Boolean idExists = queryMetadata.checkIdExistence();

                if(idExists){
                    log.info("id " + id + " is available");

                    log.info("run python commands");
                    PythonExecutor pythonExecutor = new PythonExecutor(pcManager, db);
                    ByteArrayResource resource = pythonExecutor.executePdalGetCommand(id);

                    log.info("end GET{id} command");
                    return ResponseEntity.ok()
                            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + resource.getFilename())
                            .contentType(MediaType.APPLICATION_OCTET_STREAM)
                            .body(resource);
                }

                else{
                    return new ResponseEntity("No pointcloud with id " + id + " found", HttpStatus.BAD_REQUEST);
                }

            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<Resource>(HttpStatus.INTERNAL_SERVER_ERROR);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return new ResponseEntity<Resource>(HttpStatus.OK);
    }

    public ResponseEntity<List<PointcloudMetadata>> getPointclouds() {

        log.info("start GET command");
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json") || accept.contains("*/*")) {
            try {
                Database db = context.getBean(Database.class);
                Geoserver geoserver = context.getBean(Geoserver.class);
                QueryMetadata queryMetadata = new QueryMetadata(db);
                Connection conn = queryMetadata.getConn();

                log.info("request all metadata datasets");
                List<Metadata> metadataDatasets = Metadata.execSelectAllStatement(conn);

                JSONObject resultObject = new JSONObject();
                JSONArray resultArray = new JSONArray();

                resultObject.put("url", geoserver.getUrl());

                log.info("build json object");
                for (Metadata dataset: metadataDatasets) {

                    JSONObject datasetJSON = new JSONObject();

                    Integer mid = dataset.getMid();
                    JSONObject accuracyJSON = dataset.getAccuracyAsJSON();
                    JSONObject expressionJSON = Expression.getExpressionAsJSON(conn, dataset.getExpressionId());
                    String measureMethodString = MeasureMethod.getMeasureMethodAsString(conn, dataset.getMeasureMethodId());
                    JSONObject measureDateJSON = dataset.getMeasureDateAsJSON();
                    JSONObject dataSourceJSON = DataSource.getDataSourceAsJSON(conn, dataset.getDataSourceId());
                    JSONArray attributeFieldJSON = AttributeField.getAttributeFieldAsJSON(conn, dataset.getAttributeFieldIds());
                    String freetextString = dataset.getFreetextAsString();
                    JSONObject projectionJSON = Projection.getProjectionAsJSON(conn, dataset.getProjectionId());
                    String licenseString = dataset.getLicenseAsString();
                    String boundaryString = dataset.getBoundaryAsString();

                    String metadata = datasetJSON.toString();
                    PointcloudMetadata metadataObj = objectMapper.readValue(metadata, PointcloudMetadata.class);

                    datasetJSON.put("id", mid);
                    datasetJSON.put("accuracy", accuracyJSON);
                    datasetJSON.put("expression", expressionJSON);
                    datasetJSON.put("measureMethod", measureMethodString);
                    datasetJSON.put("measureDate", measureDateJSON);
                    datasetJSON.put("dataSource", dataSourceJSON);
                    datasetJSON.put("attributeFields", attributeFieldJSON);
                    datasetJSON.put("freeText", freetextString);
                    datasetJSON.put("projection", projectionJSON);
                    datasetJSON.put("license", licenseString);
                    datasetJSON.put("boundary", boundaryString);
                    datasetJSON.put("rasterLayerName", "campus-3d-dems:pointcloud_" + mid);

                    resultArray.put(datasetJSON);
                }

                resultObject.put("pointclouds", resultArray);

                return new ResponseEntity(resultObject.toString(), HttpStatus.OK);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        log.info("end GET command");
        return new ResponseEntity("Wrong header 'accept'", HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> replacePointcloudById(@Min(1)@Parameter(in = ParameterIn.PATH, description = "The pointcloud ID", required=true, schema=@Schema(allowableValues={  }, minimum="1"
)) @PathVariable("id") Integer id,@Parameter(description = "file detail") @Valid @RequestPart("file") MultipartFile file,@Parameter(in = ParameterIn.DEFAULT, description = "", required=true,schema=@Schema()) @RequestParam(value="metadata", required=true)  PointcloudMetadata metadata) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> replacePointcloudMetadataById(@Min(1)@Parameter(in = ParameterIn.PATH, description = "The pointcloud id", required=true, schema=@Schema(allowableValues={  }, minimum="1"
)) @PathVariable("id") Integer id,@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody PointcloudMetadata body) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

}
