package week4.task2;

/**
 * @Auther: hqlv
 * @Date: 2021/5/29 23:55
 * @Description:
 */
public class Method07 {
    public static void main(String[] args) {
        MyThread t = new MyThread();
        t.start();
        System.out.println("hello......two");
    }

    static class MyThread extends Thread {
        @Override
        public void run() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("hello......one");
        }
    }

}
