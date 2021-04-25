package com.example.quanlyamnhac;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.Toast;

import java.sql.Blob;
import java.util.ArrayList;

public class Artist_list extends AppCompatActivity {

    ArrayList<Artist> data;
    GridView list_artist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_list);

        setControl();
        setEvent();
    }

    private void setControl() {
        list_artist = findViewById(R.id.list_artist);
    }

    private void setEvent() {
        khoitao();
        ArrayAdapter_Artist_list adapter = new ArrayAdapter_Artist_list(this, R.layout.activity_artis_list_item,data);
        list_artist.setAdapter(adapter);


    }

    private void khoitao() {
        data = new ArrayList<>();
        Database db = new Database(this);

        Cursor dataCV = db.getData("SELECT TENCS, IMG FROM CASI");
        while (dataCV.moveToNext()) {
            String tenCS = dataCV.getString(0);
            byte[] avatar = dataCV.getBlob(1);
            Artist art = new Artist();
            art.setName(tenCS);
            art.setAvatar(R.drawable.troll);
            data.add(art);
        }


    }
}