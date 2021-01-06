package com.neu.flightTicket.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.neu.flightTicket.model.UserCredentials;
import com.neu.flightTicket.model.CustomerInfo;

/*
 * @author ajith
 */

public interface UserCredentialsRepo extends CrudRepository<UserCredentials, Long>{
	
	@Query("SELECT c FROM UserCredentials c where c.username = :username AND c.pwd=:pwd")
	public List<UserCredentials> getCredential(@Param("username") String username, 
			@Param("pwd") String pwd); 
}

