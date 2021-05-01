package com.example.quanlyamnhac;

public class Song {
    private int namST;
    private String tenBH;
    public Song() {
    }

    public Song(int namST, String tenBH) {
        this.namST = namST;
        this.tenBH = tenBH;
    }

    public int getNamST() {
        return namST;
    }

    public void setNamST(int namST) {
        this.namST = namST;
    }

    public String getTenBH() {
        return tenBH;
    }

    public void setTenBH(String tenBH) {
        this.tenBH = tenBH;
    }
}
