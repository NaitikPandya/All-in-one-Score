package com.example.allinonescore.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.allinonescore.Adapter.MatchListAdapter;
import com.example.allinonescore.AddmatchesActivity;
import com.example.allinonescore.DataBase.DataManagement;
import com.example.allinonescore.R;
import com.example.allinonescore.ScorecardActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.List;


public class MatchesFragment extends Fragment {

    MaterialButton button;
    View view;
    String Tname,Identity;
    ImageView empty;
    MatchListAdapter adapter;
    ExtendedFloatingActionButton add;
    DataManagement manage = new DataManagement();
    String SportName;
    Context context;
    ListView list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_matches, container, false);

        Tname = getArguments().getString("Tname");
        Identity = getArguments().getString("Identity");

        context = MatchesFragment.this.getContext();

        button = view.findViewById(R.id.add_matches);
        empty = view.findViewById(R.id.nodata);
        add = view.findViewById(R.id.float_add);
        list = view.findViewById(R.id.matches_list);

        manage.getSport(Tname, new DataManagement.GetSportCallBack() {
            @Override
            public void onSportCallBack(String sport) {
                SportName = sport;
                setadapter();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddmatchesActivity.class);
                intent.putExtra("Tournament",Tname);
                startActivity(intent);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddmatchesActivity.class);
                intent.putExtra("Tournament",Tname);
                startActivity(intent);
            }
        });

        if(Identity.equals("Admin"))
        {
            button.setVisibility(View.VISIBLE);
            empty.setVisibility(View.VISIBLE);
            add.setVisibility(View.INVISIBLE);
        }else{
            empty.setVisibility(View.VISIBLE);
        }


        return view;
    }
    public void setadapter(){

        switch (SportName){

            case "Cricket":
                manage.getMatches(Tname, new DataManagement.MatchCallback() {
                    @Override
                    public void onmatchcallback(List<String> TeamA, List<String> TeamB, List<String> Date, List<String> Location, List<String> over) {

                        if(!TeamA.isEmpty())
                        {
                            if(Identity.equals("Admin"))
                            {
                                button.setVisibility(View.INVISIBLE);
                                empty.setVisibility(View.INVISIBLE);
                                add.setVisibility(View.VISIBLE);
                            }else{
                                empty.setVisibility(View.INVISIBLE);
                            }
                            adapter = new MatchListAdapter(context,TeamA,TeamB,Date,over,Location,Tname,Identity,SportName);
                            list.setAdapter(adapter);
                        }

                    }
                });

            case "Badminton":
                manage.getBadmintonMatches(Tname, new DataManagement.GetBadmintonMatchCallBack() {
                    @Override
                    public void onBadmintonMatchCallBack(List<String> TeamA, List<String> Team, List<String> city, List<String> date, List<String> ground, List<String> set) {
                        if(!TeamA.isEmpty())
                        {
                            if(Identity.equals("Admin"))
                            {
                                button.setVisibility(View.INVISIBLE);
                                empty.setVisibility(View.INVISIBLE);
                                add.setVisibility(View.VISIBLE);
                            }else{
                                empty.setVisibility(View.INVISIBLE);
                            }
                            adapter = new MatchListAdapter(context,TeamA,Team,date,set,ground,city,Tname,Identity,SportName);
                            list.setAdapter(adapter);
                        }
                    }
                });

            default:

        }


    }
}