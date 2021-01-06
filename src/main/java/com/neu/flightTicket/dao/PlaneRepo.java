package com.neu.flightTicket.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.neu.flightTicket.model.ReserveTicket;
import com.neu.flightTicket.model.UserCredentials;
import com.neu.flightTicket.model.PlaneDetails;

/*
 * @author ajith
 */

public interface PlaneRepo extends CrudRepository<PlaneDetails, Long> {
	

	
}
