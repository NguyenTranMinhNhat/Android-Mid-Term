package com.example.androidmid_term;

public class Song {
    private int id;
    private String name;
    private String year;
    private int id_artist;
    private String has_sound;
    private String artist_name;

    public Song(String name, String year, int id_artist, String has_sound) {
        this.name = name;
        this.year = year;
        this.id_artist = id_artist;
        this.has_sound = has_sound;
    }

    public Song() {
    }

    public String getArtist_name() {
        return artist_name;
    }

    public void setArtist_name(String artist_name) {
        this.artist_name = artist_name;
    }

    public String getHas_sound() {
        return has_sound;
    }

    public void setHas_sound(String has_sound) {
        this.has_sound = has_sound;
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