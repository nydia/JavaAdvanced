//package io.kimmking.cache.config;
//
//import org.apache.ibatis.plugin.Interceptor;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//import org.springframework.core.io.support.ResourcePatternResolver;
//
//import javax.sql.DataSource;
//
///**
// * Copyright (C) 2021 ShangHai IPS Information Technology Co.,Ltd.
// * <p>
// * All right reserved.
// * <p>
// * This software is the confidential and proprietary information of IPS
// * Company of China. ("Confidential Information"). You shall not disclose such
// * Confidential Information and shall use it only in accordance with the terms
// * of the contract agreement you entered into with IPS inc.
// * <p>
// *
// * @Description: DataSourceConfig
// * @ClassName: DataSourceConfig
// * @Auther: nydia.lhq
// * @Date: 2021/7/13 17:30
// */
//@Configuration
//public class DataSourceConfig {
//
//    @Bean("myDataSourceProperties")
//    @ConfigurationProperties("spring.datasource")
//    public DataSourceProperties myDataSourceProperties() {
//        return new DataSourceProperties();
//    }
//
//    @Bean("myDataSource")
//    public DataSource myDataSource() {
//        DataSourceProperties dataSourceProperties = myDataSourceProperties();
//        return dataSourceProperties.initializeDataSourceBuilder().build();
//    }
//
//    @Bean("mySqlSessionFactory")
//    public SqlSessionFactory mySqlSessionFactory(@Qualifier("myDataSource") DataSource myDataSource){
//        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
//        bean.setDataSource(myDataSource);
//        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();//添加XML目录
//        try {
//            bean.setMapperLocations(resolver.getResources("classpath*:/mapper/**Mapper.xml"));
//            return bean.getObject();
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
//    }
//
//}
