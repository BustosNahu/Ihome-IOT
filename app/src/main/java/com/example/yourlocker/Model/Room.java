package com.example.yourlocker.Model;

public class Room {

    private String room, id;

    public Room(String room, String id) {
        this.room = room;
        this.id = id;

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

