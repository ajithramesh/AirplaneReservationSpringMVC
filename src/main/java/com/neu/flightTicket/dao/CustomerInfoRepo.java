package com.neu.flightTicket.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.neu.flightTicket.model.UserCredentials;
import com.neu.flightTicket.model.CustomerInfo;

/*
 * @author ajith
 */

public interface CustomerInfoRepo extends CrudRepository<CustomerInfo, Long>{

	@Query("SELECT c FROM CustomerInfo c where c.username = :username AND c.pwd=:pwd")
	public List<CustomerInfo> validateUser(@Param("username") String username, 
			@Param("pwd") String pwd);
	
	@Query("SELECT c FROM CustomerInfo c where c.username = :username")
	public CustomerInfo findByUserName(@Param("username") String username);
}

