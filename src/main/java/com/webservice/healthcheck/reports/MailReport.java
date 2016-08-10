package com.webservice.healthcheck.reports;

import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.codehaus.jettison.json.JSONException;
import org.springframework.stereotype.Service;

import com.webservice.exception.UnexpectedProcessException;
import com.webservice.healthcheck.model.MyWebService;

@Service
public class MailReport implements ReportPublisher {

	@Override
	public void publish(List<MyWebService> services)
			throws UnexpectedProcessException, IOException, JAXBException,
			JSONException {
		// TODO Auto-generated method stub

	}

}
