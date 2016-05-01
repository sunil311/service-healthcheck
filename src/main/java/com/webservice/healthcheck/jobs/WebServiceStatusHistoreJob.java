package com.webservice.healthcheck.jobs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.webservice.healthcheck.dao.ServicehealthcheckDao;
import com.webservice.healthcheck.dao.ServicehealthcheckHistoryDao;
import com.webservice.healthcheck.model.MyWebService;
import com.webservice.healthcheck.model.WebServiceHistory;

@EnableScheduling
@Configuration
public class WebServiceStatusHistoreJob {

	@Autowired
	ServicehealthcheckHistoryDao servicehealthcheckHistoryDao;
	@Autowired
	ServicehealthcheckDao servicehealthcheckDao;

	/**
	 * Cron job to be executed every 4 hour
	 */
	// @Scheduled(cron = "0 0 0/4 * * ?")
	@Scheduled(fixedDelay = 600000)
	// every 10 min
	public void dumpWebserviceHealthcheckStats() {
		List<MyWebService> webServices = servicehealthcheckDao
				.getRegisteredService();
		saveWebserviceHistory(webServices);
		System.out.println("Job Disabled for now");
	}

	/**
	 * 
	 * @param webServices
	 */
	private void saveWebserviceHistory(List<MyWebService> webServices) {
		for (MyWebService webService : webServices) {
			WebServiceHistory webServiceHistory = new WebServiceHistory();
			webServiceHistory.setActive(webService.isActive());
			webServiceHistory.setLastStatusTime(new Date());
			webServiceHistory.setServiceName(webService.getServiceName());
			webServiceHistory.setServiceUrl(webService.getServiceUrl());
			webServiceHistory.setWebServiceId(webService.getId());
			servicehealthcheckHistoryDao
					.saveWebserviceStatusHistory(webServiceHistory);
		}

	}
}
