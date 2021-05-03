package com.example.androidmid_term;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
    ImageView btn_setting;
    ImageView btn_schedule;
    Database_Artist db;
    Artist artist;
    Animation animation;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artitst_info);
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);

        db = new Database_Artist(this);

        setControl();
        init();
        setEvent();
    }

    private void init() {
        animation = AnimationUtils.loadAnimation(this,R.anim.disk_rotate);
        btn_setting.startAnimation(animation);
        artist = db.get_artist(id);
        info_name.setText(artist.getName());
        if(artist.getImg()==null){
            info_img.setImageResource(R.drawable.image_blank);
        }
        else
            info_img.setImageBitmap(convert_byte_to_bitmap(artist.getImg()));
    }

    public Bitmap convert_byte_to_bitmap(byte[] img) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
        return bitmap;
    }

    private void setControl() {
        info_songlist = findViewById(R.id.info_songlist);
        info_name = findViewById(R.id.info_name);
        info_img = findViewById(R.id.info_img);
        btn_back = findViewById(R.id.btn_back);
        btn_setting = findViewById(R.id.btn_setting);
        btn_schedule = findViewById(R.id.btn_schedule);
    }

    private void setEvent() {
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Artist_info.this,Artist_list.class));
            }
        });
        btn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Artist_info.this,EditArtist.class);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });
        btn_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Artist_info.this,layoutShowInfoView.class);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });
        khoitao();
        ArrayAdapter_Songlist adapter = new ArrayAdapter_Songlist(this, R.layout.activity_song_list_item, data);
        info_songlist.setAdapter(adapter);
    }

    private void khoitao() {
        data = db.get_song(id);
    }


}
