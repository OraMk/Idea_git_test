package The_First_week.JDBC_Utils;

import java.io.FileInputStream;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class JDBC {
    private String driver;
    private String url;
    private String user;
    private  String  password;
    private  Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private Properties properties = new Properties();




    public JDBC() throws ClassNotFoundException, SQLException, IOException {
        //载入配置文件
        properties.load(new FileInputStream("D:\\QG_work\\src\\The_First_week\\config.properties"));
        //获取配置文件中的数据
        this.driver = properties.getProperty("driver");
        this.user = properties.getProperty("user");
        this.url = properties.getProperty("url");
        this.password = properties.getProperty("password");
        //注册驱动
        Class.forName(driver);
        //获取数据库连接
        this.connection = DriverManager.getConnection(url,user,password);
    }

    public void InputSql(String sql)//create update delete select
    {
        if (sql.substring(0,6).equals("create")||sql.substring(0,6).equals("update")||sql.substring(0,6).equals("delete"))
        {
            Edit(sql);

        }

         else if (sql.substring(0,6).equals("select")) {
             Select(sql);
        }
        else {
            System.out.println("sql语句输入错误");

        }
    }
    public void Edit(String sql)  {

        try {
            //注册数据库操作对象
            preparedStatement = connection.prepareStatement(sql);
            //执行sql语句
            int n = preparedStatement.executeUpdate();
            System.out.println("已修改" + n + "条数据");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



    }
    public void Select(String sql)
    {
        ResultSetMetaData metaData = null;
        try {
            //注册数据库操作对象
            preparedStatement = connection.prepareStatement(sql);
            //执行sql语句并获取查询结构集
            resultSet = preparedStatement.executeQuery();
            metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            boolean title =false;
            while(resultSet.next())
            {
                for (int i = 1 ; i <=columnCount ; i++)
                {
                    if (title==false)
                    {
                        for (int j = 1 ; j <=columnCount ; j++)
                        System.out.print("\t\t" + metaData.getColumnName(i)  );
                        System.out.println();
                    }
                    System.out.print("\t\t" + resultSet.getString(i) );
                    title = true;
                }
                System.out.println();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }




    }
    public void close()
    {
        if (resultSet == null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if (preparedStatement == null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }if (connection == null) {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    }

}
