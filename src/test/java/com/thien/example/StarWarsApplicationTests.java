package com.thien.example;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import model.Character;
import model.Movie;
import model.Results;

//@SpringBootTest
class StarWarsApplicationTests {

	Movie mov;
	Character charac;
	Results ress;
	RestTemplate resttemplate = new RestTemplate();
	Character[] CharacterMovieFeatures;
	String sURL ;
   
	
	@BeforeEach 
	@DisplayName("Before each test execute init")
	void InitEach() {
		sURL = "https://swapi.dev/api/people/?search=Luke Skywalker";
	}

	
	@Test
	@DisplayName("Testing URL can be reached")
	void TestUrlExists() throws Exception {
		try {
			URL url = new URL("https://swapi.dev/api/");
			HttpURLConnection huc = (HttpURLConnection) url.openConnection();

			int responseCode = huc.getResponseCode();

			assertEquals(HttpURLConnection.HTTP_OK, responseCode);
		} catch (Exception e) {
			throw e;
		}
	}	
	
	@Test
	@DisplayName("Validate user exists in API and mapping to Results object")
	void TestUserExistsAPI() throws Exception {
				
		try {			
			ress = resttemplate.getForObject(sURL, Results.class);			
		
			assertTrue(ress.getCount().equals("1"));				
						
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Test
	@DisplayName("Validate user not exists in API and mapping to Results object")
	void TestUserNotExistsAPI() throws Exception {
				
		try {
					
			sURL = "https://swapi.dev/api/people/?search=aap";				 
		
			ress = resttemplate.getForObject(sURL, Results.class);			
		
			assertTrue(ress.getCount().equals("0"));				
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Test
	@DisplayName("Map found user to array of character object")
	void TestUserMapCharacter() throws Exception {
				
		try {
		
			Character[] CharacterMovieFeatures; 		
			ress = resttemplate.getForObject(sURL, Results.class);
			
			CharacterMovieFeatures = ress.getResults();
			
			assertTrue(CharacterMovieFeatures.length ==1);	
			
			assertEquals(CharacterMovieFeatures[0].getName(), "Luke Skywalker");
			assertEquals(CharacterMovieFeatures[0].getBirth_year(), "19BBY");
						
			
		} catch (Exception e) {
			throw e;
		}
	}	
	
	@Test
	@DisplayName("Get the all Url of the movies the character played in")
	void TestMovies() throws Exception {
				
		try {
		
			Character[] CharacterMovieFeatures;		
			ress = resttemplate.getForObject(sURL, Results.class);
			
			CharacterMovieFeatures = ress.getResults();
			
			assertEquals(CharacterMovieFeatures[0].getFilms().length, 4);
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Test
	@DisplayName("Get all character that played in returned movies without duplicate characters and assert a samples")
	void TestGetDistinctCharacterInMovies() throws Exception {
				
		try {	
			Boolean exists = false;
			Character[] CharacterMovieFeatures;
			ArrayList<Character> Characters = new ArrayList<Character>();

			String arie [];		 
			ress = resttemplate.getForObject(sURL, Results.class);
			
			CharacterMovieFeatures = ress.getResults();
			
			arie = CharacterMovieFeatures[0].getFilms();
			
			
			for (String m : arie) {
				m = m.replace("http", "https");
				mov = resttemplate.getForObject(m, Movie.class);
				
				//Validate if movie object contains array of character object
				assertTrue(mov.getCharacters().length > 0);
				
				for (String f : mov.getCharacters()) {

					//The URL retrieved is http but the used htt
					f = f.replace("http", "https");
					charac = resttemplate.getForObject(f, Character.class);
					
					//Validate character object name is not empty or ""
					assertTrue(charac.getName().length()> 0);
					
					exists = false;
					if (Characters.size() == 0) {
						Characters.add(charac);
					} else {
						for (Character name : Characters) {
							if (name.getName().equals(charac.getName())) {
								exists = true;
							}
						}
						if (exists == false) {
							Characters.add(charac);
						}
					}
				}				
			}			
			//assert the amount of characters
			assertEquals(Characters.size(), 52);
			
		} catch (Exception e) {
			throw e;
		}
	}	
	
	//@Disabled
	@Test
	@DisplayName("NumberformatException fail for formatting the birthyear")
	void testExpectedExceptionFail() {

		Character ch = new Character();

		//should be 19BBY to not raise error
		ch.setBirth_year("nineteenBBY");
				
		//using lambda expression
		assertThrows(NumberFormatException.class, () -> {
		    ch.getBirth_year_number();
	  });
	 
	}
	
	
}
