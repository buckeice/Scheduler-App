package Classes;

import javafx.collections.ObservableList;

import java.time.*;
import java.util.Locale;

/**
 * This class contains functions that perform date and time related operations.
 */
public class DateUtility {
    /**
     *
     * @return Creates a new LocalDateTime object with the current time converted to UTC
     */
    public static LocalDateTime nowToUTC() {
        LocalDateTime localDateTime = LocalDateTime.now(ZoneOffset.UTC);

        return localDateTime;
    }

    /**
     * Used to create a string the has the business open and close time converted to the users local timezone. To be used with an alert.
     * @return Returns the business open and close time (as well as the timezone) as a string.
     */
    public static String createLocalBusinessTimeString() {
        LocalDateTime nowTime = LocalDateTime.now();
        ZoneId localZone = ZoneId.systemDefault();
        ZoneId zoneEST = ZoneId.of("America/New_York");
        ZonedDateTime zonedTime = nowTime.atZone(localZone);
        ZonedDateTime zonedTimeEST = zonedTime.withZoneSameInstant(zoneEST);
        LocalDate dateTime = zonedTimeEST.toLocalDate();

        ZonedDateTime openHour = ZonedDateTime.of(LocalDate.from(dateTime), LocalTime.of(8,0), ZoneId.of("America/New_York"));
        ZonedDateTime closeHour = ZonedDateTime.of(LocalDate.from(dateTime), LocalTime.of(22, 0), ZoneId.of("America/New_York"));

        ZonedDateTime localOpenHour = openHour.withZoneSameInstant(localZone);
        ZonedDateTime localCloseHour = closeHour.withZoneSameInstant(localZone);

        String timeString = localOpenHour.toLocalTime().toString() + " - " + localCloseHour.toLocalTime().toString() + " " + localZone;

        return timeString;
    }

    /**
     * This function is used to check whether a LocalDateTime conflicts with business hours.
     * @param time The time that is checked.
     * @return Returns false if time conflicts with business hours. Otherwise, returns true.
     */
    public static boolean verifyNoBusinessHoursConflict(LocalDateTime time) {

        ZoneId localZone = ZoneId.systemDefault();
        ZoneId zoneEST = ZoneId.of("America/New_York");
        ZonedDateTime zonedTime = time.atZone(localZone);
        ZonedDateTime zonedTimeEST = zonedTime.withZoneSameInstant(zoneEST);


        ZonedDateTime timeToEST = time.atZone(zoneEST);
        LocalDate dateTime = zonedTimeEST.toLocalDate();
        ZonedDateTime openHour = ZonedDateTime.of(LocalDate.from(dateTime), LocalTime.of(8,0), ZoneId.of("America/New_York"));
        ZonedDateTime closeHour = ZonedDateTime.of(LocalDate.from(dateTime), LocalTime.of(22, 0), ZoneId.of("America/New_York"));




        if (zonedTimeEST.isBefore(openHour) || zonedTimeEST.isAfter(closeHour)) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Converts a LocalDateTime to UTC.
     * @param time the LocalDateTime to convert.
     * @return Returns the converted time.
     */
    public static ZonedDateTime convertToUTC(LocalDateTime time) {
        ZoneId localZone = ZoneId.systemDefault();
        ZoneId zoneUTC = ZoneId.of("UTC");
        Instant instant = time.atZone(localZone).toInstant();
        ZonedDateTime timeUTC = ZonedDateTime.ofInstant(instant, zoneUTC);

        return timeUTC;
    }

    /**
     * Verifies that there is no scheduling conflict with another appointment.
     * @param start The start time of the appointment to check.
     * @param end The end time of the appointment to check.
     * @return True if there is a conflict, false if not.
     */
    public static boolean verifyNoConflict(LocalDateTime start, LocalDateTime end) {
        ObservableList<Appointment> appointmentsList = AppointmentDB.getAllAppointments();

        for (Appointment appointment : appointmentsList) {
            LocalDateTime foundStart = appointment.getStart().toLocalDateTime();
            LocalDateTime foundEnd = appointment.getEnd().toLocalDateTime();

            if ((start.isAfter(foundStart) && end.isBefore(foundEnd))
            || (start.isBefore(foundStart) && end.isAfter(foundStart))
            || (start.isBefore(foundEnd) && end.isAfter(foundEnd))
            || (start.isEqual(foundStart) && end.isEqual(foundEnd)))

            {
                return true;
            }
        }
        return false;
    }

    /** Verifies that start and end times do not conflict with other appointments, but does not check the appointment with the provided ID.
     *  Lambda expression used to improve code in removeIf(), otherwise a less concise for loop would have had to been implemented.
     * @param start The appointment start time to check.
     * @param end The appointment end time to check.
     * @param id The ID of the appointment to exclude from the check.
     * @return True if a conflicting appointment is found. false if not.
     */
    public static boolean verifyNoConflictWithId(LocalDateTime start, LocalDateTime end, int id) {
        ObservableList<Appointment> appointmentsList = AppointmentDB.getAllAppointments();

        appointmentsList.removeIf(appointment -> appointment.getAppointmentId() == id);

        for (Appointment appointment : appointmentsList) {
            LocalDateTime foundStart = appointment.getStart().toLocalDateTime();
            LocalDateTime foundEnd = appointment.getEnd().toLocalDateTime();

            if ((start.isAfter(foundStart) && end.isBefore(foundEnd))
                    || (start.isBefore(foundStart) && end.isAfter(foundStart))
                    || (start.isBefore(foundEnd) && end.isAfter(foundEnd))
                    || (start.isEqual(foundStart) && end.isEqual(foundEnd)))

            {
                return true;
            }
        }
        return false;
    }
}
