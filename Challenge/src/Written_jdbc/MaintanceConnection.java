package Written_jdbc;

import java.io.FileInputStream;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

//连接池维护类
public class MaintanceConnection {
    private static BasicConnection basicConnection;
    public static synchronized void Initial() throws Exception {//初始化数据，并获取文件名字
        Properties properties = new Properties();
        properties.load(new FileInputStream("D:\\idea_java_projects\\Challenge\\src\\Written_jdbc\\config.properties"));
        basicConnection = new BasicConnection(properties);
    }

    public static synchronized Connection GetConnection()//获取连接池中的连接
    {
        return basicConnection.getConnection();
    }

    public static synchronized void ReleaseConnection(Connection connection)//释放连接
    {
        basicConnection.ReleaseConnection(connection);
    }

    public static synchronized void shutdown()//关闭连接池
    {
        basicConnection.shutdown();
    }

    public static synchronized void close(Connection connection, PreparedStatement ps, ResultSet rs)//从小到大关闭连接
    {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }if (connection != null) {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    }
}

