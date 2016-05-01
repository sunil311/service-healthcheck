package com.webservice.healthcheck;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.webservice.healthcheck.dao.ServicehealthcheckDao;
import com.webservice.healthcheck.model.MyWebService;
import com.webservice.healthcheck.model.WebServiceHistory;
import com.webservice.healthcheck.process.ServicehealthcheckProcess;

@Controller
public class ServicehealthCheckController {

	@Autowired
	ServicehealthcheckDao servicehealthcheckDao;
	@Autowired
	ServicehealthcheckProcess servicehealthcheckProcess;

	@RequestMapping(value = "dashboard")
	public String showServiceDashboard(ModelMap modelMap) {
		List<MyWebService> myWebServices = servicehealthcheckDao
				.getRegisteredService();
		modelMap.put("runningService",
				servicehealthcheckProcess.stoppedServices(myWebServices));
		modelMap.put("stoppedServices",
				servicehealthcheckProcess.runningServices(myWebServices));
		return "dashboard";
	}

	@RequestMapping(value = "service_config")
	public String getAllServices(ModelMap modelMap) {
		modelMap.put("serviceList",
				servicehealthcheckDao.getRegisteredService());
		return "service_config";
	}

	@RequestMapping(value = "removeService")
	public String removeService(ModelMap modelMap, int id,
			HttpServletResponse response) {
		servicehealthcheckProcess.removeService(id);
		return "redirect:service_config";
	}

	@RequestMapping(value = "addService")
	public String addService(ModelMap modelMap, String serviceName,
			String serviceUrl, String servicePassword, String serviceUserId) {
		servicehealthcheckProcess.addService(serviceName, serviceUrl);
		return "redirect:service_config";
	}

	/**
	 * Get data from service history, convert to proper format and render on UI
	 * 
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "service_details")
	public String getServiceStatus(ModelMap modelMap) {
		modelMap.put("serviceMap",
				servicehealthcheckProcess.prepareWebServiceHistory());
		return "service_details";
	}

	/**
	 * 
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "getServiceStatusDetails")
	public String getServiceStatusDetails(ModelMap modelMap, int serviceId) {
		List<WebServiceHistory> serviceHistories = servicehealthcheckProcess
				.getServicesStatusById(serviceId);
		if (serviceHistories != null) {
			modelMap.put("serviceName", serviceHistories.get(0)
					.getServiceName());
		}
		modelMap.put("serviceList", serviceHistories);
		return "serviceStatusDetails";
	}
}
