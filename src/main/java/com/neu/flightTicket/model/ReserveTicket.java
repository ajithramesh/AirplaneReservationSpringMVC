package com.neu.flightTicket.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

import com.sun.istack.NotNull;

/*
 * @author ajith
 */

@Entity
@Table(name = "reserveticket")
public class ReserveTicket {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bid;

	private String sourceLocation;
	private String destLocation;

	@Column(name = "dept_time")
	private String dept_time;
	
	@NotEmpty(message = "Name is invalid")
	private String passenger_name;
	
	@Digits(integer = 99999, fraction = 0)
	@NotNull
	@Min(value = 1, message = "Age of Passenger must be greather than 0")
	@Max(value = 90, message = "Age of Passenger must be not be greater than 90")
	private int passenger_age;
	
	@NotEmpty(message = "Enter Valid Phone Number")
	private String phone_number;
	
	@Email(message = "Enter valid Email ID")
	private String passenger_email;
	
	private long f_id;

	@ManyToOne
	private CustomerInfo customerInfo;
	
		
	public CustomerInfo getCustomerInfo() {
		return customerInfo;
	}

	public void setCustomerInfo(CustomerInfo customerInfo) {
		this.customerInfo = customerInfo;
	}

	public long getF_id() {
		return f_id;
	}

	public void setF_id(long flight_id) {
		this.f_id = flight_id;
	}

	public Long getBid() {
		return bid;
	}

	public void setBid(Long bid) {
		this.bid = bid;
	}

	public String getSourceLocation() {
		return sourceLocation;
	}

	public void setSourceLocation(String sourceLocation) {
		this.sourceLocation = sourceLocation;
	}

	public String getDestLocation() {
		return destLocation;
	}

	public void setDestLocation(String destLocation) {
		this.destLocation = destLocation;
	}

	public String getDept_time() {
		return dept_time;
	}

	public void setDept_time(String dept_time) {
		this.dept_time = dept_time;
	}

	public String getPassenger_name() {
		return passenger_name;
	}

	public void setPassenger_name(String passenger_name) {
		this.passenger_name = passenger_name;
	}

	public int getPassenger_age() {
		return passenger_age;
	}

	public void setPassenger_age(int passenger_age) {
		this.passenger_age = passenger_age;
	}

	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	public String getPassenger_email() {
		return passenger_email;
	}

	public void setPassenger_email(String passenger_email) {
		this.passenger_email = passenger_email;
	}



}
