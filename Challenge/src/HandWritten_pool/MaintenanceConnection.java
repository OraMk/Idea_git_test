package HandWritten_pool;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.util.Properties;

public class MaintenanceConnection {
    private static BasicConnection basicConnection;
    public synchronized static void Initialize() throws Exception {
        Properties properties = new Properties();
        properties.load(new FileInputStream("D:\\idea_java_projects\\Challenge\\src\\HandWritten_pool\\config.properties"));
        basicConnection= new BasicConnection(properties);
    }
    public static synchronized Connection Getconnection()
    {
        return basicConnection.getconnection();
    }

    public static synchronized void ReleaseConnection(Connection connection){
        basicConnection.ReleaseConnection(connection);
    }

    public static synchronized void shutdown()
    {
        basicConnection.shutdown();
    }
}
