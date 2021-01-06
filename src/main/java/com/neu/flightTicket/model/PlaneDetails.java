package com.neu.flightTicket.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.Negative;
import javax.validation.constraints.NegativeOrZero;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.sun.istack.NotNull;;

/*
 * @author ajith
 */

@Entity
@Table(name = "planedetails")
public class PlaneDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long plane_id;
	
	@NotEmpty(message = "Please enter Source Location")
	private String srcLoc;
	
	@NotEmpty(message = "Please enter Destination Location")
	private String destLoc;

	@NotBlank(message = "Please enter Departure time details")
	private String dept_time;
	
	@NotBlank(message = "Please enter Arrival time details")
	private String arrival_time;

	@NotNull
	private int seatsQty;
	
	@Digits(integer = 99999, fraction = 0)
	@NotNull
	private int seatsBooked = 0;
	
	@Digits(integer = 99999, fraction = 0)
	@NotNull
	@Min(value = 200, message = "Ticket Price must be greater than 200")
	private int ticketPrice = 0;

	public Long getPlane_id() {
		return plane_id;
	}

	public void setPlane_id(Long plane_id) {
		this.plane_id = plane_id;
	}

	public String getSrcLoc() {
		return srcLoc;
	}

	public void setSrcLoc(String srcLoc) {
		this.srcLoc = srcLoc;
	}

	public String getDestLoc() {
		return destLoc;
	}

	public void setDestLoc(String destLoc) {
		this.destLoc = destLoc;
	}

	public String getDept_time() {
		return dept_time;
	}
	
	public void setDept_time(String dept_time) {
		this.dept_time = dept_time;
	}
	
	public String getArrival_time() {
		return arrival_time;
	}

	public void setArrival_time(String arrival_time) {
		this.arrival_time = arrival_time;
	}
	
	public int getSeatsQty() {
		return seatsQty;
	}

	public void setSeatsQty(int seatsQty) {
		this.seatsQty = seatsQty;
	}
	
	public int getSeatsBooked() {
		return seatsBooked;
	}

	public void setSeatsBooked(int bookedSeats) {
		this.seatsBooked = bookedSeats;
	}

	public int getTicketPrice() {
		return ticketPrice;
	}

	public void setTicketPrice(int ticketCost) {
		this.ticketPrice = ticketCost;
	}
}
