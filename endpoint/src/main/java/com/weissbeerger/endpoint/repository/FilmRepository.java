package com.weissbeerger.endpoint.repository;


/**
 * Interface used to interact with a films repository
 * 
 * @author Yarik
 *
 */
public interface FilmRepository {
    /**
     * Saves information about a film in the films repository
     * 
     * @param title  film title
     * @param xml    film description in xml
     */
	void save(String title, String xml);
	
	/**
	 * Reads information a film from a films repository
	 * 
	 * @param    title
	 * @return   film description in xml or {@code null} when there
	 *           is no information about the film in the films repository.
	 */
	String read(String title);

}
