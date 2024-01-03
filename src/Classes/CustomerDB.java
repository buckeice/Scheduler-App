package Classes;

import Main.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * This class contains functions that retrieve Customer data from the database to be used by the application.
 */
public class CustomerDB {
    /**
     * Gets all customers and returns them as an ObservableList.
     *
     * @return Returns all Customers in an ObservableList.
     */
    public static ObservableList<Customer> getAllCustomers() {
        ObservableList<Customer> customerList = FXCollections.observableArrayList();
        Connection connection = JDBC.getConnection();

        try {
            String sql = "SELECT * FROM client_schedule.customers";
            JDBC.makePreparedStatement(sql, connection);
            PreparedStatement statement = JDBC.getPreparedStatement();
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                int customerId = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String customerAddress = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phoneNumber = rs.getString("Phone");
                Timestamp createDate = rs.getTimestamp("Create_Date");
                String createdBy = rs.getString("Created_By");
                Timestamp lastUpdate = rs.getTimestamp("Last_Update");
                String lastUpdateBy = rs.getString("Last_Updated_By");
                int divisionId = rs.getInt("Division_ID");

                ZonedDateTime zonedCreateDate = createDate.toLocalDateTime().atZone(ZoneId.systemDefault());
                ZonedDateTime zonedLastUpdate = lastUpdate.toLocalDateTime().atZone(ZoneId.systemDefault());

                Customer foundCustomer = new Customer(customerId, customerName, customerAddress, postalCode, phoneNumber, zonedCreateDate.toLocalDateTime(), createdBy, zonedLastUpdate.toLocalDateTime(), lastUpdateBy, divisionId);
                customerList.add(foundCustomer);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return customerList;
    }

    /**
     * Takes a Customer ID and uses it to delete that user from the table.
     * @param customerId The Customer ID that is used.
     */
    public static void deleteCustomer(int customerId) {
        Connection connection = JDBC.getConnection();

        try {
            String sql = "DELETE FROM client_schedule.customers WHERE Customer_ID = '" + customerId + "';";
            JDBC.makePreparedStatement(sql, connection);
            PreparedStatement statement = JDBC.getPreparedStatement();
            statement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static Customer getCustomerFromId(int id) {
        ObservableList<Customer> customerList = getAllCustomers();
        Customer foundCustomer = null;

        for (Customer customer : customerList) {
            if (customer.getCustomerId() == id) {
                foundCustomer = customer;
            }
        }
        return foundCustomer;
    }
}
