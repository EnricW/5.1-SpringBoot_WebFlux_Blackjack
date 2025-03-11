# Etapa de compilació
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copia els fitxers del projecte
COPY pom.xml .
COPY src ./src

# Compila l'aplicació
RUN mvn clean package -DskipTests

# Etapa d'execució
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]