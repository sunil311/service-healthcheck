package com.webservice.healthcheck;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.webservice.healthcheck.dao.ServicehealthcheckDao;
import com.webservice.healthcheck.model.MyWebService;
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
  {
    List<MyWebService> myWebServices = servicehealthcheckDao.getRegisteredService();
    modelMap.put("runningService", servicehealthcheckProcess.stoppedServices(myWebServices));
    modelMap.put("stoppedServices", servicehealthcheckProcess.runningServices(myWebServices));
    return "dashboard";
  }

  @RequestMapping(value = "service_status")
  public String getServiceStatus(ModelMap modelMap)
  {
    modelMap.put("serviceList", servicehealthcheckDao.getRegisteredService());
    return "health_check_satus";
  }

  @RequestMapping(value = "removeService")
  public String removeService(ModelMap modelMap, int id, HttpServletResponse response)
  {
    servicehealthcheckProcess.removeService(id);
    return "redirect:service_status";
  }

  @RequestMapping(value = "addService")
  public String addService(
    ModelMap modelMap,
    String serviceName,
    String serviceUrl,
    String serviceUserId,
    String servicePassword) throws IOException
  {
    servicehealthcheckProcess.addService(serviceName, serviceUrl, serviceUserId, servicePassword);
    return "redirect:service_status";
  }

}
