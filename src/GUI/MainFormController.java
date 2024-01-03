package GUI;

import Classes.*;
import Main.JDBC;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.Initializable;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This is the controller for MainForm.fxml.
 */
public class MainFormController implements Initializable {

    @FXML
    private Button addAppointmentButton;

    @FXML
    private Button addCustomerButton;

    @FXML
    private TableColumn<Customer, String> addressCol;

    @FXML
    private TableColumn<Appointment, Integer> appointmentCustomerIdCol;

    @FXML
    private TableColumn<Appointment, Integer> appointmentIdCol;

    @FXML
    private RadioButton appointmentSortAll;

    @FXML
    private RadioButton appointmentSortMonth;

    @FXML
    private RadioButton appointmentSortWeek;

    @FXML
    private TableView<Appointment> appointmentTable;

    @FXML
    private TableColumn<Appointment, String> contactCol;

    @FXML
    private TableColumn<Customer, LocalDateTime> createDateCol;

    @FXML
    private TableColumn<Customer, String> createdByCol;

    @FXML
    private TableColumn<Customer, Integer> customerIdCol;

    @FXML
    private TableColumn<Customer, String> customerNameCol;

    @FXML
    private TableView<Customer> customerTable;

    @FXML
    private Button deleteAppointmentButton;

    @FXML
    private Button deleteCustomerButton;

    @FXML
    private TableColumn<Appointment, String> descriptionCol;

    @FXML
    private TableColumn<Customer, Integer> divisionIdCol;

    @FXML
    private TableColumn<Appointment, Timestamp> endDateTimeCol;

    @FXML
    private Button exitButton;

    @FXML
    private TableColumn<Customer, LocalDateTime> lastUpdateCol;

    @FXML
    private TableColumn<Customer, String> lastUpdatedByCol;

    @FXML
    private TableColumn<Appointment, String> locationCol;

    @FXML
    private ToggleGroup monthToggleGroup;

    @FXML
    private TableColumn<Customer, String> phoneCol;

    @FXML
    private TableColumn<Customer, String> postalCodeCol;

    @FXML
    private Button reportsButton;

    @FXML
    private TableColumn<Appointment, Timestamp> startDateTimeCol;

    @FXML
    private TableColumn<Appointment, String> titleCol;

    @FXML
    private TableColumn<Appointment, String> typeCol;

    @FXML
    private Button updateAppointmentButton;

    @FXML
    private Button updateCustomerButton;

    @FXML
    private Label userLabel;

    @FXML
    private TableColumn<Appointment, Integer> userIdCol;

    // used for checking the user that is logged in
    private User loggedInUser;

    private Parent scene;

    /**
     * Takes the user to the add appointment form.
     * @param event
     * @throws IOException
     */
    @FXML
    void addAppointmentButtonPress(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("GUI/AddAppointment.fxml"));
        scene = loader.load();
        AddAppointmentController addAppointmentController = loader.getController();
        addAppointmentController.setUser(loggedInUser);
        stage.setTitle("Scheduler App");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     *
     * @param event Opens the Add Customer window.
     * @throws IOException exception signal.
     */
    @FXML
    void addCustomerButtonPress(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("GUI/AddCustomer.fxml"));
        scene = loader.load();
        AddCustomerController addCustomerController = loader.getController();
        addCustomerController.setUser(loggedInUser);
        stage.setTitle("Scheduler App");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     *
     * @param event Deleted the appointment from the database.
     */
    @FXML
    void deleteAppointmentButtonPress(ActionEvent event) {
        if (appointmentTable.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No appointment selected!");
            alert.showAndWait();
        }
        else {
            Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this appointment?");
            Optional<ButtonType> result = alertConfirm.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                int id = appointmentTable.getSelectionModel().getSelectedItem().getAppointmentId();
                String type = appointmentTable.getSelectionModel().getSelectedItem().getType();

                AppointmentDB.deleteAppointment(id);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, ("Appointment ID: " + id + " of type: " + type + " has been deleted."));
                alert.showAndWait();
            }

            // refreshes the appointment table based on sort selection
            if (appointmentSortAll.isSelected()) {
                appointmentTable.setItems(AppointmentDB.getAllAppointments());
            }
            if (appointmentSortMonth.isSelected()) {
                appointmentTable.setItems(AppointmentDB.getAppointmentsByCurrentMonth());
            }
        }
    }

    /**
     *
     * @param event Deletes the user from the table.
     */
    @FXML
    void deleteCustomerButtonPress(ActionEvent event) {
        ObservableList<Appointment> appointmentList = AppointmentDB.getAllAppointments();

        if (customerTable.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No customer selected!");
            alert.showAndWait();
        }
        else {
            Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION, "Delete selected customer?");
            Optional<ButtonType> result = alertConfirm.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                String customerName = customerTable.getSelectionModel().getSelectedItem().getCustomerName();
                int id = customerTable.getSelectionModel().getSelectedItem().getCustomerId();
                Boolean hasAppointment = false;

                for (Appointment appointment : appointmentList) {
                    if (appointment.getCustomerId() == id) {
                        hasAppointment = true;
                        break;
                    }
                }
                if (hasAppointment) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Customer has an appointment!");
                    alert.showAndWait();
                }
                else {
                    CustomerDB.deleteCustomer(id);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, (customerName + " has been deleted!"));
                    alert.showAndWait();

                    // refreshes the customer table
                    customerTable.setItems(CustomerDB.getAllCustomers());
                }
            }
        }
    }

    /**
     *
     * @param event Exits the application and closes the database connection.
     */
    @FXML
    void exitButtonPress(ActionEvent event) {
        JDBC.closeConnection();
        System.exit(0);
    }

    /**
     * Opens the Report form.
     * @param event
     * @throws IOException
     */
    @FXML
    void reportsButtonPress(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("GUI/ReportForm_ContactSchedule.fxml"));
        scene = loader.load();
        ReportForm_ContactScheduleController reportForm_ContactScheduleController = loader.getController();
        reportForm_ContactScheduleController.setUser(loggedInUser);
        stage.setTitle("Scheduler App");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     *
     * @param event Displays all appointments in the TableView.
     */
    @FXML
    void allSelected(ActionEvent event) {
        appointmentTable.setItems(AppointmentDB.getAllAppointments());
    }

    /**
     *
     * @param event Displays appointments in the TableView that are scheduled for the current month.
     */
    @FXML
    void monthSelected(ActionEvent event) {
        appointmentTable.setItems(AppointmentDB.getAppointmentsByCurrentMonth());
    }

    /**
     *
     * @param event Displays appointments in the TableView that are scheduled within the next 7 days.
     */
    @FXML
    void weekSelected(ActionEvent event) {
        appointmentTable.setItems(AppointmentDB.getAppointmentsByCurrentWeek());
    }


    /**
     * Takes the user to the update appointment form.
     * @param event Checks if an appointment is selected and sends the user to the update form.
     * @throws IOException
     */
    @FXML
    void updateAppointmentButtonPress(ActionEvent event) throws IOException {
        if (appointmentTable.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No appointment selected!");
            alert.showAndWait();
        } else {
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("GUI/UpdateAppointment.fxml"));
            scene = loader.load();
            UpdateAppointmentController UpdateAppointmentController = loader.getController();
            UpdateAppointmentController.setUser(loggedInUser);
            UpdateAppointmentController.setAppointment(appointmentTable.getSelectionModel().getSelectedItem());
            stage.setTitle("Scheduler App");
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     * Takes the user to the update customer form.
     * @param event Checks if a customer is selected and sends the user to the update form.
     * @throws IOException
     */
    @FXML
    void updateCustomerButtonPress(ActionEvent event) throws IOException {
        if (customerTable.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No customer selected!");
            alert.showAndWait();
        }
        else {
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("GUI/UpdateCustomer.fxml"));
            scene = loader.load();
            UpdateCustomerController UpdateCustomerController = loader.getController();
            UpdateCustomerController.setUser(loggedInUser);
            UpdateCustomerController.setCustomer(customerTable.getSelectionModel().getSelectedItem());
            stage.setTitle("Scheduler App");
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     *
     * @param loggedInUser Used to set which user is currently logged in.
     */
    public void setUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
        userLabel.setText(loggedInUser.getUserName());
    }

    /**
     * Checks to see if an appointment is scheduled within 15 minutes for the user that is signed in.
     * @param user The user to check against the appointment user ID.
     */
    public void checkIfAppointmentWithin15Minutes(User user) {
        ObservableList<Appointment> appointmentList = AppointmentDB.getAllAppointments();
        User userToCheck = user;
        LocalDateTime nowLocalDateTime = LocalDateTime.now();
        LocalDateTime endLocalDateTime = nowLocalDateTime.plusMinutes(15);

        for (Appointment appointment : appointmentList) {
            if (appointment.getUserId() == userToCheck.getUserId() && (appointment.getStart().toLocalDateTime().isBefore(endLocalDateTime) && appointment.getStart().toLocalDateTime().isAfter(nowLocalDateTime))) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Appointment ID: " + appointment.getAppointmentId() + " at time " + appointment.getStart() + " is within 15 minutes!");
                alert.showAndWait();;
                return;
            }
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "No appointments scheduled within 15 minutes");
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        appointmentTable.setItems(AppointmentDB.getAllAppointments());
        customerTable.setItems(CustomerDB.getAllCustomers());

        appointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startDateTimeCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        endDateTimeCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        appointmentCustomerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        userIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));

        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        postalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        createDateCol.setCellValueFactory(new PropertyValueFactory<>("createDate"));
        createdByCol.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
        lastUpdateCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdate"));
        lastUpdatedByCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdatedBy"));
        divisionIdCol.setCellValueFactory(new PropertyValueFactory<>("divisionId"));

    }
}
