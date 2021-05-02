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

public class DatabaseShowInfo extends SQLiteOpenHelper {
    public DatabaseShowInfo(@Nullable Context context) {
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

    public ArrayList get_show_info(int id){
        ArrayList<ShowInfo> arr_show = new ArrayList<>();
        String sql = "SELECT ttbd.MABD, ttbd.MACS, ttbd.MABH, ttbd.NGAYBD, ttbd.DIADIEM, bh.TENBH FROM THONGTINBIEUDIEN ttbd\n" +
                "INNER JOIN BAIHAT bh on bh.MABH = ttbd.MABH\n" +
                "WHERE ttbd.MACS = "+id+"";
        Cursor c = getData(sql);
        while (c.moveToNext()){
            ShowInfo show = new ShowInfo();
            show.setMaBD(c.getInt(0));
            show.setMaCS(c.getInt(1));
            show.setMaBH(c.getInt(2));
            show.setNgayBD(c.getString(3));
            show.setDiaDiem(c.getString(4));
            show.setTenBH(c.getString(5));
            arr_show.add(show);
        }

        return arr_show;
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
        String sql = "INSERT INTO BAIHAT VALUES(null,?,?,?,?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1,song.getName());
        statement.bindString(2,song.getYear());
        statement.bindString(3,""+song.getId_artist());
        statement.bindString(4,""+song.getHas_sound());

        statement.executeInsert();
    }

    public Artist get_artist(int id){
        Artist art = new Artist();
        SQLiteDatabase database = getReadableDatabase();
        String sql = "SELECT * FROM CASI WHERE MACS = '"+id+"';";

        Cursor c = database.rawQuery(sql,null);
        while (c.moveToNext()) {
            art.setId(c.getInt(0));
            art.setName(c.getString(1));
            art.setImg(c.getBlob(2));
        }
        return art;
    }
    public ArrayList get_artist_list(){
        ArrayList <Artist> arr_artist = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        String sql = "SELECT * FROM CASI";

        Cursor c = database.rawQuery(sql,null);
        while (c.moveToNext()){
            Artist art = new Artist();
            art.setId(c.getInt(0));
            art.setName(c.getString(1));
            art.setImg(c.getBlob(2));
            arr_artist.add(art);
        }

        return arr_artist;
    }
    public ArrayList get_song(int id){
        ArrayList <Song> arr_song = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        String sql = "SELECT * FROM BAIHAT WHERE MACS = "+id+"";

        Cursor c = database.rawQuery(sql,null);
        while (c.moveToNext()){
            Song song = new Song();
            song.setId(c.getInt(0));
            song.setName(c.getString(1));
            song.setYear(c.getString(2));
            song.setHas_sound(c.getString(4));
            arr_song.add(song);
        }
        return arr_song;
    }
    public int get_song_id_by_name(String name){
        String sql = "SELECT MABH FROM BAIHAT WHERE TENBH = '"+name+"'";
        Cursor c = getData(sql);

        int id=0;
        while (c.moveToNext()){
            id = c.getInt(0);
        }
        return id;
    }

    public void update_song_by_id(Song song){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "UPDATE BAIHAT SET NAMST = ? WHERE MABH = "+song.getId()+"";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1,song.getYear());

        statement.executeInsert();
    }

    public void update_song_has_sound(Song song){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "UPDATE BAIHAT SET HAS_SOUND = ? WHERE MABH = "+song.getId()+"";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1,song.getHas_sound());

        statement.executeInsert();
    }
    public void del_perform_info_by_song_id(int id){
        String sql ="DELETE FROM THONGTINBIEUDIEN WHERE MABH = "+id+"";
        QuerryData(sql);
    }

    public void del_song_by_id(int id){
        String sql = "DELETE FROM BAIHAT WHERE MABH = "+id+"";
        QuerryData(sql);
    }

    public void del_perform_info_by_artist_id(int id){
        String sql ="DELETE FROM THONGTINBIEUDIEN WHERE MACS = "+id+"";
        QuerryData(sql);
    }

    public void del_song_by_artist_id(int id){
        String sql = "DELETE FROM BAIHAT WHERE MACS = "+id+"";
        QuerryData(sql);
    }

    public void del_artist_by_id(int id){
        String sql = "DELETE FROM CASI WHERE MACS = "+id+"";
        QuerryData(sql);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
