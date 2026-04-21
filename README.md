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
