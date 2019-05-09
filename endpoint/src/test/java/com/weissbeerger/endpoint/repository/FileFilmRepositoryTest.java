package com.weissbeerger.endpoint.repository;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class FileFilmRepositoryTest {

	@Rule
	public TemporaryFolder tempFolder = new TemporaryFolder();
	
	public FilmRepository repository;

	@Before
	public void setUp() throws Exception {	
		repository = new FileFilmRepository(tempFolder.getRoot().getAbsolutePath());
	}

	@Test
	public void testSave() {
		repository.save("Titanic", "<Title>Titanic</Title>");
		Path path = Paths.get(tempFolder.getRoot().getAbsolutePath(), "Titanic.xml");
		assertTrue(Files.exists(path));
	}

	@Test
	public void testReadExistingFile() throws IOException {
		tempFolder.newFile("Batman.xml");
		String res = repository.read("Batman");
		assertNotNull(res);
	}

	@Test
	public void testReadNotExistingFile() {
		String res = repository.read("fake.xml");
		assertNull(res);
	}	
}
