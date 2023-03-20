package io.swagger.api.impl;

import io.swagger.api.PointcloudsApiController;
import io.swagger.model.PointcloudMetadata;
import io.swagger.model.PointcloudMetadataProjection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class PythonExecutor {

    private static final Logger log = LoggerFactory.getLogger(PointcloudsApiController.class);

    private PointcloudManager pcManager;
    private Database database;
    private Geoserver geoserver;

    public PythonExecutor(PointcloudManager pcManager, Database database, Geoserver geoserver) {
        this.pcManager = pcManager;
        this.database = database;
        this.geoserver = geoserver;
    }

    public PythonExecutor(PointcloudManager pcManager, Database database) {
        this.pcManager = pcManager;
        this.database = database;
    }

    public PythonExecutor(PointcloudManager pcManager, Geoserver geoserver) {
        this.pcManager = pcManager;
        this.geoserver = geoserver;
    }

    public PointcloudMetadata executePdalPostCommands(PointcloudMetadata metadataObj, int id) throws IOException {

//        CommandlineExecutor.initCommandLine(pcManager);

//        String activateCondaEnvCommand = "conda activate campus-3d-lidar";
        String activateCondaEnvCommand = "source " + pcManager.getCondaPath() + "activate && conda activate " + pcManager.getCondaEnvName();
        String runScriptCommand = " && python3 " + pcManager.getPythonScriptPostPath() +
                " -in " + pcManager.getTempFilePath() +
                " -odir " + pcManager.getTempDirPath() +
                " -db " + database.getDbParams() +
                " -gs " + geoserver.getGeoserverParams() +
                " -color " + pcManager.getColorFilePath() +
                " -id " + id +
                " -crs " + metadataObj.getProjection().getPosition();

        String command = activateCondaEnvCommand + runScriptCommand;



        List<String> results = CommandlineExecutor.exec(command, pcManager);
        PointcloudMetadataProjection proj = new PointcloudMetadataProjection();

        log.info(String.valueOf(results.size()));

        for (String info: results) {
            log.info(info);
        }

        if (results.size() == 3) {

            proj.setPosition(results.get(0));
            proj.setHeight(results.get(1));
            metadataObj.setBoundary(results.get(2));
        }

        if (results.size() == 2) {

            proj.setPosition(results.get(0));
            proj.setHeight("unknown");
            metadataObj.setBoundary(results.get(1));
        }

        metadataObj.setProjection(proj);
//        this.pcManager.setRasterFilePath(results.get(results.size()-1));

        log.info("successfully executed py-commands");

        return metadataObj;
    }

    public ByteArrayResource executePdalGetCommand(int id) throws IOException {


        String activateCondaEnvCommand = "source " + pcManager.getCondaPath() + "activate && conda activate " + pcManager.getCondaEnvName();
        String runScriptCommand = " && python3 " + pcManager.getPythonScriptGetPath() +
                " -odir " + pcManager.getTempDirPath() + File.separator +
                " -db " + database.getDbParams() +
                " -id " + id
                ;

        String command = activateCondaEnvCommand + runScriptCommand;
        List<String> results = CommandlineExecutor.exec(command, pcManager);
        for (String info: results) {
            log.info(info);
        }
        File file = new File(pcManager.getTempDirPath() + File.separator + "pc.laz");
        Path path = Paths.get(file.getAbsolutePath());

        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path)){
            @Override
            public String getFilename() {
                return "pointcloud_" + id + ".laz";
            }
        };

        return resource;
    }

    public String executeGeoserverDeleteCommand(int id) throws IOException {

        String activateCondaEnvCommand = "source " + pcManager.getCondaPath() + "activate && conda activate " + pcManager.getCondaEnvName();
        String runScriptCommand = " && python3 " + pcManager.getPythonScriptDeletePath() +
                " -gs " + geoserver.getGeoserverParams() +
                " -id " + id;

        String command = activateCondaEnvCommand + runScriptCommand;
        String result = CommandlineExecutor.exec(command, pcManager).get(0);

        return result;
    }

}