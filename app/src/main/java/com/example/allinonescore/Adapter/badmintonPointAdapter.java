package com.example.allinonescore.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.avatarfirst.avatargenlib.AvatarConstants;
import com.avatarfirst.avatargenlib.AvatarGenerator;
import com.bumptech.glide.Glide;
import com.example.allinonescore.AddPlayerActivity;
import com.example.allinonescore.Dialogs.TeamnameDialog;
import com.example.allinonescore.R;

import java.util.ArrayList;
import java.util.List;

public class badmintonPointAdapter extends ArrayAdapter<String> {


    private List<String> point = new ArrayList<>();
    private Context context;

    public badmintonPointAdapter(Context context, List<String> point)
    {
        super(context, R.layout.grid_card_team,point);
        this.context = context;
        this.point = point;

    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View card_view =inflater.inflate(R.layout.badminton_point_text, null,true);
        TextView data = card_view.findViewById(R.id.point);

        data.setText(point.get(position));

        return card_view;
    }

}
