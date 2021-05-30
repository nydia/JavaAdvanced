package week4.task2;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @Auther: hqlv
 * @Date: 2021/5/29 23:55
 * @Description: CompletableFuture.get 阻塞执行
 */
public class Method04 {
    public static void main(String[] args) {
        CompletableFuture future = CompletableFuture.runAsync(new MyThread());
        try {
            future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    static class MyThread implements Runnable {
        @Override
        public void run() {
            System.out.println("hello......");
        }
    }
}
