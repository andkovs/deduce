package dto;

import java.util.ArrayList;

public class StudentDTO {

    private CourseDTO course;
    private OrderDTO order;

    public CourseDTO getCourse() {
        return course;
    }

    public void setCourse(CourseDTO course) {
        this.course = course;
    }

    public OrderDTO getOrder() {
        return order;
    }

    public void setOrder(OrderDTO order) {
        this.order = order;
    }
}
