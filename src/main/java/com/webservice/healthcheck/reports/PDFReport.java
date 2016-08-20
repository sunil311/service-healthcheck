package com.webservice.healthcheck.reports;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.webservice.healthcheck.model.WebServiceHistory;
import com.webservice.healthcheck.provider.ResourceLocator;

@Service
public class PDFReport implements ReportPublisher {
	@Override
	public void publish(Map<Integer, List<WebServiceHistory>> servicesMap,
			ResourceLocator resourceLocator) {

	}
}
