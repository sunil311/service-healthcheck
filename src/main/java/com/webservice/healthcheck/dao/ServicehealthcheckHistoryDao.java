package com.webservice.healthcheck.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public List<WebServiceHistory> getWebServiceHistory(int id) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		Criteria criteria = session.createCriteria(WebServiceHistory.class);
		criteria.add(Restrictions.eq("id", id));
		List<WebServiceHistory> myWebServices = (List<WebServiceHistory>) criteria
				.list();
		transaction.commit();
		session.close();
		return myWebServices;
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<WebServiceHistory> getWebServiceHistoryByServiceId(int serviceId) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		Criteria criteria = session.createCriteria(WebServiceHistory.class);
		criteria.add(Restrictions.eq("webServiceId", serviceId));
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.HOUR, -24);
		Date date = calendar.getTime();
		criteria.add(Restrictions.ge("lastStatusTime", date));
		criteria.addOrder(Order.desc("lastStatusTime"));
		List<WebServiceHistory> myWebServices = (List<WebServiceHistory>) criteria
				.list();
		transaction.commit();
		session.close();
		return myWebServices;
	}

	/**
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<WebServiceHistory> getWebServiceHistory() {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		Criteria criteria = session.createCriteria(WebServiceHistory.class);
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.HOUR, -24);
		Date date = calendar.getTime();
		criteria.add(Restrictions.ge("lastStatusTime", date));
		criteria.addOrder(Order.desc("lastStatusTime"));
		List<WebServiceHistory> webServiceHistories = (List<WebServiceHistory>) criteria
				.list();
		transaction.commit();
		session.close();
		return webServiceHistories;
	}

	/**
	 * 
	 * @param webServiceHistory
	 */
	public void saveWebserviceStatusHistory(WebServiceHistory webServiceHistory) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		session.save(webServiceHistory);
		transaction.commit();
		session.close();
	}
}
