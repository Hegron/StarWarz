package model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Actor {

	private String name;	
	private String birth_year;	
	private String[] films;
	//private String count;
	//private List<Actor> results;
	
	public Actor() {	
	}
		
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getBirth_year() {
		return birth_year;
	}

	public void setBirth_year(String birth_year) {
		this.birth_year = birth_year;
	}	
	

	public String[] getFilms() {
		return films;
	}

	public void setFilms(String[] films) {
		this.films = films;
	}



	@Override
	public String toString() {		
		
		String strFilm = "";
		
		for (String strTemp : films){

			strFilm += strTemp + ";";

			}
		
		strFilm.substring(0, strFilm.length()-1);
		
		return "{" + "Name=" + name + ", birth_year=" + birth_year + ", films:" + strFilm + '}';
		
		
	}


}
