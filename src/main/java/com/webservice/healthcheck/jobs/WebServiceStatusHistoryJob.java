package com.webservice.healthcheck.jobs;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.codehaus.jettison.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.webservice.exception.UnexpectedProcessException;
import com.webservice.healthcheck.dao.ServicehealthcheckDao;
import com.webservice.healthcheck.dao.ServicehealthcheckHistoryDao;
import com.webservice.healthcheck.model.MyWebService;
import com.webservice.healthcheck.model.WebServiceHistory;

@EnableScheduling
@Configuration
public class WebServiceStatusHistoryJob
{

  @Autowired
  ServicehealthcheckHistoryDao servicehealthcheckHistoryDao;
  @Autowired
  ServicehealthcheckDao servicehealthcheckDao;

  /**
   * Cron job to be executed every 4 hour
   * 
   * @throws IOException
   * @throws UnexpectedProcessException
   * @throws JAXBException
 * @throws JSONException 
   */
  // @Scheduled(cron = "0 0 0/4 * * ?")
  @Scheduled(fixedDelay = 600000)
  // every 10 min
  public void dumpWebserviceHealthcheckStats()
    throws IOException, JAXBException, UnexpectedProcessException, JSONException
  {
    List<MyWebService> webServices = servicehealthcheckDao.getRegisteredService();
    //saveWebserviceHistory(webServices);
  }

  /**
   * @param webServices
   */
  private void saveWebserviceHistory(List<MyWebService> webServices)
  {
    for (MyWebService webService : webServices)
    {
      WebServiceHistory webServiceHistory = new WebServiceHistory();
      webServiceHistory.setStatus(webService.getStatus());
      webServiceHistory.setLastStatusTime(new Date());
      webServiceHistory.setServiceName(webService.getServiceName());
      webServiceHistory.setServiceUrl(webService.getServiceUrl());
      webServiceHistory.setWebServiceId(webService.getId());
      webServiceHistory.setExecutionTime(webService.getExecutionTime());
      servicehealthcheckHistoryDao.saveWebserviceStatusHistory(webServiceHistory);
    }

  }
}
