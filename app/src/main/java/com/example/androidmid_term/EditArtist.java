package com.example.androidmid_term;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class EditArtist extends AppCompatActivity {
    int REQUEST_CODE_FOLDER = 10;
    TextView btn_back;
    TextView txt_name_artist;
    EditText txt_artist_name;
    EditText txt_song_list;
    ImageView btn_save;
    ImageView img_arttist;
    Database_Artist db;
    Artist at;
    Song song;
    Intent file_br;
    final int id = 120;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_artist);
        db = new Database_Artist(this);
        setControl();
        init();
        setEvent();
    }

    private void v_get_artist(){
        at = db.get_artist(id);
        Bitmap img = convert_byte_to_bitmap(at.getImg());
        img_arttist.setImageBitmap(img);
        txt_artist_name.setText(at.getName());
    }
    private void v_get_song(){
        String song_list="";
        ArrayList<Song> arr_song = db.get_song(id);
        if (arr_song.size()==0){
            Toast.makeText(this, "This artist haven't had any song yet", Toast.LENGTH_SHORT).show();
            txt_song_list.setText("");
            return;
        }
        Toast.makeText(this, ""+arr_song.size(), Toast.LENGTH_SHORT).show();
        for (int i=0;i<arr_song.size();i++){
            song_list = song_list+ arr_song.get(i).getName();
            //Toast.makeText(this, arr_song.get(i).getName(), Toast.LENGTH_SHORT).show();
            song_list = song_list + ";";
            song_list = song_list + arr_song.get(i).getYear();
            //Toast.makeText(this, arr_song.get(i).getYear(), Toast.LENGTH_SHORT).show();
            song_list = song_list+"\n";
        }
        Toast.makeText(this, song_list, Toast.LENGTH_SHORT).show();
        txt_song_list.setText(song_list);
    }
    private void init() {
        v_get_artist();
        v_get_song();
    }

    public Bitmap convert_byte_to_bitmap(byte[] img) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
        return bitmap;
    }

    public void setControl() {
        btn_back = findViewById(R.id.btn_back);
        txt_artist_name = findViewById(R.id.txt_artist_name);
        txt_song_list = findViewById(R.id.txt_song_list_edit);
        txt_name_artist = findViewById(R.id.txt_artist_name);
        btn_save = findViewById(R.id.btn_save);
        img_arttist = findViewById(R.id.img_artist);
    }

    public void save_artist(){
        String name_artist = txt_name_artist.getText().toString().trim();
        at = new Artist();
        byte[] img = convert_img_view_to_byte();
        at.setName(name_artist);
        at.setImg(img);
        db.update_artist(at,id);
        Toast.makeText(EditArtist.this, "Save Artist", Toast.LENGTH_SHORT).show();
    }

    public byte[] convert_img_view_to_byte() {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) img_arttist.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArray);

        byte[] img = byteArray.toByteArray();

        return img;
    }
    public void save_song(){
        String song_lst = txt_song_list.getText().toString().trim();
        String[] arr_song_lst = song_lst.split("\n");
        for (int i = 0; i < arr_song_lst.length; i++) {
            String[] arr_song = arr_song_lst[i].split(";");
            if (arr_song.length < 2) {
                Toast.makeText(EditArtist.this, "Please fill in song's name and song's year", Toast.LENGTH_SHORT).show();
                return;
            }
            song = new Song();
            song.setId_artist(id);
            String song_name = arr_song[0].trim();
            String song_year = arr_song[1].trim();
            song.setName(song_name);
            song.setYear(song_year);
            db.insert_song_artist(song);
        }
        Toast.makeText(EditArtist.this, "Save Song", Toast.LENGTH_SHORT).show();
    }

    public void setEvent() {
        img_arttist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                file_br = new Intent(Intent.ACTION_PICK);
                file_br.setType("image/*");
                startActivityForResult(file_br, REQUEST_CODE_FOLDER);
            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.del_song(id);
                save_artist();
                save_song();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_FOLDER && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                InputStream input = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(input);
                img_arttist.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}