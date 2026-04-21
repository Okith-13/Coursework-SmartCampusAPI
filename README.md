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
---

## 13. Coursework Question Answers

### Part 1 – Service Architecture & Setup

#### 1. Resource Lifecycle
By default, JAX-RS resource classes are usually request scoped, which means a new resource instance is created for each incoming HTTP request. This is useful because it avoids unwanted sharing of instance fields between requests. However, in this coursework, the data must remain available across different requests, so storing data inside normal resource object fields would be unsafe because that data could be lost when a new resource instance is created. For this reason, I used a shared in memory store class to hold the application data. To reduce concurrency risks when multiple clients access the API at the same time, I used thread-safe structures such as `ConcurrentHashMap` for the main collections. This approach helps preserve data between requests while lowering the chance of race conditions.

#### 2. Hypermedia / HATEOAS
Hypermedia is considered an advanced RESTful design principle because it allows the server to guide clients through the available resources using links inside the response itself. Instead of relying only on external documentation, the client can discover what endpoints exist and how to move through the API by following those links. This makes the API more self descriptive and easier to explore. It benefits client developers because the server can expose the current structure of the API directly, which reduces guesswork and makes the system easier to use, especially when APIs evolve over time. In my coursework, the discovery endpoint provides version information, contact details and links to the main resource collections, which is a simple form of hypermedia support.

### Part 2 – Room Management

#### 3. Returning IDs vs Full Room Objects
Returning only room IDs reduces the response size, so it saves bandwidth and can be more efficient if the client only needs identifiers. However, it also means the client may need to make additional requests to fetch more details about each room. Returning the full room objects increases the size of the response, but it is more convenient because the client immediately receives useful information such as room name, capacity and sensor IDs. This can reduce the number of follow up requests and simplify client side logic. In my API, I returned full room objects because it provides a more practical response for management tasks and makes the system easier to test and demonstrate.

#### 4. Is DELETE idempotent?
DELETE is intended to be idempotent because repeating the same request should leave the server in the same final state. In my implementation, if a room exists and has no assigned sensors, the first DELETE request removes it successfully. If the same DELETE request is sent again, the room no longer exists, so the server returns a not found error. Even though the second response is different, the resource state is unchanged after the first successful deletion, which still fits the idea of idempotency. If the room contains sensors, every DELETE attempt will fail with the same conflict response until the sensors are removed, so the state also remains unchanged in that case.

### Part 3 – Sensor Operations & Linking

#### 5. Effect of `@Consumes(MediaType.APPLICATION_JSON)`
The `@Consumes(MediaType.APPLICATION_JSON)` annotation tells JAX-RS that the endpoint only accepts request bodies in JSON format. If a client sends data with a different content type, such as `text/plain` or `application/xml`, JAX-RS will not match the request to that method correctly because the media type is unsupported. In practice, this usually results in an HTTP `415 Unsupported Media Type` response. This behaviour is useful because it enforces a clear contract between the client and server, ensuring that the request body is sent in the format the API expects and can safely parse.

#### 6. Why use `@QueryParam` for filtering?
Using `@QueryParam` is generally better for filtering because it expresses that the client is searching within a collection rather than requesting a completely separate resource. The resource is still the sensors collection, but the query parameter narrows the results. For example, `/api/v1/sensors?type=CO2` clearly means “give me sensors, filtered by type.” This is more flexible because additional filters can easily be added later, such as status or roomId, without changing the resource structure. A path such as `/api/v1/sensors/type/CO2` is less flexible and can make filtering look like a completely different nested resource instead of a filtered view of the same collection.

### Part 4 – Deep Nesting with Sub-Resources

#### 7. Benefits of the Sub-Resource Locator Pattern
The Sub-Resource Locator pattern improves organisation by delegating nested resource logic to a dedicated class. Instead of placing all endpoints for sensors and readings inside one large resource class, the sensor resource can pass control to a separate sensor reading resource when the path reaches `/{sensorId}/readings`. This makes the code easier to read, test and maintain. Each class has a clearer responsibility, which reduces complexity and avoids creating one very large controller with too many unrelated methods. In larger APIs, this separation is especially valuable because it keeps nested resource behaviour modular and easier to extend.

### Part 5 – Advanced Error Handling, Exception Mapping & Logging

#### 8. Why `422 Unprocessable Entity` is more accurate than `404`
HTTP `422 Unprocessable Entity` is more accurate here because the request itself is syntactically correct and the endpoint exists, but the data inside the request cannot be processed due to a semantic problem. In this coursework, a client may send valid JSON to create a sensor, but the `roomId` inside that JSON may refer to a room that does not exist. That is not really the same as a normal `404 Not Found`, because the missing element is not the endpoint itself. Instead, the problem is that the submitted representation contains an invalid reference. For this reason, `422` communicates the issue more precisely.

#### 9. Cybersecurity risks of exposing stack traces
Exposing raw Java stack traces is dangerous because it reveals internal details about how the application is built. An attacker could learn class names, package names, framework details, file paths, library versions, method names and even hints about the server configuration. This information can help an attacker understand the internal structure of the system and identify weak points or known vulnerabilities in specific libraries. It also makes reconnaissance easier, which increases the chance of targeted attacks. For this reason, my API uses custom exception mappers to return clean JSON error messages instead of raw stack traces.

#### 10. Why use JAX-RS filters for logging?
JAX-RS filters are better for cross cutting concerns such as logging because they centralize the behavior in one place instead of repeating the same logging code inside every resource method. This reduces duplication, makes the resource classes cleaner and ensures that logging is applied consistently to all requests and responses. It also improves maintainability because any future changes to logging behavior can be made in a single filter class rather than across many endpoints. In my project, the logging filter records the request method and URI for incoming requests and the final status code for outgoing responses.

---
