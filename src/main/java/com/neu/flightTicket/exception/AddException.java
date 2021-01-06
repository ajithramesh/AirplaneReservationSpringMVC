package com.neu.flightTicket.exception;

/*
 * @author ajith
 */

public class AddException extends Exception {
	public AddException(String message)
	{
		super(message);
	}
	
	public AddException(String message, Throwable cause)
	{
		super(message,cause);
	}
}
