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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class EditArtist extends AppCompatActivity {
    int REQUEST_CODE_FOLDER = 10;
    ArrayList<Song> data;
    TextView btn_back;
    TextView btn_edit_song;
    EditText txt_artist_name;
    ImageView btn_save;
    ImageView img_arttist;
    Database_Artist db;
    Artist at;
    Intent file_br;
    ListView info_songlist;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_artist);
        db = new Database_Artist(this);
        Intent intent = getIntent();
        id = intent.getIntExtra("id",1);
        setControl();
        init();
        setEvent();
    }

    private void v_get_artist(){
        at = db.get_artist(id);
        if(at.getImg()==null){
            img_arttist.setImageResource(R.drawable.image_blank);
            return;
        }
        Bitmap img = convert_byte_to_bitmap(at.getImg());
        img_arttist.setImageBitmap(img);
        txt_artist_name.setText(at.getName());
    }
    private void init() {
        v_get_artist();
    }

    public Bitmap convert_byte_to_bitmap(byte[] img) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
        return bitmap;
    }

    public void setControl() {
        btn_back = findViewById(R.id.btn_back);
        btn_edit_song = findViewById(R.id.btn_edit_song);
        txt_artist_name = findViewById(R.id.txt_artist_name);
        info_songlist = findViewById(R.id.info_songlist);
        btn_save = findViewById(R.id.btn_save);
        img_arttist = findViewById(R.id.img_artist);
    }

    public void save_artist(){
        String name_artist = txt_artist_name.getText().toString().trim();
        at = new Artist();
        byte[] img = convert_img_view_to_byte();
        at.setId(id);
        at.setName(name_artist);
        at.setImg(img);
        if(db.check_artist_name_distinct(at)==0) {
            db.update_artist(at, id);
            Toast.makeText(EditArtist.this, "Save Artist", Toast.LENGTH_SHORT).show();
        }
        else Toast.makeText(this, "This artist name existed", Toast.LENGTH_SHORT).show();
    }

    public byte[] convert_img_view_to_byte() {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) img_arttist.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArray);

        byte[] img = byteArray.toByteArray();

        return img;
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
                save_artist();
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditArtist.this,Artist_info.class);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });
        btn_edit_song.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditArtist.this,EditSongList.class);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });
        data = db.get_song(id);
        ArrayAdapter_Songlist adapter = new ArrayAdapter_Songlist(this, R.layout.activity_song_list_item, data);
        info_songlist.setAdapter(adapter);
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