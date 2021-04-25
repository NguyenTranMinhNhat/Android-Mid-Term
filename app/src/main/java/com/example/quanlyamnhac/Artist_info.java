package com.example.quanlyamnhac;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Artist_info extends AppCompatActivity {

    ArrayList<Song> data;
    ListView info_songlist;
    TextView info_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artitst_info);
        setControl();
        setEvent();
    }

    private void setControl() {
        info_songlist = findViewById(R.id.info_songlist);
        info_name = findViewById(R.id.info_name);
    }

    private void setEvent() {
        khoitao();
        ArrayAdapter_Songlist adapter = new ArrayAdapter_Songlist(this, R.layout.activity_song_list_item,data);
        info_songlist.setAdapter(adapter);
    }

    private void khoitao(){
        data = new ArrayList<>();
        Database db = new Database(this);

        Cursor dataCV = db.getData("SELECT C.TENCS, B.TENBH, B.NAMST FROM BAIHAT B, CASI C " +
                "WHERE C.MACS = '1' AND  B.MACS = '1' ");
        while (dataCV.moveToNext()) {
            String tenCS = dataCV.getString(0);
            String tenBH = dataCV.getString(1);
            int namST = dataCV.getInt(2);
            Song song = new Song();
            song.setTenCS(tenCS);
            song.setTenBH(tenBH);
            song.setNamST(namST);
            data.add(song);
            info_name.setText(tenCS);
//            Toast.makeText(this, tenBH + namST, Toast.LENGTH_SHORT).show();
        }



    }
}