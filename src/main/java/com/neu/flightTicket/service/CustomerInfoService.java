package com.neu.flightTicket.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.neu.flightTicket.dao.CustomerInfoRepo;
import com.neu.flightTicket.model.CustomerInfo;

/*
 * @author ajith
 */

@Service("customerInfoService")
public class CustomerInfoService implements UserDetailsService {

	@Autowired
	CustomerInfoRepo customerInfoRepository;
	

	@Transactional(readOnly = true)
	@Override
	public org.springframework.security.core.userdetails.User loadUserByUsername(final String username)
			throws UsernameNotFoundException {
		System.out.println("Load user by username ==> "+username);
		CustomerInfo user = customerInfoRepository.findByUserName(username);
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session= attr.getRequest().getSession(true);
		session.setAttribute("custinfo", user);
		List<GrantedAuthority> authorities = buildUserAuthority(user.getUser_role());
		 org.springframework.security.core.userdetails.User user2=buildUserForAuthentication(user, authorities);
		 System.out.println("Load user by username ==> "+user2);
		return user2; 

	}

	private org.springframework.security.core.userdetails.User buildUserForAuthentication(CustomerInfo user,
			List<GrantedAuthority> authorities) {
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPwd(), true, true,
				true, true, authorities);
	}

	private List<GrantedAuthority> buildUserAuthority(String userRoles) {

		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

		setAuths.add(new SimpleGrantedAuthority(userRoles));

		List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);

		return Result;
	}

}
