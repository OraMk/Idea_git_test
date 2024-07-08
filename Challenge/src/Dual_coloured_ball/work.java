package Dual_coloured_ball;

import java.util.Properties;
import java.util.Scanner;

public class work {




    public static void main(String[] args) {
        ball ball = new ball();
        ball MedalBall = new ball();
        MedalBall.random();
        ball.setBlue();
        ball.setRed();
        int i;
        System.out.println("请输入是否进入管理员模式:1.进入、其他.退出");
        Scanner scanner = new Scanner(System.in);
        i = scanner.nextInt();
        scanner.nextLine();
        Adm person = new Adm();
        switch(i){
            case 1 :
                person.Log();
                person.exchange(MedalBall);
                break;
            default:break;
        }
        MedalBall.medal(ball,MedalBall);
        MedalBall.show(ball,MedalBall);

    }
}
class ball{
     int blue;
     int[] red;
    //设置用户蓝色球
    public void setBlue() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入蓝色球的号码");
        blue = scanner.nextInt();
        while (blue < 1 || blue > 99) {
            System.out.println("号码输入异常，请重新输入，范围在1-99");
            blue = scanner.nextInt();
        }
    }
    //设置用户红色球
    public void setRed() {
        red = new int[6];
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < red.length; i++) {//输入红色球号码
            System.out.println("请输入第"+ (i+1) + "个红色球的号码");
            red[i] = scanner.nextInt();
            while (red [i] < 1 || red [i] >99) {//判断非法
                System.out.println("号码输入异常，请重新输入，范围在1-99");
                red[i] = scanner.nextInt();
            }
            for (int j = 0; j < i; j++) {//判断重复
                if (red[i] == red[j]) {
                    System.out.println("号码重复，请重新输入，范围在1-99");
                    i--;
                }
            }
        }
        scanner.nextLine();

    }
    //随机双色球
    public void random()
    {
        red = new int[6];
        blue = (int) (Math.random() * 100);
        for (int i = 0; i < 6; i++) {
            red[i] = (int) (Math.random() * 100);//产生随机数
            for (int j = 0; j < i; j++) {//判断重复
                if (red[i] == red[j]) {
                    i--;
                }
            }

        }
    }
    //显示获奖情况
    public void medal(ball A, ball B)
    {
        int tred = 0;
        int tblue = 0;
        if (A.blue == B.blue) {
            tblue = 1;
        }
        for (int i = 0; i < B.red.length; i++) {
            for (int j = 0; j < A.red.length; j++) {
                if (A.red[j] == B.red[i]) {
                    tred++;
                }
            }
        }
        if (tblue == 1) {
            switch (tred) {
                case 0:
                    System.out.println("0红1蓝中5元");
                    break;
                case 1:
                    System.out.println("1红1蓝中5元");
                    break;
                case 2:
                    System.out.println("2红1蓝中5元");
                    break;
                case 3:
                    System.out.println("3红1蓝中10元");
                    break;
                case 4:
                    System.out.println("4红1蓝中200");
                    break;
                case 5:
                    System.out.println("5红1蓝中3000");
                    break;
                case 6:
                    System.out.println("6红1蓝中1000万");
                    break;
            }
        } else {
            switch (tred) {
                case 0:
                case 1:
                case 2:
                case 3:
                    System.out.println("很遗憾，没有中奖");
                    break;
                case 4:
                    System.out.println("4红0蓝中100");
                    break;
                case 5:
                    System.out.println("5红0蓝中200");
                    break;
                case 6:
                    System.out.println("6红0蓝中10万");
                    break;
            }
        }
    }
    //展示球号码
    public void show(ball A,ball B)
    {
        System.out.print("红色中奖号码: ");
//        Arrays.sort(red);
        for (int i:B.red) {
            System.out.print(i + " ");
        }
        System.out.println();
        System.out.print("用户的红色号码为: ");
        for (int i:A.red) {
            System.out.print(i + " ");
        }
        System.out.println();
        System.out.print("蓝色中奖号码: " + B.blue );
        System.out.println();
        System.out.print("用户的蓝色号码: " + A.blue );
    }

}
class Adm{
    String code = "abc12345";
    String input;

    public void Log()
    {

        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入管理员密码: ");
        input = scanner.nextLine() ;
        while (!input.equals(code))
        {
            System.out.println("密码错误，请重试\\或输入0退出");
            input = scanner.nextLine();
            if (input.isEmpty())continue;
            if (input.charAt(0) == '0' && input.length() == 1)break;
        }
    }
    public void exchange(ball A)
    {
        Scanner scanner = new Scanner(System.in);
        if (input.equals(code))
        {
            System.out.println("请输入你要更改的号码颜色: 1.红色、2.蓝色、0.退出");
            int pan = scanner.nextInt();
            while(pan!=0){
                switch (pan){
                    case 1:
                        int i = 0;
                        int exred;
                        while(true){
                            System.out.println("请输入要更改的第" + (i+1) + "个红色球的号码或者输入0退出");
                            exred = scanner.nextInt();
                            if (exred == 0 )
                            {
                                break;
                            }
                            if (exred < 1 || exred > 99)
                            {
                                System.out.println("输入非法，范围在1-99");
                                continue;
                            }
                            A.red[i] = exred;
                            i++;
                            if (i >= 6)
                            {
                                break;
                            }
                        }
                        System.out.println("请输入你要更改的号码颜色: 1.红色、2.蓝色、0.退出");
                        pan = scanner.nextInt();
                        break;
                    case 2:
                        int exblue;
                        while (true){
                            System.out.println("请输入要更改的蓝色球号码或者输入0退出");
                            exblue = scanner.nextInt();
                            if (exblue == 0)
                            {
                                break;
                            }
                            if (exblue < 1 || exblue >99)
                            {
                                System.out.println("输入非法，范围在1-99");
                                continue;
                            }
                            A.blue = exblue;
                            break;
                        }
                        System.out.println("请输入你要更改的号码颜色: 1.红色、2.蓝色、0.退出");
                        pan = scanner.nextInt();
                        break;


                    default:
                        System.out.println("输入非法，请重新输入");
                        pan = scanner.nextInt();
                        break;
                }


            }

        }
    }

}
