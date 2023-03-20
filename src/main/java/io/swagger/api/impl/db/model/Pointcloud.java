package io.swagger.api.impl.db.model;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Pointcloud {

    public static void execDeleteStatement(Connection conn, Integer id) {

        String query = "delete from pointcloud.\"pointcloud_" + id + "\"";

        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
