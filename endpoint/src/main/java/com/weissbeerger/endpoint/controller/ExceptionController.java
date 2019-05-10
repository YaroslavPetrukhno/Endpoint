package com.weissbeerger.endpoint.controller;

import org.json.JSONObject;
import org.json.XML;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestClientResponseException;

import com.weissbeerger.endpoint.service.SearchException;

/**
 * Handles film search exceptions
 * 
 * @author Yarik
 *
 */
@ControllerAdvice
public class ExceptionController {

	/**
	 * 
	 * @param e    search movie exception
	 * @return     bad request response
	 */
	@ExceptionHandler(SearchException.class)
	public ResponseEntity<Object> searchMovie(SearchException e) {
		String xml = jsonToXml(e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(xml);		
	}	
	
	/**
	 * Wrong response from source server. 
	 * 
	 * @param e     HTTP response exception
	 * @return      internal server error response
	 */
	@ExceptionHandler(RestClientResponseException.class)
	public ResponseEntity<Object> clientException(RestClientResponseException e) {
		String xml = jsonToXml(e.getResponseBodyAsString());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(xml);		
	}
	
	
	/**
	 * Converts json string to xml string
	 * 
	 * @param json      json string
	 * @return          xml string
	 */
	private String jsonToXml(String json) {
		JSONObject jsonObject = new JSONObject(json);
		return XML.toString(jsonObject, "Result");			
	}
}
