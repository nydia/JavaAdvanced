package io.kimmking.cache.redisclient;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

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
 * @Description: RedisTemplate 客户端
 * @ClassName: RedisConfiguration
 * @Auther: nydia.lhq
 * @Date: 2021/7/15 16:08
 */
@Configuration
public class RedisConfiguration {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(redisConnectionFactory);

        // 初始化 Jackson2 序列化器
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        // 开启 POJO 类所以属性的自动检测
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 序列化时允许非常量字段输出类型，以便反序列化时得到对应的具体对象（不设置此属性时，反序列化的结果为 LinkedHashMap）
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

        // 特别注意：所有数据类型的键值序列化都必须是 String 类型序列化器，等价于 String.getBytes()，value使用 Jackson2 序列化
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(jackson2JsonRedisSerializer);

        // hash field 的序列化方式和 key 保持一致，value 使用 Jackson2 序列化
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(jackson2JsonRedisSerializer);

        template.afterPropertiesSet();
        return template;
    }

}
