FROM maven:3.9.4-eclipse-temurin-17 as build-result

# copy from current Dockerfile directory to image
COPY src src
COPY pom.xml pom.xml

# run console command in build process
RUN mvn clean package

FROM eclipse-temurin:17-jdk-alpine

# create system user, add his to group
RUN adduser --system spring-boot-user && addgroup --system spring-boot-group && adduser spring-boot-user spring-boot-group

# make current user
USER spring-boot-user

# make current directory for commands
WORKDIR /app

# copy from pervious build result directory to current (/app)
COPY --from=build-result target/*.jar ./application.jar

ENTRYPOINT ["java","-jar","./application.jar"]

# for make image (command must be from same directory when Dockerfile placed):
# docker build -t spring-boot-jwt-example-image:latest .

# for run container:
# docker run -p 8080:8080 -p 9092:9092 --name=spring-boot-jwt-example-container spring-boot-jwt-example-image:latest

# for start bash into started container
# docker exec -it spring-boot-jwt-example-container /bin/bash

# for start shell into started container
# docker exec -it spring-boot-jwt-example-container /bin/sh

# check who is current user in shell inside container
# whoami

# for runtime container logs outside it (-f means runtime)
# https://docs.docker.com/engine/reference/commandline/logs/
# docker logs -f spring-boot-jwt-example-container