package dao;

import dto.GroupDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class GroupDAO {
    public ArrayList<GroupDTO> getAllGroups() {
        ArrayList<GroupDTO> groups = new ArrayList<GroupDTO>();
        Connection connection = ConnectionToMySQLDB.getConnaction();
        if(connection==null){
            return null;
        }
        try {
            String sql = "SELECT * FROM groups";
            PreparedStatement psql = connection.prepareStatement(sql);
            ResultSet rs = psql.executeQuery();
            while (rs.next()) {
                Integer count = getUserCountByGroupId(rs.getInt("groupId"));
                groups.add(new GroupDTO(
                        rs.getInt("groupId"),
                        rs.getString("title"),
                        count,
                        parseDateForShortList(rs.getString("creationDate"))
                ));
            }
            rs.close();
            psql.close();
            connection.close();
        } catch (SQLException e) {
            return null;
        }
        return groups;
    }

    public GroupDTO getGroupByIdFromDb(int id) {
        GroupDTO group = new GroupDTO();
        String sql = "SELECT * FROM groups " +
                "WHERE groups.groupId = ?";
        try {
            Connection connection = ConnectionToMySQLDB.getConnaction();
            PreparedStatement psql = connection.prepareStatement(sql);
            psql.setInt(1, id);
            ResultSet rs = psql.executeQuery();;
            while (rs.next()) {
                group.setTitle(rs.getString("title"));
                group.setCreationDate(parseDateForShortList(rs.getString("creationDate")));
            }
            rs.close();
            psql.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        group.setId(id);
        group.setOrders(new OrderDAO().getOrdersByGroupIdFromDb(id));
        return group;
    }

    public GroupDTO setGroupInDB(GroupDTO group) {
        Connection connection = ConnectionToMySQLDB.getConnaction();
        if(connection==null){
            return null;
        }
        Date d = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        try {
            String sql = "INSERT INTO groups " +
                    "(groups.title, " +
                    "groups.creationDate) " +
                    "VALUES (?, ?)";
            PreparedStatement psql = connection.prepareStatement(sql);
            psql.setString(1, group.getTitle());
            psql.setString(2, new SimpleDateFormat("yyyy-MM-dd HH:mm").format(d));
            psql.executeUpdate();
            sql = "SELECT MAX(groups.groupId) FROM groups";
            psql = connection.prepareStatement(sql);
            ResultSet rs = psql.executeQuery();
            while (rs.next()) {
                group.setId(rs.getInt("MAX(groups.groupId)"));
            }
            group.setCreationDate(new SimpleDateFormat("dd-MM-yyyy HH:mm").format(d));
            group.setCount(getUserCountByGroupId(group.getId()));
            rs.close();
            psql.close();
            connection.close();
        } catch (SQLException e) {
            return null;
        }
        return group;
    }

    public String updateGroupInDB(int id, GroupDTO group) {
        return null;
    }

    public String deleteGroupFromDB(int id) {
        Connection connection = ConnectionToMySQLDB.getConnaction();
        if(connection==null){
            return null;
        }
        try {
            String sql = "delete from groups where groups.groupId = ?;";
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

    // get user count

    private Integer getUserCountByGroupId(Integer groupId){
        Integer count = null;
        Connection connection = ConnectionToMySQLDB.getConnaction();
        if(connection==null){
            return null;
        }
        try {
            String sql = "SELECT COUNT(*) FROM orders WHERE orders.groupId = ?";
            PreparedStatement psql = connection.prepareStatement(sql);
            psql.setInt(1, groupId);
            ResultSet rs = psql.executeQuery();
            while (rs.next()) {
                count = rs.getInt("COUNT(*)");
            }
            rs.close();
            psql.close();
            connection.close();
        } catch (SQLException e) {
            return null;
        }
        return count;
    }

    //work with date

    private String parseDateForShortList(String date){
        String s = date.substring(8, 10) + "-" + date.substring(5, 7) + "-" + date.substring(0, 4)+ " "
                + date.substring(11, 13) + ":" + date.substring(14, 16);
        return s;
    }

    private String parseDate(String date) {
        date = date.substring(6, 10)+"-"+date.substring(3, 5)+"-"+date.substring(0, 2)+" "+date.substring(11, 16).replaceAll(":", "-");
        return date;
    }
}
