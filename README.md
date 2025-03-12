# Reactive Blackjack API with Spring WebFlux 🃏

## Description

This project implements a reactive Blackjack game API using Spring WebFlux. The application connects to and manages information in two different databases: MongoDB and MySQL. The Blackjack game includes all necessary functionalities like player management, card hands, and game rules.

## Technical Requirements

- Java JDK 17 or higher
- Maven
- Spring Boot (latest stable version)
- MongoDB
- MySQL
- IDE (IntelliJ IDEA, Eclipse, VS Code, etc.)
- Postman or similar for API testing
- Docker

## Configuration

### Dependencies

- Spring WebFlux
- Spring Data Reactive MongoDB
- Spring Data JPA
- MySQL Connector
- Reactor Test
- JUnit
- Mockito
- Swagger/OpenAPI for documentation

## Project Structure

The project is structured following a reactive architecture with the following packages:

```
cat.itacademy.blackjack.controller    → Contains reactive REST controllers
cat.itacademy.blackjack.model         → Contains entity classes
cat.itacademy.blackjack.service       → Contains reactive business logic
cat.itacademy.blackjack.repository    → Contains reactive repositories
cat.itacademy.blackjack.exception     → Contains global exception handling
cat.itacademy.blackjack.config        → Configuration classes
cat.itacademy.blackjack.dto           → Data Transfer Objects
```

## Database Configuration

The application is configured to use two different database schemas:

- **MongoDB**: For storing game states and real-time game data
- **MySQL**: For storing player information and game statistics

## API Endpoints

### 1. Create New Game

```http
POST /games
```

**Body (JSON):**

```json
{
  "playerName": "PlayerName"
}
```

**Returns:** Details of the created game with status code **201 Created**.

---

### 2. Get Game Details

```http
GET /games/{id}
```

**Parameter:** `id` of the game**Returns:** Detailed information about the game.

---

### 3. Get all Games

```http
GET /games
```

**Returns:** Detailed information about the games.

---

### 4. Make a Move

```http
POST /games/{id}/moves
```

**Parameter:** `id` of the game**Body (JSON):**

```json
{
  "moveType": "HIT"
}
```

**Returns:** Result of the move and current state of the game.

---

### 5. Delete Game

```http
DELETE /games/{id}
```

**Parameter:** `id` of the game**Returns:** Status code **204 No Content** if the game is successfully deleted.

---

### 6. Get Player Rankings

```http
GET /ranking
```

**Returns:** List of players ordered by their ranking position and score.

---

### 7. Change Player Name

```http
PUT /player/{playerId}
```

**Parameter:** `playerId` of the player**Body (JSON):**

```json
{
  "name": "NewPlayerName"
}
```

**Returns:** Updated player information.

---

## Error Handling

The application implements a `GlobalExceptionHandler` to handle exceptions across the entire application:

- If a **game or player ID** does not exist, it returns **404 Not Found**.
- If the request is incorrect, it returns **400 Bad Request**.
- If an **invalid move** is attempted, it returns a specific error message.
- Internal server errors are handled with **500 Internal Server Error**.

---

## Testing

The application includes unit tests for controllers and services using **JUnit and Mockito**:

- **Controller tests** to verify API endpoints work correctly.
- **Service tests** to ensure business logic functions as expected.

---

## API Documentation

The API is documented using **Swagger**. Once the application is running, you can access the Swagger UI at:

[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---

## Docker Setup

### Step 1: Create `Dockerfile`

Create a `Dockerfile` in the root directory:

```dockerfile
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
```

---

### Step 2: Create `.dockerignore`

Create a `.dockerignore` file:

```
target/*
!target/*.jar
.git
.gitignore
.idea
*.iml
```

---

### Step 3: Build the Docker Image

```bash
docker build -t blackjack-api .
```

---

### Step 4: Run the Docker Image

```bash
docker run -p 8080:8080 blackjack-api
```

---

### Step 5: Tag the Docker Image

```bash
docker tag blackjack-api username/blackjack-api:latest
```

---

### Step 6: Login to Docker Hub

```bash
docker login
```

---

### Step 7: Push the Image to Docker Hub

```bash
docker push username/blackjack-api:latest
```

---

### Step 8: Test the Docker Image

Pull and run the image to verify it works correctly:

```bash
docker pull username/blackjack-api:latest
docker run -p 8080:8080 username/blackjack-api:latest
```

---

## Running the Application

1. **Clone the repository**
2. **Configure the MongoDB and MySQL connection details** in `application.properties`
3. **Build the project**
   ```bash
   mvn clean install
   ```
4. **Run the application**
   ```bash
   mvn spring-boot:run
   ```
5. **The API will be available at:**   [http://localhost:8080](http://localhost:8080)

