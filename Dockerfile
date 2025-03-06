# 1. Usa una imatge oficial de JDK com a base
FROM eclipse-temurin:17-jdk

# 2. Estableix el directori de treball dins del contenidor
WORKDIR /app

# 3. Copia el fitxer JAR generat (Assegura't de tenir aquest nom correcte)
COPY target/S05-0.0.1-SNAPSHOT.jar app.jar

# 4. Defineix el port d'exposició de l'aplicació
EXPOSE 8080

# 5. Comanda per executar l'aplicació
CMD ["java", "-jar", "app.jar"]