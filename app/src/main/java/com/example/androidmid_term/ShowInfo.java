package com.example.androidmid_term;

public class ShowInfo {
    public int MaBD;
    public int MaCS;
    public int MaBH;
    public String ngayBD;
    public String diaDiem;

    public ShowInfo() {
    }

    public ShowInfo(int maBD, int maCS, int maBH, String ngayBD, String diaDiem) {
        MaBD = maBD;
        MaCS = maCS;
        MaBH = maBH;
        this.ngayBD = ngayBD;
        this.diaDiem = diaDiem;
    }

    public int getMaBD() {
        return MaBD;
    }

    public void setMaBD(int maBD) {
        MaBD = maBD;
    }

    public int getMaCS() {
        return MaCS;
    }

    public void setMaCS(int maCS) {
        MaCS = maCS;
    }

    public int getMaBH() {
        return MaBH;
    }

    public void setMaBH(int maBH) {
        MaBH = maBH;
    }

    public String getNgayBD() {
        return ngayBD;
    }

    public void setNgayBD(String ngayBD) {
        this.ngayBD = ngayBD;
    }

    public String getDiaDiem() {
        return diaDiem;
    }

    public void setDiaDiem(String diaDiem) {
        this.diaDiem = diaDiem;
    }
}
