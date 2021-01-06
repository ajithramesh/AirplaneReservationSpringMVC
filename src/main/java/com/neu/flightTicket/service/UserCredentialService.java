package com.neu.flightTicket.service;

import com.neu.flightTicket.model.UserCredentials;
import com.neu.flightTicket.model.CustomerInfo;

/*
 * @author ajith
 */

public interface UserCredentialService {
	UserCredentials getCredential(String username, String password);
	public CustomerInfo setCustomerInfo(CustomerInfo custInfo);
	public CustomerInfo userValidation(String username, String password);
}
