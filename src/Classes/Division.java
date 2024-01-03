package Classes;

import java.time.LocalDateTime;

/**
 * This class contains the Division object and its methods.
 */
public class Division {
    private int divisionId;
    private String divisionName;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdatedBy;
    private int countryId;

    public Division(int divisionId, String divisionName, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy, int countryId) {
        this.divisionId = divisionId;
        this.divisionName = divisionName;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.countryId = countryId;
    }

    /**
     *
     * @return Gets the first level division ID.
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     *
     * @param divisionId Sets the first level division ID.
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /**
     *
     * @return Gets the first level division name.
     */
    public String getDivisionName() {
        return divisionName;
    }

    /**
     *
     * @param divisionName Sets the first level division name.
     */
    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    /**
     *
     * @return Gets the date of creation.
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     *
     * @param createDate Sets the date of creation.
     */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /**
     *
     * @return Gets the user that created the entry.
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     *
     * @param createdBy Sets the user that created the entry.
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     *
     * @return Gets the time of last update.
     */
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    /**
     *
     * @param lastUpdate Sets the time of last update.
     */
    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     *
     * @return Gets the user who last updated the entry.
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     *
     * @param lastUpdatedBy Sets the user who last updated the entry.
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
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
    @Override
    public String toString() {
        return (getDivisionName());
    }
}
