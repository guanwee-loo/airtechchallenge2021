FROM openjdk:8-jre-alpine3.9
# copy the packaged jar file into our docker image
COPY target/AIRTechChallenge-0.0.1-SNAPSHOT.jar /air.jar
ENV ATM_API_KEY ${ATM_API_KEY} 
# set the startup command to execute the jar
CMD ["java", "-jar", "/air.jar", "--api.key=${ATM_API_KEY}"]
