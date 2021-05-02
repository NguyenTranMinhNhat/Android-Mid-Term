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

public class ArrayAdapter_ShowInfo extends ArrayAdapter<ShowInfo> {
    Context context;
    int resource;
    ArrayList<ShowInfo> data;

    @NonNull
    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    public ArrayAdapter_ShowInfo(@NonNull Context context, int resource, ArrayList data) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
        this.data = data;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource, null);
        TextView detail = convertView.findViewById(R.id.detail);

        ShowInfo show = data.get(position);

        String rs = "";
        rs = rs + show.getMaBD();
        rs = rs + " - " + show.getTenBH();
        rs = rs + " - " + show.getNgayBD();
        rs = rs + " - " + show.getDiaDiem();
        detail.setText(rs);
        return convertView;
    }
}