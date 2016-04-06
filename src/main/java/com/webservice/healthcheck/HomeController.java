/**
 * 
 */
package com.webservice.healthcheck;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.webservice.healthcheck.model.WebServiceHealthChecker;

@Controller
public class HomeController {
	@Autowired
	private WebServiceHealthChecker webServiceHealthChecker;

	/**
	 * 
	 * @param username
	 * @param password
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = { "loggedMeIn" }, method = RequestMethod.GET)
	public String loggedMeIn(String username, String password, ModelMap modelMap) {
		if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
			modelMap.put("message", "Invalid Credentials!");
			return "login_page";
		}
		modelMap.put("username", username);
		modelMap.put("serviceList", webServiceHealthChecker.getServiceList());
		return "user_home";
	}
}
