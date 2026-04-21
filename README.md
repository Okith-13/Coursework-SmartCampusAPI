# Smart Campus API

## Module
5COSC022W – Client-Server Architectures

## Coursework Title
Smart Campus Sensor & Room Management API

## Student
Okith Hettihewa - w2120560

---

## 1. Overview

This project is a RESTful API developed in Java using JAX-RS (Jersey) and an embedded Grizzly HTTP server. It was built as part of the Client-Server Architectures coursework for the Smart Campus scenario.

The API allows campus facilities managers or automated systems to manage:

- Rooms
- Sensors assigned to rooms
- Historical sensor readings

The application uses in-memory data structures only and does not use any database, in line with the coursework requirements.

---

## 2. API Design Summary

The API follows a resource-based REST design.

### Main resources
- `Room`
- `Sensor`
- `SensorReading`

### Main endpoint groups
- `/api/v1/`
- `/api/v1/rooms`
- `/api/v1/sensors`
- `/api/v1/sensors/{sensorId}/readings`

### Key features
- Discovery endpoint for API metadata
- Create and retrieve rooms
- Create and retrieve sensors
- Filter sensors by type using query parameters
- Add and retrieve historical sensor readings
- Prevent room deletion if sensors are still assigned
- Prevent creation of sensors for rooms that do not exist
- Prevent new readings for sensors in maintenance or offline state
- Custom exception mapping with JSON error responses
- Request and response logging using JAX-RS filters

---

## 3. Technology Stack

- Java 21
- Maven
- JAX-RS (Jersey)
- Grizzly HTTP Server
- JSON with Jackson
- NetBeans IDE 24

---

## 4. Project Structure

```text
src/main/java/com/mycompany/smartcampusapi
├── Main.java
├── config
│   └── ApplicationConfig.java
├── model
│   ├── Room.java
│   ├── Sensor.java
│   ├── SensorReading.java
│   └── ApiError.java
├── store
│   └── DataStore.java
├── resource
│   ├── DiscoveryResource.java
│   ├── RoomResource.java
│   ├── SensorResource.java
│   └── SensorReadingResource.java
├── exception
│   ├── RoomNotEmptyException.java
│   ├── LinkedResourceNotFoundException.java
│   └── SensorUnavailableException.java
├── mapper
│   ├── RoomNotEmptyExceptionMapper.java
│   ├── LinkedResourceNotFoundExceptionMapper.java
│   ├── SensorUnavailableExceptionMapper.java
│   └── GlobalExceptionMapper.java
└── filter
    └── LoggingFilter.java
---
```
---

## 5. How to Build and Run the Project

### In NetBeans
1. Open the Maven project in NetBeans.
2. Wait for Maven dependencies to load.
3. Right-click the project and choose **Clean and Build**.
4. Right-click the project and choose **Run**.
5. The server should start on:

```text
http://localhost:8080/api/v1/
```
---

## 6. Example API Endpoints

### Discovery
- GET /api/v1/
### Rooms
- GET /api/v1/rooms
- POST /api/v1/rooms
- GET /api/v1/rooms/{roomId}
- DELETE /api/v1/rooms/{roomId}
### Sensors
- GET /api/v1/sensors
- GET /api/v1/sensors?type=CO2
- POST /api/v1/sensors
- GET /api/v1/sensors/{sensorId}
### Sensor Reading
- GET /api/v1/sensors/{sensorId}/readings
- POST /api/v1/sensors/{sensorId}/readings

---

## 7. Sample Curl Commands

1. Get API discovery information
- curl http://localhost:8080/api/v1/

2. Get all rooms
- curl http://localhost:8080/api/v1/rooms

3. Create a new room
- curl -X POST http://localhost:8080/api/v1/rooms -H "Content-Type: application/json" -d "{\"id\":\"ENG-101\",\"name\":\"Engineering Lab 1\",\"capacity\":40}"

4. Get a room by ID
- curl http://localhost:8080/api/v1/rooms/ENG-101

5. Create a new sensor
- curl -X POST http://localhost:8080/api/v1/sensors -H "Content-Type: application/json" -d "{\"id\":\"CO2-001\",\"type\":\"CO2\",\"status\":\"ACTIVE\",\"currentValue\":400.0,\"roomId\":\"ENG-101\"}"

6. Get all sensors
- curl http://localhost:8080/api/v1/sensors

7. Filter sensors by type
- curl http://localhost:8080/api/v1/sensors?type=CO2

8. Add a reading to a sensor
- curl -X POST http://localhost:8080/api/v1/sensors/CO2-001/readings -H "Content-Type: application/json" -d "{\"value\":425.5}"

9. Get sensor reading history
- curl http://localhost:8080/api/v1/sensors/CO2-001/readings

10. Attempt to delete a room that still contains sensors
- curl -X DELETE http://localhost:8080/api/v1/rooms/ENG-101

11. Attempt to create a sensor with a non-existent room
- curl -X POST http://localhost:8080/api/v1/sensors -H "Content-Type: application/json" -d "{\"id\":\"TEMP-999\",\"type\":\"Temperature\",\"status\":\"ACTIVE\",\"currentValue\":22.5,\"roomId\":\"NO-ROOM\"}"

12. Attempt to add a reading to a maintenance sensor
- curl -X POST http://localhost:8080/api/v1/sensors/TEMP-002/readings -H "Content-Type: application/json" -d "{\"value\":21.0}"

---

## 8. Example JSON Payloads

### Room
```json
{
  "id": "ENG-101",
  "name": "Engineering Lab 1",
  "capacity": 40
}
```
### Sensor
```
{
  "id": "CO2-001",
  "type": "CO2",
  "status": "ACTIVE",
  "currentValue": 400.0,
  "roomId": "ENG-101"
}
```
### Sensor Reading
```
{
  "value": 425.5
}
```
---

## 9. Error Handling

The API returns structured JSON error responses instead of raw Java stack traces.

### Implemented error cases
- `409 Conflict` when attempting to delete a room that still has sensors assigned
- `422 Unprocessable Entity` when creating a sensor linked to a room that does not exist
- `403 Forbidden` when trying to post a reading to a sensor in `MAINTENANCE` or `OFFLINE`
- `500 Internal Server Error` as a global fallback for unexpected exceptions

### Example error response
```json
{
  "status": 409,
  "error": "Conflict",
  "message": "Cannot delete room because sensors are still assigned to it"
}
```
---

## 10. Logging

The API uses JAX-RS request and response filters to log:
- incoming HTTP method and request URI
- outgoing HTTP status code

This helps with API observability and keeps logging separate from the resource classes.
---

## 11. Testing Summary

The API was tested successfully using Postman and Command Prompt with curl.

### Successfully tested functionality
- Discovery endpoint
- Room creation
- Room retrieval by ID
- Room listing
- Sensor creation
- Sensor listing
- Sensor filtering by type
- Reading creation
- Reading history retrieval
- Sensor `currentValue` update after posting a reading
- Protected room deletion with `409 Conflict`
- Invalid room reference with `422 Unprocessable Entity`
- Maintenance sensor restriction with `403 Forbidden`

---

## 12. Notes

This project was implemented using JAX-RS only and uses in-memory Java data structures instead of a database, following the coursework restrictions.

The GitHub repository includes:
- an overview of the API
- build and run instructions
- sample curl commands for testing the endpoints
