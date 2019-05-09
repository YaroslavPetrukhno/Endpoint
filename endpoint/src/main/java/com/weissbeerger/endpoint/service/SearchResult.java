package com.weissbeerger.endpoint.service;


/**
 * Contains information about result of a search
 * 
 * @author Yarik
 *
 */
public class SearchResult {
    
	/**
	 * Film information in xml
	 */
	public final String xml;
	
	/**
	 * Indicator whether the information was retrieved from a cache
	 */
	public final boolean fromCache;
	
	/**
	 * Creates a new {@code SearchResult} instanse
	 * @param xml         film information in xml 
	 * @param fromCache   indicator whether the information was retrieved from a cache
	 */
	public SearchResult(String xml, boolean fromCache) {
		this.xml = xml;
		this.fromCache = fromCache;
	}

}
