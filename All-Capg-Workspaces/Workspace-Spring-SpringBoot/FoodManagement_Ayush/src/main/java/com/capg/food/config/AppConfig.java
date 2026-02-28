package com.capg.food.config;

import org.springframework.context.annotation.*;
import org.springframework.orm.jpa.*;
import org.springframework.orm.jpa.vendor.*;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import javax.persistence.EntityManagerFactory;
import com.zaxxer.hikari.HikariDataSource;
import java.util.Properties;

@Configuration
@ComponentScan("com.capg.food")
@EnableTransactionManagement
public class AppConfig {

    @Bean
    public DataSource dataSource() {
        HikariDataSource ds = new HikariDataSource();
        ds.setDriverClassName("oracle.jdbc.OracleDriver");
        ds.setJdbcUrl("jdbc:oracle:thin:@localhost:1521:xe");
        ds.setUsername("capgdb");
        ds.setPassword("capgdb");
        return ds;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

        LocalContainerEntityManagerFactoryBean emf =
                new LocalContainerEntityManagerFactoryBean();

        emf.setDataSource(dataSource());
        emf.setPackagesToScan("com.capg.food.entity");

        HibernateJpaVendorAdapter vendorAdapter =
                new HibernateJpaVendorAdapter();

        emf.setJpaVendorAdapter(vendorAdapter);

        Properties props = new Properties();
        props.put("hibernate.dialect",
                "org.hibernate.dialect.Oracle10gDialect");
        props.put("hibernate.hbm2ddl.auto", "update");
        props.put("hibernate.show_sql", "true");

        emf.setJpaProperties(props);

        return emf;
    }

    @Bean
    public JpaTransactionManager transactionManager(
            EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }
}