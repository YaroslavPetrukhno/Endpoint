package com.weissbeerger.endpoint.service;

/**
 * Thrown when movie not found due to wrong search parameters.
 * 
 * 
 * @author Yarik
 *
 */
public class SearchException extends RuntimeException {
	private static final long serialVersionUID = 3890507813652113281L;

	/**
	 * Constructs {@code SearchException}
	 * 
	 * @param message
	 */
	public SearchException(String message) {
		super(message);
	}
    
	
}
