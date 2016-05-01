package com.webservice.healthcheck;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @author kuldeep
 * 
 */
@Controller
public class NotificationController {

	@RequestMapping(value = "sendnotification")
	public String sendnotification() {
		return "redirect:service_details";
	}
}
