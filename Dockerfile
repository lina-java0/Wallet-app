FROM openjdk:17-jdk-slim

WORKDIR /app

COPY build/libs/wallet-app.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]

EXPOSE 8080


