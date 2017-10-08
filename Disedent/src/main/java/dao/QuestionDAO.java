package dao;


import dto.AnswerDTO;
import dto.QuestionDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class QuestionDAO {


    public ArrayList<QuestionDTO> getQuestionsByTestId(int testId) {
        ArrayList<QuestionDTO> questions = new ArrayList<QuestionDTO>();
        Connection connection = ConnectionToMySQLDB.getConnaction();
        if(connection==null){
            return null;
        }
        try {
            String sql = "SELECT * FROM questions WHERE questions.testId = ?";
            PreparedStatement psql = connection.prepareStatement(sql);
            psql.setInt(1, testId);
            ResultSet rs = psql.executeQuery();
            while (rs.next()) {
                questions.add(new QuestionDTO(rs.getInt("questionId"), testId,  rs.getString("title")));
            }
            rs.close();
            psql.close();
            connection.close();
        } catch (SQLException e) {
            return null;
        }
        return questions;
    }

    public QuestionDTO getQuestionByIdFromDb(int id) {
        QuestionDTO question = new QuestionDTO();
        String sql = "SELECT * FROM questions " +
                "WHERE questions.questionId = ?";
        try {
            Connection connection = ConnectionToMySQLDB.getConnaction();
            PreparedStatement psql = connection.prepareStatement(sql);
            psql.setInt(1, id);
            ResultSet rs = psql.executeQuery();;
            while (rs.next()) {
                question.setTitle(rs.getString("title"));
                question.setTestId(rs.getInt("testId"));
            }
            rs.close();
            psql.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        question.setId(id);
        question.setAnswers(new AnswerDAO().getAnswersByQuestionId(id));
        return question;
    }

    public QuestionDTO setQuestionInDB(QuestionDTO question) {
        Connection connection = ConnectionToMySQLDB.getConnaction();
        if(connection==null){
            return null;
        }
        QuestionDTO responseQuestion = new QuestionDTO();
        try {
            String newTitle = question.getTitle().replaceAll("\'","\"");
            String sql = "INSERT INTO questions " +
                    "(questions.title, questions.testId) " +
                    "VALUES (?, ?)";
            PreparedStatement psql = connection.prepareStatement(sql);
            psql.setString(1, newTitle);
            psql.setInt(2, question.getTestId());
            psql.executeUpdate();
            sql = "SELECT MAX(questions.questionId) FROM questions";
            psql = connection.prepareStatement(sql);
            ResultSet rs = psql.executeQuery();
            while (rs.next()) {
                responseQuestion.setId(rs.getInt("MAX(questions.questionId)"));
            }
            responseQuestion.setTitle(question.getTitle());
            responseQuestion.setTestId(question.getTestId());
            rs.close();
            psql.close();
            connection.close();
        } catch (SQLException e) {
            return null;
        }
        return responseQuestion;
    }

    public String updateQuestionInDB(int id, QuestionDTO question) {
        Connection connection = ConnectionToMySQLDB.getConnaction();
        if(connection==null){
            return null;
        }
        try {
            String newTitle = question.getTitle().replaceAll("\'","\"");
            String sql = "UPDATE questions " +
                    "SET questions.title = ?" +
                    " WHERE questions.questionId = ?";
            PreparedStatement psql = connection.prepareStatement(sql);
            psql.setString(1, newTitle);
            psql.setInt(2, id);
            psql.executeUpdate();
            psql.close();
            connection.close();
        } catch (SQLException e) {
            return null;
        }
        AnswerDAO dao = new AnswerDAO();
        dao.deleteAnswersFromDbByQuestionId(question.getId());
        for (AnswerDTO a :
                question.getAnswers()) {
            dao.setAnswerInDb(a, question.getId());
        }
        return "ok";
    }

    public String deleteQuestionFromDB(int id) {
        Connection connection = ConnectionToMySQLDB.getConnaction();
        if(connection==null){
            return null;
        }
        try {
            String sql = "delete from questions where questions.questionId = ?;";
            PreparedStatement psql = connection.prepareStatement(sql);
            psql.setInt(1, id);
            psql.executeUpdate();
            psql.close();;
            connection.close();
        } catch (SQLException e) {
            return null;
        }
        return "ok";
    }
}
