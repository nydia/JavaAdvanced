# 第四周作业

#### 作业2：

1.方法1：

```java
/**
 * @Auther: hqlv
 * @Date: 2021/5/29 23:55
 * @Description: CountDownLatch 阻塞执行
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
```

2.方法2：

```java
/**
 * @Auther: hqlv
 * @Date: 2021/5/29 23:55
 * @Description: CyclicBarrier 阻塞执行
 */
public class Method02 {
    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(1);
        Thread t = new Thread(new MyThread(cyclicBarrier));
        t.start();
        cyclicBarrier.reset();

    }
    static class MyThread implements Runnable{
        private CyclicBarrier cyclicBarrier;
        public MyThread(CyclicBarrier cyclicBarrier){
            this.cyclicBarrier = cyclicBarrier;
        }
        @Override
        public void run() {
            try {
                this.cyclicBarrier.await();
            }catch (InterruptedException e){
                e.printStackTrace();
            }catch (BrokenBarrierException e){
                e.printStackTrace();
            }
            System.out.println("hello......");
        }
    }
}
```

3.方法3：

```java

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
```

4. 方法4:

```java
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
```


#### 作业6:

![](nThread&JavaConcurrency.png)

