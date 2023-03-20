package io.swagger.api.impl.db.model;

import org.json.JSONObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Address {

    private static final String TABLENAME = "Address";
    private String institution;
    private String street;
    private String houseNumber;
    private String zipCode;
    private String country;

    public Address(String institution, String street, String houseNumber, String zipCode, String country) {
        this.institution = institution;
        this.street = street;
        this.houseNumber = houseNumber;
        this.zipCode = zipCode;
        this.country = country;
    }


    public String getInstitution() {
        return institution;
    }

    public String getStreet() {
        return street;
    }


    public String getHouseNumber() {
        return houseNumber;
    }


    public String getZipCode() {
        return zipCode;
    }


    public String getCountry() {
        return country;
    }

    @Override
    public boolean equals(Object object) {
        boolean result = false;
        if (object != null && object.getClass() == getClass()) {
            Address address = (Address) object;
            if (this.institution.equals(address.getInstitution()) &&
                    this.street.equals(address.getStreet()) &&
                    this.houseNumber.equals(address.getHouseNumber()) &&
                    this.zipCode.equals(address.getZipCode()) &&
                    this.country.equals(address.getCountry())) {
                result = true;
            }
        }
        return result;
    }

    public static List<Address> execSelectAllStatement(Connection conn, String whereClause) {

        List<Address> resultList = new ArrayList<>();
        String query = "select * from public.\"" + TABLENAME + "\"" + whereClause;

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query))
        {
            while (rs.next()) {
                //Display values
                Address address = new Address(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
                resultList.add(address);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;

    }

    public static List<Address> execSelectAllStatement(Connection conn) {

        return execSelectAllStatement(conn, "");

    }

    public static JSONObject getAddressAsJSON(Connection conn, String institution){
        JSONObject result = new JSONObject();

        List<Address> addresses = execSelectAllStatement(conn, " where institution = '" + institution + "'");
        Address address = addresses.get(0);

        result.put("street", address.getStreet());
        result.put("hno", address.getHouseNumber());
        result.put("zipCode", address.getZipCode());
        result.put("country", address.getCountry());

        return result;
    }

    public static void execDeleteStatement(Connection conn, String institution) {

        String query = "delete from public.\"" + TABLENAME + "\" where institution = '" + institution + "'";

        Statement stmt;
        try {
            stmt = conn.createStatement();
            stmt.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

}
