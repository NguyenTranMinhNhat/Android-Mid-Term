package com.example.androidmid_term;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Edit_show extends AppCompatActivity {

    ArrayList<ShowInfo> data;
    TextView tv_back;
    ImageView imageSave;
    ImageView imageTacGia;
    TextView tenTacGia;
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
        imageSave = findViewById(R.id.imv_save_icon_es);
        imageTacGia = findViewById(R.id.imv_tacGia_es);
        tenTacGia = findViewById(R.id.tv_tenTacGia_es);
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