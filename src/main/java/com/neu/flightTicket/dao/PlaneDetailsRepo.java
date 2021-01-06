package com.neu.flightTicket.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.neu.flightTicket.model.PlaneDetails;
import com.neu.flightTicket.model.CustomerInfo;
import com.neu.flightTicket.view.findPlaneDTO;

/*
 * @author ajith
 */

public interface PlaneDetailsRepo extends CrudRepository<PlaneDetails, Long>  {
	
	@Query("SELECT f.plane_id, f.srcLoc, f.destLoc, f.dept_time, f.arrival_time, f.seatsQty FROM PlaneDetails f"
			+ " WHERE f.srcLoc=:srcLoc AND f.destLoc=:destLoc")
	public List<PlaneDetails> findPlaneResults(@Param("srcLoc") String srcLoc, 
			@Param("destLoc") String destLoc);
	
	@Query("SELECT u FROM PlaneDetails u WHERE u.srcLoc=?1 AND u.destLoc=?2 and u.seatsQty>u.seatsBooked")
	List<PlaneDetails> findPlaneBySrcLoc(String location,String destination);
	
}
