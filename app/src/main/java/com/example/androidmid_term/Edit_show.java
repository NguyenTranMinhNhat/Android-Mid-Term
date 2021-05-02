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
    ImageView imageTacGia;
    TextView tenTacGia;
    ListView info_show;
    DatabaseShowInfo db;
    private int maBd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_show);

    }
}