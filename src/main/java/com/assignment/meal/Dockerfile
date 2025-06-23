FROM openjdk:17-jdk-slim
COPY target/meal-v1.jar /app.jar
ENV JAVA_OPTS="-Djasypt.encryptor.password=Harsh_PS"
EXPOSE 8080
LABEL authors="Harsh"
ENTRYPOINT sh -c "java $JAVA_OPTS -jar /app.jar"