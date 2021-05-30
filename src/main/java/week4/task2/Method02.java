package week4.task2;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

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
