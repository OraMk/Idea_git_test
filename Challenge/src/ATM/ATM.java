package ATM;



import Written_jdbc.MaintanceConnection;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;



public class ATM {

    public static void Initial() throws Exception {//初始化数据库
        MaintanceConnection.Initial();//调用手写jdbc连接池
    }
    public static void Saving(Connection connection,user user)  {//存款功能

        System.out.print("请输入要存进的金额:");
        //获取用户输入
        Scanner scanner = new Scanner(System.in);
        //获取金额
        double amount = scanner.nextDouble();
        //创建sql语句
        String sql="update user_data set balance = balance + ? where bkID = ?";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection.setAutoCommit(false);//关闭自动提交机制
            //获取数据库操作对象
            preparedStatement = connection.prepareStatement(sql);
            //根据？的下标更改值（从1开始）
            preparedStatement.setDouble(1,amount);
            preparedStatement.setString(2, user.getBkID());
            //执行sql语句
            preparedStatement.executeUpdate();
            //提交事务
            connection.commit();
            //打印流水账
            print_bill(user,amount,0);
            //查询结果，创建查询sql语句
            String Sql = "select * from user_data where bkID = ? ";
            //获取数据库操作对象
            preparedStatement = connection.prepareStatement(Sql);
            //根据？的下标更改值（从1开始）
            preparedStatement.setString(1,user.getBkID());
            //获取查询结果集
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next())//判断是否查询到结果
            {
                //打印当前的余额
                System.out.println("目前存款"+String.format("%.2f",resultSet.getDouble("balance")));
                //更新当前user对象的余额
                user.setBalance(resultSet.getDouble("balance"));
            }


        } catch (SQLException e) {
            try {
                connection.rollback();//回滚事务
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
        finally {
            //从小到大关闭连接
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }


    }

    public static void Withdraw(Connection connection,user user) throws SQLException {//取款功能
        System.out.println("存款为:"+String.format("%.2f", user.getBalance()));
        System.out.print("请输入要取的的金额");
        Scanner scanner = new Scanner(System.in);
        double amount =0;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        while(true)//
        {
            System.out.println("若不想取款则输入0");
            //获取用户输入
             amount = scanner.nextDouble();
             if (amount == 0)//退出取款
             {
                 break;
             }
            if (amount > user.getBalance())//判断取款是否大于余额
            {
                System.out.println("取款超过存款，无法取出");
                continue;
            }
            break;
        }

        //创建sql语句
        String sql="update user_data set balance = balance - ? where bkID = ?";
        try {
            //关闭自动提交机制
            connection.setAutoCommit(false);
            //获取数据库操作对象
            preparedStatement = connection.prepareStatement(sql);
            //根据？的下标更改值（从1开始）
            preparedStatement.setDouble(1,amount);
            preparedStatement.setString(2, user.getBkID());
            //执行sql语句
            preparedStatement.executeUpdate();
            //提交事务
            connection.commit();
            //打印流水账
            print_bill(user,amount,1);
            //创建查询sql语句
            String Sql = "select * from user_data where bkID = ?";
            //创建数据库操作对象
            preparedStatement = connection.prepareStatement(Sql);
            //根据？的下标更改值（从1开始）
            preparedStatement.setString(1,user.getBkID());
            //执行sql语句并获取查询结果集
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next())//判断是否查询到结果
            {
                //输出当前余额
                System.out.println("目前存款"+String.format("%.2f",resultSet.getDouble("balance")));
                //更新当前user对象的余额
                user.setBalance(resultSet.getDouble("balance"));
            }
        } catch (SQLException e) {
            connection.rollback();//回滚事务
            throw new RuntimeException(e);
        }
        finally {
            //从小到大关闭连接
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }
    public static void menu() throws Exception {//菜单页面

        Initial();//初始化连接池
        Connection connection = MaintanceConnection.GetConnection();//获取连接
        Scanner scanner = new Scanner(System.in);
        while(true)//判断用户是否继续
        {
            System.out.println("请输入（登录账户）/0（退出ATM机）");
            int start =scanner.nextInt();//获取用户输入
            if (start == 0) break;//输入0则退出
            System.out.println("请登录账户");
            user user = login(connection);//进入登录页面判断是否登录成功，成功返回user对象，反之null；
            while (user == null) {//判断是否登录成功
                System.out.println("请输入1（登录账户）/0（退出ATM机）");
                start =scanner.nextInt();
                if (start == 0) break;
                System.out.println("请登录账户");
                user = login(connection);
            }
            if (start == 0) break;
            System.out.println("登录成功");
            System.out.println("存款为:"+String.format("%.2f", user.getBalance()));


            int choose = 0;
            while(true)
            {
                System.out.println("===========  菜单   ============");
                System.out.println("=========== 1.存款  ============");
                System.out.println("=========== 2.取款  ============");
                System.out.println("=========== 3.转账  ============");
                System.out.println("=========== 0.退出  ============");
                System.out.println();
                System.out.println("请输入选项:");
                choose = scanner.nextInt();
                switch (choose)//判断用户输入
                {
                    case 1 :
                        Saving(connection,user);
                        break;
                    case 2 :
                        Withdraw(connection,user);
                        break;
                    case 3:
                        Transfer(connection,user);
                        break;
                    case 0:
                        user = null;
                        break;
                }
                if (choose==0)
                {

                    break;
                }
            }
            if (choose == 0){
                MaintanceConnection.ReleaseConnection(connection);//若用户退出，则释放连接
                user = null;//销毁用户对象
            }
        }

    }
    public static user login(Connection connection)//登录功能
    {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int choose = 0;
        while(true){//获取user对象
            System.out.print("请先插入卡或输入卡号:");
            Scanner scanner = new Scanner(System.in);
            //获取用户输入
            String bkID = scanner.next();
            System.out.print("请输入密码:");
            String password = scanner.next();
            if (password.length() != 6)
            {
                System.out.println("密码应该为6位(均为数字)");
                System.out.println("请输入1（重新输入密码）/2(重新输入卡号)/0（退出登录）");
                choose = scanner.nextInt();
                while(choose == 1)
                {
                    System.out.print("请输入密码:");
                    password = scanner.next();
                    if (password.length() == 6)
                    {
                        break;
                    }
                    System.out.println("密码应该为6位(均为数字)");
                    System.out.println("请输入1（重新输入密码）/2(重新输入卡号)/其他数字（退出登录）");
                    choose = scanner.nextInt();

                }
                if (choose == 2 )
                {
                    continue;
                } else  {
                    break;
                }


            }
            String sql = "select * from user_data where bkID = ? and password = ?";//创建查询结果集
            int show = 0;//判断是否查询到结果的对象
            user user=new user();
            try {
                //获取数据库操作对象
                preparedStatement = connection.prepareStatement(sql);
                //根据？的下标更改值（从1开始）
                preparedStatement.setString(1,bkID);
                preparedStatement.setString(2,password);
                //执行查询sql语句并获取查询结果集
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next())
                {
                    show=1;//查询到结果更改show为1用以判断是否获取到对象

                    //将查询结果赋值给user对象的各项属性
                    user.setName(resultSet.getString("name"));
                    user.setBkID(resultSet.getString("bkID"));
                    user.setBalance(resultSet.getDouble("balance"));
                    user.setID(resultSet.getString("ID"));
                    user.setPnumber(resultSet.getString("Pnumber"));
                    user.setPassword(resultSet.getString("password"));
                }
                if (show == 1)//查询到用户
                {
                    return user;//返回该对象
                }
                else{
                    System.out.println("账号或者密码不存在");
                    choose = 0;
                    while(true)
                    {
                        System.out.println("请输入1重试或者输入0退出");
                        choose = scanner.nextInt();
                        switch (choose)
                        {
                            case 1 : break;
                            case 0 : break;
                            default:
                                System.out.println("输入错误请重试");
                        }
                        if (choose==1||choose==0)//若为1/0则退出循环
                        {
                            break;
                        }
                    }
                    if (choose == 1)//判断为1则重新输入账号密码
                    {
                        continue;
                    }else//反之则返回null对象
                    {
                        return null;
                    }

                }



            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            finally {
                //从小到大关闭连接
                if (resultSet != null) {
                    try {
                        resultSet.close();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
                if (preparedStatement != null) {
                    try {
                        preparedStatement.close();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }


        return null;
    }

    public static void Transfer(Connection connection,user transferor)//转账功能
    {
        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入到账银行卡：");
        String bkID = scanner.nextLine();//获取银行卡号
        user payee  = Search(connection,bkID);//查找是否存在转账的到账用户
        int choose = 0;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        while (payee==null)
        {
            System.out.println("银行卡号输入错误，请重新输入(1)或者退出(0)");
            choose = scanner.nextInt();
            switch (choose)
            {
                case 1:
                    System.out.print("请输入到账银行卡：");
                    scanner.nextLine();
                    bkID = scanner.nextLine();//获取银行卡号
                    payee  = Search(connection,bkID);//查找是否存在转账的到账用户
                    break;
                case 0:return;
                default:
                    System.out.println("输入错误，请重试");
                    break;
            }

        }
        double amount = 0;
        System.out.print("请核实收款人信息：");
        System.out.println("姓名：" + payee.getName() + "卡号: " + payee.getBkID());
        System.out.println("余额:"+transferor.getBalance());
        System.out.print("请输入要转账的金额(输入0则取消转账)：");
        amount = scanner.nextDouble();
        if (amount == 0)//判断是否取消转账
        {
            System.out.println("取消转账");
            return;
        }
        while (amount > transferor.getBalance())//判断转账金额是否大于余额
        {
            System.out.println("转账金额超过余额，输入1重试或者输入0退出");
            choose = scanner.nextInt();
            switch(choose)
            {
                case 1 :System.out.print("请输入要转账的金额：");
                    amount = scanner.nextDouble();
                case 0:
                    System.out.println("退出转账");
                    return;
            }
        }
        //创建入账，出账以及查询sql语句
        String Transfer ="update user_data set balance = balance - ? where bkID = ?";
        String proceeds ="update user_data set balance = balance + ? where bkID = ?";
        String STransfer = "select * from user_data where bkID = ?";
        try {
            //关闭自动提交机制
            connection.setAutoCommit(false);
            //获取数据库操作对象
            preparedStatement = connection.prepareStatement(Transfer);
            //根据？的下标更改值（从1开始）
            preparedStatement.setDouble(1,amount);
            preparedStatement.setString(2,transferor.getBkID());
            //执行sql语句
            preparedStatement.executeUpdate();
            //获取数据库操作对象
            preparedStatement = connection.prepareStatement(proceeds);
            //根据？的下标更改值（从1开始）
            preparedStatement.setDouble(1,amount);
            preparedStatement.setString(2,payee.getBkID());
            //执行sql语句
            preparedStatement.executeUpdate();
            //当两件事务都正常完成则提交事务
            connection.commit();
            //打印流水账
            print_bill(transferor,payee,amount);
            //获取数据库操作对象
            preparedStatement = connection.prepareStatement(STransfer);
            //根据？的下标更改值（从1开始）
            preparedStatement.setString(1,transferor.getBkID());
            //执行sql语句并获取查询结果集
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next())//判断是否查找到结果
            {
                transferor.setBalance(resultSet.getDouble("balance"));//更新转账用户的对象的余额属性
            }
            //获取数据库操作对象
            preparedStatement = connection.prepareStatement(STransfer);
            //根据？的下标更改值（从1开始）
            preparedStatement.setString(1,payee.getBkID());
            //执行sql语句并获取查询结果集
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next())//判断是否查找到结果
            {
                payee.setBalance(resultSet.getDouble("balance"));//更新收款用户的对象的余额属性
            }

        } catch (SQLException e) {
            try {
                connection.rollback();//回滚事务
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }finally {
            //从小到大关闭连接
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }


    }
    public static user Search(Connection connection,String bkID)//根据银行卡查找用户
    {
        user user = new user();
        //创建查询sql语句
        String sql = "select * from user_data where bkID = ?";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            //获取数据库操作对象
            preparedStatement = connection.prepareStatement(sql);
            //根据？的下标更改值（从1开始）
            preparedStatement.setString(1,bkID);
            //执行sql语句并获取查询结果集
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next())//判断是否查询到结果
            {
                //更新user用户对象部分属性
                user.setBalance(resultSet.getDouble("balance"));
                user.setName(resultSet.getString("name"));
                user.setBkID(resultSet.getString("bkID"));
            }
            if (user.getName()!=null)//判断是否查询到
            {
                return user;
            }else {
                return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            //从小到大关闭连接
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }
    public static void print_bill(user user, double amount , int choose)//打印存/取款流水账
    {
        try {
            //获取打印文件
            FileOutputStream fileOutputStream = new FileOutputStream("D:\\idea_java_projects\\Challenge\\src\\ATM\\Running_account.txt",true);
            //获取当前日期
            Date date = new Date();
            //格式化日期
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //获取日期并更改为字节数组
            byte Date[]= format.format(date).getBytes();
            try {
                fileOutputStream.write(Date);//打印时间
                if (choose == 0)//存款
                {
                    fileOutputStream.write(("\t\t"+user.getBkID()+"存入了"+amount+"元\n").getBytes());

                }
                else {//取款
                    fileOutputStream.write(("\t\t"+user.getBkID()+"取出了"+amount+"元\n").getBytes());
                }
                //刷新输出流并强制写出任何缓冲的输出字节
                fileOutputStream.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
    //方法重载
    public static void print_bill(user transferor,user payee,double amount)//打印转账流水账
    {
        try {
            //获取打印文件
            FileOutputStream fileOutputStream = new FileOutputStream("D:\\idea_java_projects\\Challenge\\src\\ATM\\Running_account.txt",true);
            //获取当前日期
            Date date = new Date();
            //格式化日期
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //获取日期并更改为字节数组
            byte Date[]= format.format(date).getBytes();
            try {
                fileOutputStream.write(Date);//打印时间
                fileOutputStream.write(("\t\t"+transferor.getBkID()+"向"+payee.getBkID()+"转账了"+amount+"元\n").getBytes());//打印转账信息
                //刷新输出流并强制写出任何缓冲的输出字节
                fileOutputStream.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
