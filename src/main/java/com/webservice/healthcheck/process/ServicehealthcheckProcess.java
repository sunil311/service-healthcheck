/**
 * 
 */
package com.webservice.healthcheck.process;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webservice.healthcheck.dao.ServicehealthcheckDao;
import com.webservice.healthcheck.dao.ServicehealthcheckHistoryDao;
import com.webservice.healthcheck.model.MyWebService;
import com.webservice.healthcheck.model.WebServiceHistory;

;

@Service
public class ServicehealthcheckProcess {

	@Autowired
	ServicehealthcheckDao servicehealthcheckDao;

	@Autowired
	ServicehealthcheckHistoryDao servicehealthcheckHistoryDao;

	/**
	 * 
	 * @param serviceName
	 * @param serviceUrl
	 */
	public void addService(String serviceName, String serviceUrl) {
		MyWebService wbService = new MyWebService();
		wbService.setServiceName(serviceName);
		wbService.setServiceUrl(serviceUrl);
		wbService.setActive(getStatus(serviceUrl));
		servicehealthcheckDao.saveService(wbService);
	}

	/**
	 * 
	 * @param url
	 * @return
	 */
	public static boolean getStatus(String url) {

		boolean result = true;
		try {
			URL siteURL = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) siteURL
					.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();

			int code = connection.getResponseCode();
			if (code != 200 && code != 401) {
				result = false;
			}
		} catch (Exception e) {
			result = false;
		}
		return result;
	}

	/**
	 * 
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public static boolean getStatus(String urlStr, String password)
			throws IOException {

		boolean result = true;
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(urlStr);
		// URL url = null;
		// URLConnection urlConnection = null;
		try {
			// url = new URL(urlStr);

			String userpassword = "UhgQA" + ":" + "UHGQA";
			String encodedAuthorization = "Basic "
					+ new String(Base64.encodeBase64(userpassword.getBytes()));
			httpPost.setHeader("Authorization", encodedAuthorization);
			// httpclient.execute(httpPost);

			// URLConnection urlConnection = url.openConnection();

			HttpResponse response = httpclient.execute(httpPost);
			System.out.println(response.getStatusLine().getStatusCode());
			if (response.getStatusLine().getStatusCode() != 404) {
				result = true;
				System.out.println("GOOD URL");
			} else {
				result = false;
				System.out.println("BAD URL");
			}
		} catch (MalformedURLException ex) {
			result = false;
			System.out.println("bad URL");
		} catch (IOException ex) {
			result = false;
			System.out
					.println("Failed opening connection. Perhaps WS is not up?");
		}
		return result;
	}

	/**
	 * 
	 * @param serviceId
	 */
	public void removeService(int serviceId) {
		servicehealthcheckDao.removeService(serviceId);

	}

	/**
	 * 
	 * @param myWebServices
	 * @return
	 */
	public int stoppedServices(List<MyWebService> myWebServices) {
		int count = 0;
		if (myWebServices != null) {
			for (MyWebService myWebService : myWebServices) {
				if (!myWebService.isActive()) {
					count++;
				}
			}
		}
		return count;
	}

	/**
	 * 
	 * @param myWebServices
	 * @return
	 */
	public int runningServices(List<MyWebService> myWebServices) {
		int count = 0;
		if (myWebServices != null) {
			for (MyWebService myWebService : myWebServices) {
				if (myWebService.isActive()) {
					count++;
				}
			}
		}
		return count;
	}

	/**
	 * 
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
}