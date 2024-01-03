package GUI;

import Classes.*;
import Main.JDBC;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.*;

import java.io.IOException;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

/**
 * The controller for the AddAppointment fxml
 */
public class AddAppointmentController implements Initializable {

    @FXML
    private Button addButton;

    @FXML
    private Button cancelButton;

    @FXML
    private ComboBox<Contact> contactBox;

    @FXML
    private ComboBox<Customer> customerIdBox;

    @FXML
    private TextField descriptionField;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private Spinner<LocalTime> endTimeSpinner;

    @FXML
    private TextField locationField;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private Spinner<LocalTime> startTimeSpinner;

    @FXML
    private TextField titleField;

    @FXML
    private TextField typeField;

    @FXML
    private Label userLabel;

    @FXML
    private ComboBox<User> userBox;

    private User loggedInUser;
    private Parent scene;
    private LocalTime startLocalTime;
    private LocalTime endLocalTime;

    /**
     * Adds the appointment to the database using user inputs.
     * @param event Adds the appointment to the database using the user's inputs.
     * @throws IOException Exception signal.
     */
    @FXML
    void addButtonPress(ActionEvent event) throws IOException {


        // Error checking for blank inputs
        if (titleField.getText().isBlank() || descriptionField.getText().isBlank() || locationField.getText().isBlank() || contactBox.getSelectionModel().isEmpty() || typeField.getText().isBlank() || startDatePicker.getValue() == null || endDatePicker.getValue() == null || customerIdBox.getSelectionModel().isEmpty() || userBox.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please do not leave empty fields!");
            alert.showAndWait();
        }
        else {
            // used to create start and end LocalDateTime
            LocalDate startDate = startDatePicker.getValue();
            LocalDate endDate = endDatePicker.getValue();
            LocalTime startTime = startTimeSpinner.getValue();
            LocalTime endTime = endTimeSpinner.getValue();
            LocalDateTime start = LocalDateTime.of(startDate, startTime);
            LocalDateTime end = LocalDateTime.of(endDate, endTime);

            // Error checking for business time conflict.
            if (DateUtility.verifyNoBusinessHoursConflict(start) || DateUtility.verifyNoBusinessHoursConflict(end)) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Times conflict with business hours! 8:00 - 22:00 EST | " + DateUtility.createLocalBusinessTimeString());
                alert.showAndWait();
            }
            else {
                // Error checking for start and end times.
                if (startTimeSpinner.getValue().compareTo(endTimeSpinner.getValue()) > 0 || startTimeSpinner.getValue().compareTo(endTimeSpinner.getValue()) == 0  ) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Ensure Start time is before End time!");
                    alert.showAndWait();
                }
                else {
                    // Verifies no appointment conflict.
                    if (DateUtility.verifyNoConflict(start, end)) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Time conflicts with another appointment!");
                        alert.showAndWait();
                        return;
                    }
                    else {
                        // Appointment data
                        String title = titleField.getText();
                        String description = descriptionField.getText();
                        String location = locationField.getText();
                        String type = typeField.getText();
                        LocalDateTime appointmentStart = DateUtility.convertToUTC(start).toLocalDateTime();
                        LocalDateTime appointmentEnd = DateUtility.convertToUTC(end).toLocalDateTime();
                        LocalDateTime createDate = DateUtility.nowToUTC();
                        String createdBy = loggedInUser.getUserName();
                        LocalDateTime lastUpdate = DateUtility.nowToUTC();
                        String lastUpdatedBy = loggedInUser.getUserName();
                        int customerId = Integer.parseInt(customerIdBox.getSelectionModel().getSelectedItem().toString());
                        int userId = userBox.getSelectionModel().getSelectedItem().getUserId();
                        int contactId = contactBox.getSelectionModel().getSelectedItem().getContactId();

                        Connection connection = JDBC.getConnection();

                        try {
                            String sql = "INSERT INTO client_schedule.appointments (Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) " +
                                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
                            JDBC.makePreparedStatement(sql, connection);
                            PreparedStatement statement = JDBC.getPreparedStatement();
                            statement.setString(1, title);
                            statement.setString(2, description);
                            statement.setString(3, location);
                            statement.setString(4, type);
                            statement.setString(5, appointmentStart.toString());
                            statement.setString(6, appointmentEnd.toString());
                            statement.setString(7, createDate.toString());
                            statement.setString(8, createdBy);
                            statement.setString(9, lastUpdate.toString());
                            statement.setString(10, lastUpdatedBy);
                            statement.setString(11, Integer.toString(customerId));
                            statement.setString(12, Integer.toString(userId));
                            statement.setString(13,Integer.toString(contactId));
                            statement.executeUpdate();


                        } catch (SQLException throwables) {
                            Alert alert = new Alert(Alert.AlertType.ERROR, "SQL issue");
                            alert.showAndWait();
                            throwables.printStackTrace();
                        }


                    }
                    Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("GUI/MainForm.fxml"));
                    scene = loader.load();
                    MainFormController mainFormController = loader.getController();
                    mainFormController.setUser(loggedInUser);
                    stage.setTitle("Scheduler App");
                    stage.setScene(new Scene(scene));
                    stage.show();
                }
            }
        }
    }


    /**
     *
     * @param event Backs out of the Add Appointment Form and returns the user to the Main Form.
     * @throws IOException Exception signal.
     */
    @FXML
    void cancelButtonPress(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("GUI/MainForm.fxml"));
        scene = loader.load();
        MainFormController mainFormController = loader.getController();
        mainFormController.setUser(loggedInUser);
        stage.setTitle("Scheduler App");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     *
     * @param event Makes the date in the endDatePicker the same date as the start date.
     */
    @FXML
    void startDatePull(ActionEvent event) {
        endDatePicker.setValue(startDatePicker.getValue());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        contactBox.setItems(ContactDB.getAllContacts());
        customerIdBox.setItems(CustomerDB.getAllCustomers());
        userBox.setItems(UserDB.getAllUsers());

        //Start spinner ValueFactory
        SpinnerValueFactory<LocalTime> startValueFactory = new SpinnerValueFactory<LocalTime>() {
            private LocalTime defaultTime() {
                return LocalTime.of(12, 00);
            }

            {
                setValue(defaultTime());
            }
            @Override
            public void decrement(int i) {
                LocalTime time = getValue();
                setValue(time == null ? defaultTime() : time.minusMinutes(i + 14));
            }

            @Override
            public void increment(int i) {
                LocalTime time = getValue();
                setValue(time == null ? defaultTime() : time.plusMinutes(i + 14));
            }
        };

        //End spinner ValueFactory
        SpinnerValueFactory<LocalTime> endValueFactory = new SpinnerValueFactory<LocalTime>() {
            private LocalTime defaultTime() {
                return LocalTime.of(12, 00);
            }

            {
                setValue(defaultTime());
            }
            @Override
            public void decrement(int i) {
                LocalTime time = getValue();
                setValue(time == null ? defaultTime() : time.minusMinutes(i + 14));
            }

            @Override
            public void increment(int i) {
                LocalTime time = getValue();
                setValue(time == null ? defaultTime() : time.plusMinutes(i + 14));
            }
        };
        startTimeSpinner.setValueFactory(startValueFactory);
        endTimeSpinner.setValueFactory(endValueFactory);


    }

    public void setUser(User user) {
        userLabel.setText(user.getUserName());
        this.loggedInUser = user;
    }
}

