
package com.neu.flightTicket.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.neu.flightTicket.model.CustomerInfo;

/*
 * @author ajith
 */

//Controller for the home page
@Controller
public class HomePageController {

	@GetMapping("/")
	public ModelAndView startup() {
		ModelAndView modelView = new ModelAndView("login");
		CustomerInfo cusInfo = new CustomerInfo();
		modelView.addObject("userdetails", cusInfo);
		return modelView;
	}
}
