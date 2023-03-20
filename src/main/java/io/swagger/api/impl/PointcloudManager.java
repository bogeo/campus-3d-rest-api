package io.swagger.api.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class PointcloudManager {

    private MultipartFile file;
    private String fileName;
    private String rasterFilePath;

    @Value("${project.path.resources}")
    private String resourcesDir;

    @Value("${conda.path}")
    private String condaPath;

    @Value("${conda.env-name}")
    private String condaEnvName;

    String tempDirPath;

    String pythonScriptPostPath;
    String pythonScriptGetPath;
    String pythonScriptDeletePath;

    public void generatePaths(){

    this.tempDirPath = Paths.get(resourcesDir).normalize().toString();

    this.pythonScriptPostPath = Paths.get(resourcesDir, "python", "post.py").normalize().toString();
    this.pythonScriptGetPath = Paths.get(resourcesDir, "python", "get.py").normalize().toString();
    this.pythonScriptDeletePath = Paths.get(resourcesDir, "python", "delete.py").normalize().toString();

    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public void setFileName(Integer id) {
        this.fileName = "pointcloud_" + id.toString() + ".laz";
    }

    public void savePointcloudFile() throws IOException {
        File tempFile = new File(getTempFilePath());

        try (OutputStream os = new FileOutputStream(tempFile)) {
            os.write(file.getBytes());
        }
    }

    public void deleteTempPointcloudFiles() {
        for(File file: new File(tempDirPath).listFiles())
            if (!file.isDirectory())
                file.delete();
    }

    public String getTempFilePath(){
        return Paths.get(tempDirPath, fileName).normalize().toString();
    }

    public String getColorFilePath(){return resourcesDir + "colorfile.txt";}

    public String getPythonScriptPostPath(){return pythonScriptPostPath;}

    public String getPythonScriptGetPath(){return pythonScriptGetPath;}

    public String getPythonScriptDeletePath(){return pythonScriptDeletePath;}

    public String getTempDirPath() {
        return tempDirPath;
    }

    public String getCondaPath(){return condaPath;}

    public String getCondaEnvName() {
        return condaEnvName;
    }
}
