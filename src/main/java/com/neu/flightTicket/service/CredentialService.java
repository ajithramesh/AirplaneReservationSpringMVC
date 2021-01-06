package com.neu.flightTicket.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neu.flightTicket.dao.UserCredentialsRepo;
import com.neu.flightTicket.dao.CustomerInfoRepo;
import com.neu.flightTicket.model.UserCredentials;
import com.neu.flightTicket.model.CustomerInfo;

/*
 * @author ajith
 */

@Service
public class CredentialService implements UserCredentialService{

	@Autowired
	UserCredentialsRepo userCredRepo;
	
	@Autowired
	CustomerInfoRepo custInfoRepo;
	@Override
	public UserCredentials getCredential(String username, String password) {
		List<UserCredentials> credential = userCredRepo.getCredential(username, password);
		if (credential != null && credential.size() > 0) {
			System.out.println("***************" +  credential.get(0).getId());
			return credential.get(0);		
		} 
		return null;
	}

	public CustomerInfo setCustomerInfo(CustomerInfo custInfo) {
		custInfo.setUser_role("user");
		return custInfoRepo.save(custInfo);
	}

	public CustomerInfo userValidation(String username, String password) {
		List<CustomerInfo> custInfo = custInfoRepo.validateUser(username, password);
		System.out.println("userdetails list: " + custInfo);
		if (custInfo != null && custInfo.size() > 0) {
			System.out.println("***************" + custInfo.get(0).getId());
			return custInfo.get(0);
		}
		return null;
	}

}
