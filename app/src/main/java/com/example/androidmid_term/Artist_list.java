package com.example.androidmid_term;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.SearchView;

import java.util.ArrayList;

public class Artist_list extends AppCompatActivity {
    Database_Artist db;
    ArrayList<Artist> data;
    GridView list_artist;
    Button add;
    Button btn_get_rank;
    EditText txt_rank_date;
    SearchView searchView;
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
        btn_get_rank = findViewById(R.id.btn_get_rank);
        txt_rank_date = findViewById(R.id.txt_date_rank);
        searchView = findViewById(R.id.search);
    }

    private void setEvent() {
        ArrayAdapter_Artist_list adapter = new ArrayAdapter_Artist_list(Artist_list.this, R.layout.activity_artis_list_item,data);
        list_artist.setAdapter(adapter);
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

        btn_get_rank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = txt_rank_date.getText().toString().trim();
                data= db.get_rank_artist_by_date(date);
                //data = arr_artist;
                ArrayAdapter_Artist_list adapter = new ArrayAdapter_Artist_list(Artist_list.this, R.layout.activity_artis_list_item,data);
                list_artist.setAdapter(adapter);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            public boolean onQueryTextSubmit(String text) {
                adapter.getFilter().filter(text);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
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