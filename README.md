# SWAPI API project

# Table of Contents
* General info
* Technologies
* Tools
* External API
* How to use


# General info
This project is part of an assessment to test Java development skills. The assessment is to create an endpoint with Spring boot project that uses the swapi API, to retrieve
starwars characters that played in the same movies as the searched character and show them in a list with their corresponding birthyear in an ascending order.


# Technologies
* Java 8
* Spring boot REST API using model and controller
* RestTemplate
* JUnit 5


# Tools
* Eclipse IDE
* Postman


# External Api
https://swapi.dev/


 # How to use
 
 First you need Spring tool suit plugin for your IDE.
 
 Download the project from Github and import into your IDE. In the IDE, run the StarWarsApplication.java file as "Spingboot Application"
 
 The program is hosted on localhost:8080 with tomcat server (internally). In postman or Browser go to: Search for starwars character: localhost:8080/{name of character}
            
 Input : Name of star wars character (case insensitive)
 Output: Show all star wars characters in same movie as the input character and order them by birthyear ascending.
