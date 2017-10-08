package dao;

import dto.UserDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAO {

    public ArrayList<UserDTO> getAllUsers() {
        ArrayList<UserDTO> users = new ArrayList<UserDTO>();
        Connection connection = ConnectionToMySQLDB.getConnaction();
        if (connection == null) {
            return null;
        }
        try {
            String sql = "SELECT * FROM users";
            PreparedStatement psql = connection.prepareStatement(sql);
            ResultSet rs = psql.executeQuery();
            while (rs.next()) {
                Boolean isEnabled = true;
                if (rs.getInt("isEnabled") == 0) {
                    isEnabled = false;
                }
                users.add(new UserDTO(
                        rs.getInt("userId"),
                        rs.getString("login"),
                        rs.getString("password"),
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getString("middlename"),
                        rs.getString("companyTitle"),
                        rs.getString("position"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        isEnabled)
                );
            }
            rs.close();
            psql.close();
            connection.close();
        } catch (SQLException e) {
            return null;
        }
        return users;
    }

    public UserDTO getUserByIdFromDb(int id) {
        if (id == 0) {
            return new UserDTO(0, "", "", "", "", "", "");
        }
        Connection connection = ConnectionToMySQLDB.getConnaction();
        if (connection == null) {
            return null;
        }
        UserDTO user = new UserDTO();
        String sql = "SELECT * FROM users " +
                "WHERE users.userId = ?";
        try {
            PreparedStatement psql = connection.prepareStatement(sql);
            psql.setInt(1, id);
            ResultSet rs = psql.executeQuery();
            while (rs.next()) {
                user.setLogin(rs.getString("login"));
                user.setFirstName(rs.getString("firstname"));
                user.setLastName(rs.getString("lastname"));
                user.setMiddleName(rs.getString("middlename"));
                user.setPhone(rs.getString("phone"));
                user.setEmail(rs.getString("email"));
                user.setCompanyTitle(rs.getString("companyTitle"));
                user.setPosition(rs.getString("position"));
                if (rs.getInt("isEnabled") == 1) {
                    user.setEnabled(true);
                    user.setPassword(rs.getString("password"));
                } else {
                    user.setEnabled(false);
                    user.setPassword(rs.getString("passArchive"));
                }
            }
            rs.close();
            psql.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        user.setId(id);
        user.setRoles(new RoleDAO().getRolesByLogin(user.getLogin()));
        return user;
    }

    public UserDTO setUserInDB(UserDTO user) {
        Connection connection = ConnectionToMySQLDB.getConnaction();
        if (connection == null) {
            return null;
        }
        String sql = null;
        if (user.getEnabled()) {
            sql = "INSERT INTO users " +
                    "(users.login, users.password, " +
                    "users.firstname, users.lastname, " +
                    "users.middlename, users.companyTitle, " +
                    "users.position, " +
                    "users.phone, users.email) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        } else {
            sql = "INSERT INTO users " +
                    "(users.login, users.passArchive, " +
                    "users.firstname, users.lastname, " +
                    "users.middlename, users.companyTitle, " +
                    "users.position, " +
                    "users.phone, users.email) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        }
        try {
            PreparedStatement psql = connection.prepareStatement(sql);
            psql.setString(1, user.getLogin());
            psql.setString(2, user.getPassword());
            psql.setString(3, user.getFirstName());
            psql.setString(4, user.getLastName());
            psql.setString(5, user.getMiddleName());
            psql.setString(6, user.getCompanyTitle());
            psql.setString(7, user.getPosition());
            psql.setString(8, user.getPhone());
            psql.setString(9, user.getEmail());
            psql.executeUpdate();
            sql = "SELECT MAX(users.userId) FROM users";
            psql = connection.prepareStatement(sql);
            ResultSet rs = psql.executeQuery();
            while (rs.next()) {
                user.setId(rs.getInt("MAX(users.userId)"));
            }
            rs.close();
            psql.close();
            connection.close();
            RoleDAO dao = new RoleDAO();
            for (String r :
                    user.getRoles()) {
                dao.setUserToRoleInDb(r, user.getLogin());
            }
            new TokenDAO().updateTokenInDB(user.getLogin(), TokenGenerator.generateToken(user.getLogin()));
        } catch (SQLException e) {
            return null;
        }
        return user;
    }

    public String updateUserInDB(int id, UserDTO user) {
        Connection connection = ConnectionToMySQLDB.getConnaction();
        if (connection == null) {
            return null;
        }
        String sql = null;
        if (user.getEnabled()) {
            sql = "UPDATE users " +
                    "SET users.login = ?, users.password = ?, " +
                    "users.firstname = ?, users.lastname = ?, " +
                    "users.middlename = ?, users.companyTitle = ?, " +
                    "users.position = ?, " +
                    "users.phone = ?, users.email = ? " +
                    " WHERE users.userId = ?";
        } else {
            sql = "UPDATE users " +
                    "SET users.login = ?, users.passArchive = ?, " +
                    "users.firstname = ?, users.lastname = ?, " +
                    "users.middlename = ?, users.companyTitle = ?, " +
                    "users.position = ?, " +
                    "users.phone = ?, users.email = ? " +
                    " WHERE users.userId = ?";
        }
        try {

            PreparedStatement psql = connection.prepareStatement(sql);
            psql.setString(1, user.getLogin());
            psql.setString(2, user.getPassword());
            psql.setString(3, user.getFirstName());
            psql.setString(4, user.getLastName());
            psql.setString(5, user.getMiddleName());
            psql.setString(6, user.getCompanyTitle());
            psql.setString(7, user.getPosition());
            psql.setString(8, user.getPhone());
            psql.setString(9, user.getEmail());
            psql.setInt(10, id);
            psql.executeUpdate();
            psql.close();
            connection.close();
        } catch (SQLException e) {
            return null;
        }
        RoleDAO dao = new RoleDAO();
        dao.deleteRoleFromDbByLogin(user.getLogin());
        for (String r :
                user.getRoles()) {
            dao.setUserToRoleInDb(r, user.getLogin());
        }
        return "ok";
    }

    public String deleteUserFromDB(int id) {
        Connection connection = ConnectionToMySQLDB.getConnaction();
        if (connection == null) {
            return null;
        }
        try {
            RoleDAO dao = new RoleDAO();
            dao.deleteRoleFromDbByLogin(getUserByIdFromDb(id).getLogin());
            String sql = "delete from users where users.userId = ?;";
            PreparedStatement psql = connection.prepareStatement(sql);
            psql.setInt(1, id);
            psql.executeUpdate();
            psql.close();
            connection.close();
        } catch (SQLException e) {
            return null;
        }
        return "ok";
    }

    public UserDTO getUserByLogin(String login) {
        UserDTO user = new UserDTO();
        String sql = "SELECT * FROM users " +
                "WHERE users.login = ?";
        try {
            Connection connection = ConnectionToMySQLDB.getConnaction();
            PreparedStatement psql = connection.prepareStatement(sql);
            psql.setString(1, login);
            ResultSet rs = psql.executeQuery();
            while (rs.next()) {
                user.setId(rs.getInt("userId"));
                user.setLogin(rs.getString("login"));
                user.setPassword(rs.getString("password"));
                user.setFirstName(rs.getString("firstname"));
                user.setLastName(rs.getString("lastname"));
            }
            rs.close();
            psql.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public Integer getUserIdByLToken(String token) {
        Integer id = null;
        String sql = "SELECT * FROM users " +
                "WHERE users.token = ?";
        try {
            Connection connection = ConnectionToMySQLDB.getConnaction();
            PreparedStatement psql = connection.prepareStatement(sql);
            psql.setString(1, token);
            ResultSet rs = psql.executeQuery();
            while (rs.next()) {
                id = rs.getInt("userId");
            }
            rs.close();
            psql.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    public String enableUserInDB(int id) {
        String sql = "SELECT passArchive FROM users " +
                "WHERE users.userId = ?";
        String passArchive = null;
        try {
            Connection connection = ConnectionToMySQLDB.getConnaction();
            PreparedStatement psql = connection.prepareStatement(sql);
            psql.setInt(1, id);
            ResultSet rs = psql.executeQuery();
            while (rs.next()) {
                passArchive = rs.getString("passArchive");
            }
            sql = "UPDATE users " +
                    "SET users.password = ?, " +
                    "users.passArchive = ?, " +
                    "users.isEnabled = ? " +
                    " WHERE users.userId = ?";
            psql = connection.prepareStatement(sql);
            psql.setString(1, passArchive);
            psql.setString(2, "DeDuCe2017");
            psql.setInt(3, 1);
            psql.setInt(4, id);
            psql.executeUpdate();
            rs.close();
            psql.close();
            connection.close();
        } catch (SQLException e) {
            return null;
        }
        return "ok";
    }

    public String disableUserInDB(int id) {
        String sql = "SELECT password FROM users " +
                "WHERE users.userId = ?";
        String password = null;
        try {
            Connection connection = ConnectionToMySQLDB.getConnaction();
            PreparedStatement psql = connection.prepareStatement(sql);
            psql.setInt(1, id);
            ResultSet rs = psql.executeQuery();
            while (rs.next()) {
                password = rs.getString("password");
            }
            sql = "UPDATE users " +
                    "SET users.password = ?, " +
                    "users.passArchive = ?, " +
                    "users.isEnabled = ? " +
                    " WHERE users.userId = ?";
            psql = connection.prepareStatement(sql);
            psql.setString(1, "DeDuCe2017");
            psql.setString(2, password);
            psql.setInt(3, 0);
            psql.setInt(4, id);
            psql.executeUpdate();
            rs.close();
            psql.close();
            connection.close();
        } catch (SQLException e) {
            return null;
        }
        return "ok";
    }

    public String getLoginByTokenFromDB(String token) {
        Connection connection = ConnectionToMySQLDB.getConnaction();
        if (connection == null) {
            return null;
        }
        UserDTO user = new UserDTO();
        String sql = "SELECT * FROM users " +
                "WHERE users.token = ?";
        try {
            PreparedStatement psql = connection.prepareStatement(sql);
            psql.setString(1, token);
            ResultSet rs = psql.executeQuery();
            while (rs.next()) {
                user.setLogin(rs.getString("login"));
            }
            rs.close();
            psql.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user.getLogin();
    }
}
