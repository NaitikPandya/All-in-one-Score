package com.example.allinonescore.Adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.allinonescore.R;

import java.util.ArrayList;
import java.util.List;

public class Galleryadapter extends ArrayAdapter<String> {

    private List<String> data = new ArrayList<>();
    private Context context;

    public Galleryadapter(Context context,List<String> data)
    {
        super(context, R.layout.galley_item,data);
        this.data = data;
        this.context = context;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View imageview =inflater.inflate(R.layout.galley_item, null,true);

        ImageView image = imageview.findViewById(R.id.Gallery_item);

        Glide.with(context).load(data.get(position)).into(image);

        return imageview;
    }
}
