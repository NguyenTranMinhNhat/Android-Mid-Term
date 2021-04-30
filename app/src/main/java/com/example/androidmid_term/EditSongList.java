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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class EditSongList extends AppCompatActivity {
    ArrayList<Song> data;
    Database_Artist db;
    Button btn_add;
    Button btn_song_update;
    EditText txt_song_name;
    EditText txt_song_year;
    TextView btn_back;
    ListView info_songlist;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.song_list_edit);
        db = new Database_Artist(this);
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 1);
        setConttrol();
        init();
        setEvent();
    }

    private void init() {
        data = db.get_song(id);
        ArrayAdapter_Songlist adapter = new ArrayAdapter_Songlist(this, R.layout.song_list, data);
        info_songlist.setAdapter(adapter);
    }

    private void setConttrol() {
        btn_add = findViewById(R.id.btn_song_add);
        btn_song_update = findViewById(R.id.btn_song_update);
        btn_back = findViewById(R.id.btn_back);
        info_songlist = findViewById(R.id.info_songlist);
        txt_song_name = findViewById(R.id.txt_song_name);
        txt_song_year = findViewById(R.id.txt_song_year);
    }

    private void setEvent() {
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditSongList.this, EditArtist.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Song song = new Song();
                song.setName(txt_song_name.getText().toString().trim());
                song.setYear(txt_song_year.getText().toString().trim());
                song.setId_artist(id);
                if(db.get_song_id_by_name(txt_song_name.getText().toString().trim())==0){
                    db.insert_song_artist(song);
                }
                else Toast.makeText(EditSongList.this, "This song is already exist", Toast.LENGTH_SHORT).show();

                init();
            }
        });
        btn_song_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name= txt_song_name.getText().toString().trim();
                String year = txt_song_year.getText().toString().trim();
                int id = db.get_song_id_by_name(name);

                if(id==0){
                    Toast.makeText(EditSongList.this, "Please add this song before edit", Toast.LENGTH_SHORT).show();
                    return;
                }

                Song song = new Song();
                song.setId(id);
                song.setYear(year);
                db.update_song_by_id(song);

                init();
            }
        });
        info_songlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                txt_song_name.setText(data.get(position).getName());
                txt_song_year.setText(data.get(position).getYear());
            }
        });
        info_songlist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(EditSongList.this, "ok", Toast.LENGTH_SHORT).show();

                del_confirm(position);
                return false;
            }
        });
    }

    private void del_confirm(int position){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Warning !!!");
        alert.setMessage("Do you want to delete this song ???");

        int id = data.get(position).getId();
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                db.del_perform_info_by_song_id(id);
                db.del_song_by_id(id);
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