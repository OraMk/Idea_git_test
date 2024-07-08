package HandWritten_pool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class test {
    public static void main(String[] args)  {
        Connection connection =null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            MaintenanceConnection.Initialize();//初始化
            connection = MaintenanceConnection.Getconnection();//获取连接
            ps = connection.prepareStatement("select * from emp");//查询数据库数据
            rs = ps.executeQuery();//执行sql语句
            //判断是否查询到
            while(rs.next())
            {
                String empno = rs.getString("empno");//获取查询结果根据列名
                String ename = rs.getString("ename");
                String sal = rs.getString("sal");
                System.out.println(empno + "\t" + ename + "\t" + sal );
            }
            MaintenanceConnection.shutdown();//关闭连接
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
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
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }
}
