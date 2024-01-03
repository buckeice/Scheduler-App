package GUI;

import Classes.*;
import Main.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class UpdateCustomerController implements Initializable {

    @FXML
    private TextField addressField;

    @FXML
    private Button cancelButton;

    @FXML
    private ComboBox<Country> countryBox;

    @FXML
    private TextField customerIdField;

    @FXML
    private TextField customerNameField;

    @FXML
    private ComboBox<Division> divisionBox;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private TextField postalCodeField;

    @FXML
    private Button updateButton;

    @FXML
    private Label userLabel;

    private User loggedInUser;

    private Parent scene;

    /**
     *
     * @param event Backs out of the Update Customer Form and returns the user to the Main Form.
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
     * @param event Updates the customer and returns to the main form.
     * @throws IOException Exception signal.
     */
    @FXML
    void updateButtonPress(ActionEvent event) throws IOException {
        //Checking if inputs are blank
        if (customerNameField.getText().isBlank() ||
                addressField.getText().isBlank() || postalCodeField.getText().isBlank() || phoneNumberField.getText().isBlank()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please do not leave blank text fields!");
            alert.showAndWait();
            return;

        } else {
            int customerId = Integer.parseInt(customerIdField.getText());
            String customerName = customerNameField.getText();
            String address = addressField.getText();
            String customerPostalCode = postalCodeField.getText();
            String customerPhoneNumber = phoneNumberField.getText();
            LocalDateTime createDate = Classes.DateUtility.nowToUTC();
            LocalDateTime lastUpdate = Classes.DateUtility.nowToUTC();
            String lastUpdatedBy = this.loggedInUser.getUserName();
            int divisionId = divisionBox.getSelectionModel().getSelectedItem().getDivisionId();

            Connection connection = JDBC.getConnection();

            //Creates a SQL statement to update the chosen record
            try {
                String sql = "UPDATE client_schedule.customers SET " + "Customer_Name = ?, "
                        + "Address = ?, " + "Postal_Code = ?, " + "Phone = ?, "
                        + "Last_Update = ?, " + "Last_Updated_By = ?, " + "Division_ID = ?"
                        + "WHERE Customer_ID = ?;";

                JDBC.makePreparedStatement(sql, connection);
                PreparedStatement statement = JDBC.getPreparedStatement();
                statement.setString(1, customerName);
                statement.setString(2, address);
                statement.setString(3, customerPostalCode);
                statement.setString(4, customerPhoneNumber);
                statement.setString(5,lastUpdate.toString());
                statement.setString(6,lastUpdatedBy);
                statement.setString(7, Integer.toString(divisionId));
                statement.setString(8, Integer.toString(customerId));
                statement.executeUpdate();

            } catch (SQLException throwables) {
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
     * @param event Gets the selected country in the countryBox and fills the divisionBox with the correct Division IDs.
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

    /**
     * Updates the form to include customer data.
     * @param customerToUpdate The customer to be displayed on the update from.
     */
    public void setCustomer(Customer customerToUpdate) {
        Division customerDivision;
        customerDivision = DivisionDB.getDivisionFromCustomer(customerToUpdate);
        Country customerCountry = CountryDB.getCountryFromDivision(DivisionDB.getDivisionFromCustomer(customerToUpdate));

        customerIdField.setText(Integer.toString(customerToUpdate.getCustomerId()));
        customerNameField.setText(customerToUpdate.getCustomerName());
        addressField.setText(customerToUpdate.getCustomerAddress());
        postalCodeField.setText(customerToUpdate.getPostalCode());
        phoneNumberField.setText(customerToUpdate.getPhoneNumber());
        countryBox.setValue(customerCountry);

        //Filling division list
        ObservableList<Division> customerDivisionList;
        Country selectedCountry = countryBox.getSelectionModel().getSelectedItem();
        customerDivisionList = DivisionDB.getDivisionsFromCountry(selectedCountry);
        divisionBox.setItems(customerDivisionList);
        divisionBox.setValue(customerDivision);

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Country> countryList;
        countryList = CountryDB.getAllCountries();
        countryBox.setItems(countryList);
    }
}
