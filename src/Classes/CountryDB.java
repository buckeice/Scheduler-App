package Classes;

import Main.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

/**
 * This class contains functions that retrieve Country data from the database to be used by the application.
 */
public class CountryDB {
    /**
     * Gets all Countries and returns them as an ObservableList.
     * @return Returns and ObservableList of all countries.
     */
    public static ObservableList<Country> getAllCountries() {
        ObservableList<Country> countryList = FXCollections.observableArrayList();
        Connection connection = JDBC.getConnection();

        try {
         String sql = "SELECT * FROM client_schedule.countries;";
         JDBC.makePreparedStatement(sql, connection);
         PreparedStatement statement = JDBC.getPreparedStatement();
         ResultSet rs = statement.executeQuery();

         while (rs.next()) {
             int countryId = rs.getInt("Country_ID");
             String countryName = rs.getString("Country");
             Timestamp createDate = rs.getTimestamp("Create_Date");
             String createdBy = rs.getString("Created_By");
             Timestamp lastUpdate = rs.getTimestamp("Last_Update");
             String lastUpdatedBy = rs.getString("Last_Updated_By");

             Country foundCountry = new Country(countryId, countryName, createDate, createdBy, lastUpdate, lastUpdatedBy);
             countryList.add(foundCountry);
         }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return countryList;
    }

    public static Country getCountryFromDivision(Division division) {
        ObservableList<Country> countriesList = getAllCountries();
        Country foundCountry = null;

        for (Country country : countriesList) {
            if (country.getCountryId() == division.getCountryId()) {
                foundCountry = country;
                break;
            }
        }
        return foundCountry;
    }

}
