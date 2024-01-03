package GUI;

import Classes.AppointmentDB;
import Classes.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import Classes.Appointment;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.text.DateFormatSymbols;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * This is the controller class for ReportForm_ByTypeMonth.fxml
 */
public class ReportForm_ByTypeMonthController implements Initializable {

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
    private ComboBox<String> customerByMonthBox;

    @FXML
    private ComboBox<String> customerByTypeBox;

    @FXML
    private RadioButton customerByTypeMonthButton;

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
    private Button searchButton;

    @FXML
    private TableColumn<Appointment, LocalDateTime> startCol;

    @FXML
    private TableColumn<Appointment, String> titleCol;

    @FXML
    private TableColumn<Appointment, String> typeCol;

    @FXML
    private Label totalLabel;

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
     * Takes the user to the Contact Schedule report form.
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
     * Takes the user to the Report by User ID form.
     * @param event
     * @throws IOException
     */
    @FXML
    void customerByUserIdButtonPress(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((RadioButton) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("GUI/ReportForm_ByUserId.fxml"));
        scene = loader.load();
        ReportForm_ByUserIdController reportForm_ByUserIdController = loader.getController();
        reportForm_ByUserIdController.setUser(loggedInUser);
        stage.setTitle("Scheduler App");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Displays an error if either ComboBox is empty, otherwise, gets the month and type from the ComboBoxes to be used as parameters
     * and then sets the items for the appointmentsTable.
     * @param event
     */
    @FXML
    void searchButtonPress(ActionEvent event) {
        if (customerByTypeBox.getSelectionModel().isEmpty() || customerByMonthBox.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Type or month is not selected!");
            alert.showAndWait();
        }
        else {
            int month = (customerByMonthBox.getSelectionModel().getSelectedIndex()) + 1;
            String type = customerByTypeBox.getSelectionModel().getSelectedItem();

            appointmentsTable.setItems(AppointmentDB.getAppointmentsByTypeMonth(type, month));
            totalLabel.setText("Total: " + appointmentsTable.getItems().size());
        }
    }

    /**
     * Sets the user who is currently logged in.
     * @param loggedInUser The user that is currently logged in.
     */
    public void setUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    /**
     * Gets a list of all possible types within the appointment table in the database while ensuring that there are no duplicates.
     * @return Returns a list of possible types.
     */
    public ObservableList<String> getTypeList() {
        ObservableList<Appointment> appointmentsList = AppointmentDB.getAllAppointments();
        ObservableList<String> typeList = FXCollections.observableArrayList();

        for (Appointment appointment : appointmentsList) {
            typeList.add(appointment.getType().toUpperCase(Locale.ROOT));
        }
        List<String> typeListNoDuplicates = typeList.stream().toList().stream().distinct().collect(Collectors.toList());
        ObservableList<String> finalTypeList = FXCollections.observableList(typeListNoDuplicates);

        return finalTypeList;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<String> monthAsInteger = Arrays.asList("1", "2", "3","4", "5", "6", "7", "8", "9", "10", "11", "12");
        ObservableList<String> monthIntegerObservableList = FXCollections.observableArrayList(monthAsInteger);



        customerByMonthBox.setItems(monthIntegerObservableList);
        customerByTypeBox.setItems(getTypeList());

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
