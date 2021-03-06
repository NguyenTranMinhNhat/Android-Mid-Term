package com.example.androidmid_term;

public class Song {
    private int id;
    private String name;
    private String year;
    private int id_artist;

    public Song(String name, String year, int id_artist) {
        this.name = name;
        this.year = year;
        this.id_artist = id_artist;
    }

    public Song() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getId_artist() {
        return id_artist;
    }

    public void setId_artist(int id_artist) {
        this.id_artist = id_artist;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}