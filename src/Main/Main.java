package Main;


import Classes.DateUtility;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Samuel Buck
 * This is the main class for the application.
 */
public class Main extends Application {

    /**
     * Loads the LoginForm.
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("GUI/LoginForm.fxml"));
        primaryStage.setTitle("Scheduler App");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    /**
     * The main method. Starts the connection with the database and closes the connection when the application is closed.
     * @param args
     */
    public static void main(String[] args) {
        //Locale.setDefault(new Locale("fr"));
        JDBC.makeConnection();
        launch(args);
        JDBC.closeConnection();
    }
}
