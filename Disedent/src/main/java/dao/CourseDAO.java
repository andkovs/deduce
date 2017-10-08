package dao;


import dto.CourseDTO;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CourseDAO {

    //private static final String SERVER_UPLOAD_LOCATION_FOLDER = "C://IdeaProjects//quizFiles//";
    //private static final String SERVER_UPLOAD_LOCATION_FOLDER = "/var/docs/"+ContextName.getName()+"/";
    private static final String SERVER_UPLOAD_LOCATION_FOLDER = "/var/dis/docs/";

    public ArrayList<CourseDTO> getAllCourses()  {
        ArrayList<CourseDTO> courses = new ArrayList<CourseDTO>();
        Connection connection = ConnectionToMySQLDB.getConnaction();
        if(connection==null){
            return null;
        }
        try {
            String sql = "SELECT * FROM courses";
            PreparedStatement psql = connection.prepareStatement(sql);
            ResultSet rs = psql.executeQuery();
            while (rs.next()) {
                courses.add(new CourseDTO(
                        rs.getInt("courseId"),
                        rs.getString("title"),
                        rs.getString("description"))
                );
            }
            rs.close();
            psql.close();
            connection.close();
        } catch (SQLException e) {
            return null;
        }
        return courses;
    }

    public CourseDTO getCourseByIdFromDb(int id) {
        if(id==0){
            return new CourseDTO(0, "", "");
        }
        CourseDTO course = new CourseDTO();
        String sql = "SELECT * FROM courses " +
                "WHERE courses.courseId = ?";
        try {
            Connection connection = ConnectionToMySQLDB.getConnaction();
            PreparedStatement psql = connection.prepareStatement(sql);
            psql.setInt(1, id);
            ResultSet rs = psql.executeQuery();;
            while (rs.next()) {
                course.setTitle(rs.getString("title"));
                course.setDescription(rs.getString("description"));
            }

            rs.close();
            psql.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        course.setId(id);
        course.setTests(new TestDAO().getShortTestsByCourseId(id));
        course.setChapters(new ChapterDAO().getChaptersByCourseId(id));
        return course;
    }

    public Integer getCourseIdByTopicIdFromDb(int topicId) {
        Integer courseId = null;
        String sql = "select c.courseId " +
                "From courses c " +
                "join chapters ch on c.courseId=ch.courseId " +
                "join topics t on ch.chapterId=t.chapterId " +
                "where t.topicId=?;";
        try {
            Connection connection = ConnectionToMySQLDB.getConnaction();
            PreparedStatement psql = connection.prepareStatement(sql);
            psql.setInt(1, topicId);
            ResultSet rs = psql.executeQuery();;
            while (rs.next()) {
                courseId = rs.getInt("c.courseId");
            }
            rs.close();
            psql.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return courseId;
    }

    public CourseDTO setCourseInDB(CourseDTO course) {
        Connection connection = ConnectionToMySQLDB.getConnaction();
        if(connection==null){
            return null;
        }
        CourseDTO responseCourse = new CourseDTO();
        try {
            String newTitle = course.getTitle().replaceAll("\'","\"");
            String newDescription = course.getDescription().replaceAll("\'","\"");
            String sql = "INSERT INTO courses " +
                    "(courses.title, courses.description) " +
                    "VALUES (?, ?)";
            PreparedStatement psql = connection.prepareStatement(sql);
            psql.setString(1, newTitle);
            psql.setString(2, newDescription);
            psql.executeUpdate();
            sql = "SELECT MAX(courses.courseId) FROM courses";
            psql = connection.prepareStatement(sql);
            ResultSet rs = psql.executeQuery();
            while (rs.next()) {
                responseCourse.setId(rs.getInt("MAX(courses.courseId)"));
            }
            responseCourse.setTitle(course.getTitle());
            responseCourse.setDescription(course.getDescription());
            rs.close();
            psql.close();
            connection.close();
        } catch (SQLException e) {
            return null;
        }
        createDirectories(responseCourse.getId());
        return responseCourse;
    }

    public String updateCourseInDB(int id, CourseDTO course) {
        Connection connection = ConnectionToMySQLDB.getConnaction();
        if(connection==null){
            return null;
        }
        try {
            String newTitle = course.getTitle().replaceAll("\'","\"");
            String newDescription = course.getDescription().replaceAll("\'","\"");
            String sql = "UPDATE courses " +
                    "SET courses.title = ?, courses.description = ?" +
                    " WHERE courses.courseId = ?";
            PreparedStatement psql = connection.prepareStatement(sql);
            psql.setString(1, newTitle);
            psql.setString(2, newDescription);
            psql.setInt(3, id);
            psql.executeUpdate();
            psql.close();
            connection.close();
        } catch (SQLException e) {
            return null;
        }
        return "ok";
    }

    public String deleteCourseFromDB(int id) {
        Connection connection = ConnectionToMySQLDB.getConnaction();
        if(connection==null){
            return null;
        }
        try {
            String sql = "delete from coursetotest where coursetotest.courseId = ?;";
            PreparedStatement psql = connection.prepareStatement(sql);
            psql.setInt(1, id);
            psql.executeUpdate();
            sql = "delete from courses where courses.courseId = ?;";
            psql = connection.prepareStatement(sql);
            psql.setInt(1, id);
            psql.executeUpdate();
            psql.close();;
            connection.close();
        } catch (SQLException e) {
            return null;
        }
        deleteDirectories(id);
        return "ok";
    }

    //work with dir

    private void createDirectories(Integer courseId) {
        File dir = new File(SERVER_UPLOAD_LOCATION_FOLDER + courseId );
        boolean created = dir.mkdir();
    }

    private void deleteDirectories(Integer courseId){
        try {
            FileUtils.deleteDirectory(new File(SERVER_UPLOAD_LOCATION_FOLDER + courseId ));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
