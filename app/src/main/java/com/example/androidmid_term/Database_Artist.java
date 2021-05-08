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
    public int get_song_id_by_name_different(String name,int id){
        String sql = "SELECT MABH FROM BAIHAT WHERE TENBH = '"+name+"' AND MABH != "+id+" ";
        Cursor c = getData(sql);

        int id_=0;
        while (c.moveToNext()){
            id_ = c.getInt(0);
        }
        return id_;
    }
    public void update_song_by_id(Song song){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "UPDATE BAIHAT SET NAMST = ?, TENBH =? WHERE MABH = "+song.getId()+"";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1,song.getYear());
        statement.bindString(2,song.getName());

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
    public ArrayList get_rank_artist_by_date(String date){
        ArrayList <Artist> arr_artist = new ArrayList<>();
        String sql = "SELECT cs.*,count(ttbd.MACS) as count FROM THONGTINBIEUDIEN ttbd\n" +
                "INNER JOIN CASI cs on cs.MACS = ttbd.MACS\n" +
                "WHERE ttbd.NGAYBD like '%"+date+"%'\n" +
                "GROUP BY ttbd.MACS\n" +
                "ORDER BY count DESC";
        Cursor c = getData(sql);

        while (c.moveToNext()){
            Artist art = new Artist();
            if(arr_artist.size()==0){
                art.setName(arr_artist.size()+1+"st - "+c.getString(1)+"\n"+c.getString(3)+" shows");
            }
            else if(arr_artist.size()==1){
                art.setName(arr_artist.size()+1+"nd - "+c.getString(1)+"\n"+c.getString(3)+" shows");
            }
            else if(arr_artist.size()==2){
                art.setName(arr_artist.size()+1+"rd - "+c.getString(1)+"\n"+c.getString(3)+" shows");
            }
            else {
                art.setName(arr_artist.size()+1+"th - "+c.getString(1)+"\n"+c.getString(3)+" shows");
            }
            art.setId(c.getInt(0));
            art.setImg(c.getBlob(2));
            arr_artist.add(art);
        }

        return arr_artist;
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
