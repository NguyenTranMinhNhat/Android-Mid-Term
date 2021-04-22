package com.example.quanlyamnhac;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;

import java.util.ArrayList;

public class Artist_list extends AppCompatActivity {

    ArrayList<Artist> data;
    GridView list_artist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_list);

        setControl();
        setEvent();
    }

    private void setControl() {
        list_artist = findViewById(R.id.list_artist);
    }

    private void setEvent() {
        khoitao();
        ArrayAdapter_Artist adapter = new ArrayAdapter_Artist(this, R.layout.activity_artis_list_item,data);
        list_artist.setAdapter(adapter);
    }

    private void khoitao() {
        data = new ArrayList<>();
        Artist art1 = new Artist();
        art1.setAvatar(R.drawable.troll);
        art1.setName("Troll");
        data.add(art1);

        Artist art2 = new Artist();
        art2.setAvatar(R.drawable.why_u_n);
        art2.setName("Why");
        data.add(art2);

        Artist art3 = new Artist();
        art3.setAvatar(R.drawable.not_bad);
        art3.setName("Obama");
        data.add(art3);

        Artist art4 = new Artist();
        art4.setAvatar(R.drawable.trinh_thang_binh_avt);
        art4.setName("Trịnh Thằng Bình");
        data.add(art4);

    }
}