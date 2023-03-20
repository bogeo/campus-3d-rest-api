/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.30).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package io.swagger.api;

import io.swagger.model.PointcloudMetadata;
import org.springframework.core.io.Resource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.CookieValue;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Map;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-12-08T12:14:47.659Z[GMT]")
@Validated
public interface PointcloudsApi {

    @Operation(summary = "Add a new pointcloud as LAZ", description = "", tags={ "pointclouds" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "201", description = "CREATED"),
        
        @ApiResponse(responseCode = "400", description = "Bad Request"),
        
        @ApiResponse(responseCode = "401", description = "User Credentials are missing or invalid") })
    @RequestMapping(value = "/pointclouds",
        consumes = { "multipart/form-data" }, 
        method = RequestMethod.POST)
    ResponseEntity<Void> addPointcloud(@Parameter(description = "file detail") @Valid @RequestPart("file") MultipartFile file, @Parameter(in = ParameterIn.DEFAULT, description = "", required=true,schema=@Schema()) @RequestParam(value="pointcloudId", required=true)  Integer pointcloudId, @Parameter(in = ParameterIn.DEFAULT, description = "", required=true,schema=@Schema()) @RequestParam(value="metadata", required=true)  String metadata);


    @Operation(summary = "Deletes a pointcloud by ID", description = "", tags={ "pointclouds" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "204", description = "No Content"),
        
        @ApiResponse(responseCode = "400", description = "Bad Request"),
        
        @ApiResponse(responseCode = "401", description = "User Credentials are missing or invalid"),
        
        @ApiResponse(responseCode = "404", description = "Spatial Unit not found") })
    @RequestMapping(value = "/pointclouds/{id}",
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deletePointcloudById(@Min(1)@Parameter(in = ParameterIn.PATH, description = "The pointcloud ID", required=true, schema=@Schema(allowableValues={  }, minimum="1"
)) @PathVariable("id") Integer id);


    @Operation(summary = "Get a pointcloud LAZ file by ID", description = "", tags={ "pointclouds" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/octet-stream", schema = @Schema(implementation = Resource.class))) })
    @RequestMapping(value = "/pointclouds/{id}",
        produces = { "application/octet-stream" }, 
        method = RequestMethod.GET)
    ResponseEntity<Resource> getPointcloudById(@Min(1)@Parameter(in = ParameterIn.PATH, description = "The pointcloud ID.", required=true, schema=@Schema(allowableValues={  }, minimum="1"
)) @PathVariable("id") Integer id);


    @Operation(summary = "Get all existing pointclouds metadata and raster", description = "", tags={ "pointclouds" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Ok", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = PointcloudMetadata.class)))) })
    @RequestMapping(value = "/pointclouds",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<List<PointcloudMetadata>> getPointclouds();


    @Operation(summary = "Updates a pointcloud by replacing the dataset with the new feature data", description = "", tags={ "pointclouds" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "204", description = "No Content"),
        
        @ApiResponse(responseCode = "400", description = "Bad Request"),
        
        @ApiResponse(responseCode = "401", description = "User Credentials are missing or invalid"),
        
        @ApiResponse(responseCode = "404", description = "pointcloud not found") })
    @RequestMapping(value = "/pointclouds/{id}",
        consumes = { "multipart/form-data" }, 
        method = RequestMethod.PUT)
    ResponseEntity<Void> replacePointcloudById(@Min(1)@Parameter(in = ParameterIn.PATH, description = "The pointcloud ID", required=true, schema=@Schema(allowableValues={  }, minimum="1"
)) @PathVariable("id") Integer id, @Parameter(description = "file detail") @Valid @RequestPart("file") MultipartFile file, @Parameter(in = ParameterIn.DEFAULT, description = "", required=true,schema=@Schema()) @RequestParam(value="metadata", required=true)  PointcloudMetadata metadata);


    @Operation(summary = "Updates a pointcloud metadata set by replacing the metadata contents", description = "", tags={ "pointclouds" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "204", description = "No Content"),
        
        @ApiResponse(responseCode = "400", description = "Bad Request"),
        
        @ApiResponse(responseCode = "401", description = "User Credentials are missing or invalid"),
        
        @ApiResponse(responseCode = "404", description = "pointcloud not found") })
    @RequestMapping(value = "/pointclouds/{id}",
        consumes = { "application/json" }, 
        method = RequestMethod.PATCH)
    ResponseEntity<Void> replacePointcloudMetadataById(@Min(1)@Parameter(in = ParameterIn.PATH, description = "The pointcloud id", required=true, schema=@Schema(allowableValues={  }, minimum="1"
)) @PathVariable("id") Integer id, @Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody PointcloudMetadata body);

}
