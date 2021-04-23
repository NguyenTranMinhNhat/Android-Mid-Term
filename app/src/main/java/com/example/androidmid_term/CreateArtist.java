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
    int REQUEST_CODE_FOLDER=10;
    ImageView img_artist;
    ImageView save;
    EditText txt_name_artist;
    EditText txt_song_list;
    Database database;
    Intent file_br;
    Artist at;
    Song song;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_artist);
        database = new Database(this);
        setControl();
        setEvent();
    }

    private void setControl() {
        img_artist = findViewById(R.id.img_artist);
        save = findViewById(R.id.save);
        txt_name_artist = findViewById(R.id.txt_artist_name);
        txt_song_list = findViewById(R.id.txt_song_list);
    }

    private void setEvent(){
        img_artist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                file_br = new Intent(Intent.ACTION_PICK);
                file_br.setType("image/*");
                startActivityForResult(file_br,REQUEST_CODE_FOLDER);

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Save to CASI
                String name_artist = txt_name_artist.getText().toString().trim();
                at = new Artist();
                byte []img = convert_img_view_to_byte();
                at.setName(name_artist);
                at.setImg(img);
                database.Insert_Artist_Img(at);
                Toast.makeText(CreateArtist.this, "Save Artist", Toast.LENGTH_SHORT).show();

                //Save to BAIHAT
                String song_lst = txt_song_list.getText().toString().trim();
                int id_art = database.get_last_id();
                String []arr_song_lst = song_lst.split("\n");
                for (int i=0;i<arr_song_lst.length;i++){
                    String []arr_song = arr_song_lst[i].split(";");
                    song = new Song();
                    song.setId_artist(id_art);
                    String song_name = arr_song[0].trim();
                    String song_year = arr_song[1].trim();
                    song.setName(song_name);
                    song.setYear(song_year);
                    database.insert_song_artist(song);
                }

                Toast.makeText(CreateArtist.this, "Save Song", Toast.LENGTH_SHORT).show();
                Toast.makeText(CreateArtist.this, "Complete", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public byte[] convert_img_view_to_byte(){
        BitmapDrawable bitmapDrawable = (BitmapDrawable) img_artist.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArray);

        byte[] img = byteArray.toByteArray();

        return img;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode==REQUEST_CODE_FOLDER && resultCode == RESULT_OK && data !=null){
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
//        switch (requestCode) {
//            case 10:
//                if (resultCode == RESULT_OK) {
//                    Uri uri = data.getData();
//                    String [] proj={MediaStore.Images.Media.DATA};
//                    Cursor cursor = managedQuery( uri,
//                            proj, // Which columns to return
//                            null,       // WHERE clause; which rows to return (all rows)
//                            null,       // WHERE clause selection arguments (none)
//                            null); // Order-by clause (ascending by name)
//                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//                    cursor.moveToFirst();
//                    String path = cursor.getString(column_index);
////                    File file = new File(path);
////                    try {
////                        Bitmap b = BitmapFactory.decodeStream(new FileInputStream(file));
////                        img_artist.setImageBitmap(b);
////                    } catch (FileNotFoundException e) {
////                        e.printStackTrace();
////                    }
//                    try {
//                        FileInputStream fis = new FileInputStream(path);
//                        byte[] image = new byte[fis.available()];
//                        fis.read(image);
//                        ContentValues values = new ContentValues();
//                        values.put("a",image);
//                        database.QuerryData("UPDATE CASI SET IMG = '"+values+"' WHERE MACS = 'CS01'");
//                        Toast.makeText(this, "save ok", Toast.LENGTH_SHORT).show();
//                        fis.close();
//
//                        Cursor c = database.getData("SELECT * FROM CASI");
//                        if(c.moveToNext()){
//                            byte[] img = c.getBlob(2);
//                            Bitmap bmp = BitmapFactory.decodeByteArray(img,0,img.length);
//                            img_artist.setImageBitmap(bmp);
//                            Toast.makeText(this, "load ok", Toast.LENGTH_SHORT).show();
//                        }
//                    } catch (FileNotFoundException ex) {
//                        Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
//                    } catch (IOException e) {
//                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                }
//                break;
//        }
    }
}