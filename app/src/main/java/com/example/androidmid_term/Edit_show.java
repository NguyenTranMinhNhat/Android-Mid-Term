package com.example.androidmid_term;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Edit_show extends AppCompatActivity {
    ArrayList<ShowInfo> data;
    TextView btn_back;
    TextView txt_song_name;
    TextView txt_date;
    TextView txt_place;
    Button btn_update;
    Button btn_save;
    ListView info_show;
    DatabaseShowInfo db;
    private int id=0;
    private int id_bd=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_show);
        Intent intent = getIntent();
        id = intent.getIntExtra("id",0);
        db = new DatabaseShowInfo(this);
        setControl();
        init();
        setEvent();
    }

    private void setEvent() {
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowInfo show = new ShowInfo();

                String song_name = txt_song_name.getText().toString().trim();
                String date = txt_date.getText().toString().trim();
                String place = txt_place.getText().toString().trim();

                int id_song = db.get_song_id_by_name(song_name);
                if(id_song==0){
                    Toast.makeText(Edit_show.this, "This song not exist", Toast.LENGTH_SHORT).show();
                    return;
                }
                show.setMaBH(id_song);
                show.setMaCS(id);
                show.setDiaDiem(place);
                show.setNgayBD(date);

                db.insert_show_info(show);

                init();
            }
        });

        info_show.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                id_bd = data.get(position).getMaBD();

                String song_name = data.get(position).getTenBH();
                String date = data.get(position).getNgayBD();
                String place = data.get(position).getDiaDiem();

                txt_song_name.setText(song_name);
                txt_date.setText(date);
                txt_place.setText(place);
            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String song_name = txt_song_name.getText().toString().trim();
                String date = txt_date.getText().toString().trim();
                String place = txt_place.getText().toString().trim();

                ShowInfo show = new ShowInfo();

                int id_song = db.get_song_id_by_name(song_name);
                if(id_song==0){
                    Toast.makeText(Edit_show.this, "This song not exist", Toast.LENGTH_SHORT).show();
                    return;
                }

                show.setMaBD(id_bd);
                show.setMaBH(id_song);
                show.setDiaDiem(place);
                show.setNgayBD(date);

                db.update_show_info(show);

                init();
            }
        });

        info_show.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                del_confirm(position);
                return false;
            }
        });
    }

    private void setControl() {
        info_show = findViewById(R.id.info_show);
        btn_back = findViewById(R.id.btn_back);
        txt_song_name = findViewById(R.id.txt_song_name);
        txt_date = findViewById(R.id.txt_date);
        txt_place = findViewById(R.id.txt_place);
        btn_save = findViewById(R.id.btn_save);
        btn_update = findViewById(R.id.btn_update);
    }
    private void init(){
        data = db.get_show_info(id);
        ArrayAdapter_ShowInfo adapter = new ArrayAdapter_ShowInfo(this,R.layout.activity_layout_show_info_view,data);
        info_show.setAdapter(adapter);
        Toast.makeText(this, ""+id, Toast.LENGTH_SHORT).show();
    }
    private void del_confirm(int position){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Warning !!!");
        alert.setMessage("Do you want to delete this show info ???");

        id_bd = data.get(position).getMaBD();
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                db.del_show_info_by_id(id_bd);
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