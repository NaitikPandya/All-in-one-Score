package com.example.allinonescore.Adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.allinonescore.R;
import com.example.allinonescore.ScorecardActivity;

import java.util.ArrayList;
import java.util.List;

public class MatchListAdapter extends ArrayAdapter<String> {

    Context context;
    List<String> TeamA ;
    List<String> TeamB;
    List<String> Date;
    List<String> over;
    List<String> location;
    List<String> Ground;
    List<String> city;
    List<String> set;
    List<String> point = new ArrayList<>();
    String Tournament;
    LayoutInflater inflater;
    View rowView;
    String Sport;
    String auth;

    public MatchListAdapter(Context context1,List<String> TeamA ,List<String> TeamB,List<String> Date,List<String> over,List<String> location,String Tournament,String auth,String Sport)
    {
        super(context1, R.layout.match_card_list,location);
        context = context1;
        this.TeamA = TeamA;
        this.TeamB = TeamB;
        this.Date = Date;
        this.over = over;
        this.location = location;
        this.Tournament = Tournament;
        this.auth = auth;
        this.Sport = Sport;

    }
    public MatchListAdapter(Context context1,List<String> TeamA ,List<String> TeamB,List<String> Date,List<String> set,List<String> Ground,List<String> city,String Tournament,String auth,String Sport)
    {
        super(context1, R.layout.badminton_match_card_list,TeamA);
        context = context1;
        this.TeamA = TeamA;
        this.TeamB = TeamB;
        this.Date = Date;
        this.set = set;
        this.Ground = Ground;
        this.city = city;
        this.Tournament = Tournament;
        this.auth = auth;
        this.Sport = Sport;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        switch (Sport){

            case "Cricket":
                inflater = LayoutInflater.from(context);
                rowView = inflater.inflate(R.layout.match_card_list, null,true);

                TextView mlocation = rowView.findViewById(R.id.match_location_list);
                TextView mdate = rowView.findViewById(R.id.match_date_list);
                TextView mover = rowView.findViewById(R.id.match_over_list);
                TextView mTeamA = rowView.findViewById(R.id.match_teamA_list);
                TextView mTeamB = rowView.findViewById(R.id.match_teamB_list);
                TextView schedule = rowView.findViewById(R.id.schedule);

                mlocation.setText(location.get(position));
                mdate.setText(Date.get(position));
                mover.setText(over.get(position) + "Ov.");
                mTeamA.setText(TeamA.get(position));
                mTeamB.setText(TeamB.get(position));
                schedule.setText("Match scheduled at " + Date.get(position));

                rowView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(auth.equals("Admin")){
                            Intent intent = new Intent(context, ScorecardActivity.class);
                            intent.putExtra("TeamA",TeamA.get(position));
                            intent.putExtra("TeamB",TeamB.get(position));
                            intent.putExtra("Tname",Tournament);
                            context.startActivity(intent);
                        }



                    }
                });
            case "Badminton":
                inflater = LayoutInflater.from(context);
                rowView = inflater.inflate(R.layout.badminton_match_card_list, null,true);

                TextView blocation = rowView.findViewById(R.id.badminton_match_location_list);
                TextView bdate = rowView.findViewById(R.id.badminton_match_date_list);
                TextView bset = rowView.findViewById(R.id.match_set_list);
                TextView bTeamA = rowView.findViewById(R.id.badminton_team1);
                TextView bTeamB = rowView.findViewById(R.id.badminton_team2);
                ImageView bteamAserve = rowView.findViewById(R.id.badminton_team1_serve);
                ImageView bteamBserve = rowView.findViewById(R.id.badminton_team2_serve);
                GridView points = rowView.findViewById(R.id.badminton_team_point);

                int sets = Integer.parseInt(set.get(position));
                points.setNumColumns(sets);
                for(int i=0;i<sets*2;i++){
                    point.add("0");
                }


                badmintonPointAdapter adapter = new badmintonPointAdapter(context,point);
                points.setAdapter(adapter);

                blocation.setText(Ground.get(position) + " , " + city.get(position));
                bdate.setText(Date.get(position));
                bset.setText(set.get(position) + " Sets");
                bTeamA.setText(TeamA.get(position));
                bTeamB.setText(TeamB.get(position));

                bteamBserve.setVisibility(View.INVISIBLE);

                rowView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(auth.equals("Admin")){
                            Intent intent = new Intent(context, ScorecardActivity.class);
                            intent.putExtra("TeamA",TeamA.get(position));
                            intent.putExtra("TeamB",TeamB.get(position));
                            intent.putExtra("Tname",Tournament);
                            context.startActivity(intent);
                        }



                    }
                });



        }


        return rowView;
    }
}
