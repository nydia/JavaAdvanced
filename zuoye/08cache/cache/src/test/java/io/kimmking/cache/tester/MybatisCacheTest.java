package io.kimmking.cache.tester;

import io.kimmking.cache.entity.User;
import io.kimmking.cache.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;

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
 * @Description: MybatisCacheTest
 * @ClassName: MybatisCacheTest
 * @Auther: nydia.lhq
 * @Date: 2021/7/13 16:13
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class MybatisCacheTest {

    @Autowired
    private SqlSessionFactory mySqlSessionFactory;

    //mybatis一级缓存测试
    @Test
    public void oneSqlSession(){
        SqlSession sqlSession = mySqlSessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        // 同一个 sqlSession 进行第一次查询
        User user = userMapper.find(1);
        System.out.println(user);
        // 同一个 sqlSession 进行第二次查询
        User user2 = userMapper.find(1);
        System.out.println(user2);
    }

    //mybatis一级缓存测试 (mybatis.configuration.cache-enabled 是否开启全局配置)
    @Test
    public void diffSqlSession(){
        // 不同 sqlSession 进行第一次查询
        SqlSession sqlSession = mySqlSessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = userMapper.find(1);
        System.out.println(user);
        // 不同 sqlSession 进行第二次查询
        SqlSession sqlSession2 = mySqlSessionFactory.openSession();
        UserMapper userMapper2 = sqlSession2.getMapper(UserMapper.class);
        User user2 = userMapper2.find(1);
        System.out.println(user2);
    }

}
