package HandWritten_pool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class BasicConnection implements Pool_Interface{
    private String url;
    private String username;
    private String password;
    private String driver;
    private int MaxSize;
    private int InitialSize;
    private int IncreaseSize;
    private List<MyConnection> connections;

    public BasicConnection(Properties properties) throws Exception {
        //获取配置文件中的配置信息
        this.url = properties.getProperty("url");
        this.username = properties.getProperty("username");;
        this.password = properties.getProperty("password");;
        this.driver = properties.getProperty("driver");;
        MaxSize = new Integer(properties.getProperty("MaxSize"));
        InitialSize = new Integer(properties.getProperty("InitialSize"));
        IncreaseSize = new Integer(properties.getProperty("IncreaseSize"));
        //创建ArrayList保存连接池中的连接
        connections=new ArrayList<>();
        //注册驱动
        Class.forName(driver);
        for (int i = 0; i < InitialSize; i++)
        {
            //获取数据库连接
            Connection connection = DriverManager.getConnection(url,username,password);
            //将创建的连接对象存入connections中
            connections.add(new MyConnection(connection));
        }

    }

    @Override
    public synchronized Connection getconnection() {
        for (MyConnection Myconnection:connections)//增强for循环
        {
            if (Myconnection.getValiable())//判断是否有连接空闲
            {
                Myconnection.setValiable(false);//改变连接类中的判断连接是否空闲为被占用
                return Myconnection.getConnection();

            }
        }
        int currentsize=connections.size();//获取当前的集合大小
        if (currentsize<this.MaxSize)//判断是否大于最大容量
        {
            for (int i = 0 ; i < IncreaseSize;i++)
            {
                Connection connection = null;
                try {
                    connection = DriverManager.getConnection(url,username,password);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                connections.add(new MyConnection(connection));
            }
            return getconnection();
        }else{
            throw new RuntimeException("连接池没有空闲的连接，无法连接");
        }

    }

    @Override
    public synchronized void ReleaseConnection(Connection connection) {
        for (MyConnection connections:connections){
            if (connections.getConnection()==connection)//判断该对象为集合中的具体那个对象
            {
                connections.setValiable(true);//改变连接类中的判断连接是否空闲为空闲
                break;//找到即退出循环
            }
        }
    }

    @Override
    public synchronized void shutdown() {//关闭连接池中的全部连接
        for (MyConnection connection:connections)
        {
            connection.close();//关闭连接
        }
        connections.clear();
    }
}
