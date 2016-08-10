package com.webservice.helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.connecture.integration.esb.model.ESBResponse;
import com.webservice.exception.UnexpectedProcessException;
import com.webservice.healthcheck.model.MyWebService;
import com.webservice.healthcheck.process.IOUtils;
import com.webservice.healthcheck.process.JAXBContextHolder;

public class ESBHelper {
	protected static final String AUTHORIZATION_HEADER = "Authorization";
	protected static final String APPLICATION_XML_VALUE = "application/xml";
	protected static final String CONTENT_TYPE_HEADER = "Content-Type";
	protected static final String ACCEPT_HEADER = "Accept";
	protected static final String EXTERNAL_SERVICE_ERROR_RESPONSE_CODE = "520";
	protected static final String SUCCESSFUL_ESB_RESPONSE_CODE = "200";
	public static final String EXTERNAL_ENDPOINT_ERROR_MESSAGE = "The UHG Service is Down at the moment. Please try again later.";

	private static final Log LOGGER = LogFactory.getLog(ESBHelper.class);

	public static boolean isSuccessfulEsbResponseCode(String esbResponseCode) {
		return SUCCESSFUL_ESB_RESPONSE_CODE.equals(esbResponseCode);
	}

	public static ESBResponse unmarshallEsbResponse(HttpResponse response)
			throws JAXBException, IOException {
		JAXBContext context = JAXBContextHolder.getContext(ESBResponse.class);
		ESBResponse esbResponse = (ESBResponse) IOUtils.secureUnmarshallXML(
				context, response.getEntity().getContent());
		return esbResponse;
	}

	public static boolean isExternalEndpointDown(String esbResponseCode) {
		return EXTERNAL_SERVICE_ERROR_RESPONSE_CODE.equals(esbResponseCode);
	}

	public static JSONObject sendToHTTP(String xmlString, String esbUri,
			String esbUsername, String esbPassword)
			throws SocketTimeoutException, JSONException {
		// uhgEsbRequestTransactionHandler.preESBCallProcess();
		HttpResponse httpResponse = null;
		long startTime = System.currentTimeMillis();
		long endTime = System.currentTimeMillis();
		try {
			HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, 40000);
			HttpClient httpclient = new DefaultHttpClient(httpParams);
			HttpPost httpPost = getHttpRequest(xmlString, esbUri, esbUsername,
					esbPassword);
			startTime = System.currentTimeMillis();
			httpResponse = httpclient.execute(httpPost);
		} catch (ClientProtocolException e) {
			throw new UnexpectedProcessException(e);
		} catch (IOException e) {
			LOGGER.error(e);
			throw new SocketTimeoutException(
					"Socket timeout while calling valiadte quote");
		} catch (Exception e) {
			LOGGER.error(e);
			throw new SocketTimeoutException(
					"Socket timeout while calling valiadte quote");
		} finally {
			endTime = System.currentTimeMillis();
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("httpResponse", httpResponse);
		jsonObject.put("executionTime", (endTime - startTime) / 1000);
		return jsonObject;
	}

	public static HttpPost getHttpRequest(String xmlString, String httpURI,
			String esbUsername, String esbPassword)
			throws UnsupportedEncodingException {
		HttpEntity xmlEntity = new StringEntity(xmlString);
		HttpPost httpPost = new HttpPost(httpURI);
		httpPost.setEntity(xmlEntity);
		httpPost.setHeader(ACCEPT_HEADER, APPLICATION_XML_VALUE);
		httpPost.setHeader(CONTENT_TYPE_HEADER, APPLICATION_XML_VALUE);
		setAuthorizationHeader(esbUsername, esbPassword, httpPost);
		return httpPost;
	}

	/**
	 * Sets the http authorization request header if the esbUsername and
	 * esbPassword are both not null
	 * 
	 * @param esbUsername
	 * @param esbPassword
	 * @param httpPost
	 */
	private static void setAuthorizationHeader(String esbUsername,
			String esbPassword, HttpPost httpPost) {

		if (esbUsername != null && esbPassword != null) {
			String userpassword = esbUsername + ":" + esbPassword;
			String encodedAuthorization = "Basic "
					+ new String(Base64.encodeBase64(userpassword.getBytes()));
			httpPost.setHeader(AUTHORIZATION_HEADER, encodedAuthorization);
		}

	}

	public static String getXMLasString(String fileName)
			throws FileNotFoundException, IOException {

		File xmlFile = null;
		try {
			// xmlFile = new File(new URI(fileName));
			xmlFile = new File(
					"/home/kuldeep/data/java/projects/sunil/service-healthcheck/src/filestore/requestXML/VALIDATE_QUOTE_REQUEST.xml");
		} catch (Exception e) {
			e.printStackTrace();
		}
		Reader fileReader = new FileReader(xmlFile);
		BufferedReader bufReader = new BufferedReader(fileReader);
		StringBuilder sb = new StringBuilder();
		String line = bufReader.readLine();
		while (line != null) {
			sb.append(line).append("\n");
			line = bufReader.readLine();
		}
		String xml2String = sb.toString();
		return xml2String;
	}

	public static String createReport(List<MyWebService> services) {
		return services.toString();
	}

}
