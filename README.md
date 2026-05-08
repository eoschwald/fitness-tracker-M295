# Fitness Tracker API

Backend-Applikation zur Verwaltung von Fitnessaktivitäten mit Spring Boot, REST, PostgreSQL, JPA, Keycloak und Swagger/OpenAPI.

## Funktionen

- Benutzerverwaltung
- Workouts verwalten
- Exercises verwalten
- Exercises einem Workout zuordnen und entfernen
- REST-API mit JSON
- OAuth2/JWT-Schutz über Keycloak
- Rollenmodell: `READ` und `UPDATE`
- Swagger UI zum Testen der Endpunkte

## Architektur

- `entity/` – JPA-Entitäten: `User`, `Workout`, `Exercise`
- `repository/` – Spring Data JPA Repositories
- `service/` – Business-Logik
- `controller/` – REST-Endpunkte
- `dto/` – Request-/Response-Objekte
- `mapper/` – Umwandlung zwischen Entity und DTO
- `config/` – Security und OpenAPI
- `exception/` – zentrale Fehlerbehandlung

## Ports
- **Spring Boot Backend:** `8081`
- **Keycloak:** `8080`
- **PostgreSQL:** `5432`
- **Swagger UI:** `http://localhost:8081/swagger-ui.html`
  
## Voraussetzungen

- Java 21
- Maven Wrapper (`./mvnw`)
- PostgreSQL
- Keycloak

## Lokales Setup

### 1. PostgreSQL Datenbank anlegen

In pgAdmin oder per SQL eine Datenbank mit dem Namen `fitness_tracker` anlegen.

### 2. `application.yaml` prüfen

Die Datei `src/main/resources/application.yaml` muss zu deiner lokalen Umgebung passen:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/fitness_tracker
    username: <dein-db-user>
    password: <dein-db-passwort>
```

### 3. Keycloak starten

Keycloak muss laufen, bevor die Anwendung gestartet wird, da der Resource Server die OpenID-Konfiguration vom Issuer lädt.

Wichtige Werte:

- Realm: `fitness-tracker`
- Issuer: `http://localhost:8080/realms/fitness-tracker`
- Client: `fitness-tracker-client`

### 4. Anwendung starten

```bash
./mvnw spring-boot:run
```

### 5. Swagger öffnen

```text
http://localhost:8081/swagger-ui.html
```

## Authentifizierung

Die API ist als OAuth2 Resource Server konfiguriert. Requests an `/api/**` benötigen ein gültiges Bearer Token.

Für Tests und Demo können z. B. diese Benutzer genutzt werden:

- `reader` mit Rolle `READ`
- `editor` mit Rollen `READ` und `UPDATE`

## Wichtige Endpunkte

### Workouts
- `GET /api/workouts`
- `GET /api/workouts/{id}`
- `POST /api/workouts`
- `PUT /api/workouts/{id}`
- `DELETE /api/workouts/{id}`

### Exercises
- `GET /api/exercises`
- `GET /api/exercises/{id}`
- `PUT /api/exercises/{id}`
- `DELETE /api/exercises/{id}`

### Workout-Exercise-Beziehung
- `GET /api/workouts/{workoutId}/exercises`
- `POST /api/workouts/{workoutId}/exercises`
- `DELETE /api/workouts/{workoutId}/exercises/{exerciseId}`

### Users
- `GET /api/users`
- `GET /api/users/{id}`
- `POST /api/users`
- `PUT /api/users/{id}`
- `DELETE /api/users/{id}`

## HTTP Status Codes

- `200 OK` – Anfrage erfolgreich
- `201 Created` – Ressource erstellt
- `204 No Content` – Ressource gelöscht
- `400 Bad Request` – Validierung fehlgeschlagen
- `401 Unauthorized` – Token fehlt oder ist ungültig/abgelaufen
- `403 Forbidden` – Token gültig, aber Rolle fehlt
- `404 Not Found` – Ressource nicht gefunden
- `500 Internal Server Error` – unbehandelter Serverfehler

## Tests

### Ausführen

```bash
./mvnw test
```

### Enthaltene Tests

- `WorkoutControllerTest` – MockMvc-Test für CRUD-Requests am Controller
- `WorkoutRepositoryTest` – JPA-Test für Save, Find und Delete

