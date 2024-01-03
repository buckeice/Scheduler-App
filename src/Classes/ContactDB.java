package Classes;

import Main.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

/**
 * This class contains functions that retrieve Contact data from the database to be used by the application.
 */
public class ContactDB {
    /**
     * Gets all contacts and returns them as an ObservableList.
     * @return Returns all contacts in an ObservableList.
     */
    public static ObservableList<Contact> getAllContacts() {
        ObservableList<Contact> contactList = FXCollections.observableArrayList();
        Connection connection = JDBC.getConnection();

        try {
            String sql = "SELECT * FROM client_schedule.contacts";
            JDBC.makePreparedStatement(sql, connection);
            PreparedStatement statement = JDBC.getPreparedStatement();
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                int contactId = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                String contactEmail = rs.getString("Email");

                Contact foundContact = new Contact(contactId, contactName, contactEmail);
                contactList.add(foundContact);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return contactList;
    }
    public static Contact getContactFromId(int id) {
        ObservableList<Contact> contactList = getAllContacts();
        Contact foundContact = null;
        for (Contact contact : contactList) {
            if (contact.getContactId() == id) {
                foundContact = contact;
            }
        }
        return foundContact;
    }
}
