FROM openjdk:17-jdk-slim
COPY target/meal-0.0.1-SNAPSHOT.jar /app.jar
ENV APP_HOME = /app
WORKDIR ${APP_HOME}
EXPOSE 8080
LABEL authors="Harsh"

ENTRYPOINT ["java", "-jar", "/app.jar"]