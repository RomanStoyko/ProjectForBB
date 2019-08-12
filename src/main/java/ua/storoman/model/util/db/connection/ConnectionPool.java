package ua.storoman.model.util.db.connection;

import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPool {
    final private static Logger logger = Logger.getLogger(ConnectionPool.class);
    private static volatile ConnectionPool instance;
    private ConnectionPool(){}

    public static ConnectionPool getInstance(){
        if(instance == null){
            synchronized (ConnectionPool.class){
                if(instance == null) {
                    instance = new ConnectionPool();
                }
            }
        }
        return instance;
    }


    private static final String DATASOURE_NAME = "jdbc/bus_stop_rout";
    private static DataSource dataSource;
    static {

        try {
            Context initContext = new InitialContext();
            Context envVontext = (Context) initContext.lookup("java:comp/env");
            dataSource = (DataSource) envVontext.lookup(DATASOURE_NAME);
        } catch (NamingException e) {
            e.printStackTrace();
        }

    }

    public static Connection getConnection()throws SQLException{
        Connection connection = dataSource.getConnection();
        return connection;

    }

    public static void closeConnection(Connection connection)throws SQLException {
        if(connection != null){
            connection.close();
        }
    }

}
