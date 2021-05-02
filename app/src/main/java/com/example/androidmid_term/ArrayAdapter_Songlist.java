package com.example.androidmid_term;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ArrayAdapter_Songlist extends ArrayAdapter<Song> {
    Context context;
    int resource;
    ArrayList<Song> data ;

    @NonNull
    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    public ArrayAdapter_Songlist(@NonNull Context context, int resource, ArrayList data) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
        this.data = data;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource,null);
        TextView detail = convertView.findViewById(R.id.detail);

        Song song = data.get(position);
        String name_n_year = song.getId() + " - "+ song.getName() + " - " +song.getYear()+" ------------- "+(song.getHas_sound().equals("1")?"ok":"miss audio");
        detail.setText(name_n_year);
        return convertView;
    }
}
