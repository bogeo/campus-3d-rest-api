package io.swagger.api.impl;

import io.swagger.api.PointcloudsApiController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CommandlineExecutor {

    private static final Logger log = LoggerFactory.getLogger(PointcloudsApiController.class);

    public CommandlineExecutor(){
    }

    public static void initCommandLine(PointcloudManager pcManager) throws IOException {

        ProcessBuilder builder;

        String OS = System.getProperty("os.name").toLowerCase();

        String command = "cd" + pcManager.getCondaPath() + " && source activate && conda activate " + pcManager.getCondaEnvName();

        log.info("execute " + command);

        if (OS.contains("win")) {
            builder = new ProcessBuilder("cmd.exe", "/c", command);
        } else {
            builder = new ProcessBuilder("bash", "-c", command);
        }
        builder.redirectErrorStream(true);
        Process p = builder.start();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()))) {

            String line;

            while ((line = reader.readLine()) != null) {
                log.info(line);
            }
        } catch (
                IOException e) {
            e.printStackTrace();
        }


    }

    public static List<String> exec(String command, PointcloudManager pcManager) throws IOException {
        
        List<String> results = new ArrayList<String>();

        ProcessBuilder builder;

        String OS = System.getProperty("os.name").toLowerCase();


        log.info("execute " + command);

        if (OS.contains("win")) {
            builder = new ProcessBuilder("cmd.exe", "/c", command);
        } else {
            builder = new ProcessBuilder( "sudo", "bash", "-c", command);
        }
        builder.redirectErrorStream(true);
        Process p = builder.start();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()))) {

            String line;

            while ((line = reader.readLine()) != null) {
                results.add(line);
            }
        } catch (
                IOException e) {
            e.printStackTrace();
        }

        return results;
    }

}
