package com.weissbeerger.endpoint.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.weissbeerger.endpoint.service.FilmService;
import com.weissbeerger.endpoint.service.SearchResult;

/**
 * Serves http requests for the films searching
 * 
 * @author Yarik
 *
 */

@RestController
public class FilmController {
	
	private FilmService filmService;
	
	/**
	 * Creates a new {@code FilmController} instance
	 * @param filmService    the {@code FilmService} for films searching
	 * @see                  com.weissbeerger.endpoint.service.FilmService
	 */
	public FilmController(FilmService filmService) {
		this.filmService = filmService;
	}

	/**
	 * Returns response with result of the film search in xml format and 
	 * information whether the search result came from the local server in 
	 * the http header "cached-result"
	 * 
 	 * @param params   film search parameters
	 * @return         response with
	 */
	@GetMapping(value = "/", produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<String> search(@RequestParam MultiValueMap<String, String> params) {
		SearchResult res = filmService.search(params);
		return ResponseEntity.ok().header("cached-result", String.valueOf(res.fromCache)).body(res.xml);
	}	
	
	
}
