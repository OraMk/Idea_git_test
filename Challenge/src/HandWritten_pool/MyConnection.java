package HandWritten_pool;

import java.sql.Connection;
import java.sql.SQLException;

public class MyConnection {
    private Connection connection;//连接对象
    private Boolean IsValiable;//判断是否空闲，即可以被使用

    public MyConnection(Connection connection) {
        this.connection = connection;
        this.IsValiable = true;//创建后初始化为空闲，即可以被使用;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Boolean getValiable() {
        return IsValiable;
    }

    public void setValiable(Boolean valiable) {
        IsValiable = valiable;
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
