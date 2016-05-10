package com.webservice.healthcheck.dao;

import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webservice.exception.UnexpectedProcessException;
import com.webservice.healthcheck.model.MyWebService;
import com.webservice.healthcheck.process.ServicehealthcheckProcess;
import com.webservice.helper.ESBHelper;

@Service
public class ServicehealthcheckDao
{
  @Autowired
  SessionFactory sessionFactory;

  /**
   * @return
   * @throws IOException
   * @throws UnexpectedProcessException
   * @throws JAXBException
   */
  @SuppressWarnings("unchecked")
  public List<MyWebService> getRegisteredService()
    throws IOException, JAXBException, UnexpectedProcessException
  {
    Session session = sessionFactory.openSession();
    Transaction transaction = session.beginTransaction();
    Criteria criteria = session.createCriteria(MyWebService.class);
    List<MyWebService> myWebServices = (List<MyWebService>) criteria.list();
    transaction.commit();
    session.close();

    String xml2String = ESBHelper.getXMLasString();

    for (MyWebService myWebService : myWebServices)
    {
      /*
       * myWebService.setActive(ServicehealthcheckProcess
       * .getStatus(myWebService
       * .getServiceUrl(),myWebService.getUserId(),myWebService.getPassword()));
       */

      myWebService.setStatus(ServicehealthcheckProcess.getStatus(xml2String,
        myWebService.getServiceUrl(), myWebService.getUserId(), myWebService.getPassword()));

    }
    return myWebServices;
  }

  /**
   * @param wbService
   */
  public void saveService(MyWebService wbService)
  {
    Session session = sessionFactory.openSession();
    Transaction transaction = session.beginTransaction();
    session.save(wbService);
    transaction.commit();
    session.close();
  }

  /**
   * @param serviceId
   */
  public void removeService(int serviceId)
  {
    Session session = sessionFactory.openSession();
    Transaction transaction = session.beginTransaction();
    MyWebService webService = (MyWebService) session.get(MyWebService.class, serviceId);
    if (webService != null)
    {
      session.delete(webService);
    }
    transaction.commit();
    session.close();

  }
}
