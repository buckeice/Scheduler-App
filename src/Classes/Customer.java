package Classes;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

/**
 * This class contains the Customer object and its methods.
 */
public class Customer {
    private int customerId;
    private String customerName;
    private String customerAddress;
    private String postalCode;
    private String phoneNumber;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdatedBy;
    private int divisionId;

    public Customer(int customerId, String customerName, String customerAddress, String postalCode, String phoneNumber, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy, int divisionId) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.divisionId = divisionId;
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
     * @return Gets the customer name.
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     *
     * @param customerName Sets the customer name.
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     *
     * @return Gets the customer address.
     */
    public String getCustomerAddress() {
        return customerAddress;
    }

    /**
     *
     * @param customerAddress Sets the customer address.
     */
    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    /**
     *
     * @return Gets the customer postal code.
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     *
     * @param postalCode Sets the customer postal code.
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     *
     * @return Gets the customer phone number.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     *
     * @param phoneNumber Sets the customer phone number.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     *
     * @return Gets the customer create date.
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     *
     * @param createDate Sets the customer create date.
     */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /**
     *
     * @return Gets the user the created the customer entry.
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     *
     * @param createdBy Sets the user creating the entry.
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     *
     * @return Gets the last time the entry was updated.
     */
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    /**
     *
     * @param lastUpdate Sets the last time that the entry was updated.
     */
    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     *
     * @return Gets the user that last updated the entry.
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     *
     * @param lastUpdatedBy Sets the user that last updated the entry.
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     *
     * @return Gets the division ID.
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     *
     * @param divisionId Sets the division ID.
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /**
     *
     * @return Returns Customer ID as string.
     */
    @Override
    public String toString() {
        return (Integer.toString(getCustomerId()));
    }
}
