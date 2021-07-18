package io.kimmking.cache.tester;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

/**
 * Copyright (C) 2021 ShangHai IPS Information Technology Co.,Ltd.
 * <p>
 * All right reserved.
 * <p>
 * This software is the confidential and proprietary information of IPS
 * Company of China. ("Confidential Information"). You shall not disclose such
 * Confidential Information and shall use it only in accordance with the terms
 * of the contract agreement you entered into with IPS inc.
 * <p>
 *
 * @Description: Guava Cache
 * @ClassName: GuavaCacheTest\
 * @Auther: nydia.lhq
 * @Date: 2021/7/13 16:13
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest
@Slf4j
public class GuavaCacheTest {

//    @Test
    public void testCache(){

    }

    public static void main(String[] args) {
        try {
            Cache<String, String> cache = CacheBuilder.newBuilder()
                    .maximumSize(1024)
                    .expireAfterWrite(2, TimeUnit.SECONDS)
                    .weakValues() //弱引用
                    .build();
            cache.put("word","Hello Guava Cache");
            System.out.println(cache.getIfPresent("word"));

            Thread.sleep(3000L);

            System.out.println(cache.getIfPresent("word"));//过期

        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
