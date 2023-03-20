package io.swagger.api.impl.db.model;

import org.json.JSONObject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MeasureMethod {

    private static final String TABLENAME = "MeasureMethod";
    private int id;
    private String method;

    public MeasureMethod(int id, String method) {
        this.id = id;
        this.method = method;
    }

    public MeasureMethod(String method) {
        this.method = method;
    }

    public int getId() {
        return id;
    }

    public String getMethod() {
        return method;
    }

    @Override
    public boolean equals (Object object) {
        boolean result = false;
        if (object == null || object.getClass() != getClass()) {
            result = false;
        } else {
            MeasureMethod measureMethod = (MeasureMethod) object;
            if (this.method.equals(measureMethod.getMethod())) {
                result = true;
            }
        }
        return result;
    }

    public static List<MeasureMethod> execSelectAllStatement(Connection conn) {

        return execSelectAllStatement(conn, "");
    }

    public static List<MeasureMethod> execSelectAllStatement(Connection conn, String whereClause) {

        List<MeasureMethod> resultList = new ArrayList<>();
        String query = "select * from public.\"" + TABLENAME + "\"" + whereClause;

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)
        ) {
            while (rs.next()) {
                //Display values
                MeasureMethod measureMethod = new MeasureMethod(rs.getInt(1), rs.getString(2));
                resultList.add(measureMethod);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    public static String getMeasureMethodAsString(Connection conn, Integer id){

        List<MeasureMethod> measureMethods = execSelectAllStatement(conn, " where id = " + id.toString());
        return measureMethods.get(0).getMethod();
    }
}
