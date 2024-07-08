package Dual_coloured_ball;


import java.util.Arrays;
import java.util.Scanner;

public class work1 {
    public static void main(String[] args) {
        int[] red = new int[6];
        int blue = 0;
        int[] ired = new int[6];
        int iblue;
        Scanner scanner = new Scanner(System.in);


        //用户的号码
        System.out.println("请输入蓝色球的号码");
        iblue = scanner.nextInt();
        while (iblue < 1 || iblue > 99) {
            System.out.println("号码输入异常，请重新输入，范围在1-99");
            iblue = scanner.nextInt();
        }

        for (int i = 0; i < ired.length; i++) {
            System.out.println("请输入第"+ (i+1) + "个红色球的号码");
            ired[i] = scanner.nextInt();
            while (ired [i] < 1 || ired [i] >99) {
                System.out.println("号码输入异常，请重新输入，范围在1-99");
                ired[i] = scanner.nextInt();
            }
            for (int j = 0; j < i; j++) {
                if (ired[i] == ired[j]) {
                    System.out.println("号码重复，请重新输入，范围在1-99");
                    i--;
                }
            }
        }
        scanner.nextLine();


        //中奖号码
        blue = (int) (Math.random() * 100);
        for (int i = 0; i < 6; i++) {
            red[i] = (int) (Math.random() * 100);
            for (int j = 0; j < i; j++) {
                if (red[i] == red[j]) {
                    i--;
                }
            }

        }

        //人为控制某些号码的中奖概率
        System.out.println("请输入管理员密码: ");
        String code = "abc12345";
        String Code = scanner.nextLine() ;

        while (!Code.equals(code))
        {
            System.out.println("密码错误，请重试\\或输入0退出");
            Code = scanner.nextLine();
            if (Code.isEmpty())continue;
            if (Code.charAt(0) == '0' && Code.length() == 1)break;
        }
        if (Code.equals(code))
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
                            red[i] = exred;
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
                                System.out.printf("输入非法，范围在1-99");
                                continue;
                            }
                            blue = exblue;
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





        //判断中奖情况
        int tred = 0;
        int tblue = 0;
        if (iblue == blue) {
            tblue = 1;
        }
        for (int i = 0; i < red.length; i++) {
            for (int j = 0; j < ired.length; j++) {
                if (ired[j] == red[i]) {
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
        System.out.print("红色中奖号码: ");
//        Arrays.sort(red);
        for (int i:red) {
            System.out.print(i + " ");
        }
        System.out.println();
        System.out.print("用户的红色号码为: ");
        for (int i:ired) {
            System.out.print(i + " ");
        }
        System.out.println();
        System.out.print("用户蓝色中奖号码: " + blue );
        System.out.println();
        System.out.print("蓝色中奖号码: " + blue );


    }

}