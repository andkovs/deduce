package dao;

import dto.AnswerDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class AnswerDAO {
    public ArrayList<AnswerDTO> getAnswersByQuestionId(int questionId) {
        ArrayList<AnswerDTO> answers = new ArrayList<AnswerDTO>();
        Connection connection = ConnectionToMySQLDB.getConnaction();
        if(connection==null){
            return null;
        }
        try {
            String sql = "SELECT * FROM answers WHERE answers.questionId = ?";
            PreparedStatement psql = connection.prepareStatement(sql);
            psql.setInt(1, questionId);
            ResultSet rs = psql.executeQuery();
            while (rs.next()) {
                Boolean isCorrect = true;
                if(rs.getString("iscorrect").equals("n")){
                    isCorrect = false;
                }
                answers.add(new AnswerDTO(rs.getInt("answerId"), questionId, rs.getString("title"), isCorrect));
            }
            rs.close();
            psql.close();
            connection.close();
        } catch (SQLException e) {
            return null;
        }
        return answers;
    }

    public String setAnswerInDb(AnswerDTO answer, Integer questionId) {
        Connection connection = ConnectionToMySQLDB.getConnaction();
        if(connection==null){
            return null;
        }
        try {
            String newTitle = answer.getTitle().replaceAll("\'","\"");
            String sql = "INSERT INTO answers " +
                    "(answers.title, answers.questionId, answers.iscorrect) " +
                    "VALUES (?, ?, ?)";
            PreparedStatement psql = connection.prepareStatement(sql);
            psql.setString(1, newTitle);
            psql.setInt(2, questionId);
            if (answer.getIsCorrect()){
                psql.setString(3, "y");
            }
            else {
                psql.setString(3, "n");
            }
            psql.executeUpdate();
            psql.close();
            connection.close();
        } catch (SQLException e) {
            return null;
        }
        return "ok";
    }

    public String deleteAnswersFromDbByQuestionId(Integer questionId){
        Connection connection = ConnectionToMySQLDB.getConnaction();
        if(connection==null){
            return null;
        }
        try {
            String sql = "delete from answers where answers.questionId = ?;";
            PreparedStatement psql = connection.prepareStatement(sql);
            psql.setInt(1, questionId);
            psql.executeUpdate();
            psql.close();;
            connection.close();
        } catch (SQLException e) {
            return null;
        }
        return "ok";
    }

    public AnswerDTO getAnswerById(Integer id) {
        AnswerDTO answer = new AnswerDTO();
        Connection connection = ConnectionToMySQLDB.getConnaction();
        if(connection==null){
            return null;
        }
        try {
            String sql = "SELECT * FROM answers WHERE answers.answerId = ?";
            PreparedStatement psql = connection.prepareStatement(sql);
            psql.setInt(1, id);
            ResultSet rs = psql.executeQuery();
            while (rs.next()) {
                answer.setId(id);
                answer.setTitle(rs.getString("title"));
                answer.setQuestionId(rs.getInt("questionId"));
                Boolean isCorrect = true;
                if(rs.getString("iscorrect").equals("n")){
                    isCorrect = false;
                }
                answer.setIsCorrect(isCorrect);
            }
            rs.close();
            psql.close();
            connection.close();
        } catch (SQLException e) {
            return null;
        }
        return answer;
    }
}
