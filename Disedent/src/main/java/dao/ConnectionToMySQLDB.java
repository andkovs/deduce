package dao;


import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.sql.DataSource;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;


public class ConnectionToMySQLDB {

    public static Connection getConnaction() {

        Context initCtx = null;
        Connection connection = null;
        try {
            initCtx = new InitialContext();
            Context envCtx = (Context) initCtx.lookup("java:comp/env");
            // Look up our data source
            //DataSource ds = (DataSource) envCtx.lookup("jdbc/"+ContextName.getName()+"db");
            DataSource ds = (DataSource) envCtx.lookup("jdbc/deducedb");
            // Allocate and use a connection from the pool
            connection = ds.getConnection();
        } catch (NamingException e) {
            return null;
        } catch (SQLException e) {
            return null;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
}
