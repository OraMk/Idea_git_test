package The_First_week.JDBC_Utils;

import The_First_week.JDBC_Utils.JDBC;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class test {
    public static void main(String[] args) {
        JDBC jdbc = null;
        try {
            jdbc = new JDBC();
            String sql = null;
            Scanner scanner = new Scanner(System.in);
            System.out.println("请输入sql语句:");
            sql = scanner.nextLine();
            jdbc.InputSql(sql);
            jdbc.close();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
