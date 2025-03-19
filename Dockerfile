# Используем официальный образ для Java
FROM openjdk:17-jdk-slim

# Устанавливаем рабочую директорию внутри контейнера
WORKDIR /app

# Копируем .jar файл в контейнер
COPY build/libs/*.jar app.jar

# Запускаем Spring Boot приложение
ENTRYPOINT ["java", "-jar", "app.jar"]

# Открываем порт, чтобы приложение было доступно из контейнера
EXPOSE 8080


