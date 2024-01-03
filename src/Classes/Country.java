package Classes;

import javafx.collections.ObservableList;

import java.sql.Timestamp;

/**
 * This class contains the Country object and its methods.
 */
public class Country {
    private int countryId;
    private String countryName;
    private Timestamp createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;

    public Country(int countryId, String countryName, Timestamp createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy) {
        this.countryId = countryId;
        this.countryName = countryName;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     *
     * @return Gets the country ID.
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     *
     * @param countryId Sets the country ID.
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /**
     *
     * @return Gets the country name.
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     *
     * @param countryName Sets the country name.
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
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
     * @return Gets the user that created entry.
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     *
     * @param createdBy Sets the user that created entry.
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     *
     * @return Gets the time last of last update.
     */
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    /**
     *
     * @param lastUpdate Sets the time of last update.
     */
    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     *
     * @return Gets the user who last updated entry.
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     *
     * @param lastUpdatedBy Sets the user who last updated entry.
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     *
     * @return Overrides toString() method to propery display name in a ComboBox.
     */
    @Override
    public String toString() {
        return (getCountryName());
    }


}
