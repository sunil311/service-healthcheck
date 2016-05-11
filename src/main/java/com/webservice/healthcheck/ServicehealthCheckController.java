package com.webservice.healthcheck;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;

import org.codehaus.jettison.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.webservice.exception.UnexpectedProcessException;
import com.webservice.healthcheck.dao.ServicehealthcheckDao;
import com.webservice.healthcheck.model.MyWebService;
import com.webservice.healthcheck.model.WebServiceHistory;
import com.webservice.healthcheck.process.ServicehealthcheckProcess;

@Controller
public class ServicehealthCheckController
{

  @Autowired
  ServicehealthcheckDao servicehealthcheckDao;
  @Autowired
  ServicehealthcheckProcess servicehealthcheckProcess;

  @RequestMapping(value = "dashboard")
  public String showServiceDashboard(ModelMap modelMap)
    throws JSONException, IOException, JAXBException, UnexpectedProcessException
  {
    List<MyWebService> myWebServices = servicehealthcheckDao.getRegisteredService();
    List<MyWebService> stoppedServices = servicehealthcheckProcess.stoppedServices(myWebServices);
    List<MyWebService> runningServices = servicehealthcheckProcess.runningServices(myWebServices);
    modelMap.put("stoppedServicesCount", stoppedServices.size());
    modelMap.put("runningServiceCount", runningServices.size());
    modelMap.put("servicesJsonList",
      servicehealthcheckProcess.createJsonForGraph(stoppedServices, runningServices));
    return "dashboard";
  }

  @RequestMapping(value = "service_config")
  public String getAllServices(ModelMap modelMap)
    throws IOException, JAXBException, UnexpectedProcessException, JSONException
  {
    modelMap.put("serviceList", servicehealthcheckDao.getRegisteredService());
    return "service_config";
  }

  @RequestMapping(value = "removeService")
  public String removeService(ModelMap modelMap, int id, HttpServletResponse response)
  {
    servicehealthcheckProcess.removeService(id);
    return "redirect:service_config";
  }

  @RequestMapping(value = "addService")
  public String addService(
    ModelMap modelMap,
    String serviceName,
    String serviceUrl,
    String servicePassword,
    String serviceUserId) throws IOException, JAXBException, UnexpectedProcessException
  {
    servicehealthcheckProcess.addService(serviceName, serviceUrl, serviceUserId, servicePassword);
    return "redirect:service_config";
  }

  /**
   * Get data from service history, convert to proper format and render on UI
   * 
   * @param modelMap
   * @return
   */
  @RequestMapping(value = "service_details")
  public String getServiceStatus(ModelMap modelMap)
  {
    modelMap.put("serviceMap", servicehealthcheckProcess.prepareWebServiceHistory());
    return "service_details";
  }

  /**
   * @param modelMap
   * @return
   */
  @RequestMapping(value = "getServiceStatusDetails")
  public String getServiceStatusDetails(ModelMap modelMap, int serviceId)
  {
    List<WebServiceHistory> serviceHistories = servicehealthcheckProcess
      .getServicesStatusById(serviceId);
    if (serviceHistories != null)
    {
      modelMap.put("serviceName", serviceHistories.get(0).getServiceName());
    }
    modelMap.put("serviceList", serviceHistories);
    return "serviceStatusDetails";
  }
}
