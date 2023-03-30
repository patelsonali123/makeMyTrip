package com.ashokit.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ashokit.reponse.Ticket;
import com.ashokit.request.Passenger;

@Service
public class MakeMyTripService {
	
	/*public static final String IRCTC_ENDPOINT_1= "http://3.110.190.17:8080/ticket";
	public static final String IRCTC_ENDPOINT_2="http://3.110.190.17:8080/ticket/{ticketId}";
	*/
	@Value("${irctc.endpoint.book.ticket}")
	private String IRCTC_BOOK_TICKET_URL;

	@Value("${irctc.endpoint.get.ticket}")
	private String IRCTC_GET_TICKET_URL;

	public Ticket getTicketInfo(String ticketId) {

		RestTemplate rt = new RestTemplate();

		ResponseEntity<Ticket> responseEntity = 
				rt.getForEntity(IRCTC_GET_TICKET_URL, Ticket.class, ticketId);

		int status = responseEntity.getStatusCodeValue();
		if (status == 200) {
			Ticket ticket = responseEntity.getBody();
			return ticket;
		}

		return null;
	}

	public Ticket processTicketBooking(Passenger passenger) {

		RestTemplate rt = new RestTemplate();

		ResponseEntity<Ticket> responseEntity = 
					rt.postForEntity(IRCTC_BOOK_TICKET_URL, passenger, Ticket.class);

		int statusCode = responseEntity.getStatusCodeValue();

		if (statusCode == 200) {
			Ticket ticket = responseEntity.getBody();
			return ticket;
		}
		return null;
	}
}
