FROM eclipse-temurin:17

ARG JAR_FILE

WORKDIR /app

COPY ${JAR_FILE} app.jar

EXPOSE 8080

CMD ["java", "-Dspring.profiles.active=docker", "-jar", "app.jar"]