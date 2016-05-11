package com.webservice.healthcheck;

import java.io.IOException;

import javax.xml.bind.JAXBException;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jettison.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.webservice.exception.UnexpectedProcessException;
import com.webservice.healthcheck.dao.ServicehealthcheckDao;

@Controller
public class HomeController {

  @Autowired
  ServicehealthcheckDao servicehealthcheckDao;
	/**
	 * 
	 * @param username
	 * @param password
	 * @param modelMap
	 * @return
	 * @throws JSONException 
	 * @throws JAXBException 
	 * @throws IOException 
	 * @throws UnexpectedProcessException 
	 */
	@RequestMapping(value = { "loggedMeIn" }, method = RequestMethod.GET)
	public String loggedMeIn(String username, String password, ModelMap modelMap) throws Exception {
		if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
			modelMap.put("message", "Invalid Credentials!");
			return "login_page";
		}
		modelMap.put("serviceList", servicehealthcheckDao.getRegisteredService());
		return "service_config";
	}
}
