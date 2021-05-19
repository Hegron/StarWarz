package com.thien.example.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

//import org.json.simple.JSONObject;
//import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


import model.Character;
import model.Results;
import model.Movie;

/**
 * Restcontroller that retrieves information about starwars characters from an extrnal API http://swapi.dev. For more information about the API see https://swapi.dev/documentation
 * Using RestTemplate to map returned JSON fields to Class objects
 * The endpoint can be called through localhost:8080
 * 
 * @author TN
 *
 */
@RestController
public class SWcontroller {	
	
	private static final Logger log = LoggerFactory.getLogger(SWcontroller.class);
	private HashMap<String, ArrayList<Character>> MyHash = new HashMap<String, ArrayList<Character>>();
	private String apiUrl = "https://swapi.dev/api/people/?search=";

	@Autowired
	private RestTemplate restTemplate;

	/**
	 * HTTP Get action is done to the external API get the information of the character and the movies played in retured in JSON format: localhost:8080/{name}
	 *
	 * @param Name name of the searched character
	 * @return Calls a method ShowSingle to return a list of the character played in the same movie as the searched character ordered by birthyear ascending
	 *         or an error.
	 */
	@GetMapping("/{name}")
	public String getCharacter(@PathVariable(value = "name") String Name) {
		String response = "";
		Results results;
		Character[] CharacterMovieFeatures;
		 
		try {
			if (characterExists(Name) == false) {
				results = restTemplate.getForObject(apiUrl + Name, Results.class);
				// response = restTemplate.getForObject(apiUrl + Name, String.class);

				if (results.getCount().equals("0")) {
					return "Character " + Name + " not found!";
					// throw new ResourceNotFoundException ("Character " + Name + " not found!");
				} else {
					// store all movies where character plays in
					CharacterMovieFeatures = results.getResults();
					//Store search character and all co character in hashmap
					MyHash.put(Name, getMovies(CharacterMovieFeatures[0].getFilms(), Name));
				}
			}
			return showSingle(Name);
		} catch (Exception e) {			
			return "Something went wrong:::" + e.getMessage();				
		}		
	}

	/**
	 * Http Get action is done here to show all the stored information about the starwars character: localhost:8080/show
	 * This can be used primariy for debugging
	 * 
	 * @return returns the stored information
	 */
	@GetMapping("/show")
	public String show() {

		String message = "";
		ArrayList<Character> Characters = new ArrayList<Character>();
		String key = "";
		Character a;

		if (MyHash.isEmpty()) {
			return "No characters stored";
		} else {

			for (Map.Entry<String, ArrayList<Character>> entry : MyHash.entrySet()) {
				key = entry.getKey();
				Characters = entry.getValue();
				message += "Key = " + key + "\n\n";

				Iterator<Character> i = Characters.iterator();
				while (i.hasNext()) {
					a = i.next();
					message += "feature character name = " + a.getName() + "\nbirthyear = " + a.getBirth_year() + "\n\n";
				}
			}		
		}
		return message;
	}
	
	/**
	 * Show the characters played in the same movie as the searched character ordered by birthyear ascending
	 * 
	 * @param Name Name of the character that is searched
	 * @return returns a list of the character played in the same movie as the searched character with the birthyear ordered ascending
	 */
	private String showSingle(String Name) {

		String message = "";
		String caseInsensitiveName = "";
		ArrayList<Character> coCharacters = new ArrayList<Character>();

		try {
			for (Map.Entry<String, ArrayList<Character>> entry : MyHash.entrySet()) {
				if (entry.getKey().toUpperCase().equals(Name.toUpperCase())) {
					caseInsensitiveName = entry.getKey();
					break;
				}
			}

			if (caseInsensitiveName.equals("")) {
				return message = "No characters with name " + Name + " found.";
			} 
			else {
				coCharacters = MyHash.get(caseInsensitiveName);
				Collections.sort(coCharacters, (o1, o2) -> o1.getBirth_year_number().compareTo(o2.getBirth_year_number()));

				for (Character curCharacter : coCharacters) {
					if (Name.toUpperCase().contains(curCharacter.getName().toUpperCase()) == false) {
						if (!curCharacter.getName().toUpperCase().contains(Name.toUpperCase())) {
							message += "Name      = " + curCharacter.getName() + "\nBirthYear = " + curCharacter.getBirth_year() + "\n\n"; 		
						}	
					}			
				}
			}
		}
		catch(Exception e) {
			throw e;
		}		

		return message;
	}
	
	
	/**
	 * Get all charactes played in the movie and mapp this movie and characters to Movie objects, see class Movie
	 * 
	 * @param strmovies array of URLS to the movies in the exteral API
	 * @param searchName the name of the searched character
	 * @return Returns an Arraylist of characters objects retrieved from the movies
	 */
	private ArrayList<Character> getMovies(String strmovies[], String searchName) {
		String res = "";
		Movie movie;
		ArrayList<Character> Characters = new ArrayList<Character>();

		try {
			for (String m : strmovies) {
				m = m.replace("http", "https");
				movie = restTemplate.getForObject(m, Movie.class);
				getCharacterInMovie(movie, Characters);
			}
		}
		catch(Exception e) {
			throw e;
		}	
		return Characters;
	}

	/**
	 * Get characters in movie and map them to an Character object
	 * 
	 * @param mov movie object where all list of URLs of movies are stored
	 * @param Characters Arraylist to store character objects
	 */
	private void getCharacterInMovie(Movie mov, ArrayList<Character> Characters) {

		boolean exists;
		String res = "";

		Character charac;

		try {
			//Loop through the character list and map each character to a Character object using Resttemplate
			for (String f : mov.getCharacters()) {

				//The URL retrieved is http but the used htt
				f = f.replace("http", "https");
				charac = restTemplate.getForObject(f, Character.class);
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
		catch(Exception e){
			throw e;
		}
		
	}

	/**
	 * Determine of the name of the character exists, true = yes, false= no
	 * 
	 * @param characterName name of the character
	 * @return Boolean if the character aready exist in the HashMap
	 */
	  private boolean characterExists(String characterName) {
	  
		for (Map.Entry<String, ArrayList<Character>> entry : MyHash.entrySet()) {
			if (entry.getKey().toUpperCase().equals(characterName.toUpperCase())) {
				log.info("Character " + characterName + " existing.");
				return true;
			}
		}
	  return false; 
	  }	 

}
