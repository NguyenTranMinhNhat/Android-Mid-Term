package com.example.androidmid_term;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ArrayAdapter_Artist_list extends ArrayAdapter<Artist> {
    Context context;
    int resource;
    ArrayList<Artist> data;

    @NonNull
    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    public ArrayAdapter_Artist_list(@NonNull Context context, int resource, ArrayList data) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
        this.data = data;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource,null);
        ImageView avatar = convertView.findViewById(R.id.avatar);
        TextView name = convertView.findViewById(R.id.name);
        Artist art = data.get(position);
        if(art.getImg()==null){
            avatar.setImageResource(R.drawable.image_blank);
        }
        else{
            Bitmap img = convert_byte_to_bitmap(art.getImg());
            avatar.setImageBitmap(img);
        }
        name.setText(art.getName());
        return convertView;
    }
    public Bitmap convert_byte_to_bitmap(byte[] img) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
        return bitmap;
    }
}
