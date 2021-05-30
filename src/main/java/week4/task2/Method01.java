package week4.task2;

import java.util.concurrent.CountDownLatch;

/**
 * @Auther: hqlv
 * @Date: 2021/5/29 23:42
 * @Description: CountDownLatch 阻塞实现
 */
public class Method01 {
    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Thread t = new Thread(new MyThread(countDownLatch));
        t.start();
        try {
            countDownLatch.await();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    static class MyThread implements Runnable{
        private CountDownLatch countDownLatch;
        public MyThread(CountDownLatch countDownLatch){
            this.countDownLatch = countDownLatch;
        }
        @Override
        public void run() {
            System.out.println("hello.......");
            countDownLatch.countDown();
        }
    }
}
