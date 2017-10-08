package dao;


import dto.ChapterDTO;
import dto.CourseDTO;
import dto.OrderDTO;
import dto.StudentDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class StudentDAO {

    public StudentDTO getStudentByIdFromDb(int id) {
        StudentDTO student = new StudentDTO();
        OrderDAO orderDAO = new OrderDAO();
        CourseDAO courseDAO = new CourseDAO();
        TopicDAO topicDAO = new TopicDAO();
        OrderDTO order = orderDAO.getOrderByIdFromDb(id);
        CourseDTO course = courseDAO.getCourseByIdFromDb(order.getCourseId());
        for (ChapterDTO ch :
                course.getChapters()) {
            ch.setTopics(topicDAO.getTopicsByChapterId(ch.getId()));
        }
        student.setCourse(course);
        student.setOrder(order);
        return student;
    }
}
