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
    public void insert_show_info(ShowInfo show){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO THONGTINBIEUDIEN VALUES(null,?,?,?,?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1,""+show.getMaCS());
        statement.bindString(2,""+show.getMaBH());
        statement.bindString(3,show.getNgayBD());
        statement.bindString(4,show.getDiaDiem());

        statement.executeInsert();

    }
    public void update_show_info(ShowInfo show){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "UPDATE THONGTINBIEUDIEN SET MABH=?, NGAYBD=?, DIADIEM=? WHERE MABD = "+show.getMaBD()+"";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1,""+show.getMaBH());
        statement.bindString(2,show.getNgayBD());
        statement.bindString(3,show.getDiaDiem());

        statement.executeInsert();
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
    public void del_show_info_by_id(int id){
        String sql ="DELETE FROM THONGTINBIEUDIEN WHERE MABD = "+id+"";
        QuerryData(sql);
    }
    public ArrayList find_show_info_by_date(ShowInfo show){
        ArrayList <ShowInfo> arr_show = new ArrayList<>();
        String sql ="SELECT ttbd.MABD, ttbd.NGAYBD, ttbd.DIADIEM, bh.TENBH FROM THONGTINBIEUDIEN ttbd\n" +
                "INNER JOIN BAIHAT bh on bh.MABH = ttbd.MABH\n" +
                "WHERE ttbd.MACS = "+show.getMaCS()+" AND ttbd.NGAYBD like '%"+show.getNgayBD()+"%'";
        Cursor c = getData(sql);

        while (c.moveToNext()){
            ShowInfo showInfo = new ShowInfo();
            showInfo.setMaBD(c.getInt(0));
            showInfo.setTenBH(c.getString(3));
            showInfo.setNgayBD(c.getString(1));
            showInfo.setDiaDiem(c.getString(2));
            arr_show.add(showInfo);
        }

        return arr_show;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
