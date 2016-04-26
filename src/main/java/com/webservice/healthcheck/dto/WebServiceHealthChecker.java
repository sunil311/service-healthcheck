/**
 * 
 */
package com.webservice.healthcheck.dto;

import java.util.List;

public class WebServiceHealthChecker {
	private List<String> serviceList;

	public List<String> getServiceList() {
		return serviceList;
	}

	public void setServiceList(List<String> serviceList) {
		this.serviceList = serviceList;
	}
}
