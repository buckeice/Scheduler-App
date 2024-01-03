package Classes;

import java.io.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * @author Samuel Buck
 * This is the class for the login reporting function of the application.
 */
public class LoginReport {
    /**
     * This writes the user's login attempt to a file in the root folder showing the username, timestamp, timezone, and whether the attempt was successful or unsuccessful.
     * @param user The username that is written to the file.
     * @param verification Used to write whether the attempt was successful or unsuccessful.
     * @throws IOException
     */
    public static void reportLogin(String user, Boolean verification) throws IOException {
        String filename = "login_activity.txt";
        FileWriter fileWriter = new FileWriter(filename, true);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        Timestamp nowTimestamp = Timestamp.valueOf(LocalDateTime.now());
        String localZone = ZoneId.systemDefault().toString();

        if (verification) {
            printWriter.println("LOGIN ATTEMPT BY USERNAME: " + user + " AT TIME: " + nowTimestamp + " " + localZone + " | SUCCESSFUL");
        }
        else {
            printWriter.println("LOGIN ATTEMPT BY USERNAME: " + user + " AT TIME: " + nowTimestamp + " " + localZone + " | UNSUCCESSFUL");
        }
        printWriter.close();

    }
}
