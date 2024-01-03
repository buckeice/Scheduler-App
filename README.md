 # Scheduler App
    Author: Samuel Buck
    Application Version: 1.0
    Date: July 12th, 2023

    IDE: IntelliJ IDEA Community Edition 2021.1.3
    JDK Version: Java 17.0.1
    JavaFX version: JavaFX 11.0.2
    MySQL Connector Driver Version: mysql-connector-java-8.0.25

Purpose: To allow users to schedule appointments, control appointment data, control customer data, and view reports based on data pulled from the database.

## Directions:
### LOGIN FORM
The login form will be in English or French depending on system settings. To log in you must enter a username and password that matches with a user in the database. Click the login button and you will be taken to the Main Form, otherwise, an error will be displayed. To exit the application, click the Exit button.

### MAIN FORM
Here you can view all appointment and customer data. To exit the application, click the Exit button.

#### Appointment Controls
To add an appointment, click the Add Appointment button. To update an appointment, select an appointment from the table and click the Update Selected Appointment button. To delete an appointment, select an appointment from the table and click the Delete Selected Appointment button.

When adding or updating appointments, no field or selection can be left blank.

#### Customer Controls
To add a customer, click the Add Customer button. To update a customer, select a customer from the customer table and click the Update Selected Customer button. To delete a customer, select a customer from the table and click the Delete Selected Customer button.

When adding or updating appointments, no field or selection can be left blank.

#### Appointment Sort
To sort appointments by All, click the All radio button. To sort appointments by the current month, select the By Month radio button. To sort appointments by the current week, select the By Week radio button.

To view the Reports form click the Reports button.

### REPORTS
To view Contact Schedule, Appointments by User, or Appointments by Type and Month, click their respective radio button.

#### Contact Schedule
To view a contact's schedule, choose them from the ComboBox.

	
#### Appointments by User
To view appointments by User, select their ID from the ComboBox.

#### Appointments by Type and Month
To view appointments by type and month, select Type from the first ComboBox and Month from the second ComboBox. Pressing the Search button with an empty ComboBox will result in an error.

### ADDITIONAL REPORT
The Appointment sort by User will get any appointment associated with that user by their User ID.
