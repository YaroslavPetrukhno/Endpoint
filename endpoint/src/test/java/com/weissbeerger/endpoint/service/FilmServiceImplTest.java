package com.weissbeerger.endpoint.service;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.weissbeerger.endpoint.SearchParameters;
import com.weissbeerger.endpoint.repository.FilmRepository;

public class FilmServiceImplTest {

	public FilmService filmService;
	
	public MultiValueMap<String, String> headers;
	
	@Mock
	public RestTemplate restTemplate;
	
	@Mock
	public FilmRepository repository;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		filmService = new FilmServiceImpl(restTemplate, repository);
		headers = new HttpHeaders();
	}

	@Test
	public void testSearchResultFromOMDB() {	
		ResponseEntity<String> resp = ResponseEntity.ok("{ \"title\" : \"Titanic\", \"Response\":\"True\"}");
		Mockito.when(restTemplate.getForEntity(anyString(), eq(String.class))).thenReturn(resp);
		SearchResult sr = filmService.search(headers);
		assertThat(sr.xml, containsString("Titanic"));
		assertFalse(sr.fromCache);
	}

	@Test
	public void testSearchResultFromCache() {
		headers.add(SearchParameters.TITLE, "Titanic");
		Mockito.when(repository.read(anyString())).thenReturn("<Title>Titanic</Title>");
		SearchResult sr = filmService.search(headers);
		assertThat(sr.xml, containsString("Titanic"));
		assertTrue(sr.fromCache);
	}

	@Test
	public void testSearchNoResult() {
		ResponseEntity<String> resp = ResponseEntity.ok("{ \"Response\" : \"False\"}");
		Mockito.when(restTemplate.getForEntity(anyString(), eq(String.class))).thenReturn(resp);
		SearchResult sr = filmService.search(headers);
		assertEquals(sr.xml, "<Result/>");
		assertFalse(sr.fromCache);
	}	
}
