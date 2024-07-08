package Written_jdbc;

import java.sql.Connection;
import java.sql.SQLException;

//连接池类
public class ConnectionWrapper {
    private Connection connection;
    private boolean Valiable;

    public ConnectionWrapper(Connection connection) {
        this.connection = connection;
        this.Valiable = true;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean isValiable() {
        return Valiable;
    }

    public void setValiable(boolean valiable) {
        Valiable = valiable;
    }
    public void close()
    {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
