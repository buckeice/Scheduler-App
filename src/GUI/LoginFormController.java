package GUI;

import Classes.LoginReport;
import Classes.User;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import Main.JDBC;
import Classes.UserDB;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * This is the controller for LoginForm.fxml
 * LANGUAGE: English, French
 */
public class LoginFormController implements Initializable{

    @FXML
    private Button exitButton;

    @FXML
    private Label locationLabel;

    @FXML
    private Button loginButton;

    @FXML
    private Label loginFormLabel;

    @FXML
    private Label passwordLabel;

    @FXML
    private TextField passwordTextField;

    @FXML
    private TextField userTextField;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label zoneIdLabel;

    private Parent scene;


    //Buttons

    /**
     * @param event Exits the application.
     */
    @FXML
    void exitButtonPress(ActionEvent event) {
        JDBC.closeConnection();
        System.exit(0);
    }

    /**
     *
     * @param event Verifies login data from text fields and sends user to the main form.
     * @throws SQLException Exception signal.
     */
    @FXML
    void loginButtonPressed(ActionEvent event) throws SQLException, IOException {
        Locale locale = Locale.getDefault(); //For alert translation
        boolean loginVerified = false;
        String username = userTextField.getText();
        String password = passwordTextField.getText();
        ObservableList<User> userNameList = UserDB.getAllUsers();



        for (User user : userNameList) {
            if (username.equals(user.getUserName()) && password.equals(user.getUserPassword())) {
                loginVerified = true;
                LoginReport.reportLogin(username, true);

                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("GUI/MainForm.fxml"));
                scene = loader.load();
                MainFormController mainFormController = loader.getController();
                mainFormController.setUser(user);
                mainFormController.checkIfAppointmentWithin15Minutes(user);
                stage.setTitle("Scheduler App");
                stage.setScene(new Scene(scene));
                stage.show();
                break;
            }
        }
        if (!loginVerified && locale.getLanguage().equals("en")) {
            LoginReport.reportLogin(username, false);
            Alert alert = new Alert(Alert.AlertType.ERROR, "Incorrect user information!");
            alert.showAndWait();
        }
        if (!loginVerified && locale.getLanguage().equals(("fr"))) {
            LoginReport.reportLogin(username, false);
            Alert alert = new Alert(Alert.AlertType.ERROR, "Informations utilisateur incorrectes !");
            alert.showAndWait();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Locale locale = Locale.getDefault();
        ZoneId localZone = ZoneId.systemDefault();

        zoneIdLabel.setText(localZone.toString());

        //Changes text based on the systems default language
        if (locale.getLanguage().equals("fr")) {
            exitButton.setText("Sortie");
            locationLabel.setText("Emplacement:");
            loginButton.setText("Connexion");
            loginFormLabel.setText("Formulaire de connexion");
            passwordLabel.setText("MOT DE PASSE:");
            usernameLabel.setText("NOM D'UTILISATEUR");

        }
    }
}



