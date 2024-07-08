package HandWritten_pool;

import java.sql.Connection;

public interface Pool_Interface {
    Connection getconnection();//获取连接
    void ReleaseConnection (Connection connection);//释放连接，使得connection 空闲可以被调用
    void shutdown();
}
