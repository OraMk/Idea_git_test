package Written_jdbc;


import HandWritten_pool.MaintenanceConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
//测试类
public class test {
    public static void main(String[] args) {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        try {
            //初始化，即注册驱动
            MaintanceConnection.Initial();
            //获取数据库连接
            connection = MaintanceConnection.GetConnection();
            //执行sql语句
            String sql = "select * from dept";
            preparedStatement = connection.prepareStatement(sql);
            //获取查询结果集
            resultSet=preparedStatement.executeQuery();
            //判断是否查询到
            while (resultSet.next())
            {
                String deptno = resultSet.getString("deptno");//获取查询结果
                String dname = resultSet.getString("dname");
                String loc = resultSet.getString("loc");
                System.out.println(deptno + "\t" + dname + "\t" +loc);

            }
            MaintenanceConnection.shutdown();//关闭连接
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            MaintanceConnection.close(connection,preparedStatement,resultSet);
        }
    }
}
