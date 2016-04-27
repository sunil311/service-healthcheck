package com.webservice.healthcheck.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webservice.healthcheck.model.MyWebService;
import com.webservice.healthcheck.process.ServicehealthcheckProcess;

@Service
public class ServicehealthcheckDao {
	@Autowired
	SessionFactory sessionFactory;

	/**
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<MyWebService> getRegisteredService() {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		Criteria criteria = session.createCriteria(MyWebService.class);
		List<MyWebService> myWebServices = (List<MyWebService>) criteria.list();
		transaction.commit();
		session.close();
		for (MyWebService myWebService : myWebServices) {
			myWebService.setActive(ServicehealthcheckProcess
					.getStatus(myWebService.getServiceUrl()));
		}
		return myWebServices;
	}

	/**
	 * 
	 * @param wbService
	 */
	public void saveService(MyWebService wbService) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		session.save(wbService);
		transaction.commit();
		session.close();
	}

	/**
	 * 
	 * @param serviceId
	 */
	public void removeService(int serviceId) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		MyWebService webService = (MyWebService) session.get(
				MyWebService.class, serviceId);
		if (webService != null) {
			session.delete(webService);
		}
		transaction.commit();
		session.close();

	}
}
