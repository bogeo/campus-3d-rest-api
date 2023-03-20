package io.swagger.api.impl.db.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AttributeField {

    private static final String TABLENAME = "AttributeField";
    private int id;
    private String attributeField;

    public AttributeField(String attributeField) {
        this.attributeField = attributeField;
    }

    public AttributeField(int id, String attributeField) {
        this.id = id;
        this.attributeField = attributeField;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAttributeField() {
        return attributeField;
    }

    public void setAttributeField(String attributeField) {
        this.attributeField = attributeField;
    }

    @Override
    public boolean equals (Object object) {
        boolean result = false;
        if (object == null || object.getClass() != getClass()) {
            result = false;
        } else {
            AttributeField attributeField = (AttributeField) object;
            if (this.attributeField.equals(attributeField.getAttributeField())){
                result = true;
            }
        }
        return result;
    }

    public static List<AttributeField> execSelectAllStatement(Connection conn, String whereClause) {

        List<AttributeField> resultList = new ArrayList<>();
        String query = "select * from public.\"" + TABLENAME + "\"" + whereClause;

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query);

        ) {

            while (rs.next()) {
                //Display values
                AttributeField attributeField = new AttributeField(rs.getInt(1), rs.getString(2));
                resultList.add(attributeField);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;

    }

    public static List<AttributeField> execSelectAllStatement(Connection conn) {

        return execSelectAllStatement(conn, "");
    }

    public static JSONArray getAttributeFieldAsJSON(Connection conn, Short[] ids){

        JSONArray result = new JSONArray();

        String whereClause = "where \"ID\" = ";

        if(ids.length > 0){

            for (int i=1; i<=ids.length; i++) {

                if(i < ids.length){
                    whereClause = whereClause + ids[i-1].toString() + " or \"ID\" = ";
                }
                if(i == ids.length){
                    whereClause = whereClause + ids[i-1];
                }
            }

            List<AttributeField> attributeFields = execSelectAllStatement(conn, whereClause);

            for (AttributeField attributeField:attributeFields) {
                result.put(attributeField.getAttributeField());
            }
        }

        return result;
    }
}
