package com.weissbeerger.endpoint.service;

import org.springframework.util.MultiValueMap;

/**
 * Interface used to interact with a film service
 * 
 * @author Yarik
 *
 */
public interface FilmService {
	
	/**
	 * Searches film information by parameters. If information by specified parameters
	 * was not found {@code SearchResult} contains empty xml as result 
	 * 
	 * @param params     search parameters
	 * @return           result of films searching
	 * 
	 * @see              com.weissbeerger.endpoint.service.SearchResult
	 */
	SearchResult search(MultiValueMap<String, String> params);

}
