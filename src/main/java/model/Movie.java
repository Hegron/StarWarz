package model;

//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Movie represents a Movie object where movies API URLs are stored with the characters played the movies.
 * Movies are mutable using setters.
 * @author TN
 *
 */

//@JsonIgnoreProperties(ignoreUnknown = true)
public class Movie{

	private String characters[];
	
	/**
	 * Constructs an initializes a Movie object.
	 */
	public Movie() {
		
	}
	
	/**
	 * Get a list of API URL of characters
	 * @return list API URL of characters
	 */
	public String[] getCharacters() {
		return characters;
	}
	public void setCharacters(String characters[]) {
		this.characters = characters;
	}
	
	@Override
	public String toString() {		
		
		String strCharacter = "";		
		for (String strChar : characters){
			strCharacter += strChar + ";\n";
			}		
		strCharacter.substring(0, strCharacter.length()-1);		
		return "{\n" + strCharacter + "}";
	}
	
}
