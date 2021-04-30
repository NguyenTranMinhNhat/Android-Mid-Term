package com.example.androidmid_term;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import java.sql.Blob;
import java.util.ArrayList;

public class Artist_list extends AppCompatActivity {
    Database_Artist db;
    ArrayList<Artist> data;
    GridView list_artist;
    Button add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_list);
        db = new Database_Artist(this);
        setControl();
        init();
        setEvent();
    }

    private void init() {
        data = db.get_artist_list();
        ArrayAdapter_Artist_list adapter = new ArrayAdapter_Artist_list(Artist_list.this, R.layout.activity_artis_list_item,data);
        list_artist.setAdapter(adapter);
    }

    private void setControl() {
        list_artist = findViewById(R.id.list_artist);
        add = findViewById(R.id.btn_add);
    }

    private void setEvent() {
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Artist_list.this,CreateArtist.class));
            }
        });

        list_artist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Artist_list.this,Artist_info.class);
                intent.putExtra("id",data.get(position).getId());
                startActivity(intent);
            }
        });

        list_artist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                del_confirm(position);
                return false;
            }
        });
    }

    private void del_confirm(int position){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Warning !!!");
        alert.setMessage("Do you want to delete this artist and all of song and perform's info belong to this ???");

        int id = data.get(position).getId();
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                db.del_perform_info_by_artist_id(id);
                db.del_song_by_artist_id(id);
                db.del_artist_by_id(id);
                init();
            }
        });

        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });

        alert.show();
    }
}