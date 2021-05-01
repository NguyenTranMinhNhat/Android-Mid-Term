package com.example.quanlyamnhac;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.sql.Blob;
import java.util.ArrayList;

public class Artist_list extends AppCompatActivity {

    ArrayList<Artist> data;
    GridView list_artist;
    Button btn_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_list);

        setControl();
        setEvent();
    }

    private void setControl() {
        list_artist = findViewById(R.id.list_artist);
        btn_add = findViewById(R.id.btn_add);
    }

    private void setEvent() {
        khoitao();
        ArrayAdapter_Artist_list adapter = new ArrayAdapter_Artist_list(this, R.layout.activity_artis_list_item,data);
        list_artist.setAdapter(adapter);

        list_artist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int maCS = position + 1;
//                Toast.makeText(Artist_list.this, position + "#Selected", Toast.LENGTH_SHORT).show();

                // get info from <position> in list
                Artist artist_info = data.get(position);
                byte[] artist_avt = artist_info.getAvatar();

                open_artist_info(maCS, artist_avt);
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(Artist_list.this, "Add", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void khoitao() {
        data = new ArrayList<>();
        Database db = new Database(this);

        Cursor dataCV = db.getData("SELECT TENCS, IMG FROM CASI");
        while (dataCV.moveToNext()) {
            String tenCS = dataCV.getString(0);
            byte[] avatar_byte = dataCV.getBlob(1);
//            Toast.makeText(this, "Image: "+String.valueOf(avatar_byte), Toast.LENGTH_SHORT).show();

            Artist art = new Artist();
            art.setName(tenCS);
            // set avatar
            if(avatar_byte == null){
                // Do NOTHING
            }
            else{
                Bitmap data_img = convert_byte_to_bitmap(avatar_byte);
                art.setAvatar(avatar_byte);
            }

            data.add(art);
        }
    }

    private void open_artist_info(int artist_id, byte[] artist_avt){

        // Attached data from current activity and send to next activity

        Intent intent = new Intent(this, Artist_info.class);
        intent.putExtra("ID", artist_id);
        intent.putExtra("AVATAR", artist_avt);

        startActivity(intent);
    }

    public Bitmap convert_byte_to_bitmap(byte[] img) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
        return bitmap;
    }

}