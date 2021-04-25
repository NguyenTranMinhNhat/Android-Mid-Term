package com.example.quanlyamnhac;

public class Song {
    private int namST;
    private String tenBH;
    private String tenCS;
    public Song() {
    }

    public Song(int namST, String tenBH, String tenCS) {
        this.namST = namST;
        this.tenBH = tenBH;
        this.tenCS = tenCS;
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

    public String getTenCS() {
        return tenCS;
    }

    public void setTenCS(String tenCS) {
        this.tenCS = tenCS;
    }
}
