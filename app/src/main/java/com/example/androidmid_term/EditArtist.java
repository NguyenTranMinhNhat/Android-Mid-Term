package com.example.androidmid_term;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class EditArtist extends AppCompatActivity {
    TextView btn_back;
    EditText txt_artist_name;
    ImageView btn_save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_artist);
        setControl();
        setEvent();
    }

    public void setControl(){
        btn_back = findViewById(R.id.btn_back);
        txt_artist_name = findViewById(R.id.txt_artist_name);
        btn_save = findViewById(R.id.btn_save);
    }

    public void setEvent(){
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_artist_name.setText("ok");
            }
        });
    }
}