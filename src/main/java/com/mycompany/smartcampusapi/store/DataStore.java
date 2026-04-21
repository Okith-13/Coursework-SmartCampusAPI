package com.mycompany.smartcampusapi.store;

import com.mycompany.smartcampusapi.model.Room;
import com.mycompany.smartcampusapi.model.Sensor;
import com.mycompany.smartcampusapi.model.SensorReading;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DataStore {

    private static final DataStore INSTANCE = new DataStore();

    private final Map<String, Room> rooms = new ConcurrentHashMap<>();
    private final Map<String, Sensor> sensors = new ConcurrentHashMap<>();
    private final Map<String, List<SensorReading>> readingsBySensor = new ConcurrentHashMap<>();

    private DataStore() {
        Room defaultRoom = new Room("LIB-301", "Library Quiet Study", 50);
        rooms.put(defaultRoom.getId(), defaultRoom);
    }

    public static DataStore getInstance() {
        return INSTANCE;
    }

    public Map<String, Room> getRooms() {
        return rooms;
    }

    public Map<String, Sensor> getSensors() {
        return sensors;
    }

    public Map<String, List<SensorReading>> getReadingsBySensor() {
        return readingsBySensor;
    }

    public List<SensorReading> getReadingsForSensor(String sensorId) {
        return readingsBySensor.computeIfAbsent(sensorId, key -> new ArrayList<>());
    }
}