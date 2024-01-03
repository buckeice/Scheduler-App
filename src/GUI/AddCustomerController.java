package GUI;

import Classes.*;
import Main.JDBC;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/**
 * This is the controller for AddCustomer.fxml.
 */
public class AddCustomerController implements Initializable {

    @FXML
    private Button addButton;

    @FXML
    private TextField addressField;

    @FXML
    private Button cancelButton;

    @FXML
    private ComboBox<Country> countryBox;

    @FXML
    private TextField customerIdField;

    @FXML
    private ComboBox<Division> divisionBox;

    @FXML
    private TextField nameField;

    @FXML
    private TextField phoneNumber;

    @FXML
    private TextField postalCode;

    @FXML
    private Label userLabel;

    private User loggedInUser;

    private Parent scene;

    /**
     *
     * @param event Retrieves the user inputs and inserts a new customer entry into the database.
     * @throws IOException Exception signal.
     */
    @FXML
    void addButtonPress(ActionEvent event) throws IOException {
        //Checking if text fields are blank
        if (nameField.getText().isBlank() ||
                addressField.getText().isBlank() || postalCode.getText().isBlank() || phoneNumber.getText().isBlank() || countryBox.getSelectionModel().isEmpty() || divisionBox.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please do not leave empty fields!");
            alert.showAndWait();
            return;
        }
        else {
            String customerName = nameField.getText();
            String address = addressField.getText();
            String customerPostalCode = postalCode.getText();
            String customerPhoneNumber = phoneNumber.getText();
            //String customerCountry = countryBox.getSelectionModel().getSelectedItem().getCountryName();
            //String customerDivision = divisionBox.getSelectionModel().getSelectedItem().getDivisionName();
            LocalDateTime createDate = Classes.DateUtility.nowToUTC();
            String createdBy = this.loggedInUser.getUserName();
            LocalDateTime lastUpdate = Classes.DateUtility.nowToUTC();
            String lastUpdatedBy = this.loggedInUser.getUserName();
            int divisionId = divisionBox.getSelectionModel().getSelectedItem().getDivisionId();

            Connection connection = JDBC.getConnection();
            // Creates a SQL statement using retrieved data and inserts that data into the database
            try {
                String sql = "INSERT INTO client_schedule.customers (Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";

                        //+ customerName + "', '" + address + "', '" + customerPostalCode + "', '" + customerPhoneNumber + "', '" + createDate + "', '" + createdBy + "', '" + lastUpdate + "', '" + lastUpdatedBy + "', '" + divisionId + "');";

                JDBC.makePreparedStatement(sql, connection);
                PreparedStatement statement = JDBC.getPreparedStatement();
                statement.setString(1, customerName);
                statement.setString(2, address);
                statement.setString(3, customerPostalCode);
                statement.setString(4, customerPhoneNumber);
                statement.setString(5, createDate.toString());
                statement.setString(6, createdBy);
                statement.setString(7, lastUpdate.toString());
                statement.setString(8, lastUpdatedBy);
                statement.setString(9, Integer.toString(divisionId));
                statement.executeUpdate();


            } catch (SQLException throwables) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Check inputs!");
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

    /**
     *
     * @param event Backs out of the Add Customer Form and returns the user to the Main Form.
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
     * Creates a list of countries and adds it to the Country ComboBox.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Country> countryList;
        countryList = CountryDB.getAllCountries();
        countryBox.setItems(countryList);
    }

    /**
     *
     * @param event Gets the divisions from the country selected and populates the Division ComboBox with the correct divisions.
     */
    @FXML
    void countryBoxPull(ActionEvent event) {
        ObservableList<Division> divisionList;
        Country selectedCountry = countryBox.getSelectionModel().getSelectedItem();
        divisionList = DivisionDB.getDivisionsFromCountry(selectedCountry);
        divisionBox.setItems(divisionList);
        divisionBox.getSelectionModel().selectFirst();

    }

    /**
     *
     * @param user Retrieves the logged in User and sets the loggedInUser variable to be used in the addButtonPress() function.
     */
    public void setUser(User user) {
        userLabel.setText(user.getUserName());
        this.loggedInUser = user;
    }
}

