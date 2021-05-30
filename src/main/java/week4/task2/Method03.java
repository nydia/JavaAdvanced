package week4.task2;

import java.util.Map;
import java.util.concurrent.*;

/**
 * @Auther: hqlv
 * @Date: 2021/5/29 23:55
 * @Description: Future.get 阻塞执行
 */
public class Method03 {
    static final int num = Runtime.getRuntime().availableProcessors();
    final static ExecutorService executorService = Executors.newFixedThreadPool(num);
    public static void main(String[] args) {
        Future future = executorService.submit(new MyThread());
        try {
            future.get();
        }catch (InterruptedException e){
            e.printStackTrace();
        }catch (ExecutionException e){
            e.printStackTrace();
        }finally {
            executorService.shutdownNow();
        }
    }
    static class MyThread implements Callable<Map>{
        @Override
        public Map call() throws Exception {
            Map map = new ConcurrentHashMap();
            System.out.println("hello......");
            return map;
        }
    }
}
