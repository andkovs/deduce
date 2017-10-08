package dto;

public class OrderForListDTO {

    private Integer id;
    private Integer courseId;
    private Integer userId;
    private Integer groupId;
    private String courseTitle;
    private String userLastName;
    private String userFirstName;
    private String userMiddleName;
    private String userLogin;
    private String creationDate;
    private String beginDate;
    private String endDate;

    public OrderForListDTO() {
    }

    public OrderForListDTO(Integer id, Integer courseId,
                           Integer userId, Integer groupId,
                           String courseTitle, String userLastName,
                           String userLogin, String creationDate,
                           String beginDate, String endDate) {
        this.id = id;
        this.courseId = courseId;
        this.userId = userId;
        this.groupId = groupId;
        this.courseTitle = courseTitle;
        this.userLastName = userLastName;
        this.userLogin = userLogin;
        this.creationDate = creationDate;
        this.beginDate = beginDate;
        this.endDate = endDate;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public OrderForListDTO(Integer id, Integer courseId, Integer userId, Integer groupId,
                           String courseTitle, String userLastName, String userFirstName,
                           String userMiddleName, String userLogin,
                           String creationDate, String beginDate, String endDate) {
        this.id = id;
        this.courseId = courseId;
        this.userId = userId;
        this.groupId = groupId;
        this.courseTitle = courseTitle;
        this.userLastName = userLastName;
        this.userFirstName = userFirstName;
        this.userMiddleName = userMiddleName;
        this.userLogin = userLogin;
        this.creationDate = creationDate;
        this.beginDate = beginDate;
        this.endDate = endDate;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserMiddleName() {
        return userMiddleName;
    }

    public void setUserMiddleName(String userMiddleName) {
        this.userMiddleName = userMiddleName;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public Integer getId() {
        return id;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
