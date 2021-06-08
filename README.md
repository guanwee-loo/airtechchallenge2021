# airtechchallenge2021

Use Spring Boot 

## To execute after performing "mvn package"
 java -jar target/AIRTechChallenge-0.0.1-SNAPSHOT.jar --api.key=$ATM_API_KEY 
 
 <img width="960" alt="console" src="https://user-images.githubusercontent.com/6189477/120899476-81b2b780-c662-11eb-8b43-46d334cdc0da.PNG">

Replace $ATM_API_KEY with your API Key or ATM_API_KEY is your environment variable. 

## Retrieve all airports
HTTP GET http://localhost:8080/airports 

## Retrieve all SIDS in airport
HTTP GET http://localhost:8080/sids/airport/{icao}

## Retrieve all STARS in airport
HTTP GET http://localhost:8080/stars/airport/{icao}

## Retrieval top N waypoints of all SIDS in airport
HTTP GET http://localhost:8080/sids/airport/{icao}/topWaypoints/{N}

<img width="534" alt="topwp_sids" src="https://user-images.githubusercontent.com/6189477/121049476-4ba03f80-c7ea-11eb-8d12-c7327c57cc04.PNG">

## Retrieval top N waypoints of all STARS in airport
HTTP GET http://localhost:8080/stars/airport/{icao}/topWaypoints/{N}

<img width="511" alt="topwp_stars" src="https://user-images.githubusercontent.com/6189477/121049504-52c74d80-c7ea-11eb-810a-d6a03ce9b1aa.PNG">

## Building the Docker image and pushing to Docker Hub
docker build -t loogw/airtechchallenge2021:0.0.1-SNAPSHOT .

docker push loogw/airtechchallenge2021:0.0.1-SNAPSHOT
<img width="960" alt="h" src="https://user-images.githubusercontent.com/6189477/121223470-24b13e80-c8ba-11eb-9a4c-840ba04134fc.PNG">


## Running the container
docker run -e ATM_API_KEY=$ATM_API_KEY -p 8080:8080 loogw/airtechchallenge2021:0.0.1-SNAPSHOT

Replace $ATM_API_KEY with your API token or set it in the environment variable
