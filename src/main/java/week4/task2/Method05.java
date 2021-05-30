package week4.task2;

/**
 * @Auther: hqlv
 * @Date: 2021/5/29 23:55
 * @Description: Thread.join() 阻塞当前线程的执行等待子线程执行完成
 */
public class Method05 {
    public static void main(String[] args) {
        TD t = new TD();
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("hello......two");
    }
    static class TD extends Thread {
        @Override
        public void run() {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("hello......one");
        }
    }
}
