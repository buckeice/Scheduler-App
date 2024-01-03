package Classes;

import Main.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.util.stream.Collectors;

/**
 * This class contains functions that retrieve Appointment data from the database to be used by the application.
 */
public class AppointmentDB {
    /**
     * Gets all appointments as an ObservableList.
     * @return Returns an ObservableList of all appointments.
     */
    public static ObservableList<Appointment> getAllAppointments() {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
        Connection connection = JDBC.getConnection();

        try {
            String sql = "SELECT * FROM client_schedule.appointments";
            JDBC.makePreparedStatement(sql, connection);
            PreparedStatement statement = JDBC.getPreparedStatement();
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp start = rs.getTimestamp("Start");
                Timestamp end = rs.getTimestamp("End");
                Timestamp createDate = rs.getTimestamp("Create_Date");
                String createdBy = rs.getString("Created_By");
                Timestamp lastUpdate = rs.getTimestamp("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");

                Appointment foundAppointment = new Appointment(appointmentId, title, description, location, type, start, end, createDate, createdBy, lastUpdate, lastUpdatedBy, customerId, userId, contactId, ContactDB.getContactFromId(contactId).getContactName());
                appointmentList.add(foundAppointment);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return appointmentList;
    }

    /**
     * Gets all appointments within the current month and returns them as an ObservableList.
     * @return Returns the sorted appointments ObservableList.
     */
    public static ObservableList<Appointment> getAppointmentsByCurrentMonth() {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
        Connection connection = JDBC.getConnection();
        LocalDate localDate = DateUtility.nowToUTC().toLocalDate();
        int year = localDate.getYear();
        int month = localDate.getMonthValue();
        String yearMonth = year + "-" + month;

        try {
            String sql = "SELECT * FROM client_schedule.appointments WHERE CONCAT(YEAR(Start), '-', MONTH(Start)) = '" + yearMonth + "';";
            JDBC.makePreparedStatement(sql, connection);
            PreparedStatement statement = JDBC.getPreparedStatement();
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp start = rs.getTimestamp("Start");
                Timestamp end = rs.getTimestamp("End");
                Timestamp createDate = rs.getTimestamp("Create_Date");
                String createdBy = rs.getString("Created_By");
                Timestamp lastUpdate = rs.getTimestamp("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");

                Appointment foundAppointment = new Appointment(appointmentId, title, description, location, type, start, end, createDate, createdBy, lastUpdate, lastUpdatedBy, customerId, userId, contactId, ContactDB.getContactFromId(contactId).getContactName());
                appointmentList.add(foundAppointment);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return appointmentList;
    }

    /**
     * Gets a list of all appointments that exist within the span of 7 days.
     * @return Returns all appointments within the next 7 days.
     */
    public static ObservableList<Appointment> getAppointmentsByCurrentWeek() {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
        Connection connection = JDBC.getConnection();
        LocalDate localDate = DateUtility.nowToUTC().toLocalDate();
        LocalDate localDateEndWeek = DateUtility.nowToUTC().toLocalDate().plusDays(7);
        //Week start dateTime
        int year = localDate.getYear();
        int month = localDate.getMonthValue();
        int day = localDate.getDayOfMonth();
        //Week end dateTime
        int yearEnd = localDateEndWeek.getYear();
        int monthEnd = localDateEndWeek.getMonthValue();
        int dayEnd = localDateEndWeek.getDayOfMonth();

        String startWeek = year + "-" + month + "-" + day;
        String endWeek = yearEnd + "-" + monthEnd + "-" + dayEnd;

        try {
            String sql = "SELECT * FROM client_schedule.appointments WHERE Start BETWEEN '" + startWeek + "' AND '" + endWeek + "';";
            JDBC.makePreparedStatement(sql, connection);
            PreparedStatement statement = JDBC.getPreparedStatement();
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp start = rs.getTimestamp("Start");
                Timestamp end = rs.getTimestamp("End");
                Timestamp createDate = rs.getTimestamp("Create_Date");
                String createdBy = rs.getString("Created_By");
                Timestamp lastUpdate = rs.getTimestamp("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");
                String contactName = ContactDB.getContactFromId(contactId).getContactName();

                Appointment foundAppointment = new Appointment(appointmentId, title, description, location, type, start, end, createDate, createdBy, lastUpdate, lastUpdatedBy, customerId, userId, contactId, contactName);
                appointmentList.add(foundAppointment);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return appointmentList;
    }

    /**
     * Gets appointments from the database that match the input Type and Month.
     * @param selectedType The type being searched for.
     * @param month The month for the start time of the appointment.
     * @return Returns an ObservableList with the desired appointments.
     */
    public static ObservableList<Appointment> getAppointmentsByTypeMonth(String selectedType, int month) {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
        Connection connection = JDBC.getConnection();

        try {
            String sql = "SELECT * FROM client_schedule.appointments WHERE Type = '" + selectedType + "' AND CONCAT(MONTH(Start)) = '" + month + "';";
            JDBC.makePreparedStatement(sql, connection);
            PreparedStatement statement = JDBC.getPreparedStatement();
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp start = rs.getTimestamp("Start");
                Timestamp end = rs.getTimestamp("End");
                Timestamp createDate = rs.getTimestamp("Create_Date");
                String createdBy = rs.getString("Created_By");
                Timestamp lastUpdate = rs.getTimestamp("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");

                Appointment foundAppointment = new Appointment(appointmentId, title, description, location, type, start, end, createDate, createdBy, lastUpdate, lastUpdatedBy, customerId, userId, contactId, ContactDB.getContactFromId(contactId).getContactName());
                appointmentList.add(foundAppointment);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return appointmentList;
    }

    /**
     * Deletes the appointment with the input ID.
     * @param id The ID of the appointment to be deleted.
     */
    public static void deleteAppointment(int id) {
        Connection connection = JDBC.getConnection();

        try {
            String sql = "DELETE FROM client_schedule.appointments WHERE Appointment_ID = '" + id + "';";
            JDBC.makePreparedStatement(sql, connection);
            PreparedStatement statement = JDBC.getPreparedStatement();
            statement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
