package com.neu.flightTicket.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.neu.flightTicket.dao.ReserveTicketRepo;
import com.neu.flightTicket.dao.PlaneRepo;
import com.neu.flightTicket.model.ReserveTicket;
import com.neu.flightTicket.model.PlaneDetails;
import com.neu.flightTicket.model.CustomerInfo;

/*
 * @author ajith
 */

@Service
public class PlaneService {

	@Autowired
	PlaneRepo planeRepository;
	
	@Autowired
	ReserveTicketRepo reserveTicketRepository;

	public PlaneDetails saveflight(PlaneDetails plane) {
		return planeRepository.save(plane);
	}

	public List<PlaneDetails> lookUpPlane() {
		return null;
	}

	public List<PlaneDetails> updatePlaneDetails() {
		return null;
	}

	public ReserveTicket addReserveTicket(ReserveTicket reserveTicket, CustomerInfo custInfo) {
		reserveTicket.setCustomerInfo(custInfo);
		return reserveTicketRepository.save(reserveTicket);
	}

	public boolean modifySeats(long planeId) {
		Optional<PlaneDetails> optional = planeRepository.findById(planeId);
		if (optional.isPresent()) {
			PlaneDetails f = optional.get();
			f.setSeatsBooked(f.getSeatsBooked() + 1);
			planeRepository.save(f);
			return true;
		
		}

		return false;
	}
	
	
	public void cancelReserveTicket(Long ticketId, CustomerInfo custInfo) {
		reserveTicketRepository.deleteById(ticketId);
	}

	public File loadTicketPDF(ReserveTicket reserveTicket, CustomerInfo custInfo,boolean sendEmail) {
		Optional<PlaneDetails> optional = planeRepository.findById(reserveTicket.getF_id());
		File pdfFile = new File("ticket" + custInfo.getFname() + ".pdf");
		if (optional.isPresent()) {
			PlaneDetails f = optional.get();
			Document document = new Document(PageSize.A4, 50, 50, 50, 50);			
			try {
				PdfWriter.getInstance(document, new FileOutputStream(pdfFile));

				document.open();

				String ticketData = "Booking Id: 	" + reserveTicket.getBid() + "\n\nFlight Ticket\n\n"
						+ "Name: 			" + reserveTicket.getPassenger_name() + "\n" + "From: 			"
						+ reserveTicket.getSourceLocation() + "\n" + "To: 			" + reserveTicket.getDestLocation()
						+ "\n" + "Departure Time: " + reserveTicket.getDept_time() + "\n" + "Email: 		"
						+ reserveTicket.getPassenger_email() + "\n" + "PlaneDetails Details  \nDeparture Time - "
						+ f.getDept_time() + "\n Arrival Time - " + f.getArrival_time() + "\n Ticket Price - "
						+ f.getTicketPrice();

				document.add(new Paragraph(ticketData));

				document.close();

			} catch (FileNotFoundException | DocumentException e) {
				e.printStackTrace();
			}
		}
		return pdfFile;
	}

}
