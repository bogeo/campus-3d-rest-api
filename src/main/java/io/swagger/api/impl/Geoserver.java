package io.swagger.api.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

@Component
public class Geoserver {

    @Value("${geoserver.url}")
    private String url;
    @Value("${geoserver.user}")
    private String admin;
    @Value("${geoserver.password}")
    private String password;

    public String getGeoserverParams(){
        return url + "," + admin + "," + password;
    }

    public String getUrl() {
        return url;
    }
}
