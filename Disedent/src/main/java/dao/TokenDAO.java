package dao;

import dto.TokenDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TokenDAO {

    public String getTokenFromDB(String token) {
        Connection connection = ConnectionToMySQLDB.getConnaction();
        if (connection == null) {
            return null;
        }
        String t = null;
        String sql = "SELECT token FROM users " +
                "WHERE users.token = ?";
        try {
            PreparedStatement psql = connection.prepareStatement(sql);
            psql.setString(1, token);
            ResultSet rs = psql.executeQuery();
            while (rs.next()) {
                t = rs.getString("token");
            }
            rs.close();
            psql.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    public TokenDTO getTokenByLoginFromDB(String login) {
        Connection connection = ConnectionToMySQLDB.getConnaction();
        if (connection == null) {
            return null;
        }
        TokenDTO token = new TokenDTO();
        String sql = "SELECT token, tokentime FROM users " +
                "WHERE users.login = ?";
        try {
            PreparedStatement psql = connection.prepareStatement(sql);
            psql.setString(1, login);
            ResultSet rs = psql.executeQuery();
            while (rs.next()) {
                token.setToken(rs.getString("token"));
                token.setDate(rs.getString("tokentime"));
            }
            rs.close();
            psql.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return token;
    }

    public String updateTokenInDB(String login, String token) {
        Connection connection = ConnectionToMySQLDB.getConnaction();
        if (connection == null) {
            return null;
        }
        String sql = null;
        sql = "UPDATE users " +
                "SET users.token = ? " +
                " WHERE users.login = ?";

        try {
            PreparedStatement psql = connection.prepareStatement(sql);
            psql.setString(1, token);
            psql.setString(2, login);
            psql.executeUpdate();
            psql.close();
            connection.close();
        } catch (SQLException e) {
            return null;
        }
        return "ok";
    }

    public String getTokenTimeFromDB(String token) {
        Connection connection = ConnectionToMySQLDB.getConnaction();
        if (connection == null) {
            return null;
        }
        String t = null;
        String sql = "SELECT tokentime FROM users " +
                "WHERE users.token = ?";
        try {
            PreparedStatement psql = connection.prepareStatement(sql);
            psql.setString(1, token);
            ResultSet rs = psql.executeQuery();
            while (rs.next()) {
                t = rs.getString("tokentime");
            }
            rs.close();
            psql.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    public String updateTokenTimeByLoginInDB(String login) {
        Connection connection = ConnectionToMySQLDB.getConnaction();
        if (connection == null) {
            return null;
        }
        String sql = null;
        sql = "UPDATE users " +
                "SET users.tokentime = ? " +
                " WHERE users.login = ?";

        try {
            PreparedStatement psql = connection.prepareStatement(sql);
            psql.setString(1, getNow());
            psql.setString(2, login);
            psql.executeUpdate();
            psql.close();
            connection.close();
        } catch (SQLException e) {
            return null;
        }
        return "ok";
    }

    public String updateTokenTimeByTokenInDB(String token) {
        Connection connection = ConnectionToMySQLDB.getConnaction();
        if (connection == null) {
            return null;
        }
        String sql = null;
        sql = "UPDATE users " +
                "SET users.tokentime = ? " +
                " WHERE users.token = ?";

        try {
            PreparedStatement psql = connection.prepareStatement(sql);
            psql.setString(1, getNow());
            psql.setString(2, token);
            psql.executeUpdate();
            psql.close();
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


}
