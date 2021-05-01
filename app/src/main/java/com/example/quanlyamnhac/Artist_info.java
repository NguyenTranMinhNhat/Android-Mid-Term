package com.example.quanlyamnhac;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Artist_info extends AppCompatActivity {

    ArrayList<Song> data;
    ListView info_songlist;
    TextView info_name;
    TextView btn_back;
    ImageView info_img;

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
        btn_back = findViewById(R.id.btn_back);
        info_img = findViewById(R.id.info_img);
    }

    private void setEvent() {
        khoitao();
        ArrayAdapter_Songlist adapter = new ArrayAdapter_Songlist(this, R.layout.activity_song_list_item,data);
        info_songlist.setAdapter(adapter);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_artist_list();
//                Toast.makeText(Artist_info.this, "Back", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void khoitao(){
        Intent intent = getIntent();
        data = new ArrayList<>();
        Database db = new Database(this);
        int maCS = (int) intent.getSerializableExtra("ID");

//        Toast.makeText(this, "Mã ca sĩ " + maCS, Toast.LENGTH_SHORT).show();

        String sql = "SELECT C.TENCS, C.IMG, B.TENBH, B.NAMST FROM BAIHAT B, CASI C " +
                "WHERE C.MACS = '" + maCS + "'" +
                "AND  B.MACS = '" + maCS + "'";

        Cursor dataCV = db.getData(sql);
        while (dataCV.moveToNext()) {
            String tenCS = dataCV.getString(0);
            byte[] avatar_byte = dataCV.getBlob(1);
            String tenBH = dataCV.getString(2);
            int namST = dataCV.getInt(3);

            Song song = new Song();
            song.setTenBH(tenBH);
            song.setNamST(namST);
            data.add(song);
            info_name.setText(tenCS);
            Toast.makeText(this, "ca si: "+ tenCS, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "avatar: "+String.valueOf(avatar_byte), Toast.LENGTH_SHORT).show();

            if(avatar_byte == null)
                info_img.setImageResource(R.drawable.blank_img);
            else
                info_img.setImageBitmap(convert_byte_to_bitmap(avatar_byte));
        }

    }

    private void open_artist_list(){
        startActivity(new Intent(this, Artist_list.class));
    }

    public Bitmap convert_byte_to_bitmap(byte[] img) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
        return bitmap;
    }
}