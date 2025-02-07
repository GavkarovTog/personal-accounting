package com.example.personalaccounting;

import javax.sql.DataSource;

import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

public class ApplicationConfiguration {
    private DataSource dataSource;

    public LocalSessionFactoryBean localSessionFactoryBean() {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);

        return sessionFactoryBean;
    }

    public PlatformTransactionManager transactionManager() {
        return new HibernateTransactionManager();
    } 
}