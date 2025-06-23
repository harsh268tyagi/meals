FROM openjdk:17-jdk-slim
COPY target/meal-v1.jar /app.jar
EXPOSE 8080
LABEL authors="Harsh"
ENTRYPOINT ["java", "-jar", "/app.jar"]