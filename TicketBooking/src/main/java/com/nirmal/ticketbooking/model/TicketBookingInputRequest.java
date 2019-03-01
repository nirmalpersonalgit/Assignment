package com.nirmal.ticketbooking.model;

import javax.validation.constraints.NotBlank;

public class TicketBookingInputRequest {
		
	@NotBlank(message = "First Name is blank")
	private String fname;
	@NotBlank(message = "Last Name is blank")
	private String lname;
	@NotBlank(message = "Passport Name is blank")
	private String passport;
	@NotBlank(message = "Date of Journey is blank")
	private String doj;
	
	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getPassport() {
		return passport;
	}

	public void setPassport(String passport) {
		this.passport = passport;
	}

	public String getDoj() {
		return doj;
	}

	public void setDoj(String doj) {
		this.doj = doj;
	}

}
