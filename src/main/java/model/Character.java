package model;


//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Character respresents an character object with name, birthyear and movies played in.
 * Character name, birthyear and movies are mutable using setters
 * 
 * @author TN
 *
 *
 */
//@JsonIgnoreProperties(ignoreUnknown = true)
public class Character {

	private String name;	
	private String birth_year;	
	private String[] films;
	private String url;	
	
	/**
	 * Constructs and initialized an character object
	 */
	public Character() {	
	}
		
	/**
	 * Get the name of the character
	 * 
	 * @return the name of the character
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get the birthyear
	 * 
	 * @return birth_year as string
	 */
	public String getBirth_year() {
		return birth_year;
	}

	public void setBirth_year(String birth_year) {
		this.birth_year = birth_year;
	}	
	
	/**
	 * Get all returned movies
	 * 
	 * @return array of strings stored with movie urls
	 */
	public String[] getFilms() {
		return films;
	}

	public void setFilms(String[] films) {
		this.films = films;
	}
	
	/**
	 * Method to convert the string representive of the birthyear, without the BBY, to a floating point value 
	 * @return floating point value
	 */
	public Float getBirth_year_number() {
		
		float ret = 0;
		String retval = "";

		try {
			if ((birth_year.equals(null) == false) && (!birth_year.equals("unknown"))) {

				if (birth_year.contains("BBY")) {
					retval = birth_year.replace("BBY", "").trim();
					ret = Float.parseFloat(retval);
				}
			}
		} 
		catch (Exception e) {
			throw e;
		}
		return ret;
	}

	@Override
	public String toString() {		
		
		String strFilm = "";		
		for (String strTemp : films){
			strFilm += strTemp + ";\n";
			}		
		strFilm.substring(0, strFilm.length()-1);		
		return "{\n" + "Name=" + name + ", \nbirth_year=" + birth_year + ", \nUrl=" + url + ", \nmovies:\n" + strFilm + '}';
	}

}
