package com.example.yourlocker.Model;

import java.util.HashMap;

public class Room {

    private String room = "";
    private String id = "";
    private String type = "";

    public HashMap<String,Device> devices = new HashMap<String, Device>();

    public Room(String room, String id, String type, HashMap<String, Device> devices) {
        this.room = room;
        this.id = id;
        this.type = type;
        this.devices = devices;

    }
    public Room(){}

    public HashMap<String, Device> getDevices() {
        return devices;
    }

    public void setDevices(HashMap<String, Device> devices) {
        this.devices = devices;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }


    }

