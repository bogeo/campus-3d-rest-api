package io.swagger.api.impl.db.query;

import io.swagger.api.PointcloudsApiController;
import io.swagger.api.impl.Database;
import io.swagger.api.impl.db.model.Inserter;
import io.swagger.model.PointcloudMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.*;
import java.util.List;

import static io.swagger.api.impl.db.query.DatabaseReader.getColumnNames;

public class QueryMetadata {

    private static final Logger log = LoggerFactory.getLogger(PointcloudsApiController.class);

    private String url;// = "jdbc:postgresql://localhost:5432/campus_3d";
    private String user;
    private String password;
    Connection conn;

    private PointcloudMetadata metadataObj;
    private Integer pointcloudId;

    public QueryMetadata(Database db, Integer pointcloudId) throws SQLException, IOException {
        this.pointcloudId = pointcloudId;

        this.url = "jdbc:postgresql://" + db.getHost() + ":" + db.getPort() + "/" + db.getName();
        this.user = db.getUser();
        this.password = db.getPassword();

        log.info("connecting to " + this.url + " as " + this.user);
        this.conn = DriverManager.getConnection(url, user, password);
    }

    public QueryMetadata(Database db) throws SQLException, IOException {

        this.url = "jdbc:postgresql://" + db.getHost() + ":" + db.getPort() + "/" + db.getName();
        this.user = db.getUser();
        this.password = db.getPassword();
        this.conn = DriverManager.getConnection(url, user, password);
    }

    public Connection getConn() {
        return conn;
    }

    public void setMetadataObj(PointcloudMetadata metadataObj) {
        this.metadataObj = metadataObj;
    }

    public Boolean checkIdExistence() throws SQLException {

        List occupiedIds = DatabaseReader.execSelectMetadataStatement(conn);
        return occupiedIds.contains(pointcloudId);
    }

    public void insertMetadata() throws SQLException, IOException {

        String tableName = "Metadata";
        List<String> columnNames = getColumnNames(tableName, conn);
        String query = buildInsertQuery(tableName, columnNames);

        try (PreparedStatement statement = conn.prepareStatement(query);) {

            Inserter.insertMetadata(statement, this.metadataObj, pointcloudId, conn);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

//        }

    }

    public static String buildInsertQuery(String tableName, List<String> columnNames){

        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO public.\"" + tableName + "\"(");
        for (String columnName: columnNames){
            sb.append(columnName);
            sb.append(", ");
        }
        sb.replace(sb.length()-2, sb.length(), "");

        sb.append(") VALUES " + "(");
        for (int i=0; i<columnNames.size()-1; i++){
            sb.append("?, ");
        }
        sb.append("?);");

        String query = sb.toString();

        return query;

    }

}
