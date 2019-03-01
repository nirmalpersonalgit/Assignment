package com.nirmal.ticketbooking.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import com.nirmal.ticketbooking.controller.validate.TicketBookingExceptionHandler;
import com.nirmal.ticketbooking.model.TicketBookingInputRequest;
import com.nirmal.ticketbooking.model.TicketBookingSendRequest;
import com.nirmal.ticketbooking.model.User;
import com.nirmal.ticketbooking.services.UserService;

@org.springframework.web.bind.annotation.RestController
@ControllerAdvice
public class RestController {
	
	private static long ticketId = 10000;
	private static final String URL_TICKET_BOOKING = "http://localhost:8080/ticketconfirm";
	
	@Autowired
	private UserService userService;

	@GetMapping("/")
	public String hello() {
		return "This is Home page";
	}
	
	@GetMapping("/saveuser")
	public String saveUser(@RequestParam String username, @RequestParam String firstname, @RequestParam String lastname, @RequestParam int age, @RequestParam String password) {
		User user = new User(username, firstname, lastname, age, password);
		userService.saveMyUser(user);
		return "User Saved";
	}
	
	@RequestMapping(value = "/bookticket", method = RequestMethod.POST)
	public ResponseEntity<Object> bookTicket(@RequestBody TicketBookingInputRequest  ticketBookingInputRequest) throws TicketBookingExceptionHandler {
		String result="";
		if(null == ticketBookingInputRequest) {
			throw new TicketBookingExceptionHandler("Received Ticket input is null");			
		}else {
			String msg = validateInputData(ticketBookingInputRequest);
			if(!msg.isEmpty()) {
				throw new TicketBookingExceptionHandler(msg);	
			}else {
				/*HttpHeaders headers = new HttpHeaders();
		    	
		    	headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		    	headers.setContentType(MediaType.APPLICATION_JSON);
		    	
		        RestTemplate restTemplate = new RestTemplate();
		        
				TicketBookingSendRequest ticketBookingSendRequest = new TicketBookingSendRequest(ticketBookingInputRequest.getDoj(),ticketBookingInputRequest.getPassport());
				HttpEntity<TicketBookingSendRequest> requestEntity = new HttpEntity<TicketBookingSendRequest>(ticketBookingSendRequest, headers);
				try {
		        ResponseEntity<String> uri = restTemplate.exchange(URL_TICKET_BOOKING, HttpMethod.POST, requestEntity, String.class);
		        System.out.println(uri.getBody());  
		        result = uri.getBody();
				}catch(Exception e) {
					//for testing
					result = "Ticket Id is "+ (ticketId +1)+" and Passport is "+ticketBookingSendRequest.getPassport();
					e.printStackTrace();
				}*/
				
				try {
				URL url = new URL(URL_TICKET_BOOKING);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setDoOutput(true);
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Type", "application/json");

				String input = "{\"doj\":100,\"passport\":\"iPad 4\"}";

				OutputStream os = conn.getOutputStream();
				os.write(input.getBytes());
				os.flush();

				if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
					throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
				}

				BufferedReader br = new BufferedReader(new InputStreamReader(
						(conn.getInputStream())));

				result = br.readLine();
				conn.disconnect();

			  } catch (MalformedURLException e) {
				e.printStackTrace();
			  } catch (IOException e) {
				e.printStackTrace();
			 }
			}
		}
		return new ResponseEntity<>("Ticket created successfully with "+result, HttpStatus.CREATED);
	}

	private String validateInputData(TicketBookingInputRequest ticketBookingInputRequest) {
		
		StringBuilder errorMsg = new StringBuilder();;
		
		if(ticketBookingInputRequest.getFname().isEmpty()) {
			errorMsg.append("First Name is Empty! ");
		}
		
		if(ticketBookingInputRequest.getLname().isEmpty()) {
			errorMsg.append("Last Name is Empty! ");
		}
		
		if(ticketBookingInputRequest.getPassport().isEmpty()) {
			errorMsg.append("Passport is Empty! ");
		}
		
		if(ticketBookingInputRequest.getDoj().isEmpty()) {
			errorMsg.append("Date of Journey is Empty!");
		}		
		return errorMsg.toString();
	}
	
	@RequestMapping(value = "/ticketconfirm", method = RequestMethod.POST)
	public ResponseEntity<String> generateTicket(@RequestBody TicketBookingSendRequest  ticketBookingSendRequest)  {
		String response = null;
		if(ticketBookingSendRequest != null) {
			//Need to validate the DOJ and passport and will generate a new ticket number
			//Just for dummy purpose I have declared a static variable and increasing by 1
			ticketId = ticketId + 1;
			response = "Ticket Id is "+ ticketId +" and Passport is "+ticketBookingSendRequest.getPassport();
		}
		return new ResponseEntity<String>(response,HttpStatus.CREATED);		
	}
}
