package dao;

import dto.ChapterDTO;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ChapterDAO {

    //private static final String SERVER_UPLOAD_LOCATION_FOLDER = "C://IdeaProjects//quizFiles//";
    //private static final String SERVER_UPLOAD_LOCATION_FOLDER = "/var/docs/"+ContextName.getName()+"/";
    private static final String SERVER_UPLOAD_LOCATION_FOLDER = "/var/dis/docs/";

    public ArrayList<ChapterDTO> getChaptersByCourseId(int courseId) {
        ArrayList<ChapterDTO> chapters = new ArrayList<ChapterDTO>();
        Connection connection = ConnectionToMySQLDB.getConnaction();
        if(connection==null){
            return null;
        }
        try {
            String sql = "SELECT * FROM chapters WHERE chapters.courseId = ?";
            PreparedStatement psql = connection.prepareStatement(sql);
            psql.setInt(1, courseId);
            ResultSet rs = psql.executeQuery();
            while (rs.next()) {
                chapters.add(new ChapterDTO(rs.getInt("chapterId"), courseId,  rs.getString("title"), rs.getString("description")));
            }
            rs.close();
            psql.close();
            connection.close();
        } catch (SQLException e) {
            return null;
        }
        return chapters;
    }

    public ChapterDTO getChapterByIdFromDb(int id) {
        ChapterDTO chapter = new ChapterDTO();
        String sql = "SELECT * FROM chapters " +
                "WHERE chapters.chapterId = ?";
        try {
            Connection connection = ConnectionToMySQLDB.getConnaction();
            PreparedStatement psql = connection.prepareStatement(sql);
            psql.setInt(1, id);
            ResultSet rs = psql.executeQuery();;
            while (rs.next()) {
                chapter.setTitle(rs.getString("title"));
                chapter.setDescription(rs.getString("description"));
                chapter.setCourseId(rs.getInt("courseId"));
            }
            rs.close();
            psql.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        chapter.setId(id);
        chapter.setTopics(new TopicDAO().getTopicsByChapterId(id));
        return chapter;
    }

    public ChapterDTO setChapterInDB(ChapterDTO chapter) {
        Connection connection = ConnectionToMySQLDB.getConnaction();
        if(connection==null){
            return null;
        }
        ChapterDTO responseChapter = new ChapterDTO();
        try {
            String newTitle = chapter.getTitle().replaceAll("\'","\"");
            String sql = "INSERT INTO chapters " +
                    "(chapters.title, chapters.courseId) " +
                    "VALUES (?, ?)";
            PreparedStatement psql = connection.prepareStatement(sql);
            psql.setString(1, newTitle);
            psql.setInt(2, chapter.getCourseId());
            psql.executeUpdate();
            sql = "SELECT MAX(chapters.chapterId) FROM chapters";
            psql = connection.prepareStatement(sql);
            ResultSet rs = psql.executeQuery();
            while (rs.next()) {
                responseChapter.setId(rs.getInt("MAX(chapters.chapterId)"));
            }
            responseChapter.setTitle(chapter.getTitle());
            responseChapter.setCourseId(chapter.getCourseId());
            rs.close();
            psql.close();
            connection.close();
        } catch (SQLException e) {
            return null;
        }
        createDirectories(responseChapter.getCourseId(), responseChapter.getId());
        return responseChapter;
    }

    public String updateChapterInDB(int id, ChapterDTO chapter) {
        Connection connection = ConnectionToMySQLDB.getConnaction();
        if(connection==null){
            return null;
        }
        try {
            String newTitle = chapter.getTitle().replaceAll("\'","\"");
            String newDescription = chapter.getDescription().replaceAll("\'","\"");
            String sql = "UPDATE chapters " +
                    "SET chapters.title = ?, chapters.description = ?" +
                    " WHERE chapters.chapterId = ?";
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

    public String deleteChapterFromDB(int id) {
        Connection connection = ConnectionToMySQLDB.getConnaction();
        Integer courseId = null;
        if(connection==null){
            return null;
        }
        try {
            String sql = "select courseId from chapters WHERE chapterId = ?";
            PreparedStatement psql = connection.prepareStatement(sql);
            psql.setInt(1, id);
            ResultSet rs = psql.executeQuery();
            while (rs.next()) {
                courseId = rs.getInt("courseId");
            }
            sql = "delete from chapters where chapters.chapterId = ?;";
            psql = connection.prepareStatement(sql);
            psql.setInt(1, id);
            psql.executeUpdate();
            rs.close();
            psql.close();;
            connection.close();
        } catch (SQLException e) {
            return null;
        }
        deleteDirectories(courseId, id);
        return "ok";
    }

    //work with dir

    private void createDirectories(Integer courseId, Integer chapterId) {
        //File dir = new File(SERVER_UPLOAD_LOCATION_FOLDER + courseId+"//"+chapterId);
        File dir = new File(SERVER_UPLOAD_LOCATION_FOLDER + courseId+"/"+chapterId);
        boolean created = dir.mkdir();
    }

    private void deleteDirectories(Integer courseId, Integer chapterId) {
        try {
            //FileUtils.deleteDirectory(new File(SERVER_UPLOAD_LOCATION_FOLDER + courseId+"//"+chapterId));
            FileUtils.deleteDirectory(new File(SERVER_UPLOAD_LOCATION_FOLDER + courseId+"/"+chapterId));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
