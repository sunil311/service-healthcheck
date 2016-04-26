package com.webservice.healthcheck.dao.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;

import com.webservice.healthcheck.model.MyWebService;
import com.webservice.healthcheck.model.WebServiceHistory;

@Configuration
public class HibernateConfig {
	static Properties databaseProperties;
	static {
		databaseProperties = new Properties();
		InputStream resourceInputStream = null;
		try {
			Resource resource = new ClassPathResource("database.properties");
			resourceInputStream = resource.getInputStream();
			databaseProperties.load(resourceInputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (resourceInputStream != null) {
			try {
				resourceInputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Bean(name = "dataSource")
	public DataSource getDataSource() {
		DataSource dataSource = DataSourceFactory
				.getSQLDataSource(databaseProperties);
		return dataSource;
	}

	@Autowired
	@Bean(name = "sessionFactory")
	public SessionFactory getSessionFactory(DataSource dataSource) {
		LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(
				dataSource);
		sessionBuilder.addAnnotatedClasses(WebServiceHistory.class,
				MyWebService.class);

		sessionBuilder.addProperties(getHibernateProperties());
		return sessionBuilder.buildSessionFactory();
	}

	private Properties getHibernateProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.show_sql",
				databaseProperties.getProperty("hibernate.show_sql"));
		properties.put("hibernate.dialect",
				databaseProperties.getProperty("jdbc.dialact"));
		properties.put("hibernate.hbm2ddl.auto",
				databaseProperties.getProperty("hibernate.hbm2ddl.auto"));
		return properties;
	}

	@Autowired
	@Bean(name = "transactionManager")
	public HibernateTransactionManager getTransactionManager(
			SessionFactory sessionFactory) {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager(
				sessionFactory);

		return transactionManager;
	}
}
