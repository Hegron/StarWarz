package model;

//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Results represents a Results object where one or more character objects are stored,
 * the Results object is mapped to the results part of the returned JSON. A count to indicate if a characters exists in the external API.
 * Results are mutable with using setters
 * 
 * @author TN
 *
 */

//@JsonIgnoreProperties(ignoreUnknown = true)
public class Results {
	
<<<<<<< HEAD
	private Character[] results;
	private String count;
=======
	private Actor[] results;
>>>>>>> parent of 3bc3e8d (almost ready)
	
	/**
	 * Constructs an initializes a Results object.
	 */
	public Results() {
		
	}

	/**
	 * Returns an array of Character objects
	 */
	public Character[] getResults() {
		return results;
	}

	public void setResults(Character[] results) {
		this.results = results;
	}

<<<<<<< HEAD
	
	/**
	 * Get the count returns from the API
	 * @return Returns the found number of characters as a string representative
	 */
	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

=======
>>>>>>> parent of 3bc3e8d (almost ready)
	

	
}
