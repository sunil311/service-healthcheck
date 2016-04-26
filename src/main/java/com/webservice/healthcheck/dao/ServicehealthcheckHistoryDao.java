package com.webservice.healthcheck.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webservice.healthcheck.dto.WebServiceHealthChecker;
import com.webservice.healthcheck.model.WebServiceHistory;

/**
 * 
 * @author kuldeep
 * 
 */
@Service
public class ServicehealthcheckHistoryDao {
	@Autowired
	SessionFactory sessionFactory;

	/**
	 * 
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<WebServiceHealthChecker> getWebServiceHistory(int id) {

		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		Criteria criteria = session.createCriteria(WebServiceHistory.class);
		criteria.add(Restrictions.eq("id", id));
		List<WebServiceHealthChecker> myWebServices = (List<WebServiceHealthChecker>) criteria
				.list();
		transaction.commit();
		session.close();
		return myWebServices;

	}

	/**
	 * 
	 * @param webServiceHistory
	 */
	public void saveWebserviceStatusHistory(WebServiceHistory webServiceHistory) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		session.saveOrUpdate(webServiceHistory);
		transaction.commit();
		session.close();
	}
}
