package Written_jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;


//连接池实现类
public class BasicConnection implements Pool_Interface{//实现连接池接口
    private static String url;
    private static String username;
    private static String password;
    private static String driver;
    private static int Size;
    private static int InitialSize;
    private static int IncreaseSize;
    private static List<ConnectionWrapper> connectionWrappers;

    public BasicConnection(Properties properties)throws Exception {
        //获取配置文件中key对应的value值
        this.url = properties.getProperty("url");
        this.username = properties.getProperty("username");
        this.password = properties.getProperty("password");
        this.driver = properties.getProperty("driver");
        this.Size = new Integer(properties.getProperty("Size"));
        this.InitialSize = new Integer(properties.getProperty("InitialSize"));
        this.IncreaseSize = new Integer(properties.getProperty("IncreaseSize"));
        //创建ArrayList保存连接池中的连接
        connectionWrappers = new ArrayList<>();
        //注册驱动
        Class.forName(driver);
        for (int i = 0;i<InitialSize;i++ )
        {
            //获取数据库连接
            Connection connection = DriverManager.getConnection(url,username,password);
            //将创建的连接对象存入connections中
            connectionWrappers.add(new ConnectionWrapper(connection));
        }

    }

    @Override
    public synchronized Connection getConnection()  {
        for (ConnectionWrapper connectionWrapper:connectionWrappers){//增强for循环
            if (connectionWrapper.isValiable())//判断是否有连接空闲
            {
                connectionWrapper.setValiable(false);//改变连接类中的判断连接是否空闲为被占用
                return connectionWrapper.getConnection();//获取连接
            }
        }
        int CurrentSize=connectionWrappers.size();//获取当前的集合大小
        if (CurrentSize<this.Size)//判断是否大于最大容量
        {
            for (int i = 0 ; i < IncreaseSize;i++)//增添连接池数量
            {
                Connection connection = null;
                try {
                    connection = DriverManager.getConnection(url,username,password);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                connectionWrappers.add(new ConnectionWrapper(connection));
            }
            return getConnection();
        }
        else
        {
            throw new RuntimeException("连接池均正在使用，目前无法获取连接");
        }
    }

    @Override
    public synchronized void ReleaseConnection(Connection connection) {
        for (ConnectionWrapper connectionWrapper:connectionWrappers)//判断该对象为集合中的具体那个对象
        {
            if (connectionWrapper.getConnection()==connection)
            {
                connectionWrapper.setValiable(true);//改变连接类中的判断连接是否空闲为空闲
                break;//找到即退出循环
            }
        }
    }

    @Override
    public synchronized void shutdown() {//关闭连接池中的全部连接
        for (ConnectionWrapper connectionWrapper:connectionWrappers){
            connectionWrapper.close();//关闭连接
        }
        connectionWrappers.clear();

    }
}
