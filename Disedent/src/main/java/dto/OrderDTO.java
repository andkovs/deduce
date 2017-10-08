package dto;

public class OrderDTO {

    private Integer id;
    private Integer courseId;
    private Integer userId;
    private Integer groupId;
    private String beginDate;
    private String endDate;
    private String creationDate;

    public OrderDTO(Integer id, Integer courseId, Integer userId,
                    Integer groupId, String beginDate, String endDate, String creationDate) {
        this.id = id;
        this.courseId = courseId;
        this.userId = userId;
        this.groupId = groupId;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.creationDate = creationDate;
    }

    public OrderDTO() {
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }


    public Integer getId() {
        return id;
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
