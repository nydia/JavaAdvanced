package io.kimmking.cache.redission;

import org.redisson.Redisson;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RLock;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

public class RedissionDemo2 {

    //分布式锁
    public static void main(String[] args) {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://192.168.99.100:6379");
        final RedissonClient client = Redisson.create(config);

        //获取锁
        RLock lock = client.getLock("lock1");
        try{
            //锁
            lock.lock();
            //获取库存总数
            RAtomicLong total = client.getAtomicLong("total");
            long totalNum = total.get();
            System.out.println("total: " + total);
            //获取使用的库存数
            RAtomicLong used = client.getAtomicLong("used");
            long usedNum = used.get();
            System.out.println("used : "  + used);
            //如果库存数量小于库存总数，库存使用数减 1，如果库存数大于等于库存数则 返回库存不足
            if(usedNum + 1 < totalNum){
                used.set(usedNum + 1);
                System.out.println("库存总数为：" + totalNum + ",已经使用库存数: " + (usedNum + 1));
            } else {
                System.out.println("库存不足");
            }
        }finally{
            //释放锁
            lock.unlock();
        }
        //关闭redission连接
        client.shutdown();
    }

}
