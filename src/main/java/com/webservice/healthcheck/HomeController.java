package com.webservice.healthcheck;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

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
		return "home";
	}
}
