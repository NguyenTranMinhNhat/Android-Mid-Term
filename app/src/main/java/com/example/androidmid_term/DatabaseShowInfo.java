package com.example.androidmid_term;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseShowInfo extends SQLiteOpenHelper {


    public DatabaseShowInfo(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public Cursor getData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql,null);
    }

    public String getSongName(String mabh){
        String songName = "";
        String sql = "SELECT TENBH FROM BAIHAT WHERE MABH = '"+mabh+"'";
        Cursor c = getData(sql);
        while (c.moveToNext()){
            songName = c.getString(1);
        }
        return songName;
    }


    public ArrayList getSHowInfo(int maBd)
    {
        ArrayList <ShowInfo> arrShowInfo = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        String sql = "SELECT * FROM THONGTINBIEUDIEN WHERE MABD ="+maBd+"";

        Cursor c = database.rawQuery(sql,null);
        while (c.moveToNext()){
            ShowInfo showInfo = new ShowInfo();
            showInfo.setNgayBD(c.getString(2));
            showInfo.setDiaDiem(c.getString(3));
        }

        return arrShowInfo;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
