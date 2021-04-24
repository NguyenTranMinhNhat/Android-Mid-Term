package com.example.androidmid_term;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Database_Artist extends SQLiteOpenHelper {
    public Database_Artist(@Nullable Context context) {
        super(context, "MidTerm.db",null,1);
    }

    //Truy an khong tra ket qua: CREATE,UPDATE...
    public void QuerryData(String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    //Truy van tra ket qua SELECT...
    public Cursor getData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql,null);
    }

    public void Insert_Artist_Img(Artist art){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO CASI VALUES(null,?,?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1,art.getName());
        statement.bindBlob(2,art.getImg());

        statement.executeInsert();
    }

    public void update_artist(Artist art,int id){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "UPDATE CASI SET TENCS = ? , IMG = ? WHERE MACS = "+id+"";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1,art.getName());
        statement.bindBlob(2,art.getImg());

        statement.execute();
    }
    public int get_last_id(){
        SQLiteDatabase database = getReadableDatabase();
        String sql ="SELECT MACS FROM CASI ORDER BY MACS DESC LIMIT 1;";
        Cursor c = database.rawQuery(sql,null);

        int id=0;
        while (c.moveToNext()){
            id = c.getInt(0);
        }
        return id;
    }

    public void insert_song_artist(Song song){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO BAIHAT VALUES(null,?,?,?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1,song.getName());
        statement.bindString(2,song.getYear());
        statement.bindString(3,""+song.getId_artist());

        statement.executeInsert();
    }

    public Artist get_artist(int id){
        Artist art = new Artist();
        SQLiteDatabase database = getReadableDatabase();
        String sql = "SELECT * FROM CASI WHERE MACS = '"+id+"';";

        Cursor c = database.rawQuery(sql,null);
        while (c.moveToNext()) {
            art.setName(c.getString(1));
            art.setImg(c.getBlob(2));
        }
        return art;
    }

    public ArrayList get_song(int id){
        ArrayList <Song> arr_song = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        String sql = "SELECT * FROM BAIHAT WHERE MACS = "+id+"";

        Cursor c = database.rawQuery(sql,null);
        while (c.moveToNext()){
            Song song = new Song();
            song.setName(c.getString(1));
            song.setYear(c.getString(2));
            arr_song.add(song);
        }
        return arr_song;
    }
    public void del_song(int id){
        String sql = "DELETE FROM BAIHAT WHERE MACS = "+id+" ";
        QuerryData(sql);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
