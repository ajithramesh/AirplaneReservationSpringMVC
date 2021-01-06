package com.neu.flightTicket.controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.mail.EmailException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.neu.flightTicket.dao.ReserveTicketRepo;
import com.neu.flightTicket.dao.PlaneRepo;
import com.neu.flightTicket.dao.PlaneDetailsRepo;
import com.neu.flightTicket.model.ReserveTicket;

import com.neu.flightTicket.model.PlaneDetails;
import com.neu.flightTicket.model.CustomerInfo;
import com.neu.flightTicket.service.PlaneService;
import com.neu.flightTicket.util.DateHelper;
import com.neu.flightTicket.view.findPlaneDTO;
import com.neu.flightTicket.controller.SendEmailController;

/*
 * @author ajith
 */

@Controller
public class PlaneDetailsController {
	private static final Logger logger = Logger.getLogger(SignInController.class);

	@Autowired
	private SendEmailController emailController;
	
	@Autowired
	private PlaneDetailsRepo planeDetailsRepo;
	
	@Autowired
	private PlaneRepo planeRepository;

	@Autowired
	private PlaneService planeService;
	
	@Autowired
	private ReserveTicketRepo reserveTicket;



	// this part of the code is for admin purpose to view the passenger list
	@PreAuthorize("isAuthenticated() and hasRole('admin')")
	@RequestMapping(value = "/viewPassengers", method = RequestMethod.GET)
	public ModelAndView fetchCustomerDetails() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(true);

		if (session.getAttribute("custinfo") != null) {
			// Fetch customer info using session attribute
			CustomerInfo ud = (CustomerInfo) session.getAttribute("custinfo");
			logger.debug("custinfo: " + ud.getId());
			session.setAttribute("custinfo", ud);

			ModelAndView modelView = new ModelAndView("viewPassengers");

			//Take the customer list
			List<ReserveTicket> customerList = (List<ReserveTicket>) reserveTicket.findAll();
			if (ud.getUser_role().trim().equalsIgnoreCase("admin")) {
				customerList = (List<ReserveTicket>) reserveTicket.findAll();
			} else {
				customerList = (List<ReserveTicket>) reserveTicket.getReservations(ud.getId());
			}
			//If the customer list is not null, then add the object.
			if (customerList != null) {
				modelView.addObject("flightList", customerList);
				return modelView;
			} else {
				return null;
			}
		} else {
			return new ModelAndView("failed-login");
		}
	}

	// Adding planes to the list by the admin by GET method
	@PreAuthorize("isAuthenticated() and hasAuthority('admin')")
	@RequestMapping(value = "/addFlight", method = RequestMethod.GET)
	public ModelAndView navigatePlane(Map<String, Object> model) {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(true);

		if (session.getAttribute("custinfo") != null) {
			//Getting customer info from the session attribute
			CustomerInfo cusinf = (CustomerInfo) session.getAttribute("custinfo");
			logger.debug("Customer Info: " + cusinf.getId());
			session.setAttribute("custinfo", cusinf);

			ModelAndView modelView = new ModelAndView("addflight");
			PlaneDetails plane = new PlaneDetails();
			//add plane details to the flight object
			modelView.addObject("flight", plane);
			return modelView;
		} else {
			return new ModelAndView("failed-login");
		}
	}

	// Adding planes to the list by the admin by POST method
	@PreAuthorize("isAuthenticated() and hasRole('admin')")
	@RequestMapping(value = "/addflight", method = RequestMethod.POST)
	public ModelAndView newPlane(@Valid @ModelAttribute("flight") PlaneDetails plane, BindingResult res) {
		logger.debug("Adding begin...");
		ModelAndView modelView = new ModelAndView("message");
		if (res.hasErrors()) {
			return new ModelAndView("addflight");
		}
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(true);

		if (session.getAttribute("custinfo") != null) {
			//Getting customer info from the session attribute
			CustomerInfo cusinf = (CustomerInfo) session.getAttribute("custinfo");
			logger.debug("Customer Info: " + cusinf.getId());
			session.setAttribute("custinfo", cusinf);
			//Get plane arrival and departure time
			plane.setDept_time(DateHelper.convertDate(plane.getDept_time()));
			plane.setArrival_time(DateHelper.convertDate(plane.getArrival_time()));

			logger.debug("Arrival and Departure Time: " + plane.getArrival_time() + ", " + plane.getDept_time());

			if (plane != null) {
				planeService.saveflight(plane);
				modelView = new ModelAndView("admin");
				modelView.addObject("message", "Process Completed");
				return modelView;
			}
		} else {
			return new ModelAndView("failed-login");
		}
		modelView.addObject("message", "Error Occured");
		return modelView;
	}
	
	// The below method is for searching the flights
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value = "/searchFlights", method = RequestMethod.POST)
	public ModelAndView findPlanes(@Valid @ModelAttribute("searchFlight") findPlaneDTO findPlane, BindingResult bindRes) {
		ModelAndView modelView = new ModelAndView("searchResults");
		if (bindRes.hasErrors()) {
			return new ModelAndView("searchFlights");
		}
		
		modelView.addObject("searchFlight", findPlane);
		List<PlaneDetails> planeList = planeDetailsRepo.findPlaneBySrcLoc(findPlane.getSrcLocation(), findPlane.getDestLocation());
		//Get the arrival and departure time
		planeList.forEach(plane -> {
			plane.setArrival_time(plane.getArrival_time().substring(11));
			plane.setDept_time(plane.getDept_time().substring(11));
		});
		
		logger.debug("Searching the planes");

		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(true);

		if (session.getAttribute("custinfo") != null) {
			//Getting customer info from the session
			CustomerInfo custInfo = (CustomerInfo) session.getAttribute("custinfo");
			logger.debug("Customer Info: " + custInfo.getId());
			session.setAttribute("custinfo", custInfo);
			//If the plane list is empty, print the below message
			if (planeList.isEmpty()) {
				bindRes.rejectValue("srcLocation", "location", "The searched flight details is not found");
				return new ModelAndView("searchFlights");
			} else {
				logger.debug("returning null");
				modelView.addObject("flightList", planeList);
				return modelView;
			}

		} else {
			return new ModelAndView("failed-login");
		}
	}
	
	// this page is for the search flight details
	@RequestMapping(value = "/searchFlightPage")
	public ModelAndView navigatePlane() {
		ModelAndView modelView = new ModelAndView("searchFlights");
		modelView.addObject("searchFlight", new findPlaneDTO());

		return modelView;
	}

	// Book the flight by the customer
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value = "/bookFlight")
	public ModelAndView goToFlight(@ModelAttribute("flight") PlaneDetails plane,
			@ModelAttribute("departure_date") String dept_time) {

		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(true);

		logger.debug("departure_date " + dept_time);
		if (session.getAttribute("custinfo") != null) {

			CustomerInfo cusinf = (CustomerInfo) session.getAttribute("custinfo");
			logger.debug("Customer Info: " + cusinf.getId());
			session.setAttribute("custinfo", cusinf);

			logger.debug("Plane Source details: " + plane.getSrcLoc());
			ModelAndView modelV = new ModelAndView("bookFlight");
			ReserveTicket resTicket = new ReserveTicket();
			//Get passenger first and last name
			resTicket.setPassenger_name(cusinf.getFname() + " " + cusinf.getLname());
			resTicket.setDept_time(dept_time + " " + plane.getDept_time());
			CustomerInfo cus = new CustomerInfo();
			cus.setId(cusinf.getId());
			resTicket.setCustomerInfo(cus);
			modelV.addObject("flight", plane);
			modelV.addObject("bookFlight", resTicket);
			return modelV;
		} else {
			return new ModelAndView("failed-login");
		}

	}

	// This part of the code works while paying the ticket price
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value = "/finalBook", method = RequestMethod.POST)
	public ModelAndView addPlaneBook(@ModelAttribute("reserveTicket") ReserveTicket resTicket)
			throws MalformedURLException, EmailException {

		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(true);

		if (session.getAttribute("custinfo") != null) {

			CustomerInfo cusinf = (CustomerInfo) session.getAttribute("custinfo");
			logger.debug("custinfo: " + cusinf.getId());
			session.setAttribute("custinfo", cusinf);

			resTicket = planeService.addReserveTicket(resTicket, cusinf);
			planeService.modifySeats(resTicket.getF_id());

			ModelAndView modelView = new ModelAndView("searchFlights");
			modelView.addObject("searchFlight", new findPlaneDTO());
			return modelView;

		} else {
			return new ModelAndView("failed-login");
		}
	}
	
	// this page is for the payment details
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value = "/paymentPage")
	public ModelAndView transactionPayDetails() {
		ModelAndView modelView = new ModelAndView("payment");

		return modelView;
	}
	
	// Check history of the customer
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value = "/checkBookedHistory", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String findPreviousBookings(@RequestParam(name = "passenger_name") String passenger_name, @RequestParam String dept_time, @RequestParam String phone_number,
			@RequestParam(name = "f_id") String plane_id) {
		dept_time = dept_time.substring(0, 11);
		//Get the passenger name, dept time , contact number and plane id
		ReserveTicket resTicket = reserveTicket.getReservationsHistory(passenger_name, dept_time, phone_number,	Long.parseLong(plane_id));
		logger.debug(" Travel details of ticket " + resTicket);
		if (resTicket != null) {
			return "{\"success\":\"true\"}";
		}
		return "{\"success\":\"false\"}";
	}

	// This part of the code works while cancelling the ticket
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value = "/cancleETicket", method = RequestMethod.GET)
	public ModelAndView ticketCancellation(@ModelAttribute("bookingId") String ticketid)
			throws MalformedURLException, EmailException {

		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(true);

		if (session.getAttribute("custinfo") != null) {

			CustomerInfo cusinf = (CustomerInfo) session.getAttribute("custinfo");
			logger.debug("custinfo: " + cusinf.getId());
			session.setAttribute("custinfo", cusinf);
			planeService.cancelReserveTicket(Long.parseLong(ticketid), cusinf);
			ModelAndView modelView = new ModelAndView("searchFlights");
			modelView.addObject("searchFlight", new findPlaneDTO());
			return modelView;

		} else {
			return new ModelAndView("failed-login");
		}
	}

	//This part of the code works while downloading the ticket
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value = "/downloadETicket", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<byte[]> ticketPdfDownload(@ModelAttribute("bookingId") String ticketId) {

		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(true);

		if (session.getAttribute("custinfo") != null) {

			CustomerInfo cusinf = (CustomerInfo) session.getAttribute("custinfo");
			Optional<ReserveTicket> resOpt = reserveTicket.findById(Long.parseLong(ticketId));
			ReserveTicket reserved = resOpt.get();
			try {

				emailController.emailTicket(reserved);
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (EmailException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			boolean sendEmail = false;

			File file = planeService.loadTicketPDF(reserved, cusinf, sendEmail);
			if (!sendEmail) {
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_PDF);
				// Here you have to set the actual filename of your pdf
				String fName = "DownloadPlaneTicket.pdf";
				headers.add("Content-Disposition", "inline; filename=" + fName);
				headers.setContentDispositionFormData(fName, fName);
				headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
				ResponseEntity<byte[]> res = null;
				try {
					res = new ResponseEntity<>(Files.readAllBytes(Paths.get(file.getAbsolutePath())), headers,
							HttpStatus.OK);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return res;
			}
		}
		return null;
	}

	public boolean validateDate(String todayDate) {
		SimpleDateFormat fm = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
		try {
			Date d = fm.parse(todayDate);
			logger.debug("date => " + d);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	// This part of the code is for admin use for updating the flights
	@PreAuthorize("isAuthenticated() and hasRole('admin')")
	@RequestMapping(value = "/updateFlights", method = RequestMethod.POST)
	public ModelAndView loadPlanes(@Valid @ModelAttribute("flight") PlaneDetails plane, BindingResult bindRes) {
		ModelAndView modelView = new ModelAndView("updateFlights");
		if (bindRes.hasErrors()) {
			return modelView;
		}
		if (plane.getDestLoc().equalsIgnoreCase(plane.getSrcLoc())) {
			ObjectError err = new ObjectError("destination", "Please enter different Source and Destination Location");
			bindRes.addError(err);
			logger.debug("Please enter different Source and Destination Location");
			bindRes.rejectValue("destination", "error.destination",
					"Please enter different Source and Destination Location");
			modelView.addObject("flightList", planeRepository.findAll());
			modelView.addObject("message", "Please enter different Source and Destination Location");
			return modelView;
		}
		if (!(validateDate(plane.getArrival_time()) && validateDate(plane.getDept_time()))) {
			logger.debug("Please enter different Source and Destination Location");
			bindRes.rejectValue("destination", "error.destination",
					"Please check date format for Arrival and Departure");
			modelView.addObject("flightList", planeRepository.findAll());
			modelView.addObject("message", "Please check date format for Arrival and Departure");
			return modelView;
		}

		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(true);

		if (session.getAttribute("custinfo") != null) {

			CustomerInfo cusinf = (CustomerInfo) session.getAttribute("custinfo");
			logger.debug("custinfo: " + cusinf.getId());

			plane = planeService.saveflight(plane);
			logger.debug("updated to this: " + plane.getPlane_id() + ", " + plane.getSrcLoc() + ", "
					+ plane.getDestLoc() + ", " + plane.getArrival_time() + ", " + plane.getDept_time() + ", "
					+ plane.getSeatsQty());
			modelView = new ModelAndView("admin");
			modelView.addObject("searchFlight", new findPlaneDTO());
			return modelView;

		} else {
			return new ModelAndView("failed-login");
		}
	}
	
	// Only admin has the authority to update the flights
	@PreAuthorize("isAuthenticated() and hasAuthority('admin')")
	@RequestMapping(value = "/updateFlights", method = RequestMethod.GET)
	public ModelAndView fetchPlaneInfo() {
		ModelAndView modelView = new ModelAndView("updateFlights");
		List<PlaneDetails> planeList = (List) planeRepository.findAll();
		logger.debug("flightList " + planeList + " flightList " + planeList.size());

		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(true);
		//Gathering all the customer info
		if (session.getAttribute("custinfo") != null) {
			CustomerInfo cusinf = (CustomerInfo) session.getAttribute("custinfo");
			logger.debug("custinfo: " + cusinf.getId());
			session.setAttribute("custinfo", cusinf);

			if (!planeList.isEmpty()) {
				modelView.addObject("flightList", planeRepository.findAll());
				return modelView;
			} else {
				ModelAndView modelView1 = new ModelAndView("admin");
				return modelView1;
			}
		} else {
			return new ModelAndView("falied-login");
		}
	}



}