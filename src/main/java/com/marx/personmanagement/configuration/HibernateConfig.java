package com.marx.personmanagement.configuration;

import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class HibernateConfig {
    @Autowired
    private Environment environment;
    @Autowired
    private DataSource dataSource;    
    @Autowired
    @Bean(name = "entityManagerFactory")
    public LocalSessionFactoryBean getSessionFactory() {            
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setPackagesToScan(new String[]{"com.marx.personmanagement.model.domain"});
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }
    private Properties hibernateProperties() {                  
        Properties properties = new Properties();
        properties.put("hibernate.dialect", environment.getRequiredProperty("spring.jpa.properties.hibernate.dialect"));
        properties.put("hibernate.show_sql", environment.getRequiredProperty("spring.jpa.show-sql"));
        properties.put("hibernate.hbm2ddl.auto", "update");
        return properties;
    }
    @Autowired
    @Bean(name = "transactionManager")                     
    public JpaTransactionManager getTransactionManager(
            SessionFactory sessionFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager(sessionFactory);
        return transactionManager;
    }
    
	@Bean
	@Qualifier(value = "entityManager")
	public EntityManager entityManager(EntityManagerFactory entityManagerFactory) {
	    return entityManagerFactory.createEntityManager();
	}
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}