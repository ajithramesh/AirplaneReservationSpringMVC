package com.neu.flightTicket.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.neu.flightTicket.model.ReserveTicket;
import com.neu.flightTicket.model.PlaneDetails;
import com.neu.flightTicket.model.CustomerInfo;

/*
 * @author ajith
 */

public interface ReserveTicketRepo extends CrudRepository<ReserveTicket, Long> {

	@Query("SELECT c FROM ReserveTicket c where c.customerInfo.id  =:userId")
	public List<ReserveTicket> getReservations(@Param("userId") long userId);


	@Query("SELECT c FROM ReserveTicket c where c.passenger_name like :passenger_name and c.dept_time like %:dept_time% and c.phone_number like %:phone_number% and c.f_id = :f_id")
	public ReserveTicket getReservationsHistory(@Param("passenger_name") String passenger_name, @Param("dept_time") String dept_time,
			@Param("phone_number") String phone_number, @Param("f_id") long f_id);
}
