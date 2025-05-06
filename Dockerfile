# Используем официальный образ OpenJDK в качестве базового
FROM openjdk:17-jdk-slim

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем файл pom.xml и mvnw
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn
COPY src ./src

# Делаем mvnw исполняемым
RUN chmod +x mvnw

# Загружаем зависимости
RUN ./mvnw dependency:go-offline

# Собираем проект
RUN ./mvnw clean package -DskipTests

# Указываем команду для запуска приложения
CMD ["java", "-jar", "target/CardManagmentSystem-0.0.1-SNAPSHOT.jar"]