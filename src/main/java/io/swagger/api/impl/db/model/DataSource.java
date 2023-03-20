package io.swagger.api.impl.db.model;

import org.json.JSONObject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DataSource {

    private static final String TABLENAME = "DataSource";
    private int id;
    private String institution;
    private String contactName;
    private String mail;

    public DataSource(int id, String institution, String contactName, String mail) {
        this.id = id;
        this.institution = institution;
        this.contactName = contactName;
        this.mail = mail;
    }

    public DataSource(String institution, String contactName, String mail) {
        this.institution = institution;
        this.contactName = contactName;
        this.mail = mail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInstitution() {
        return institution;
    }

    public String getContactName() {
        return contactName;
    }

    public String getMail() {
        return mail;
    }

    @Override
    public boolean equals (Object object) {
        boolean result = false;
        if (object == null || object.getClass() != getClass()) {
            result = false;
        } else {
            DataSource dataSource = (DataSource) object;
            if (this.institution.equals(dataSource.getInstitution()) &&
                    this.contactName.equals(dataSource.getContactName()) &&
                    this.mail.equals(dataSource.getMail())){
                result = true;
            }
        }
        return result;
    }

    public static List<DataSource> execSelectAllStatement(Connection conn, String whereClause) {

        List<DataSource> resultList = new ArrayList<>();
        String query = "select * from public.\"" + TABLENAME + "\"" + whereClause;

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query);

        ) {

            while (rs.next()) {
                //Display values
                DataSource dataSource = new DataSource(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
                resultList.add(dataSource);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;

    }

    public static List<DataSource> execSelectAllStatement(Connection conn) {

        return execSelectAllStatement(conn, "");

    }

    public static JSONObject getDataSourceAsJSON(Connection conn, Integer id){
        JSONObject result = new JSONObject();

        List<DataSource> dataSources = execSelectAllStatement(conn, " where id = " + id.toString());
        DataSource dataSource = dataSources.get(0);

        result.put("institution", dataSource.getInstitution());
        result.put("name", dataSource.getContactName());
        result.put("address", Address.getAddressAsJSON(conn, dataSource.getInstitution()));
        result.put("mail", dataSource.getMail());

        return result;
    }

    public static Integer execSelectCountStatement(Connection conn, String column, String institution) {

        Integer result = null;
        String query = "select count(" + column + ") from public.\"" + TABLENAME + "\" where " + column + " = '" + institution + "'";

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

        String institution = (execSelectAllStatement(conn, " where id = " + id.toString())).get(0).getInstitution();
        Integer dataSourceInstitutionCount = execSelectCountStatement(conn, "institution", institution);

        if(dataSourceInstitutionCount == 1){
            Address.execDeleteStatement(conn, institution);
        }


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
