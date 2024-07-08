package practice;

import java.util.ArrayList;
import java.util.List;

public class synchornized {
    public static void main(String[] args) {
        List list = new ArrayList();
        Thread t1 = new Thread(new producity(list));
        Thread t2 = new Thread(new consumer(list));
        t1.setName("生产者线程");
        t2.setName("消费者线程");
        t1.start();
        t2.start();
    }


}

class producity implements Runnable{
    private List list;

    public producity(List list) {
        this.list = list;
    }


    @Override
    public void run() {
        while(true)
        {
            synchronized (list)
            {
                if (list.size()>2)
                {
                    try {
                        list.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                Object obj = new Object();
                list.add(obj);
                list.notifyAll();
                System.out.println(Thread.currentThread().getName() + "--->" + obj);
            }


        }
    }
}

class consumer implements Runnable {

    private List list;

    public consumer(List list) {
        this.list = list;
    }

    @Override
    public void run() {
        while(true) {
            synchronized (list) {
                if (list.size() == 0) {
                    try {
                        list.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

                for (int i = 0 ; i < list.size() ; i ++)
                {
                    Object obj = list.remove(i);
                    list.notifyAll();
                    System.out.println(Thread.currentThread().getName() + "--->" + obj);
                }


            }
        }
    }
}
