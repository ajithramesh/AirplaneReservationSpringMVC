package com.neu.flightTicket.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Locale;
import java.util.Optional;
import java.util.Properties;

import javax.servlet.http.HttpSession;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.neu.flightTicket.dao.PlaneRepo;
import com.neu.flightTicket.model.ReserveTicket;
import com.neu.flightTicket.model.PlaneDetails;
import com.neu.flightTicket.model.CustomerInfo;

/*
 * @author ajith
 */

@Controller
public class SendEmailController {

	@Autowired
	PlaneRepo planeRepository;

	// This controller is called while sending the email
	@RequestMapping(value = "/sendmail", method = RequestMethod.POST)
	public ModelAndView emailTicket(@ModelAttribute("reserveTicket") ReserveTicket resTicket)
			throws EmailException, MalformedURLException {

		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(true);

		// Get the customer info using the session attribute
		CustomerInfo cusinf = (CustomerInfo) session.getAttribute("custinfo");

		// By using the plane id fetch all the details
		Optional<PlaneDetails> op = planeRepository.findById(resTicket.getF_id());
		File pdfFormat = new File("ticket" + "PlaneDetails" + ".pdf");
		ModelAndView modelView = new ModelAndView("reserveTicket");
		if (op.isPresent()) {
			PlaneDetails planeDet = op.get();
			Document doc = new Document(PageSize.A4, 40, 40, 40, 40);
			try {
				PdfWriter.getInstance(doc, new FileOutputStream(pdfFormat));
				doc.open();
				String emailTick = "Booking Id: 	" + resTicket.getBid() + "\n\nFlight Ticket\n\n"
						+ "Name: 			" + resTicket.getPassenger_name() + "\n" + "From: 			"
						+ resTicket.getSourceLocation() + "\n" + "To: 			" + resTicket.getDestLocation() + "\n"
						+ "Departure Time: " + resTicket.getDept_time() + "\n" + "Email: 		"
						+ resTicket.getPassenger_email() + "\n" + "PlaneDetails Details  \nDeparture Time - "
						+ planeDet.getDept_time() + "\n Arrival Time - " + planeDet.getArrival_time()
						+ "\n Ticket Price - " + planeDet.getTicketPrice();

				doc.add(new Paragraph(emailTick));

				doc.close();
				try {
					EmailAttachment emailAtt = new EmailAttachment();
					emailAtt.setPath(pdfFormat.getCanonicalPath());
					emailAtt.setDisposition(EmailAttachment.ATTACHMENT);
					emailAtt.setDescription("Your PlaneDetails Tickets");
					emailAtt.setName(cusinf.getFname() + ".pdf");

					// By using the following properties, email can be sent
					MultiPartEmail multiPaEm = new MultiPartEmail();
					multiPaEm.setHostName("smtp.gmail.com");
					multiPaEm.setSmtpPort(587);
					multiPaEm.setSSLOnConnect(true);
					multiPaEm.setAuthenticator(new DefaultAuthenticator("webdevtoolsinfo@gmail.com", "info6250"));
					multiPaEm.addTo(resTicket.getPassenger_email(), "INFO6250");
					multiPaEm.setFrom("webdevtoolsinfo@gmail.com", "Me");
					multiPaEm.setSubject("PlaneDetails Tickets");
					multiPaEm.setMsg("Hello," + "Kindly Find the tickets attached");

					// Attach the details
					multiPaEm.attach(emailAtt);

					// Finally email is sent
					multiPaEm.send();
					return modelView;
				} catch (IOException e) {
					ModelAndView modelV2 = new ModelAndView("reserveTicket");
					return modelV2;

				}
			} catch (FileNotFoundException | DocumentException e) {
				ModelAndView mav1 = new ModelAndView("reserveTicket");
				return mav1;
			}
		}
		return modelView;

	}
}
