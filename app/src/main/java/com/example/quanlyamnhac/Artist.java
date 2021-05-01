package com.example.quanlyamnhac;

public class Artist {
    private byte[] avatar;
    private String name;

    public Artist() {
    }

    public Artist(byte[] avatar, String name) {
        this.avatar = avatar;
        this.name = name;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
