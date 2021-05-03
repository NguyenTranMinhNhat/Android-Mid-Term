package com.example.androidmid_term;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class layoutShowInfoView extends AppCompatActivity {
    ImageView img_artist;
    ImageView img_setting;
    TextView txt_ten_tg;
    TextView btn_back;
    DatabaseShowInfo db;
    ArrayList <ShowInfo> data;
    ListView lst_info_show;
    private int id;
    Animation animation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shows);
        db = new DatabaseShowInfo(this);
        Intent intent = getIntent();
        id = intent.getIntExtra("id",0);
        setControl();
        init();
        setEvent();
    }

    private void setEvent() {
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(layoutShowInfoView.this,Artist_info.class);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });
        img_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(layoutShowInfoView.this,Edit_show.class);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });
    }

    private void setControl() {
        img_artist = findViewById(R.id.img_tac_gia);
        txt_ten_tg = findViewById(R.id.txt_ten_tg);
        btn_back = findViewById(R.id.btn_back);
        lst_info_show = findViewById(R.id.info_Show);
        img_setting = findViewById(R.id.img_setting);
    }

    private void v_get_artist(){
        Artist at = db.get_artist(id);
        if(at.getImg()==null){
            img_artist.setImageResource(R.drawable.image_blank);
            return;
        }
        Bitmap img = convert_byte_to_bitmap(at.getImg());
        img_artist.setImageBitmap(img);
        txt_ten_tg.setText(at.getName());
    }
    private void init() {
        v_get_artist();
        v_show_info();
        animation = AnimationUtils.loadAnimation(this,R.anim.disk_rotate);
        img_setting.startAnimation(animation);
    }

    public Bitmap convert_byte_to_bitmap(byte[] img) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
        return bitmap;
    }

    private void v_show_info(){

        data = db.get_show_info(id);
        ArrayAdapter_ShowInfo adapter = new ArrayAdapter_ShowInfo(this,R.layout.activity_layout_show_info_view,data);
        lst_info_show.setAdapter(adapter);
    }
}