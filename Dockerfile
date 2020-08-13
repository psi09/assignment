FROM openjdk:8-jre-alpine
MAINTAINER psi09@hotmail.com
ARG APP_PATH=/usr/src/app
WORKDIR ${APP_PATH}
COPY ./target/test-rest-service-*.jar app.jar
ENV SPRING_PROFILES_ACTIVE=local
EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}","./app.jar"]
