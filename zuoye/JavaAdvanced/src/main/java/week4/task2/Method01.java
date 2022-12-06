package week4.task2;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * @Auther: hqlv
 * @Date: 2021/5/29 23:42
 * @Description: CountDownLatch 阻塞实现
 */
public class Method01 {
    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Thread t = new Thread(new MyThread(countDownLatch, map));
        t.setDaemon(true);
        t.start();
        try {
            countDownLatch.await();
            System.out.println(map.get("val"));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    static class MyThread implements Runnable {
        private CountDownLatch countDownLatch;
        private Map<String, String> map;
        public MyThread(CountDownLatch countDownLatch, Map<String, String> map) {
            this.countDownLatch = countDownLatch;
            this.map = map;
        }
        @Override
        public void run() {
            map.put("val", "你好主线程");
            countDownLatch.countDown();
        }
    }
}
