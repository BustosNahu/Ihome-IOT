package com.example.yourlocker.Model;

public class Room {

    private String room, id, type;

    public Room(String room, String id, String type) {
        this.room = room;
        this.id = id;
        this.type = type;

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

