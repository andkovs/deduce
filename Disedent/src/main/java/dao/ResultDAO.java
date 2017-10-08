package dao;

import dto.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ResultDAO {

    public ArrayList<UserAnswerDTO> getUserAnswersByResultId(int id) {
        ArrayList<UserAnswerDTO> userAnswers = new ArrayList<UserAnswerDTO>();
        Connection connection = ConnectionToMySQLDB.getConnaction();
        if (connection == null) {
            return null;
        }
        try {
            String sql = "SELECT * FROM useranswers WHERE useranswers.resultId = ?";
            PreparedStatement psql = connection.prepareStatement(sql);
            psql.setInt(1, id);
            ResultSet rs = psql.executeQuery();
            while (rs.next()) {
                String isCorrect = "Неверно";
                if (rs.getString("iscorrect").equals("y")) {
                    isCorrect = "Верно";
                }
                userAnswers.add(new UserAnswerDTO(rs.getString("question"),
                        rs.getString("answer"), isCorrect));

            }
            rs.close();
            psql.close();
            connection.close();
        } catch (SQLException e) {
            return null;
        }
        return userAnswers;


    }

    public Integer setResultInDB(Integer orderId, Integer testId) {
        Connection connection = ConnectionToMySQLDB.getConnaction();
        if (connection == null) {
            return null;
        }
        Integer resultId = null;
        try {
            String sql = "INSERT INTO results " +
                    "(results.orderId, results.testId, results.startTime) " +
                    "VALUES (?, ?, ?)";
            PreparedStatement psql = connection.prepareStatement(sql);
            psql.setInt(1, orderId);
            psql.setInt(2, testId);
            psql.setString(3, getNow());
            psql.executeUpdate();
            sql = "SELECT MAX(results.resultId) FROM results";
            psql = connection.prepareStatement(sql);
            ResultSet rs = psql.executeQuery();
            while (rs.next()) {
                resultId = rs.getInt("MAX(results.resultId)");
            }
            rs.close();
            psql.close();
            connection.close();
        } catch (SQLException e) {
            return null;
        }
        return resultId;
    }

    public String updateResultInDB(int id, TestViewDTO test) {
        Connection connection = ConnectionToMySQLDB.getConnaction();
        if (connection == null) {
            return null;
        }
        int amountQuestions = test.getQuestions().size();
        int amountCorrectAnswers = 0;

        for (QuestionDTO q :
                test.getQuestions()) {
            int count = 0;
            for (AnswerDTO a :
                    q.getAnswers()) {
                if (a.getIsCorrect()) {
                    AnswerDTO answer = new AnswerDAO().getAnswerById(a.getId());
                    if (answer.getIsCorrect()) {
                        setUserAnswerInDB(q.getTitle(), a.getTitle(), true, test.getResultId());
                        amountCorrectAnswers++;
                    } else {
                        setUserAnswerInDB(q.getTitle(), a.getTitle(), false, test.getResultId());
                    }
                    break;
                }
                count++;
                if (count == 4) {
                    setUserAnswerInDB(q.getTitle(), "без ответа", false, test.getResultId());
                    break;
                }
            }
        }
        try {
            String sql = "UPDATE results " +
                    "SET results.finishTime = ?, results.amountQuestions = ?, " +
                    "results.amountCorrectAnswers = ?" +
                    " WHERE results.resultId = ?";
            PreparedStatement psql = connection.prepareStatement(sql);
            psql.setString(1, getNow());
            psql.setInt(2, amountQuestions);
            psql.setInt(3, amountCorrectAnswers);
            psql.setInt(4, id);
            psql.executeUpdate();
            psql.close();
            connection.close();
        } catch (SQLException e) {
            return null;
        }
        return "ok";
    }

    private String setUserAnswerInDB(String question, String answer, boolean b, Integer resultId) {
        Connection connection = ConnectionToMySQLDB.getConnaction();
        if (connection == null) {
            return null;
        }
        try {
            String sql = "INSERT INTO useranswers " +
                    "(useranswers.question, useranswers.answer, useranswers.isCorrect, useranswers.resultId) " +
                    "VALUES (?, ?, ?, ?)";
            PreparedStatement psql = connection.prepareStatement(sql);
            psql.setString(1, question);
            psql.setString(2, answer);
            if (b) {
                psql.setString(3, "y");
            } else {
                psql.setString(3, "n");
            }
            psql.setInt(4, resultId);
            psql.executeUpdate();
            psql.close();
            connection.close();
        } catch (SQLException e) {
            return null;
        }
        return "ok";
    }

    public ResultDTO getResultByIdFromDb(int id) {
        ResultDTO result = new ResultDTO();
        String sql = "SELECT * FROM results " +
                "WHERE results.resultId = ?";
        try {
            Connection connection = ConnectionToMySQLDB.getConnaction();
            PreparedStatement psql = connection.prepareStatement(sql);
            psql.setInt(1, id);
            ResultSet rs = psql.executeQuery();
            ;
            while (rs.next()) {
                TestDTO test = new TestDAO().getTestByIdFromDb(rs.getInt("testId"));
                result.setOrderId(rs.getInt("orderId"));
                result.setTestTitle(test.getTitle());
                result.setStartTime(parseDateForViewFormat(rs.getString("startTime")));
                result.setFinishTime(parseDateForViewFormat(rs.getString("finishTime")));
                result.setAmountCorrectAnswers(rs.getInt("amountCorrectAnswers"));
                result.setAmountQuestions(rs.getInt("amountQuestions"));
            }

            rs.close();
            psql.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        result.setId(id);
        return result;
    }

    public ArrayList<ResultForListDTO> getAllResultsByGroupIdFromDb(int groupId) {
        ArrayList<ResultForListDTO> responseResults = new ArrayList<ResultForListDTO>();
        ArrayList<OrderForListDTO> orders = new OrderDAO().getOrdersByGroupIdFromDb(groupId);
        for (OrderForListDTO o :
                orders) {
            ArrayList<ResultDTO> results = getAllResultsByOrderIdFromDb(o.getId());
            UserDTO user = new UserDAO().getUserByIdFromDb(o.getUserId());
            for (ResultDTO r :
                    results) {
                responseResults.add(new ResultForListDTO(r.getId(), user.getFirstName(), user.getLastName(), user.getMiddleName(),
                        r.getTestTitle(), r.getFinishTime(), r.getAmountQuestions(), r.getAmountCorrectAnswers()));
            }

        }
        return responseResults;
    }

    public ArrayList<ResultDTO> getAllResultsByOrderIdFromDb(int orderId) {
        ArrayList<ResultDTO> results = new ArrayList<ResultDTO>();
        Connection connection = ConnectionToMySQLDB.getConnaction();
        if (connection == null) {
            return null;
        }
        try {
            String sql = "SELECT * FROM results WHERE results.orderId = ?";
            PreparedStatement psql = connection.prepareStatement(sql);
            psql.setInt(1, orderId);
            ResultSet rs = psql.executeQuery();
            while (rs.next()) {
                TestDTO test = new TestDAO().getTestByIdFromDb(rs.getInt("testId"));
                results.add(new ResultDTO(rs.getInt("resultId"),
                        test.getTitle(), parseDateForViewFormat(rs.getString("startTime")),
                        parseDateForViewFormat(rs.getString("finishTime")), rs.getInt("amountQuestions"), rs.getInt("amountCorrectAnswers")));
            }
            rs.close();
            psql.close();
            connection.close();
        } catch (SQLException e) {
            return null;
        }
        return results;
    }

    public String deleteResultFromDB(int id) {
        Connection connection = ConnectionToMySQLDB.getConnaction();
        if (connection == null) {
            return null;
        }
        try {
            String sql = "delete from results where results.resultId = ?;";
            PreparedStatement psql = connection.prepareStatement(sql);
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

    //work with date

    private String parseDateForViewFormat(String date) {
        String s = date.substring(8, 10) + "-" + date.substring(5, 7) + "-" + date.substring(0, 4) + " "
                + date.substring(11, 13) + ":" + date.substring(14, 16);
        return s;
    }

    private String parseDateForMySQLDateFormat(String date) {
        date = date.substring(6, 10) + "-" + date.substring(3, 5) + "-" + date.substring(0, 2) + " " + date.substring(11, 16).replaceAll(":", "-");
        return date;
    }

    private String getNow() {
        Date d = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(d);
    }


    public ArrayList<ResultUserDTO> getAllResultsByUserId(int id) {
        ArrayList<ResultUserDTO> userResults = new ArrayList<ResultUserDTO>();
        ArrayList<OrderUserDTO> orders = new OrderDAO().getOrderByUserIdFromDbForStudentResult(id);
        if (orders == null) {
            return null;
        }
        for (OrderUserDTO o :
                orders) {
            userResults.add(new ResultUserDTO(o, new ResultDAO().getAllResultsByOrderIdFromDb(o.getId())));
        }
        return userResults;
    }
}
