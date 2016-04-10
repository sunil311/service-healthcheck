package com.webservice.healthcheck.dao.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;

import com.webservice.healthcheck.dao.dbsource.DerbyDataSource;
import com.webservice.healthcheck.dao.dbsource.MySQLDataSource;
import com.webservice.healthcheck.dao.dbsource.OracleDataSource;

public class DataSourceFactory {
	public static DataSource getSQLDataSource(Properties databaseProperties) {
		BasicDataSource dataSource = null;

		if ("org.hibernate.dialect.MySQLDialect".equalsIgnoreCase(databaseProperties.getProperty("jdbc.dialact"))) {
			dataSource = new MySQLDataSource(databaseProperties
					.getProperty("database.username"),databaseProperties
					.getProperty("database.password"),"com.mysql.jdbc.Driver","jdbc:mysql://"
					+ databaseProperties.getProperty("database.host") + ":"
					+ databaseProperties.getProperty("database.port") + "/"
					+ databaseProperties.getProperty("database.name"));
		}
		
		if ("org.hibernate.dialect.OracleDialect".equalsIgnoreCase(databaseProperties.getProperty("jdbc.dialact"))) {
			dataSource = new OracleDataSource(databaseProperties
					.getProperty("database.username"),databaseProperties
					.getProperty("database.password"),"oracle.jdbc.driver.OracleDriver","jdbc:oracle:thin:@"
					+ databaseProperties.getProperty("database.host") + ":"
					+ databaseProperties.getProperty("database.port") + ":"
					+ databaseProperties.getProperty("database.name"));
		}
		
		if ("org.hibernate.dialect.DerbyDialect".equalsIgnoreCase(databaseProperties.getProperty("jdbc.dialact"))) {
			dataSource = new DerbyDataSource(databaseProperties
					.getProperty("database.username"),databaseProperties
					.getProperty("database.password"),"org.apache.derby.jdbc.ClientDriver","jdbc:derby://"
					+ databaseProperties.getProperty("database.host") + ":"
					+ databaseProperties.getProperty("database.port") + "/"
					+ databaseProperties.getProperty("database.name") + ";create=true");
		}
		
		return dataSource;
	}
}
