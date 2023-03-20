package io.swagger.api.impl.db.query;

import io.swagger.api.impl.db.model.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseReader {

    public static List<String> getColumnNames(String tableName, Connection conn) {

        List<String> columnNames = new ArrayList<String>();

        String query = "SELECT COLUMN_NAME\n" +
                "FROM INFORMATION_SCHEMA.COLUMNS\n" +
                "WHERE TABLE_NAME = '"+ tableName + "'\n" +
                "ORDER BY ORDINAL_POSITION";

        try(Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

        ) {

            while(rs.next()){
                //Display values
                columnNames.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return columnNames;
    }

    protected static List<String> getTableNames() throws IOException {

        StringBuilder sb = new StringBuilder();
        InputStream is = DatabaseReader.class.getClassLoader().getResourceAsStream("json/tables.json");
        BufferedReader r = new BufferedReader(new InputStreamReader(is));

        String line;
        while((line=r.readLine()) !=null){
            sb.append(line);
        }

        String jsonString = sb.toString();

        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray tableNamesArray = (JSONArray) jsonObject.get("tableNames");

        ArrayList<String> tableNames = new ArrayList<String>();
        if (tableNamesArray != null) {
            for (int i=0;i<tableNamesArray.length();i++){
                tableNames.add(tableNamesArray.getString(i));
            }
        }

        return tableNames;

    }

    public static List execSelectMetadataStatement(Connection conn) throws SQLException {

        List<Integer> resultList = new ArrayList<Integer>();

        String query = "select mid from public.\"Metadata\"";

        try(Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

        ) {

            while(rs.next()){
                int metadataId = rs.getInt(1);
                resultList.add(metadataId);
            }

        }


        return resultList;
    }

//    public static List execSelectAllStatement(String tableName, Connection conn, List resultList){
//
//        String query = "select * from public.\"" + tableName + "\"";
//
//        try(Statement stmt = conn.createStatement();
//            ResultSet rs = stmt.executeQuery(query);
//
//        ) {
//
//            while(rs.next()){
//                //Display values
//                if(tableName == "Address"){
//                    Address address = new Address(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
//                    resultList.add(address);
//                }
//                if(tableName == "DataSource"){
//                    DataSource dataSource = new DataSource(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
//                    resultList.add(dataSource);
//                }
//                if(tableName == "Expression"){
//                    Integer pointDense[] = (Integer[]) rs.getArray(3).getArray();
//                    Expression expression = new Expression(rs.getInt(1), rs.getString(2), pointDense[0], pointDense[1]);
//                    resultList.add(expression);
//                }
//                if(tableName == "AttributeField"){
//                    AttributeField attributeField = new AttributeField(rs.getInt(1), rs.getString(2));
//                    resultList.add(attributeField);
//                }
//                if(tableName == "Projection"){
//                    Projection projection = new Projection(rs.getInt(1), rs.getString(2), rs.getString(3));
//                    resultList.add(projection);
//                }
//                if(tableName == "MeasureMethod"){
//                    MeasureMethod measureMethod = new MeasureMethod(rs.getInt(1), rs.getString(2));
//                    resultList.add(measureMethod);
//                }
//
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return resultList;
//
//    }
}
