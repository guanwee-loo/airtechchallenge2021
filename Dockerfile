FROM maven:3.6.1-jdk-8-slim AS build
RUN mkdir -p workspace
WORKDIR workspace
COPY pom.xml /workspace
COPY src /workspace/src
COPY frontend /workspace/frontend
RUN mvn -f pom.xml clean install -DskipTests=true

FROM openjdk:8-jre-alpine3.9
# copy the packaged jar file into our docker image
COPY --from=build /workspace/target/*.jar air.jar
ENV ATM_API_KEY ${ATM_API_KEY} 
# set the startup command to execute the jar
CMD ["java", "-jar", "air.jar", "--api.key=${ATM_API_KEY}"]
