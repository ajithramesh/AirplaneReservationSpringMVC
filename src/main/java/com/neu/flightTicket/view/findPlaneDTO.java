package com.neu.flightTicket.view;

import org.hibernate.validator.constraints.NotEmpty;

/*
 * @author ajith
 */

public class findPlaneDTO {
	
	
	private Long find_id;
	@NotEmpty(message="Please enter Source Location")
	private String srcLocation;
	@NotEmpty(message="Please enter Destination Location")
	private String destLocation;
	@NotEmpty(message="date can't be empty")
	private String dept_time;
	@NotEmpty(message="Number of seats cannot be zero")
	private String pass_count;
	
	
	public Long getFind_id() {
		return find_id;
	}
	public void setFind_id(Long find_id) {
		this.find_id = find_id;
	}
	public String getSrcLocation() {
		return srcLocation;
	}
	public void setSrcLocation(String srcLocation) {
		this.srcLocation = srcLocation;
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
	public String getPass_count() {
		return pass_count;
	}
	public void setPass_count(String pass_count) {
		this.pass_count = pass_count;
	}




}
