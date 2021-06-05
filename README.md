# airtechchallenge2021

## To execute after performing "mvn package"
 java -jar target/AIRTechChallenge-0.0.1-SNAPSHOT.jar --api.key=$ATM_API_KEY 

Replace $ATM_API_KEY with your API Key

## Retrieve all airports
HTTP GET http://localhost:8080/airports 

## Retrieve all SIDS in airport
HTTP GET http://localhost:8080/sids/airport/{icao}

## Retrieve all STARS in airport
HTTP GET http://localhost:8080/stars/airport/{icao}

