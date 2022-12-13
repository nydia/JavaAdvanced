package io.kimmking.cache.redisclient;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
 * @Description: Redisson 配置
 * @ClassName: RedissonConfiguration
 * @Auther: nydia.lhq
 * @Date: 2021/7/15 16:00
 */
@Configuration
public class RedissonConfiguration {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port:6379}")
    private int port;

    @Value("${spring.redis.password:}")
    private String password;

    @Value("${spring.redis.timeout:30}")
    private int timeout;

    //rdis.2.4.5 配置protected-mode 有问题，用redis5.0.10可以
    @Bean
    public RedissonClient redissionClient(){
        Config config = new Config();
        config.useSingleServer()
                //.setAddress("redis://"+host+":" + port)
                .setAddress(host+":" + port)
                .setPassword(password)
                .setTimeout(timeout)
                .setClientName("redission_lock");
        return Redisson.create(config);
    }

}
