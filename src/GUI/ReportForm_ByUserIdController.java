package GUI;

import Classes.Appointment;
import Classes.AppointmentDB;
import Classes.User;
import Classes.UserDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * This is the controller class for ReportForm_ByUserId.fxml
 */
public class ReportForm_ByUserIdController implements Initializable {

    @FXML
    private TableColumn<Appointment, Integer> appointmentIdCol;

    @FXML
    private TableView<Appointment> appointmentsTable;

    @FXML
    private Button cancelButton;


    @FXML
    private TableColumn<Appointment, Integer> contactIdCol;

    @FXML
    private RadioButton contactScheduleButton;

    @FXML
    private ComboBox<?> customerByMonthBox;

    @FXML
    private ComboBox<?> customerByTypeBox;

    @FXML
    private RadioButton customerByTypeMonthButton;

    @FXML
    private ComboBox<User> customerByUserIdBox;

    @FXML
    private RadioButton customerByUserIdButton;

    @FXML
    private TableColumn<Appointment, Integer> customerIdCol;

    @FXML
    private TableColumn<Appointment, String> descriptionCol;

    @FXML
    private TableColumn<Appointment, LocalDateTime> endCol;

    @FXML
    private ToggleGroup reportsToggle;

    @FXML
    private TableColumn<Appointment, LocalDateTime> startCol;

    @FXML
    private TableColumn<Appointment, String> titleCol;

    @FXML
    private TableColumn<Appointment, String> typeCol;

    private User loggedInUser;
    private Parent scene;

    /**
     * Cancels the reports window and returns the user to the main form.
     * @param event
     * @throws IOException
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
     * Opens the Contact Schedule report form.
     * @param event
     * @throws IOException
     */
    @FXML
    void contactScheduleButtonPress(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((RadioButton) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("GUI/ReportForm_ContactSchedule.fxml"));
        scene = loader.load();
        ReportForm_ContactScheduleController reportForm_ContactScheduleController = loader.getController();
        reportForm_ContactScheduleController.setUser(loggedInUser);
        stage.setTitle("Scheduler App");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Takes the user to the Report by Type and Month form.
     * @param event
     * @throws IOException
     */
    @FXML
    void customerByTypeMonthButtonPress(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((RadioButton) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("GUI/ReportForm_ByTypeMonth.fxml"));
        scene = loader.load();
        ReportForm_ByTypeMonthController reportForm_byTypeMonthController = loader.getController();
        reportForm_byTypeMonthController.setUser(loggedInUser);
        stage.setTitle("Scheduler App");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Fills the appointment table with appointments that have the same User ID as the User selected in the User ID ComboBox.
     * Lambda used to find appointments that have matching User IDs with what is selected in the customerByUserIdBox.
     * @param event
     */
    @FXML
    void customerByUserIdBoxPull(ActionEvent event) {
        ObservableList<Appointment> appointmentsList = AppointmentDB.getAllAppointments();
        List<Appointment> foundAppointments = appointmentsList.stream().filter(appointment -> appointment.getUserId() == customerByUserIdBox.getSelectionModel().getSelectedItem().getUserId()).collect(Collectors.toList());
        ObservableList<Appointment> contactAppointmentsList = FXCollections.observableList(foundAppointments);

        appointmentsTable.setItems(contactAppointmentsList);
    }

    /**
     * Sets the user who is currently logged in.
     * @param loggedInUser The user that is currently logged in.
     */
    public void setUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerByUserIdBox.setItems(UserDB.getAllUsers());

        appointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        contactIdCol.setCellValueFactory(new PropertyValueFactory<>("contactId"));
    }
}
