package Classes;

import Main.JDBC;
import Classes.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

/**
 * This class contains functions that retrieve Division data from the database to be used by the application.
 */
public class UserDB {
    /**
     * Gets all users as an ObservableList.
     * @return returns an ObservableList of all users.
     */
    public static ObservableList<User> getAllUsers() {
        ObservableList<User> userList = FXCollections.observableArrayList();
        Connection connection = JDBC.getConnection();

        try {
            String sql = "SELECT * FROM client_schedule.users";
            JDBC.makePreparedStatement(sql, connection);
            PreparedStatement statement = JDBC.getPreparedStatement();
            ResultSet rs = statement.executeQuery();

            if (rs == null) {
                System.out.println("ResultSet failed");
            }
            else
            while (rs.next()) {
                int     userId = rs.getInt("User_ID");
                String  userName = rs.getString("User_Name");
                String  userPassword = rs.getString("Password");
                Date    createDate = rs.getDate("Create_Date");
                String  createdBy = rs.getString("Created_By");
                Date    lastUpdate = rs.getDate("Last_Update");
                String  lastUpdateBy = rs.getString("Last_Updated_By");

                User foundUser = new User(userId, userName, userPassword, createDate, createdBy, lastUpdate, lastUpdateBy);
                userList.add(foundUser);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return userList;
    }

    public static User getUserFromId(int id) {
        ObservableList<User> userList = getAllUsers();
        User foundUser = null;

        for (User user : userList) {
            if (user.getUserId() == id) {
                foundUser = user;
            }
        }
        return foundUser;
    }
}
