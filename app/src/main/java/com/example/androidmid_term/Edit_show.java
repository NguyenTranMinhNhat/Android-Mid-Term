package com.example.androidmid_term;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Edit_show extends AppCompatActivity {

    ArrayList<ShowInfo> data;
    TextView tv_back;
    EditText songName;
    EditText Time;
    EditText place;
    Button save;
    Button cancel;
    Button update;
    ListView info_show;
    DatabaseShowInfo db;
    private int maBd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_show);
        //db = new DatabaseShowInfo(this);
        Intent intent = getIntent();
        maBd = intent.getIntExtra("MABD", 1);
        setConttrol();
        init();
    }

    private void setConttrol() {
        tv_back = findViewById(R.id.tv_back_es);
        songName = findViewById(R.id.Et_tenBaiHat);
        Time = findViewById(R.id.Et_thoiGian);
        place = findViewById(R.id.Et_diaDiem);
        save = findViewById(R.id.btn_save_es);
        update = findViewById(R.id.btn_update_es);
        cancel = findViewById(R.id.btn_cancel_es);
        info_show = findViewById(R.id.info_Show);

    }

    private void init() {
        data = db.getSHowInfo(maBd);
        ArrayAdapter_ShowInfo adapter = new ArrayAdapter_ShowInfo(this, R.layout.activity_layout_show_info_view, data);
        info_show.setAdapter(adapter);
    }

    private void setEvent()
    {
        info_show.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }
}