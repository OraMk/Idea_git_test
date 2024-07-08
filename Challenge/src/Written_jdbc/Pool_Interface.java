package Written_jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

public interface Pool_Interface {
    Connection getConnection() throws SQLException;
    void ReleaseConnection(Connection connection);
    void shutdown();

}
