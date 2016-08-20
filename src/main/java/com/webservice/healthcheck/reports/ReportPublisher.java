package com.webservice.healthcheck.reports;

import java.util.List;
import java.util.Map;

import com.webservice.healthcheck.model.WebServiceHistory;
import com.webservice.healthcheck.provider.ResourceLocator;

public interface ReportPublisher {
	void publish(Map<Integer, List<WebServiceHistory>> servicesMap,
			ResourceLocator resourceLocator);
}
