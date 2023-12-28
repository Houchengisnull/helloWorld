# syntax=docker/dockerfile:1
FROM openjdk:8
WORKDIR /hc-docker
COPY ["hc-docker-0.0.1-SNAPSHOT.jar", "hc-docker.jar"]
ENTRYPOINT ["java", "-jar", "hc-docker.jar"]
EXPOSE 8080