package dto;

public class OrderUserDTO {

    private Integer id;
    private Integer userId;
    private String courseTitle;
    private String beginDate;
    private String endDate;

    public OrderUserDTO() {
    }

    public OrderUserDTO(Integer id, Integer userId, String courseTitle) {
        this.id = id;
        this.userId = userId;
        this.courseTitle = courseTitle;
    }

    public OrderUserDTO(Integer id, Integer userId, String courseTitle, String beginDate, String endDate) {
        this.id = id;
        this.userId = userId;
        this.courseTitle = courseTitle;
        this.beginDate = beginDate;
        this.endDate = endDate;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }
}
