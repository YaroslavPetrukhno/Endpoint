package com.weissbeerger.endpoint.service;

import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.weissbeerger.endpoint.SearchParameters;
import com.weissbeerger.endpoint.repository.FilmRepository;

/**
 * Implementation of the {@code FilmService} which retrieves information from
 * OMDb service with and caches in {@code FilmRepository}.
 * 
 * @author Yarik
 * 
 * @see <a href="http://www.omdbapi.com/">OMDb API</a>
 * @see com.weissbeerger.endpoint.service.FilmService
 * @see com.weissbeerger.endpoint.repository.FilmRepository
 */

@Service
public class FilmServiceImpl implements FilmService {
	
	/**
	 * URL of the OMDB films search API
	 */
	private static final String OMDB_API_URL = "http://www.omdbapi.com";
	
	/**
	 * API key of OMDB
	 */
	@Value("${endpoint.api-key}")
	private String apikey;
	
	private RestTemplate restTemplate;
	
	private FilmRepository repository;

	/**
	 * Creates a new instance of {@code FilmServiceImpl}
	 * @param restTemplate    RestTemplate service
	 * @param repository      films repository
	 * 
	 * @see org.springframework.web.client.RestTemplate
	 * @see com.weissbeerger.endpoint.repository.FilmRepository
	 */
	public FilmServiceImpl(RestTemplate restTemplate, FilmRepository repository) {
		this.restTemplate = restTemplate;
		this.repository = repository;
	}

	@Override
	public SearchResult search(MultiValueMap<String, String> params) {
		String xml = null;
		String title = params.getFirst(SearchParameters.TITLE);
		if(title!=null) {
			xml = repository.read(title);
			if(xml!=null) {
				return new SearchResult(xml, true);
			}
		}
		params.set(SearchParameters.RESPONSE_FORMAT, "json");
		params.set(SearchParameters.API_KEY, apikey);
		UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(OMDB_API_URL).queryParams(params).build();
		ResponseEntity<String> responseResult = restTemplate.getForEntity(uriComponents.toUriString(), String.class);
	    String body = responseResult.getBody();
		JSONObject jsonObject = new JSONObject(body);
		boolean found = jsonObject.getBoolean("Response");
		if(!found && !responseResult.getBody().contains("Movie not found!")) {
			throw new SearchException(body);
		}
		xml = XML.toString(found ? jsonObject: "", "Result") ;
		if(found && title!=null) {
			repository.save(title, xml);
		}
		return new SearchResult(xml, false);
	}
	
}
