package com.webservice.healthcheck.dao.dbsource;

import org.apache.commons.dbcp.BasicDataSource;

public class MySQLDataSource extends BasicDataSource {

	public MySQLDataSource(String username, String password, String driver,
			String url) {
		setUsername(username);
		setPassword(password);
		setDriverClassName(driver);
		setUrl(url);
	}

	@Override
	public synchronized void setUsername(String username) {
		super.setUsername(username);
	}

	@Override
	public synchronized void setPassword(String password) {
		super.setPassword(password);
	}

	@Override
	public synchronized void setDriverClassName(String driverClassName) {
		super.setDriverClassName(driverClassName);
	}

	@Override
	public synchronized void setUrl(String url) {
		super.setUrl(url);
	}
}
