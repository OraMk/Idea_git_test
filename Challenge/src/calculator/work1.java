package calculator;

import sun.management.snmp.jvminstr.JvmRTInputArgsEntryImpl;

import javax.security.auth.Subject;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;

public class work1 {
    public static void main(String[] args) {
        BigDecimal A;
        BigDecimal B;
        char calculator;
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入第一个数字");
        A = scanner.nextBigDecimal();
        System.out.println("请输入运算符");
        calculator = scanner.next().charAt(0);
        System.out.println("请输入第二个数字");
        B = scanner.nextBigDecimal();
        sum Sum = new sum();
        sub Sub = new sub();
        mul Mul = new mul();
        remainder Rem = new remainder();
        division Div = new division();
        BigDecimal ret;
        switch (calculator) {//判断运算符
            case '+':
                ret = Sum.Sum(A, B);
                System.out.println("结果为: " + ret);
                break;
            case '-':
                ret = Sub.Sub(A, B);
                System.out.println("结果为: " + ret);
                break;
            case '*':
                ret = Mul.Mul(A, B);
                System.out.println("结果为: " + ret);
                break;
            case '/':
                ret = Div.Division(A, B);
                System.out.println("结果为: " + ret);
                break;
            case '%':
                ret = Rem.Remainde(A, B);
                if (ret!=null)
                {
                    Integer result = Integer.valueOf(ret.intValue());
                    System.out.println("结果为: " + result);
                }

                break;

        }

    }

}


class sum {//加

    public BigDecimal Sum(BigDecimal A, BigDecimal B) {
        return A.add(B);
    }
}

class sub {//减

    public BigDecimal Sub(BigDecimal A, BigDecimal B) {
        return A.subtract(B);
    }
}

class mul {//乘

    public BigDecimal Mul(BigDecimal A, BigDecimal B) {
        return A.multiply(B);
    }
}

class division {//除

    public BigDecimal Division(BigDecimal A, BigDecimal B) {
        if (Integer.valueOf(B.toString())== 0)//判断除数是否非0
        {
            System.out.println("除数不能为0，无法计算");
            return null;
        }

        return A.divide(B,4,RoundingMode.HALF_UP);//保留4位小数

    }
}

class remainder {//取余

    public BigDecimal Remainde(BigDecimal A, BigDecimal B) {
        String s = String.valueOf(A.doubleValue());
        String[] split = s.split("\\.");//分隔字符串判断是否为小数
        String ss = String.valueOf(B.doubleValue());
        String[] Split = ss.split("\\.");
        if (!(split[1].length() == 1 && split[1].equals("0"))||!(Split[1].length() == 1 && Split[1].equals("0")))
        {
            System.out.println("取余操作应为整形,不能为小数");
            return null;
        }
        return A.remainder(B);
    }
}