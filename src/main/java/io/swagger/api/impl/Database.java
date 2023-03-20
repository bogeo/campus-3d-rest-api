package io.swagger.api.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class Database{



    @Value("${dbconnection.host}")
    private String host;

    @Value("${dbconnection.name}")
    String name;

    @Value("${dbconnection.user}")
    String user;

    @Value("${dbconnection.password}")
    String password;

    @Value("${dbconnection.port}")
    String port;

    public String getDbParams(){return host + "," + name +  "," + user +  "," + password +  "," + port;}

    public String getHost() {
        return host;
    }

    public String getName() {
        return name;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getPort() {
        return port;
    }

}
