package com.webservice.healthcheck.reports;

import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.codehaus.jettison.json.JSONException;

import com.webservice.exception.UnexpectedProcessException;
import com.webservice.healthcheck.model.MyWebService;

public interface ReportPublisher {
	void publish(List<MyWebService> services)
			throws UnexpectedProcessException, IOException, JAXBException,
			JSONException;
}
