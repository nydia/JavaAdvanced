package week4.task2;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Auther: hqlv
 * @Date: 2021/5/29 23:55
 * @Description: CyclicBarrier 阻塞执行
 */
public class Method02 {
    public static void main(String[] args) {
        final AtomicInteger atomicInteger = new AtomicInteger(1);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5, new MyThread());
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    atomicInteger.getAndIncrement();
                    cyclicBarrier.await();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }catch (BrokenBarrierException e){
                    e.printStackTrace();
                }
            }
        });
        t.setDaemon(true);
        t.start();
        System.out.println(atomicInteger.get());
        cyclicBarrier.reset();//打破当前屏障并且重置新屏障
    }
    static class MyThread implements Runnable{
        @Override
        public void run() {
            System.out.println("你好主线程，我是子线程");
        }
    }
}
