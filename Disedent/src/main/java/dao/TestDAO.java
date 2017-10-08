package dao;

import dto.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;


public class TestDAO {

    public ArrayList<TestDTO> getAllTestsFromDb() {
        ArrayList<TestDTO> tests = new ArrayList<TestDTO>();
        Connection connection = ConnectionToMySQLDB.getConnaction();
        if (connection == null) {
            return null;
        }
        try {
            String sql = "SELECT * FROM tests";
            PreparedStatement psql = connection.prepareStatement(sql);
            ResultSet rs = psql.executeQuery();
            while (rs.next()) {
                tests.add(new TestDTO(rs.getInt("testId"), rs.getString("title"),
                        rs.getString("description"), rs.getInt("amountQuestions"),
                        rs.getInt("timeLimit"), rs.getInt("attempts")));
            }
            rs.close();
            psql.close();
            connection.close();
        } catch (SQLException e) {
            return null;
        }
        return tests;
    }

    public ArrayList<TestShortDTO> getShortTestsByCourseId(int courseId) {
        ArrayList<TestShortDTO> tests = new ArrayList<TestShortDTO>();
        ArrayList<Integer> testIds = new ArrayList<Integer>();
        Connection connection = ConnectionToMySQLDB.getConnaction();
        if (connection == null) {
            return null;
        }
        try {
            String sql = "SELECT coursetotest.testId FROM coursetotest WHERE coursetotest.courseId = ?";
            PreparedStatement psql = connection.prepareStatement(sql);
            psql.setInt(1, courseId);
            ResultSet rs = psql.executeQuery();
            while (rs.next()) {
                testIds.add(rs.getInt("testId"));
            }
            rs.close();
            psql.close();
            connection.close();
        } catch (SQLException e) {
            return null;
        }
        for (Integer i :
                testIds) {
            tests.add(getShortTestById(i));
        }
        return tests;
    }

    private TestShortDTO getShortTestById(int testId) {
        TestShortDTO test = new TestShortDTO();
        Connection connection = ConnectionToMySQLDB.getConnaction();
        if (connection == null) {
            return null;
        }
        try {
            String sql = "SELECT * FROM tests WHERE tests.testId = ?";
            PreparedStatement psql = connection.prepareStatement(sql);
            psql.setInt(1, testId);
            ResultSet rs = psql.executeQuery();
            while (rs.next()) {
                test.setId(rs.getInt("testId"));
                test.setTitle(rs.getString("title"));
                test.setDescription(rs.getString("description"));
            }
            rs.close();
            psql.close();
            connection.close();
        } catch (SQLException e) {
            return null;
        }
        return test;
    }

    public TestDTO getTestByIdFromDb(int id) {
        if (id == 0) {
            return new TestDTO(0, "", "", null, null, null);
        }
        TestDTO test = new TestDTO();
        String sql = "SELECT * FROM tests " +
                "WHERE tests.testId = ?";
        try {
            Connection connection = ConnectionToMySQLDB.getConnaction();
            PreparedStatement psql = connection.prepareStatement(sql);
            psql.setInt(1, id);
            ResultSet rs = psql.executeQuery();
            ;
            while (rs.next()) {
                test.setTitle(rs.getString("title"));
                test.setDescription(rs.getString("description"));
                test.setQuestionsAmount(rs.getInt("amountQuestions"));
                test.setTimeLimit(rs.getInt("timeLimit"));
                test.setAttempts(rs.getInt("attempts"));
            }

            rs.close();
            psql.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        test.setId(id);
        test.setQuestions(new QuestionDAO().getQuestionsByTestId(id));
        return test;
    }

    public TestDTO setTestInDB(TestDTO test) {
        Connection connection = ConnectionToMySQLDB.getConnaction();
        if (connection == null) {
            return null;
        }
        TestDTO responseTest = new TestDTO();
        try {
            String newTitle = test.getTitle().replaceAll("\'", "\"");
            String newDescription = test.getDescription().replaceAll("\'", "\"");
            String sql = "INSERT INTO tests " +
                    "(tests.title, tests.description, " +
                    "tests.amountQuestions, tests.timeLimit, tests.attempts) " +
                    "VALUES (?, ?, ?, ?, ?)";
            PreparedStatement psql = connection.prepareStatement(sql);
            psql.setString(1, newTitle);
            psql.setString(2, newDescription);
            psql.setInt(3, test.getQuestionsAmount());
            psql.setInt(4, test.getTimeLimit());
            psql.setInt(5, test.getAttempts());
            psql.executeUpdate();
            sql = "SELECT MAX(tests.testId) FROM tests";
            psql = connection.prepareStatement(sql);
            ResultSet rs = psql.executeQuery();
            while (rs.next()) {
                responseTest.setId(rs.getInt("MAX(tests.testId)"));
            }
            responseTest.setTitle(test.getTitle());
            responseTest.setDescription(test.getDescription());
            responseTest.setQuestionsAmount(test.getQuestionsAmount());
            responseTest.setTimeLimit(test.getTimeLimit());
            responseTest.setAttempts(test.getAttempts());
            rs.close();
            psql.close();
            connection.close();
        } catch (SQLException e) {
            return null;
        }
        return responseTest;
    }

    public String updateTestInDB(int id, TestDTO test) {
        Connection connection = ConnectionToMySQLDB.getConnaction();
        if (connection == null) {
            return null;
        }
        try {
            String newTitle = test.getTitle().replaceAll("\'", "\"");
            String newDescription = test.getDescription().replaceAll("\'", "\"");
            String sql = "UPDATE tests " +
                    "SET tests.title = ?, tests.description = ?, " +
                    "tests.amountQuestions = ?, tests.timelimit = ?, tests.attempts = ? " +
                    " WHERE tests.testId = ?";
            PreparedStatement psql = connection.prepareStatement(sql);
            psql.setString(1, newTitle);
            psql.setString(2, newDescription);
            psql.setInt(3, test.getQuestionsAmount());
            psql.setInt(4, test.getTimeLimit());
            psql.setInt(5, test.getAttempts());
            psql.setInt(6, id);
            psql.executeUpdate();
            psql.close();
            connection.close();
        } catch (SQLException e) {
            return null;
        }
        return "ok";
    }

    public String deleteTestFromDB(int id) {
        Connection connection = ConnectionToMySQLDB.getConnaction();
        if (connection == null) {
            return null;
        }
        try {
            String sql = "delete from coursetotest where coursetotest.testId = ?;";
            PreparedStatement psql = connection.prepareStatement(sql);
            psql.setInt(1, id);
            psql.executeUpdate();
            sql = "delete from tests where tests.testId = ?;";
            psql = connection.prepareStatement(sql);
            psql.setInt(1, id);
            psql.executeUpdate();
            psql.close();
            ;
            connection.close();
        } catch (SQLException e) {
            return null;
        }
        return "ok";
    }

    public TestDTO getTestInfoByIdFromDb(int orderId, int id) {
        TestDTO test = new TestDTO();
        int userAttempts = 0;
        String sql = "SELECT * FROM tests " +
                "WHERE tests.testId = ?";
        try {
            Connection connection = ConnectionToMySQLDB.getConnaction();
            PreparedStatement psql = connection.prepareStatement(sql);
            psql.setInt(1, id);
            ResultSet rs = psql.executeQuery();
            while (rs.next()) {
                test.setTitle(rs.getString("title"));
                test.setDescription(rs.getString("description"));
                test.setQuestionsAmount(rs.getInt("amountQuestions"));
                test.setTimeLimit(rs.getInt("timeLimit"));
                userAttempts = rs.getInt("attempts");
            }
            sql = "SELECT COUNT(*) FROM results " +
                    "WHERE results.testId = ? AND results.orderId = ?";
            psql = connection.prepareStatement(sql);
            psql.setInt(1, id);
            psql.setInt(2, orderId);
            rs = psql.executeQuery();
            while (rs.next()) {
                userAttempts = userAttempts - rs.getInt("COUNT(*)");
            }
            rs.close();
            psql.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        test.setId(id);
        test.setAttempts(userAttempts);
        return test;
    }

    public TestViewDTO getTestViewByIdFromDb(int orderId, int id) {
        TestViewDTO responseTest = new TestViewDTO();
        Integer resultId = new ResultDAO().setResultInDB(orderId, id);
        if(resultId==null){
            return null;
        }
        TestDTO test = getTestByIdFromDb(id);
        if(resultId==null){
            return null;
        }
        ArrayList <QuestionDTO> allQuestions = new ArrayList<QuestionDTO>(test.getQuestions());
        for (QuestionDTO q:
                allQuestions) {
            q.setAnswers(new AnswerDAO().getAnswersByQuestionId(q.getId()));
            for (AnswerDTO a :
                    q.getAnswers()) {
                a.setIsCorrect(false);
            }
        }
        ArrayList <QuestionDTO> forTestQuestions = new ArrayList<QuestionDTO>(getQuestionsForTest(test.getQuestionsAmount(), allQuestions));
        responseTest.setId(test.getId());
        responseTest.setTitle(test.getTitle());
        responseTest.setQuestionsAmount(test.getQuestionsAmount());
        responseTest.setResultId(resultId);
        responseTest.setOrderId(orderId);
        responseTest.setTimeLimit(test.getTimeLimit());
        responseTest.setQuestions(forTestQuestions);
        return responseTest;
    }

    private ArrayList<QuestionDTO> getQuestionsForTest(Integer questionsAmount, ArrayList<QuestionDTO> allQuestions) {
        ArrayList<QuestionDTO> questions = new ArrayList<QuestionDTO>();
        Random random = new Random();
        if(allQuestions.size()==0||allQuestions==null) {
            return null;
        }
        if(allQuestions.size()==questionsAmount){
            return allQuestions;
        }
        if(allQuestions.size()<questionsAmount){
            for (int i=0; i<questionsAmount; i++){
                int questionNumber = random.nextInt(allQuestions.size() - 1);
                questions.add(allQuestions.get(questionNumber));
            }

        }
        else{
            for (int i=0; i<questionsAmount; i++){
                int questionNumber = random.nextInt(allQuestions.size() - 1);
                questions.add(allQuestions.get(questionNumber));
                allQuestions.remove(questionNumber);
            }
        }
        return questions;
    }
}
