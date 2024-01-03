package Classes;

import Classes.Division;
import Main.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;

/**
 * This class contains functions that retrieve Division data from the database to be used by the application.
 */
public class DivisionDB {
    public static ObservableList<Division> getAllDivisions() {
        ObservableList<Division> divisionList = FXCollections.observableArrayList();
        Connection connection = JDBC.getConnection();

        try {
            String sql = "SELECT * FROM client_schedule.first_level_divisions";
            JDBC.makePreparedStatement(sql, connection);
            PreparedStatement statement = JDBC.getPreparedStatement();
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                int divisionId = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
                String createdBy = rs.getString("Created_By");
                LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int divCountryId = rs.getInt("Country_ID");

                Division foundDivision = new Division(divisionId, divisionName, createDate, createdBy, lastUpdate, lastUpdatedBy, divCountryId);
                divisionList.add(foundDivision);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return divisionList;
    }
    /**
     * This function retrieves the Divisions from a specific Country.
     * @param country The Country to retrieve the Division data from.
     * @return Returns an ObservableList of the Divisions based on the country parameter.
     */
    public static ObservableList<Division> getDivisionsFromCountry(Country country) {
        ObservableList<Division> divisionList = FXCollections.observableArrayList();
        int countryId = country.getCountryId();
        Connection connection = JDBC.getConnection();
        String sql = "SELECT * FROM client_schedule.first_level_divisions WHERE Country_ID = '" + countryId + "';";
        try{
            JDBC.makePreparedStatement(sql, connection);
            PreparedStatement statement = JDBC.getPreparedStatement();
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                int divisionId = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
                String createdBy = rs.getString("Created_By");
                LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int divCountryId = rs.getInt("Country_ID");

                Division foundDivision = new Division(divisionId, divisionName, createDate, createdBy, lastUpdate, lastUpdatedBy, divCountryId);
                divisionList.add(foundDivision);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return divisionList;
    }

    public static Division getDivisionFromCustomer(Customer customer) {
        ObservableList<Division> divisionList;
        divisionList = getAllDivisions();
        int divisionId;
        divisionId = customer.getDivisionId();
        Division foundDivision = null;

        for (Division division : divisionList) {
            if (divisionId == division.getDivisionId()) {
                foundDivision = division;
            }
        }
        return foundDivision;
    }
}
