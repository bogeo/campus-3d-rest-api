package io.swagger.api.impl.db.model;

import org.json.JSONObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Metadata {

    private static final String TABLENAME = "Metadata";
    Integer mid;
    String name;
    Array accuracy;
    Integer expressionId;
    Integer measureMethodId;
    Array measureDate;
    Integer dataSourceId;
    Array attributeFieldId;
    String freetext;
    Integer projectionId;
    String license;
    String boundary;

    public Metadata(Integer mid, String name, Array accuracy, Integer expressionId, Integer measureMethodId, Array measureDate, Integer dataSourceId, Array attributeFieldId, String freetext, Integer projectionId, String license, String boundary) {
        this.mid = mid;
        this.name = name;
        this.accuracy = accuracy;
        this.expressionId = expressionId;
        this.measureMethodId = measureMethodId;
        this.measureDate = measureDate;
        this.dataSourceId = dataSourceId;
        this.attributeFieldId = attributeFieldId;
        this.freetext = freetext;
        this.projectionId = projectionId;
        this.license = license;
        this.boundary = boundary;
    }

    public Integer getMid() {
        return mid;
    }

    public String getName() {
        return name;
    }

    public JSONObject getAccuracyAsJSON() throws SQLException {

        JSONObject result = new JSONObject();
        Integer[] accuracyIntArr = (Integer[]) accuracy.getArray();
        result.put("position", accuracyIntArr[0]);
        result.put("height", accuracyIntArr[1]);

        return result;
    }

    public Integer getExpressionId() {
        return expressionId;
    }

    public Integer getMeasureMethodId() {
        return measureMethodId;
    }

    public JSONObject getMeasureDateAsJSON() throws SQLException {

        JSONObject result = new JSONObject();
        Date[] measureDateDateArr = (Date[]) measureDate.getArray();
        result.put("startDate", Date.valueOf(String.valueOf(measureDateDateArr[0])));
        result.put("endDate", Date.valueOf(String.valueOf(measureDateDateArr[1])));

        return result;
    }

    public Integer getDataSourceId() {
        return dataSourceId;
    }

    public Short[] getAttributeFieldIds() throws SQLException {
        return (Short[]) attributeFieldId.getArray();
    }

    public String getFreetextAsString() {
        return freetext;
    }

    public Integer getProjectionId() {
        return projectionId;
    }

    public String getLicenseAsString() {
        return license;
    }

    public String getBoundaryAsString() {
        return boundary;
    }

    public static List<Metadata> execSelectAllStatement(Connection conn) {

        return execSelectAllStatement(conn, "");
    }

    public static List<Metadata> execSelectAllStatement(Connection conn, String whereClause) {

        List<Metadata> resultList = new ArrayList<>();
        String query = "select * from public.\"" + TABLENAME + "\"" + whereClause;

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)
        ) {
            while (rs.next()) {
                //Display values
                Metadata metadata = new Metadata(rs.getInt(1), rs.getString(2), rs.getArray(3),
                        rs.getInt(4), rs.getInt(5), rs.getArray(6),
                        rs.getInt(7), rs.getArray(8), rs.getString(9),
                        rs.getInt(10), rs.getString(11), rs.getString(12));
                resultList.add(metadata);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    public static Integer execSelectCountStatement(Connection conn, String column, Integer id) {

        Integer result = null;
        String query = "select count(" + column + ") from public.\"" + TABLENAME + "\" where " + column + " = " + id.toString();

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)
        ) {
            while (rs.next()) {
                //Display values
                result = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void execDeleteStatement(Connection conn, Integer id) {

        String query = "delete from public.\"" + TABLENAME + "\" where mid = " + id.toString();

        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
