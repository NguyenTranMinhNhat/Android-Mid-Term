//package com.example.quanlyamnhac;
//
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//
//public class DatabaseAccess {
//    private SQLiteOpenHelper openHelper;
//    private SQLiteDatabase db;
//    private static  DatabaseAccess instance;
//    Cursor c = null;
//
//    //private constructor so that object creation from outside the class is avoided
//    private DatabaseAccess(Context context){
//        this.openHelper = new DataOpenHelper(context);
//
//    }
//
//    // to return the single instance of database
//
//    public static DatabaseAccess getInstance(Context context){
//        if(instance == null){
//            instance = new DatabaseAccess(context);
//        }
//        return instance;
//    }
//
//    // to open the darabase
//    public void orpn(){
//        this.db = openHelper.getWritableDatabase();
//    }
//
//    // closing the database connection
//
//    public void close(){
//        if(db != null){
//            this.db.close();
//        }
//    }
//
//
//}
