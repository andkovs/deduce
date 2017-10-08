package dao;


import dto.CourseToTestDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CourseToTestDAO {
    public String setCourseToTestInDB(CourseToTestDTO courseToTest) {
        Connection connection = ConnectionToMySQLDB.getConnaction();
        if(connection==null){
            return null;
        }
        try {
            String sql = "INSERT INTO coursetotest " +
                    "(coursetotest.courseId, coursetotest.testId) " +
                    "VALUES (?, ?)";
            PreparedStatement psql = connection.prepareStatement(sql);
            psql.setInt(1, courseToTest.getCourseId());
            psql.setInt(2, courseToTest.getTestId());
            psql.executeUpdate();
            psql.close();
            connection.close();
        } catch (SQLException e) {
            return null;
        }
        return "ok";
    }

    public String deleteCourseToTestInDB(CourseToTestDTO courseToTest) {
        Connection connection = ConnectionToMySQLDB.getConnaction();
        if(connection==null){
            return null;
        }
        try {
            String sql = "delete from coursetotest where coursetotest.courseId = ? " +
                    "AND coursetotest.testId = ?;";
            PreparedStatement psql = connection.prepareStatement(sql);
            psql.setInt(1, courseToTest.getCourseId());
            psql.setInt(2, courseToTest.getTestId());
            psql.executeUpdate();
            psql.close();;
            connection.close();
        } catch (SQLException e) {
            return null;
        }
        return "ok";
    }
}
