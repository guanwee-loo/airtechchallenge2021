# airtechchallenge2021

Use Spring Boot 

## To execute after performing "mvn package"
 java -jar target/AIRTechChallenge-0.0.1-SNAPSHOT.jar --api.key=$ATM_API_KEY 
 
 <img width="960" alt="console" src="https://user-images.githubusercontent.com/6189477/120899476-81b2b780-c662-11eb-8b43-46d334cdc0da.PNG">

Replace $ATM_API_KEY with your API Key

## Retrieve all airports
HTTP GET http://localhost:8080/airports 

## Retrieve all SIDS in airport
HTTP GET http://localhost:8080/sids/airport/{icao}

## Retrieve all STARS in airport
HTTP GET http://localhost:8080/stars/airport/{icao}

