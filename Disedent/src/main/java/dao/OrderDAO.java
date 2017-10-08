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


public class OrderDAO {

    public OrderDTO getOrderByIdFromDb(int id) {
        if (id == 0) {
            OrderDTO order = new OrderDTO(0, 0, 0, 0, "", "", "");
            Date d = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(d);
            cal.add(Calendar.MONTH, 1);
            order.setBeginDate(new SimpleDateFormat("dd-MM-yyyy HH:mm").format(d));
            order.setEndDate(new SimpleDateFormat("dd-MM-yyyy HH:mm").format(cal.getTime()));
            return order;
        }
        OrderDTO order = new OrderDTO();
        String sql = "SELECT * FROM orders " +
                "WHERE orders.orderId = ?";
        try {
            Connection connection = ConnectionToMySQLDB.getConnaction();
            PreparedStatement psql = connection.prepareStatement(sql);
            psql.setInt(1, id);
            ResultSet rs = psql.executeQuery();
            ;
            while (rs.next()) {
                order.setId(rs.getInt("orderId"));
                order.setGroupId(rs.getInt("groupId"));
                order.setCourseId(rs.getInt("courseId"));
                order.setUserId(rs.getInt("userId"));
                order.setBeginDate(parseDateForViewFormat(rs.getString("beginDate")));
                order.setEndDate(parseDateForViewFormat(rs.getString("endDate")));
            }
            rs.close();
            psql.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return order;
    }

    public ArrayList<OrderUserDTO> getOrderByUserIdFromDbForStudentResult(int id) {
        ArrayList<OrderUserDTO> orders = new ArrayList<OrderUserDTO>();
        Connection connection = ConnectionToMySQLDB.getConnaction();
        if (connection == null) {
            return null;
        }
        try {
            String sql = "SELECT * FROM orders WHERE orders.userId = ?";
            PreparedStatement psql = connection.prepareStatement(sql);
            psql.setInt(1, id);
            ResultSet rs = psql.executeQuery();
            while (rs.next()) {
                CourseDTO course = new CourseDAO().getCourseByIdFromDb(rs.getInt("courseId"));
                orders.add(new OrderUserDTO(
                        rs.getInt("orderId"),
                        rs.getInt("userId"),
                        course.getTitle(),
                        parseDateForViewFormat(rs.getString("beginDate")),
                        parseDateForViewFormat(rs.getString("endDate")))
                );
            }
            rs.close();
            psql.close();
            connection.close();
        } catch (SQLException e) {
            return null;
        }
        return orders;
    }

    public Boolean isUserIdCourseId(Integer userId, Integer courseId){
        Connection connection = ConnectionToMySQLDB.getConnaction();
        if (connection == null) {
            return null;
        }
        try {
            Date d = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(d);
            String sql = "SELECT * FROM orders WHERE orders.userId = ? AND orders.courseId = ? AND orders.endDate > ? AND orders.beginDate < ?";
            PreparedStatement psql = connection.prepareStatement(sql);
            psql.setInt(1, userId);
            psql.setInt(2, courseId);
            psql.setString(3, new SimpleDateFormat("yyyy-MM-dd HH:mm").format(d));
            psql.setString(4, new SimpleDateFormat("yyyy-MM-dd HH:mm").format(d));
            ResultSet rs = psql.executeQuery();
            while (rs.next()) {
                return true;
            }
            rs.close();
            psql.close();
            connection.close();
        } catch (SQLException e) {
            return null;
        }
        return false;
    }

    public ArrayList<OrderUserDTO> getOrderByUserIdFromDb(int id) {
        ArrayList<OrderUserDTO> orders = new ArrayList<OrderUserDTO>();
        Connection connection = ConnectionToMySQLDB.getConnaction();
        if (connection == null) {
            return null;
        }
        try {
            Date d = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(d);
            String sql = "SELECT * FROM orders WHERE orders.userId = ? AND orders.endDate > ? AND orders.beginDate < ?";
            PreparedStatement psql = connection.prepareStatement(sql);
            psql.setInt(1, id);
            psql.setString(2, new SimpleDateFormat("yyyy-MM-dd HH:mm").format(d));
            psql.setString(3, new SimpleDateFormat("yyyy-MM-dd HH:mm").format(d));
            ResultSet rs = psql.executeQuery();
            while (rs.next()) {
                CourseDTO course = new CourseDAO().getCourseByIdFromDb(rs.getInt("courseId"));
                orders.add(new OrderUserDTO(
                        rs.getInt("orderId"),
                        rs.getInt("userId"),
                        course.getTitle(),
                        parseDateForViewFormat(rs.getString("beginDate")),
                        parseDateForViewFormat(rs.getString("endDate")))
                );
            }
            rs.close();
            psql.close();
            connection.close();
        } catch (SQLException e) {
            return null;
        }
        return orders;
    }

    public Integer getUserIdByOrderIdFromDb(int orderId) {
        Integer userId = null;
        Connection connection = ConnectionToMySQLDB.getConnaction();
        if (connection == null) {
            return null;
        }
        try {
            Date d = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(d);
            String sql = "SELECT orders.userId FROM orders WHERE orders.orderId = ? AND orders.endDate > ? AND orders.beginDate < ?";
            PreparedStatement psql = connection.prepareStatement(sql);
            psql.setInt(1, orderId);
            psql.setString(2, new SimpleDateFormat("yyyy-MM-dd HH:mm").format(d));
            psql.setString(3, new SimpleDateFormat("yyyy-MM-dd HH:mm").format(d));
            ResultSet rs = psql.executeQuery();
            while (rs.next()) {
                userId = rs.getInt("userId");
            }
            rs.close();
            psql.close();
            connection.close();
        } catch (SQLException e) {
            return null;
        }
        return userId;
    }

    public ArrayList<OrderForListDTO> getOrdersByGroupIdFromDb(int id) {
        ArrayList<OrderForListDTO> orders = new ArrayList<OrderForListDTO>();
        Connection connection = ConnectionToMySQLDB.getConnaction();
        if (connection == null) {
            return null;
        }
        try {
            String sql = "SELECT * FROM orders WHERE orders.groupId = ?";
            PreparedStatement psql = connection.prepareStatement(sql);
            psql.setInt(1, id);
            ResultSet rs = psql.executeQuery();
            while (rs.next()) {
                UserDTO user = new UserDAO().getUserByIdFromDb(rs.getInt("userId"));
                CourseDTO course = new CourseDAO().getCourseByIdFromDb(rs.getInt("courseId"));
                orders.add(new OrderForListDTO(
                        rs.getInt("orderId"),
                        rs.getInt("courseId"),
                        rs.getInt("userId"),
                        rs.getInt("groupId"),
                        course.getTitle(),
                        user.getLastName(),
                        user.getFirstName(),
                        user.getMiddleName(),
                        user.getLogin(),
                        parseDateForViewFormat(rs.getString("creationDate")),
                        parseDateForViewFormat(rs.getString("beginDate")),
                        parseDateForViewFormat(rs.getString("endDate"))
                ));
            }
            rs.close();
            psql.close();
            connection.close();
        } catch (SQLException e) {
            return null;
        }
        return orders;
    }

    public OrderForListDTO setOrderInDB(OrderDTO order) {
        if (checkOrders(order)) {
            return null;
        }
        OrderForListDTO responseOrder = new OrderForListDTO();
        Connection connection = ConnectionToMySQLDB.getConnaction();
        if (connection == null) {
            return null;
        }
        Date d = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        try {
            String sql = "INSERT INTO orders " +
                    "(orders.userId, orders.courseId, " +
                    "orders.groupId, orders.beginDate, " +
                    "orders.endDate, " +
                    "orders.creationDate) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement psql = connection.prepareStatement(sql);
            psql.setInt(1, order.getUserId());
            psql.setInt(2, order.getCourseId());
            psql.setInt(3, order.getGroupId());
            psql.setString(4, parseDateForMySQLDateFormat(order.getBeginDate()));
            psql.setString(5, parseDateForMySQLDateFormat(order.getEndDate()));
            psql.setString(6, new SimpleDateFormat("yyyy-MM-dd HH:mm").format(d));
            psql.executeUpdate();
            sql = "SELECT MAX(orders.orderId) FROM orders";
            psql = connection.prepareStatement(sql);
            ResultSet rs = psql.executeQuery();
            while (rs.next()) {
                responseOrder.setId(rs.getInt("MAX(orders.orderId)"));
            }
            rs.close();
            psql.close();
            connection.close();
        } catch (SQLException e) {
            return null;
        }
        responseOrder.setUserId(order.getUserId());
        responseOrder.setCourseId(order.getCourseId());
        responseOrder.setGroupId(order.getGroupId());
        responseOrder.setBeginDate(order.getBeginDate());
        responseOrder.setEndDate(order.getEndDate());
        responseOrder.setCreationDate(new SimpleDateFormat("dd-MM-yyyy HH:mm").format(d));
        UserDTO user = new UserDAO().getUserByIdFromDb(responseOrder.getUserId());
        responseOrder.setUserLogin(user.getLogin());
        responseOrder.setUserLastName(user.getLastName());
        return responseOrder;
    }

    public String deleteOrderFromDB(int id) {
        Connection connection = ConnectionToMySQLDB.getConnaction();
        if (connection == null) {
            return null;
        }
        try {
            String sql = "delete from orders where orders.orderId = ?;";
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

    //check orders

    private Boolean checkOrders(OrderDTO order) {
        Connection connection = ConnectionToMySQLDB.getConnaction();
        if (connection == null) {
            return null;
        }
        try {
            String sql = "SELECT * FROM orders " +
                    "WHERE orders.endDate >= ? AND orders.userId = ? AND orders.courseId = ?;";
            PreparedStatement psql = connection.prepareStatement(sql);
            psql.setString(1, parseDateForMySQLDateFormat(order.getBeginDate()));
            psql.setInt(2, order.getUserId());
            psql.setInt(3, order.getCourseId());
            ResultSet rs = psql.executeQuery();
            if (rs.next()) {
                return true;
            }
            psql.close();
            ;
            connection.close();
        } catch (SQLException e) {
            return null;
        }
        return false;
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


}
