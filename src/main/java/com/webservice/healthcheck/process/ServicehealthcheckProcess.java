package com.webservice.healthcheck.process;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.connecture.integration.esb.model.ESBResponse;
import com.webservice.common.ValidateQuoteResult;
import com.webservice.common.ValidationMessage;
import com.webservice.exception.UnexpectedProcessException;
import com.webservice.healthcheck.dao.ServicehealthcheckDao;
import com.webservice.healthcheck.dao.ServicehealthcheckHistoryDao;
import com.webservice.healthcheck.model.MyWebService;
import com.webservice.healthcheck.model.WebServiceHistory;
import com.webservice.helper.ESBHelper;

@Service
public class ServicehealthcheckProcess {
	@Autowired
	ServicehealthcheckDao servicehealthcheckDao;

	@Autowired
	ServicehealthcheckHistoryDao servicehealthcheckHistoryDao;

	private static final Log LOGGER = LogFactory.getLog(ESBHelper.class);
	public static final String SERVICE_DOWN_STATUS_CODE = "800"; // either ESB
																	// or
																	// external
																	// service
																	// down

	/**
	 * @param serviceName
	 * @param serviceUrl
	 * @throws IOException
	 * @throws UnexpectedProcessException
	 * @throws JAXBException
	 * @throws JSONException 
	 */
	public void addService(String serviceName, String serviceUrl,
			String serviceUserId, String servicePassword) throws IOException,
			JAXBException, UnexpectedProcessException, JSONException {
		MyWebService wbService = new MyWebService();
		wbService.setServiceName(serviceName);
		wbService.setServiceUrl(serviceUrl);
		wbService.setUserId(serviceUserId);
		wbService.setPassword(servicePassword);
		String xml2String = ESBHelper.getXMLasString();
		JSONObject httpResponceJson = getStatus(xml2String, serviceUrl,
				serviceUserId, servicePassword);
		wbService.setStatus(httpResponceJson.getString("status"));
		wbService.setExecutionTime(httpResponceJson.getLong("executionTime"));
		servicehealthcheckDao.saveService(wbService);
	}

	/**
	 * @param xmlString
	 * @param esbUri
	 * @param esbUsername
	 * @param esbPassword
	 * @return
	 * @throws JSONException 
	 * @throws UnexpectedProcessException
	 * @throws JAXBException
	 * @throws IOException
	 */
	public static JSONObject getStatus(String xmlString, String esbUri,
			String esbUsername, String esbPassword) throws JSONException {
		String status = "Failure";
		ValidateQuoteResult validateQuoteResult = new ValidateQuoteResult();
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = ESBHelper.sendToHTTP(xmlString, esbUri, esbUsername,
					esbPassword);
			HttpResponse httpResponse = (HttpResponse) jsonObject
					.get("httpResponse");
			if (httpResponse == null) {
				status = "Failure";
			}
			ESBResponse esbResponse = ESBHelper
					.unmarshallEsbResponse(httpResponse);

			String esbResponseCode = esbResponse.getCode();

			if (ESBHelper.isSuccessfulEsbResponseCode(esbResponseCode)) {
				status = "Success";
			} else if (ESBHelper.isExternalEndpointDown(esbResponseCode)) {
				String esbResponseMessage = esbResponse.getMessage();
				LOGGER.error("ESB FAILED TO SEND VALIDATE QUOTE REQUEST ----> Esb Response Code: "
						+ esbResponseCode
						+ " Esb Response Message: "
						+ esbResponseMessage);
				validateQuoteResult.setStatusCode(SERVICE_DOWN_STATUS_CODE);

				List<ValidationMessage> validationMessages = new ArrayList<ValidationMessage>();
				ValidationMessage message = new ValidationMessage();
				message.setType(SERVICE_DOWN_STATUS_CODE);
				message.setErrorCode("");
				validationMessages.add(message);
				validateQuoteResult
						.setFatalErrorMessage(ESBHelper.EXTERNAL_ENDPOINT_ERROR_MESSAGE);
				validateQuoteResult.setStatusCode(SERVICE_DOWN_STATUS_CODE);
				validateQuoteResult.setValidationMessages(validationMessages);
			} else {
				String esbResponseMessage = esbResponse.getMessage();
				LOGGER.error("ESB FAILED TO SEND VALIDATE QUOTE REQUEST ----> Esb Response Code: "
						+ esbResponseCode
						+ " Esb Response Message: "
						+ esbResponseMessage);
				validateQuoteResult.setStatusCode(SERVICE_DOWN_STATUS_CODE);

				List<ValidationMessage> validationMessages = new ArrayList<ValidationMessage>();
				ValidationMessage message = new ValidationMessage();
				message.setType(SERVICE_DOWN_STATUS_CODE);
				message.setErrorCode("");
				validationMessages.add(message);
				validateQuoteResult
						.setFatalErrorMessage("The service is temporarily unavailable. Please try again later.");
				validateQuoteResult.setStatusCode(SERVICE_DOWN_STATUS_CODE);
				validateQuoteResult.setValidationMessages(validationMessages);
			}
		} catch (Exception e) {
			LOGGER.error(e);
			status = "Failure";
			jsonObject.put("executionTime", 0L);
		}
		jsonObject.put("status", status);
		return jsonObject;

	}

	/**
	 * @param serviceId
	 */
	public void removeService(int serviceId) {
		servicehealthcheckDao.removeService(serviceId);

	}

	/**
	 * @param myWebServices
	 * @return
	 */
	public List<MyWebService> stoppedServices(List<MyWebService> myWebServices) {
		List<MyWebService> webServices = new ArrayList<MyWebService>();
		if (myWebServices != null) {
			for (MyWebService myWebService : myWebServices) {
				if (!("Success".equalsIgnoreCase(myWebService.getStatus()))) {
					webServices.add(myWebService);
				}
			}
		}
		return webServices;
	}

	/**
	 * @param myWebServices
	 * @return
	 */
	public List<MyWebService> runningServices(List<MyWebService> myWebServices) {
		List<MyWebService> webServices = new ArrayList<MyWebService>();
		if (myWebServices != null) {
			for (MyWebService myWebService : myWebServices) {
				if ("Success".equalsIgnoreCase(myWebService.getStatus())) {
					webServices.add(myWebService);
				}
			}
		}
		return webServices;
	}

	/**
	 * @param webServiceHistories
	 */
	public Map<Integer, List<WebServiceHistory>> prepareWebServiceHistory() {

		List<WebServiceHistory> webServiceHistories = servicehealthcheckHistoryDao
				.getWebServiceHistory();
		Map<Integer, List<WebServiceHistory>> webServiceMap = new HashMap<Integer, List<WebServiceHistory>>();
		List<WebServiceHistory> list = null;
		for (WebServiceHistory serviceHistory : webServiceHistories) {
			list = webServiceMap.get(serviceHistory.getWebServiceId());
			if (list == null) {
				List<WebServiceHistory> lists = new ArrayList<WebServiceHistory>();
				lists.add(serviceHistory);
				webServiceMap.put(serviceHistory.getWebServiceId(), lists);
			} else {
				list.add(serviceHistory);
				webServiceMap.put(serviceHistory.getWebServiceId(),
						list.subList(0, 1));
			}
		}
		return webServiceMap;
	}

	public List<WebServiceHistory> getServicesStatusById(int serviceId) {
		servicehealthcheckHistoryDao.getWebServiceHistory(serviceId);
		return servicehealthcheckHistoryDao
				.getWebServiceHistoryByServiceId(serviceId);
	}

	/**
	 * @param stoppedServices
	 * @param runningServices
	 * @return
	 * @throws JSONException
	 */
	public JSONArray createJsonForGraph(List<MyWebService> stoppedServices,
			List<MyWebService> runningServices) throws JSONException {
		JSONArray array = new JSONArray();
		for (MyWebService myWebService : runningServices) {
			array.put(new JSONArray("['" + myWebService.getServiceUrl()
					+ "', 1, 'green']"));
		}
		for (MyWebService myWebService : stoppedServices) {
			array.put(new JSONArray("['" + myWebService.getServiceUrl()
					+ "', 1, 'red']"));
		}
		return array;
	}

}