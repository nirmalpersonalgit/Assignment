package com.nirmal.ticketbooking.model;

import java.io.Serializable;

public class TicketBookingSendRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String doj;
	private String passport;
	public TicketBookingSendRequest() {
		
	}
	
	public TicketBookingSendRequest(String doj, String passport){	
		this.doj = doj;
		this.passport = passport;
	}

	public String getDoj() {
		return doj;
	}

	public void setDoj(String doj) {
		this.doj = doj;
	}

	public String getPassport() {
		return passport;
	}

	public void setPassport(String passport) {
		this.passport = passport;
	}
	
}
