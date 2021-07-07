package com.example.allinonescore.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.allinonescore.R;

import java.util.ArrayList;
import java.util.List;

public class PointTableAdapter extends ArrayAdapter<String> {

    Context context;
    List<String> Matches = new ArrayList<>();
    List<String> Win = new ArrayList<>();
    List<String> Loss = new ArrayList<>();
    List<String> Tied = new ArrayList<>();
    List<String> Team = new ArrayList<>();
    View view;

    public PointTableAdapter(Context context1,List<String> Team,List<String> matches,List<String> win,List<String> loss , List<String> tied)
    {
        super(context1, R.layout.pointtable_item,Team);

        context = context1;
        Matches = matches;
        this.Team = Team;
        Win = win;
        Loss = loss;
        Tied = tied;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);

        view = inflater.inflate(R.layout.pointtable_item,parent,false);

        TextView win = view.findViewById(R.id.point_win);
        TextView team = view.findViewById(R.id.point_team);
        TextView match = view.findViewById(R.id.point_match);
        TextView loss = view.findViewById(R.id.point_loss);
        TextView tied = view.findViewById(R.id.point_tied);

        win.setText(Win.get(position));
        team.setText(Team.get(position));
        match.setText(Matches.get(position));
        loss.setText(Loss.get(position));
        tied.setText(Tied.get(position));

        return view;

    }
}
