package Classes;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * This class contains the Appointment object and its methods.
 */
public class Appointment {
    private int appointmentId;
    private String title;
    private String description;
    private String location;
    private String type;
    private Timestamp start;
    private Timestamp end;
    private Timestamp createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;
    private int customerId;
    private int userId;
    private int contactId;
    private String contactName;


    public Appointment(int appointmentId, String title, String description, String location, String type, Timestamp start, Timestamp end, Timestamp createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy, int customerId, int userId, int contactId, String contactName) {
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
        this.contactName = contactName;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     *
     * @return Gets the appointment ID.
     */
    public int getAppointmentId() {
        return appointmentId;
    }

    /**
     *
     * @param appointmentId sets the appointment ID.
     */
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    /**
     *
     * @return Gets the title.
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title Sets the title.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return Gets the description.
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description Sets the description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return Gets the location.
     */
    public String getLocation() {
        return location;
    }

    /**
     *
     * @param location Sets the location.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     *
     * @return Gets the type.
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type Sets the type.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return Gets the appointment start.
     */
    public Timestamp getStart() {
        return start;
    }

    /**
     *
     * @param start Sets the appointment start.
     */
    public void setStart(Timestamp start) {
        this.start = start;
    }

    /**
     *
     * @return Gets the appointment end.
     */
    public Timestamp getEnd() {
        return end;
    }

    /**
     *
     * @param end Sets the appointment end.
     */
    public void setEnd(Timestamp end) {
        this.end = end;
    }

    /**
     *
     * @return Gets the create date.
     */
    public Timestamp getCreateDate() {
        return createDate;
    }

    /**
     *
     * @param createDate Sets the create date.
     */
    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    /**
     *
     * @return Gets the user who created appointment.
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     *
     * @param createdBy Sets the user who created appointment.
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     *
     * @return Get the time of last update.
     */
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    /**
     *
     * @param lastUpdate Set the user who last updated appointment.
     */
    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     *
     * @return Get the user who last updated appointment.
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     *
     * @param lastUpdatedBy Sets the user who last updated appointment.
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     *
     * @return Gets the customer ID.
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     *
     * @param customerId Sets the customer ID.
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     *
     * @return Gets the user ID.
     */
    public int getUserId() {
        return userId;
    }

    /**
     *
     * @param userId Sets the user ID.
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     *
     * @return Gets the contact ID.
     */
    public int getContactId() {
        return contactId;
    }

    /**
     *
     * @param contactId Sets the contact ID.
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }
}
