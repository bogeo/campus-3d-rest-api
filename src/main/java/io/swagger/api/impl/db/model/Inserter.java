package io.swagger.api.impl.db.model;

import io.swagger.api.impl.db.query.DatabaseReader;
import io.swagger.api.impl.db.query.QueryMetadata;
import io.swagger.model.PointcloudMetadata;
import io.swagger.model.PointcloudMetadataDataSource;
import io.swagger.model.PointcloudMetadataDataSourceAddress;
import org.threeten.bp.*;

import java.util.*;

import java.sql.*;

public class Inserter {

    public static void insertMetadata(PreparedStatement statement, PointcloudMetadata metadataObj, Integer pointcloudId, Connection conn){

        try {
            // metadata id
            statement.setInt(1, pointcloudId);
            // name
            statement.setString(2, metadataObj.getName());
            // accuracy
            Array accuracy = conn.createArrayOf("INTEGER", new Integer[]{metadataObj.getAccuracy().getPosition(),metadataObj.getAccuracy().getHeight()});
            statement.setArray(3, accuracy);

            // expression
            int expressionId = getExpressionId(metadataObj, conn, "Expression");
            statement.setInt(4, expressionId);

            // measure method
            int measureMethodId = getMeasureMethodId(metadataObj, conn, "MeasureMethod");
            statement.setInt(5, measureMethodId);

            // measure date
            Array measureDate = getMeasureDate(metadataObj, conn);
            statement.setArray(6, measureDate);

            // data source
            int dataSourceId = getDataSourceId(metadataObj, conn, "DataSource");
            statement.setInt(7, dataSourceId);
            // attribute field
            List<Integer> attributeFieldIds = getAttributeFieldId(metadataObj, conn);
            Array attributeFieldId = conn.createArrayOf("INTEGER", attributeFieldIds.toArray());
            statement.setArray(8, attributeFieldId);
            // freetext
            String freetext = metadataObj.getFreeText();
            statement.setString(9, freetext);
            // projection
            int projectionId = getProjectionId(metadataObj, conn, "Projection");
            statement.setInt(10, projectionId);
            // license
            String license = metadataObj.getLicense();
            statement.setString(11, license);
            // boundary
            String boundary = metadataObj.getBoundary();
            statement.setString(12, boundary);


            statement.executeUpdate();



        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static int getExpressionId(PointcloudMetadata metadataObj, Connection conn, String tableName){
        String kind = String.valueOf(metadataObj.getExpression().getKind());
        int pointDenseMin = metadataObj.getExpression().getPointDense().getMin();
        int pointDenseMax = metadataObj.getExpression().getPointDense().getMax();

        Expression newExpression = new Expression(kind, pointDenseMin, pointDenseMax);

        // get expressions contained in database
        List<Expression> expressions = Expression.execSelectAllStatement(conn);
        int newId = 0;

        // if expression is not in db, add it to db
        if(!expressions.contains(newExpression)){

            // get new id
            if (expressions.size() > 0){
                int currentId = 0;
                for (Expression expression: expressions) {
                    if(expression.getId() > currentId){
                        currentId = expression.getId();
                    }
                }
                newId = currentId +1;
            }
            else {
                newId = 1;
            }

            List<String> columnNames = DatabaseReader.getColumnNames(tableName, conn);
            String query = QueryMetadata.buildInsertQuery(tableName, columnNames);

            try (PreparedStatement statement = conn.prepareStatement(query))
            {
                statement.setInt(1, newId);
                statement.setString(2, newExpression.getKind());
                Array accuracy = conn.createArrayOf("INTEGER", new Integer[]{newExpression.getPointDenseMin(), newExpression.getPointDenseMax()});
                statement.setArray(3, accuracy);

                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        else{
            for (Expression expression: expressions) {
                if(expression.equals(newExpression)){
                    newId = expression.getId();
                    return newId;
                }
            }
        }

        return newId;
    }

    private static int getDataSourceId(PointcloudMetadata metadataObj, Connection conn, String tableName){


        String institution = metadataObj.getDataSource().getInstitution();
        String contactName = metadataObj.getDataSource().getName();
        String mail = metadataObj.getDataSource().getMail();
        insertAddress(metadataObj, conn, "Address");
        DataSource newDataSource = new DataSource(institution, contactName, mail);

        // get data sources contained in database
        List<DataSource> dataSources = DataSource.execSelectAllStatement(conn);
        int newId = 0;
        // if dataSource is not in db, add it to db
        if(!dataSources.contains(newDataSource)){

            // get new id

            if (dataSources.size() > 0){
                int currentId = 0;
                for (DataSource dataSource: dataSources) {
                    if(dataSource.getId() > currentId){
                        currentId = dataSource.getId();
                    }
                }
                newId = currentId +1;
            }
            else {
                newId = 1;
            }

            List<String> columnNames = DatabaseReader.getColumnNames(tableName, conn);
            String query = QueryMetadata.buildInsertQuery(tableName, columnNames);

            try (PreparedStatement statement = conn.prepareStatement(query))
            {
                statement.setInt(1, newId);
                statement.setString(2, newDataSource.getInstitution());
                statement.setString(3, newDataSource.getContactName());
                statement.setString(4, newDataSource.getMail());

                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        else{
            for (DataSource dataSource: dataSources) {
                if(dataSource.equals(newDataSource)){
                    newId = dataSource.getId();
                    return newId;
                }
            }
        }

        return newId;
    }

    protected static int getMeasureMethodId(PointcloudMetadata metadataObj, Connection conn, String tableName){

        String method = metadataObj.getMeasureMethod().toString();

        MeasureMethod newMeasureMethod = new MeasureMethod(method);

        // get measure methods contained in database
        List<MeasureMethod> measureMethods = MeasureMethod.execSelectAllStatement(conn);
        int newId = 0;

        if(!measureMethods.contains(newMeasureMethod)){

        // get new id
        if (measureMethods.size() > 0){
            int currentId = 0;
            for (MeasureMethod measureMethod: measureMethods) {
                if(measureMethod.getId() > currentId){
                    currentId = measureMethod.getId();
                }
            }
            newId = currentId +1;
        }
        else {
            newId = 1;
        }

        List<String> columnNames = DatabaseReader.getColumnNames(tableName, conn);
        String query = QueryMetadata.buildInsertQuery(tableName, columnNames);

        try (PreparedStatement statement = conn.prepareStatement(query))
        {
            statement.setInt(1, newId);
            statement.setString(2, newMeasureMethod.getMethod());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

        else{
        for (MeasureMethod measureMethod: measureMethods) {
            if(measureMethod.getMethod().equals(newMeasureMethod.getMethod())){
                newId = measureMethod.getId();
                return newId;
            }
        }
    }

        return newId;
    }

    public static void insertAddress(PointcloudMetadata metadataObj, Connection conn, String tableName){
        PointcloudMetadataDataSource dataSource = metadataObj.getDataSource();
        PointcloudMetadataDataSourceAddress address = dataSource.getAddress();
        Address newAddress = new Address(dataSource.getInstitution(), address.getStreet(), address.getHno(), address.getZipCode(), address.getCountry());

        // get addresses contained in database
        List<Address> addresses = Address.execSelectAllStatement(conn);

        // if address is not in db, add it to db
        if (!addresses.contains(newAddress)){
            List<String> columnNames = DatabaseReader.getColumnNames(tableName, conn);
            String query = QueryMetadata.buildInsertQuery(tableName, columnNames);

            try (PreparedStatement statement = conn.prepareStatement(query))
            {
                statement.setString(1, newAddress.getInstitution());
                statement.setString(2, newAddress.getStreet());
                statement.setString(3, newAddress.getHouseNumber());
                statement.setString(4, newAddress.getZipCode());
                statement.setString(5, newAddress.getCountry());

                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    protected static List<Integer> getAttributeFieldId(PointcloudMetadata metadataObj, Connection conn){

        List<AttributeField> newAttributeFields = new ArrayList<>();
        for (PointcloudMetadata.AttributeFieldsEnum attributeField:metadataObj.getAttributeFields()) {
            newAttributeFields.add(new AttributeField(attributeField.toString()));
        }

        // get attributeFields contained in database
        List<AttributeField> attributeFields = AttributeField.execSelectAllStatement(conn);
        List<Integer> newIds = new ArrayList<>();

            for (AttributeField attributeField: attributeFields) {
                if(newAttributeFields.contains(attributeField)){
                    newIds.add(attributeField.getId());
                }
            }

        return newIds;
    }
    
    private static Array getMeasureDate(PointcloudMetadata metadataObj, Connection conn) throws SQLException {
        LocalDate startDate = metadataObj.getMeasureDate().getStartDate();
        LocalDate endDate = metadataObj.getMeasureDate().getEndDate();

        Instant startDateInstant = startDate.atStartOfDay(ZoneOffset.UTC).toInstant();
        java.sql.Date startDateSql = new java.sql.Date(startDateInstant.toEpochMilli());

        Instant endDateInstant = endDate.atStartOfDay(ZoneOffset.UTC).toInstant();
        java.sql.Date endDateSql = new java.sql.Date(endDateInstant.toEpochMilli());

        return conn.createArrayOf("Date", new java.sql.Date[]{startDateSql, endDateSql});

    }

    private static int getProjectionId(PointcloudMetadata metadataObj, Connection conn, String tableName){

        String position = metadataObj.getProjection().getPosition();
        String height = metadataObj.getProjection().getHeight();

        Projection newProjection = new Projection(position, height);

        // get projections contained in database
        List<Projection> projections = Projection.execSelectAllStatement(conn);
        int newId = 0;
        // if projection is not in db, add it to db
        if(!projections.contains(newProjection)){

            // get new id
            if (projections.size() > 0){
                int currentId = 0;
                for (Projection projection: projections) {
                    if(projection.getId() > currentId){
                        currentId = projection.getId();
                    }
                }
                newId = currentId +1;
            }
            else {
                newId = 1;
            }

            List<String> columnNames = DatabaseReader.getColumnNames(tableName, conn);
            String query = QueryMetadata.buildInsertQuery(tableName, columnNames);

            try (PreparedStatement statement = conn.prepareStatement(query))
            {
                statement.setInt(1, newId);
                statement.setString(2, newProjection.getPosition());
                statement.setString(3, newProjection.getHeight());

                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        else{
            for (Projection projection: projections) {
                if(projection.equals(newProjection)){
                    newId = projection.getId();
                    return newId;
                }
            }
        }

        return newId;
    }

}
