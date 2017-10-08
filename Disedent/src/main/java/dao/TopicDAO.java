package dao;

import dto.TopicDTO;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TopicDAO {

    //private static final String SERVER_UPLOAD_LOCATION_FOLDER = "C://IdeaProjects//quizFiles//";
    //private static final String SERVER_UPLOAD_LOCATION_FOLDER = "/var/docs/"+ContextName.getName()+"/";
    private static final String SERVER_UPLOAD_LOCATION_FOLDER = "/var/dis/docs/";

    public ArrayList<TopicDTO> getTopicsByChapterId(int chapterId) {
        ArrayList<TopicDTO> topics = new ArrayList<TopicDTO>();
        Connection connection = ConnectionToMySQLDB.getConnaction();
        if (connection == null) {
            return null;
        }
        try {
            String sql = "SELECT * FROM topics WHERE topics.chapterId = ?";
            PreparedStatement psql = connection.prepareStatement(sql);
            psql.setInt(1, chapterId);
            ResultSet rs = psql.executeQuery();
            while (rs.next()) {
                Boolean isFile = true;
                if (rs.getString("isFile").equals("n")) {
                    isFile = false;
                }
                topics.add(new TopicDTO(rs.getInt("topicId"), chapterId, rs.getString("title"), rs.getString("description"), isFile));
            }
            rs.close();
            psql.close();
            connection.close();
        } catch (SQLException e) {
            return null;
        }
        return topics;
    }

    public TopicDTO getTopicByIdFromDb(int id) {
        TopicDTO topic = new TopicDTO();
        String sql = "SELECT * FROM topics " +
                "WHERE topics.topicId = ?";
        try {
            Connection connection = ConnectionToMySQLDB.getConnaction();
            PreparedStatement psql = connection.prepareStatement(sql);
            psql.setInt(1, id);
            ResultSet rs = psql.executeQuery();
            ;
            while (rs.next()) {
                topic.setChapterId(rs.getInt("chapterId"));
                topic.setTitle(rs.getString("title"));
                topic.setDescription(rs.getString("description"));
                if (rs.getString("isFile").equals("n")) {
                    topic.setFile(false);
                } else {
                    topic.setFile(true);
                }

            }
            rs.close();
            psql.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        topic.setId(id);
        return topic;
    }

    public TopicDTO setTopicInDB(TopicDTO topic) {
        Connection connection = ConnectionToMySQLDB.getConnaction();
        if (connection == null) {
            return null;
        }
        TopicDTO responseTopic = new TopicDTO();
        try {
            String newTitle = topic.getTitle().replaceAll("\'", "\"");
            String sql = "INSERT INTO topics " +
                    "(topics.title, topics.chapterId) " +
                    "VALUES (?, ?)";
            PreparedStatement psql = connection.prepareStatement(sql);
            psql.setString(1, newTitle);
            psql.setInt(2, topic.getChapterId());
            psql.executeUpdate();
            sql = "SELECT MAX(topics.topicId) FROM topics";
            psql = connection.prepareStatement(sql);
            ResultSet rs = psql.executeQuery();
            while (rs.next()) {
                responseTopic.setId(rs.getInt("MAX(topics.topicId)"));
            }
            responseTopic.setTitle(topic.getTitle());
            responseTopic.setChapterId(topic.getChapterId());
            rs.close();
            psql.close();
            connection.close();
        } catch (SQLException e) {
            return null;
        }
        return responseTopic;
    }

    public String updateTopicInDB(int id, TopicDTO topic) {
        Connection connection = ConnectionToMySQLDB.getConnaction();
        if (connection == null) {
            return null;
        }
        try {
            String newTitle = topic.getTitle().replaceAll("\'", "\"");
            String newDescription = topic.getDescription().replaceAll("\'", "\"");
            String sql = "UPDATE topics " +
                    "SET topics.title = ?, topics.description = ?" +
                    " WHERE topics.topicId = ?";
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

    public String deleteTopicFromDB(int id) {
        Connection connection = ConnectionToMySQLDB.getConnaction();
        if (connection == null) {
            return null;
        }
        Integer courseId = null;
        Integer chapterId = null;
        try {
            String sql = "select chapterId from topics WHERE topics.topicId = ?";
            PreparedStatement psql = connection.prepareStatement(sql);
            psql.setInt(1, id);
            ResultSet rs = psql.executeQuery();
            while (rs.next()) {
                chapterId = rs.getInt("chapterId");
            }
            sql = "select courseId from chapters WHERE chapterId = ?";
            psql = connection.prepareStatement(sql);
            psql.setInt(1, chapterId);
            rs = psql.executeQuery();
            while (rs.next()) {
                courseId = rs.getInt("courseId");
            }
            sql = "delete from topics where topics.topicId = ?;";
            psql = connection.prepareStatement(sql);
            psql.setInt(1, id);
            psql.executeUpdate();
            rs.close();
            psql.close();
            ;
            connection.close();
        } catch (SQLException e) {
            return null;
        }
        deleteFile(courseId, chapterId, id);
        return "ok";
    }

    public String updateIsFileInTopicById(Integer id, String isFile) {
        Connection connection = ConnectionToMySQLDB.getConnaction();
        if (connection == null) {
            return null;
        }
        try {

            String sql = "UPDATE topics " +
                    "SET topics.isFile = ?" +
                    " WHERE topics.topicId = ?";
            PreparedStatement psql = connection.prepareStatement(sql);
            psql.setString(1, isFile);
            psql.setInt(2, id);
            psql.executeUpdate();
            psql.close();
            connection.close();
        } catch (SQLException e) {
            return null;
        }
        return "ok";
    }

    private void deleteFile(Integer courseId, Integer chapterId, Integer topicId) {
        //File dir = new File(SERVER_UPLOAD_LOCATION_FOLDER + courseId+"//"+chapterId+"//"+topicId+".pdf");
        File dir = new File(SERVER_UPLOAD_LOCATION_FOLDER + courseId + "/" + chapterId + "/" + topicId + ".pdf");
        dir.delete();
    }
}
