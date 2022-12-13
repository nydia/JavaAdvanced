package io.kimmking.cache.redisclient;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

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
 * @Description: Lettuce 配置
 * @ClassName: LettuceConfiguration
 * @Auther: nydia.lhq
 * @Date: 2021/7/15 16:10
 */
@Configuration
public class LettuceConfiguration {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port:6379}")
    private int port;

    @Value("${spring.redis.password:}")
    private String password;

    @Value("${spring.redis.timeout:30}")
    private int timeout;

    @Bean
    public RedisClient redisClient(){
        RedisURI redisURI = RedisURI.builder()
                .withHost(host)
                .withPort(port)
                .withPassword(password)
                .withTimeout(Duration.ofSeconds(timeout))
                .build();
        return RedisClient.create(redisURI);
    }

}
