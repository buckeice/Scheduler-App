package Classes;

import java.sql.Date;

/**
 * This class contains the User object and its methods.
 */
public class User {
    private int userId;
    private String userName;
    private String userPassword;
    private Date createDate;
    private String createdBy;
    private Date lastUpdate;
    private String lastUpdateBy;

    public User(int userId, String userName, String userPassword, Date createDate, String createdBy, Date lastUpdate, String lastUpdateBy) {
        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
    }

    // Setters

    /**
     *
     * @param userId Sets the userId.
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     *
     * @param userName Sets the userName.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     *
     * @param userPassword Sets the userPassword.
     */
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    /**
     *
     * @param createDate Sets the user createDate.
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     *
     * @param createdBy Sets the user createdBy.
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     *
     * @param lastUpdate Sets the lastUpdate time.
     */
    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     *
     * @param lastUpdateBy Sets lastUpdateBy.
     */
    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    // Getters

    /**
     *
     * @return Gets the userId.
     */
    public int getUserId() {
        return userId;
    }

    /**
     *
     * @return Gets the userName.
     */
    public String getUserName() {
        return userName;
    }

    /**
     *
     * @return Gets the userPassword.
     */
    public String getUserPassword() {
        return userPassword;
    }

    /**
     *
     * @return Gets the createDate.
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     *
     * @return Gets createdBy.
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     *
     * @return Gets lastUpdate time.
     */
    public Date getLastUpdate() {
        return lastUpdate;
    }

    /**
     *
     * @return Gets lastUpdateBy.
     */
    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    @Override
    public String toString() {
        return (Integer.toString(getUserId()));
    }


}
