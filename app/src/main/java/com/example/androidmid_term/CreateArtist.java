package com.example.androidmid_term;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class CreateArtist extends AppCompatActivity {
    ImageView img_artist;
    EditText txt_name_artist;
    Database database;
    Intent file_br;
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
        img_artist.setClickable(true);
        txt_name_artist = findViewById(R.id.txt_artist_name);
    }

    private void setEvent(){
        img_artist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                file_br = new Intent(Intent.ACTION_GET_CONTENT);
                file_br.setType("*/*");
                startActivityForResult(file_br,10);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK) {
                    String path = data.getData().getPath();
//                    File file = new File(path);
//                    try {
//                        Bitmap b = BitmapFactory.decodeStream(new FileInputStream(file));
//                        img_artist.setImageBitmap(b);
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    }
                    txt_name_artist.setText(path);
                }
                break;
        }
    }
}