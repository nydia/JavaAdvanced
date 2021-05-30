package week4.task2;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @Auther: hqlv
 * @Date: 2021/5/29 23:55
 * @Description: FutureTask.get() 等待子线程返回后再执行当前线程
 */
public class Method06 {
    public static void main(String[] args) {
        FutureTask<String> futureTask = new FutureTask<>(new MyCallable());
        try {
            new Thread(futureTask).start();
            String str = futureTask.get();
            System.out.println(str);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("hello......two");
    }
    static class MyCallable implements Callable<String> {
        @Override
        public String call() throws Exception {
            return "hello......one";
        }
    }
}
