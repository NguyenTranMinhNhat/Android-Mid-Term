package com.example.androidmid_term;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;

public class CreateArtist extends AppCompatActivity {
    int REQUEST_CODE_FOLDER = 10;
    ImageView img_artist;
    ImageView save;
    TextView back;
    EditText txt_name_artist;
    EditText txt_song_list;
    Database_Artist database;
    Intent file_br;
    Artist at;
    Song song;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_artist);
        database = new Database_Artist(this);
        setControl();
        setEvent();
    }

    public void save_artist(){
        String name_artist = txt_name_artist.getText().toString().trim();
        at = new Artist();
        byte[] img = convert_img_view_to_byte();
        at.setName(name_artist);
        at.setImg(img);
        database.Insert_Artist_Img(at);
        Toast.makeText(CreateArtist.this, "Save Artist", Toast.LENGTH_SHORT).show();
    }

    public void save_song(){
        String song_lst = txt_song_list.getText().toString().trim();
        int id_art = database.get_last_id();
        String[] arr_song_lst = song_lst.split("\n");
        for (int i = 0; i < arr_song_lst.length; i++) {
            String[] arr_song = arr_song_lst[i].split(";");
            if (arr_song.length < 2) {
                Toast.makeText(CreateArtist.this, "Please fill in song's name and song's year", Toast.LENGTH_SHORT).show();
                return;
            }
            song = new Song();
            song.setId_artist(id_art);
            String song_name = arr_song[0].trim();
            String song_year = arr_song[1].trim();
            song.setName(song_name);
            song.setYear(song_year);
            database.insert_song_artist(song);
        }
        Toast.makeText(CreateArtist.this, "Save Song", Toast.LENGTH_SHORT).show();
    }
    private void setControl() {
        img_artist = findViewById(R.id.img_artist);
        save = findViewById(R.id.save);
        txt_name_artist = findViewById(R.id.txt_artist_name);
        txt_song_list = findViewById(R.id.txt_song_list);
        back = findViewById(R.id.btn_back);
    }

    private void setEvent() {
        img_artist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                file_br = new Intent(Intent.ACTION_PICK);
                file_br.setType("image/*");
                startActivityForResult(file_br, REQUEST_CODE_FOLDER);

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txt_name_artist.getText().toString().trim().length() == 0 || txt_song_list.getText().toString().trim().length() == 0) {
                    Toast.makeText(CreateArtist.this, "Please fill in name and song list", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    //Save to CASI
                    save_artist();
                    //Save to BAIHAT
                    save_song();
                    Toast.makeText(CreateArtist.this, "Complete", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(CreateArtist.this, "ERORR", Toast.LENGTH_SHORT).show();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateArtist.this,Artist_list.class));
            }
        });
    }

    public byte[] convert_img_view_to_byte() {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) img_artist.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArray);

        byte[] img = byteArray.toByteArray();

        return img;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_FOLDER && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                InputStream input = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(input);
                img_artist.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}