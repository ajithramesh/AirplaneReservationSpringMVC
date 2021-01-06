package com.neu.flightTicket.controller;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.neu.flightTicket.dao.CustomerInfoRepo;
import com.neu.flightTicket.model.CustomerInfo;
import com.neu.flightTicket.service.UserCredentialService;
import com.neu.flightTicket.view.findPlaneDTO;

/*
 * @author ajith
 */

@Controller
@RequestMapping("/login")
public class SignInController {
	private static final Logger logger = Logger.getLogger(SignInController.class);
	
	@Autowired
	private UserCredentialService userCredService;
	
	@Autowired
	private CustomerInfoRepo cusInfoRepo;

	@RequestMapping("/page")
	public ModelAndView signIn(Map<String, Object> model) {

		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(true);
		logger.debug("Application Login");
		// If the customer info is not available, reload the login page
		if (session.getAttribute("custinfo") == null) {
			logger.debug("Loading the page again");
			ModelAndView modelView = new ModelAndView("login");
			CustomerInfo cusinf = new CustomerInfo();
			model.put("userdetails", cusinf);
			return modelView;
		} else {
			logger.debug("session attribute	 available");
			ModelAndView modelView = null;
			CustomerInfo cusinf = (CustomerInfo) session.getAttribute("custinfo");
			logger.debug("Customer Info: " + cusinf);
			logger.debug("Customer Info: " + cusinf.getFname());

			session.setAttribute("custinfo", cusinf);

			logger.debug("User Role: " + cusinf.getUser_role());
			if ("admin".equalsIgnoreCase(cusinf.getUser_role())) {
				logger.debug("**Admin Signin**");
				modelView = new ModelAndView("admin");
				modelView.addObject("userdetails", cusinf);
				return modelView;
			} else if ("user".equalsIgnoreCase(cusinf.getUser_role())) {
				logger.debug("Customer SignIn");
				modelView = new ModelAndView("searchFlights");
				modelView.addObject("searchFlight", new findPlaneDTO());
				modelView.addObject("userdetails", cusinf);
				return modelView;
			}
			return new ModelAndView("failed-login");
		}
	}

//For Signup
	@RequestMapping("/signup")
	public String registerPage(Map<String, Object> model) {
		CustomerInfo cusInfo = new CustomerInfo();
		model.put("userdetails", cusInfo);
		return "signup";
	}

	@RequestMapping(value = "/sign-up", method = RequestMethod.POST)
	public ModelAndView register(@ModelAttribute("userdetails") CustomerInfo cusinf) {
		cusinf = userCredService.setCustomerInfo(cusinf);
		return new ModelAndView("login");
	}

	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logoutPage(HttpServletRequest request, HttpServletResponse response) {

		Authentication userAuth = SecurityContextHolder.getContext().getAuthentication();
		logger.debug("authenticate " + userAuth);
		if (userAuth != null) {
			new SecurityContextLogoutHandler().logout(request, response, userAuth);
		}
		logger.debug("logged out, redirecting..");
		return "redirect:/login/page";
	}

	// CheckLogin for AJAX
	@RequestMapping(value = "/checkLogin", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String validateSignIn(@RequestParam String username, @RequestParam String pwd) {
		CustomerInfo cusinf = userCredService.userValidation(username, pwd);
		if (cusinf != null) {
			ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
			HttpSession session = attr.getRequest().getSession(true);
			session.setAttribute("custinfo", cusinf);
			return "{\"success\":\"true\"}";
		}
		return "{\"success\":\"false\"}";
	}

	@RequestMapping(value = "/checkUserExists", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String validateUserHistory(@RequestParam String uid) {
		CustomerInfo cusinf = cusInfoRepo.findByUserName(uid);
		if (cusinf != null) {
			return "{\"success\":\"true\"}";
		}
		return "{\"success\":\"false\"}";
	}

	@RequestMapping(value = "/redirectLogin", method = RequestMethod.GET)
	public ModelAndView signInRedirect(@ModelAttribute("userdetails") CustomerInfo cusinf) {
		logger.debug("signInRedirect");
		logger.debug("Authority " + SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(true);

		if (session.getAttribute("custinfo") != null) {
			CustomerInfo cusIn = (CustomerInfo) session.getAttribute("custinfo");
			logger.debug("custinfo: " + cusIn.getId());

			ModelAndView modelView = null;
			if ("admin".equalsIgnoreCase(cusIn.getUser_role())) {
				modelView = new ModelAndView("admin");
				modelView.addObject("userdetails", cusIn);
			} else if ("user".equalsIgnoreCase(cusIn.getUser_role())) {

				session.setAttribute("custinfo", cusIn);
				modelView = new ModelAndView("searchFlights");
				modelView.addObject("searchFlight", new findPlaneDTO());
				modelView.addObject("userdetails", cusIn);

			}

			session.setAttribute("custinfo", cusIn);
			return modelView;
		} else {
			return new ModelAndView("failed-login");
		}
	}

}