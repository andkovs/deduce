package dao;


import dto.RoleDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RoleDAO {
//    public ArrayList<RoleDTO> getRolesByUserLogin(String login) {
//        ArrayList<RoleDTO> roles = new ArrayList<RoleDTO>();
//        ArrayList<String> roleTtls = new ArrayList<String>();
//        Connection connection = ConnectionToMySQLDB.getConnaction();
//        if (connection == null) {
//            return null;
//        }
//        try {
//            String sql = "SELECT usertorole.title FROM usertorole WHERE usertorole.login = ?";
//            PreparedStatement psql = connection.prepareStatement(sql);
//            psql.setString(1, login);
//            ResultSet rs = psql.executeQuery();
//            while (rs.next()) {
//                roleTtls.add(rs.getString("title"));
//            }
//            rs.close();
//            psql.close();
//            connection.close();
//        } catch (SQLException e) {
//            return null;
//        }
//        for (String s :
//                roleTtls) {
//            roles.add(getRoleByTitle(s));
//        }
//        return roles;
//    }

    private RoleDTO getRoleById(Integer roleId) {
        RoleDTO role = new RoleDTO();
        Connection connection = ConnectionToMySQLDB.getConnaction();
        if (connection == null) {
            return null;
        }
        try {
            String sql = "SELECT * FROM roles WHERE roles.roleId = ?";
            PreparedStatement psql = connection.prepareStatement(sql);
            psql.setInt(1, roleId);
            ResultSet rs = psql.executeQuery();
            while (rs.next()) {
                role.setId(rs.getInt("roleId"));
                role.setTitle(rs.getString("title"));
            }
            rs.close();
            psql.close();
            connection.close();
        } catch (SQLException e) {
            return null;
        }
        return role;
    }

    private RoleDTO getRoleByTitle(String title) {
        RoleDTO role = new RoleDTO();
        Connection connection = ConnectionToMySQLDB.getConnaction();
        if (connection == null) {
            return null;
        }
        try {
            String sql = "SELECT * FROM roles WHERE roles.title = ?";
            PreparedStatement psql = connection.prepareStatement(sql);
            psql.setString(1, title);
            ResultSet rs = psql.executeQuery();
            while (rs.next()) {
                role.setId(rs.getInt("roleId"));
                role.setTitle(rs.getString("title"));
            }
            rs.close();
            psql.close();
            connection.close();
        } catch (SQLException e) {
            return null;
        }
        return role;
    }

    public ArrayList<String> getAllRoles() {
        ArrayList<String> roles = new ArrayList<String>();
        Connection connection = ConnectionToMySQLDB.getConnaction();
        if (connection == null) {
            return null;
        }
        try {
            String sql = "SELECT * FROM roles";
            PreparedStatement psql = connection.prepareStatement(sql);
            ResultSet rs = psql.executeQuery();
            while (rs.next()) {
                roles.add(rs.getString("title"));
            }
            rs.close();
            psql.close();
            connection.close();
        } catch (SQLException e) {
            return null;
        }
        return roles;
    }

    public String setUserToRoleInDb(String title, String login) {
        Connection connection = ConnectionToMySQLDB.getConnaction();
        if (connection == null) {
            return null;
        }
        try {
            String sql = "INSERT INTO usertorole " +
                    "(usertorole.login, usertorole.title) " +
                    "VALUES (?, ?)";
            PreparedStatement psql = connection.prepareStatement(sql);
            psql.setString(1, login);
            psql.setString(2, title);
            psql.executeUpdate();
            psql.close();
            connection.close();
        } catch (SQLException e) {
            return null;
        }
        return "ok";
    }

    public String deleteRoleFromDbByLogin(String login) {
        Connection connection = ConnectionToMySQLDB.getConnaction();
        if (connection == null) {
            return null;
        }
        try {
            String sql = "delete from usertorole where usertorole.login = ?;";
            PreparedStatement psql = connection.prepareStatement(sql);
            psql.setString(1, login);
            psql.executeUpdate();
            psql.close();
            connection.close();
        } catch (SQLException e) {
            return null;
        }
        return "ok";
    }

    public ArrayList<String> getRolesByLogin(String login) {
        ArrayList<String> roles = new ArrayList<String>();
        Connection connection = ConnectionToMySQLDB.getConnaction();
        if (connection == null) {
            return null;
        }
        try {
            String sql = "SELECT title FROM usertorole WHERE usertorole.login = ?";
            PreparedStatement psql = connection.prepareStatement(sql);
            psql.setString(1, login);
            ResultSet rs = psql.executeQuery();
            while (rs.next()) {
                roles.add(rs.getString("title"));
            }
            rs.close();
            psql.close();
            connection.close();
        } catch (SQLException e) {
            return null;
        }
        return roles;
    }

    public ArrayList<String> getRolesByToken(String token) {
        ArrayList<String> roles = null;
        String login = new UserDAO().getLoginByTokenFromDB(token);
        roles = getRolesByLogin(login);
        return roles;
    }
}
