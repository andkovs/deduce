package dto;

public class CourseToTestDTO {

    private Integer courseId;
    private Integer testId;

    public CourseToTestDTO(Integer courseId, Integer testId) {
        this.courseId = courseId;
        this.testId = testId;
    }

    public CourseToTestDTO() {
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Integer getTestId() {
        return testId;
    }

    public void setTestId(Integer testId) {
        this.testId = testId;
    }
}
