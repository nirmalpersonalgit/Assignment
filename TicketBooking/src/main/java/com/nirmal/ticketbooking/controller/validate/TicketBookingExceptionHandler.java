package com.nirmal.ticketbooking.controller.validate;

public class TicketBookingExceptionHandler extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	TicketBookingExceptionHandler(){		
	}
	
	public TicketBookingExceptionHandler(String msg){
		super(msg);
	}

}
