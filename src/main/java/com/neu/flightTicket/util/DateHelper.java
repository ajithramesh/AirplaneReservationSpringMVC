package com.neu.flightTicket.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * @author ajith
 */

public class DateHelper {

	public static String convertDate(String atime) {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String mysqlDateString = formatter.format(new Date()) + " " + atime + ":00";

		return mysqlDateString;

	}
}
