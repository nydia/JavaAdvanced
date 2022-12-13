package io.kimmking.cache.tester;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.kimmking.cache.entity.Author;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManagerFactory;

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
 * @Description: HibernateCacheTest
 * @ClassName: MybatisCacheTest
 * @Auther: nydia.lhq
 * @Date: 2021/7/13 16:13
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class HibernateCacheTest {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    //测试一级缓存 （debug断点模式下查看sql）
    @Test
    public void testLv1Cache(){
        try {
            if (entityManagerFactory.unwrap(SessionFactory.class) == null) {
                throw new NullPointerException("factory is not a hibernate factory");
            }
            SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);

            Session session = sessionFactory.openSession();
            //第一次查询 查库
            Author author = session.get(Author.class, 1L);
            System.out.println(new ObjectMapper().writeValueAsString(author));
            //第二次查询 查缓存
            Author author2 = session.get(Author.class, 1L);
            System.out.println(new ObjectMapper().writeValueAsString(author2));

        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
