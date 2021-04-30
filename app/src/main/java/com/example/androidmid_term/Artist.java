package com.example.androidmid_term;

public class Artist {
    private int id;
    private String name;
    private byte[] img;

    public Artist(String name, byte[] img) {
        this.name = name;
        this.img = img;
    }

    public Artist() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }
}
