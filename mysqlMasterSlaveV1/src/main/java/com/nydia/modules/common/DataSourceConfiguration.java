package com.nydia.modules.common;

import com.nydia.modules.entity.User;
import com.nydia.modules.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitManager;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@EnableJpaRepositories(basePackageClasses = UserRepository.class, entityManagerFactoryRef = "userEntityManager", transactionManagerRef = "userTransactionManager")
@EnableTransactionManagement
public class DataSourceConfiguration {

    @Autowired(required = false)
    private PersistenceUnitManager persistenceUnitManager;

    @Bean
    @Primary
    @ConfigurationProperties("spring.jpa")
    public JpaProperties userJpaProperties() {
        return new JpaProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "master-db.datasource")
    public DataSource masterDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix = "slave1-db.datasource")
    public DataSource slave1DataSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * Adds all available datasources to datasource map.
     *
     * @return datasource of current context
     */
    @Bean
    @Primary
    public DataSource userDataSource() {
        DataSourceRouter router = new DataSourceRouter();

        final HashMap<Object, Object> map = new HashMap<>(2);
        map.put(DatabaseEnvironment.MASTER, masterDataSource());
        map.put(DatabaseEnvironment.SLAVE1, slave1DataSource());
        router.setTargetDataSources(map);
        return router;
    }

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean userEntityManager(final JpaProperties userJpaProperties) {
        EntityManagerFactoryBuilder builder =  createEntityManagerFactoryBuilder(userJpaProperties);
        return builder.dataSource(userDataSource()).packages(User.class).persistenceUnit("userEntityManager").build();
    }

    @Bean
    @Primary
    public JpaTransactionManager userTransactionManager(@Qualifier("userEntityManager") final EntityManagerFactory factory) {
        return new JpaTransactionManager(factory);
    }

    private EntityManagerFactoryBuilder createEntityManagerFactoryBuilder(JpaProperties userJpaProperties) {
        JpaVendorAdapter jpaVendorAdapter = createJpaVendorAdapter(userJpaProperties);
        return new EntityManagerFactoryBuilder(jpaVendorAdapter, userJpaProperties.getProperties(), this.persistenceUnitManager);
    }

    private JpaVendorAdapter createJpaVendorAdapter(JpaProperties jpaProperties) {
        AbstractJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setShowSql(jpaProperties.isShowSql());
        adapter.setDatabase(jpaProperties.getDatabase());
        adapter.setDatabasePlatform(jpaProperties.getDatabasePlatform());
        adapter.setGenerateDdl(jpaProperties.isGenerateDdl());
        return adapter;
    }
}