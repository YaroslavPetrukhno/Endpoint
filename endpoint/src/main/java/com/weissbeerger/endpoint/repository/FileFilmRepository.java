package com.weissbeerger.endpoint.repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Implementation of {@code FilmRepository} which stores information about films
 * in a folder on the file system. Information about a film is stored as xml files with 
 * name which matches film title.
 * 
 * @see com.weissbeerger.endpoint.repository.FilmRepository
 * 
 * @author Yarik
 *
 */

@Component
public class FileFilmRepository implements FilmRepository {

	/**
	 * Path to folder on the file system for storing films information.
	 */
	private String tempFolder;

	public FileFilmRepository(@Value("${endpoint.temp-folder:C:\\temp\\OMDB}") String tempFolder) {
		this.tempFolder = tempFolder;
		File files = new File(tempFolder);
		if(!files.exists()) {
			files.mkdirs();
		}
	}

	@Override
	public void save(String title, String xml) {
		try {
			Files.write(Paths.get(tempFolder, title+".xml"), xml.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String read(String title) {
		Path path = Paths.get(tempFolder, title+".xml");
		try {
			return new String(Files.readAllBytes(path));
		} catch (IOException e) {
			return null;
		}
	}

}
