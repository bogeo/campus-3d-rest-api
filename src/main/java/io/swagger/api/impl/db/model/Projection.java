package io.swagger.api.impl.db.model;

import org.json.JSONObject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Projection {

    private static final String TABLENAME = "Projection";
    private int id;
    private String position;
    private String height;

    public Projection(int id, String position, String height) {
        this.id = id;
        this.position = position;
        this.height = height;
    }

    public Projection(String position, String height) {
        this.position = position;
        this.height = height;
    }

    public int getId() {
        return id;
    }

    public String getPosition() {
        return position;
    }

    public String getHeight() {
        return height;
    }

    @Override
    public boolean equals (Object object) {
        boolean result = false;
        if (object == null || object.getClass() != getClass()) {
            result = false;
        } else {
            Projection projection = (Projection) object;
            if (this.position.equals(projection.position) && this.height.equals(projection.height)) {
                result = true;
            }
        }
        return result;
    }

    public static List<Projection> execSelectAllStatement(Connection conn, String whereClause) {

        List<Projection> resultList = new ArrayList<>();
        String query = "select * from public.\"" + TABLENAME + "\"" + whereClause;

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)
        ) {
            while (rs.next()) {
                //Display values
                Projection projection = new Projection(rs.getInt(1), rs.getString(2), rs.getString(3));
                resultList.add(projection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    public static List<Projection> execSelectAllStatement(Connection conn) {

        return execSelectAllStatement(conn, "");

    }

    public static JSONObject getProjectionAsJSON(Connection conn, Integer id){
        JSONObject result = new JSONObject();

        List<Projection> projections = execSelectAllStatement(conn, " where id = " + id.toString());
        Projection projection = projections.get(0);

        result.put("position", projection.getPosition());
        result.put("height", projection.getHeight());

        return result;
    }

    public static void execDeleteStatement(Connection conn, Integer id) {

        String query = "delete from public.\"" + TABLENAME + "\" where id = " + id.toString();

        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
