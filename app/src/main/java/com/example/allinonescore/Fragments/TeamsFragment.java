package com.example.allinonescore.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.allinonescore.Adapter.TeamGridAdapter;
import com.example.allinonescore.DataBase.DataManagement;
import com.example.allinonescore.Dialogs.TeamnameDialog;
import com.example.allinonescore.R;

import java.util.ArrayList;
import java.util.List;

public class TeamsFragment extends Fragment {

    View view;
    List<String> Teamname = new ArrayList<>();
    TeamGridAdapter adapter;
    GridView gridView;
    String T_name;
    String authentication;
    ImageView imageView;
    int count =0;
    DataManagement dataManagement = new DataManagement();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_teams, container, false);
        T_name = getArguments().getString("T_name");
        authentication = getArguments().getString("Identity");

       gridView = view.findViewById(R.id.team_gridview);
       imageView = view.findViewById(R.id.team_nodata);

       if(authentication.equals("Admin"))
       {
           dataManagement.getAdminTeams(T_name, new DataManagement.TeamsAdminCallback() {
               @Override
               public void onTeamCallback(List<String> team) {
                   adapter = new TeamGridAdapter(TeamsFragment.this.getContext(),team,getFragmentManager(),authentication,T_name);
                   gridView.setAdapter(adapter);
               }
           });
       }
       else
       {
           dataManagement.getUserTeams(T_name, new DataManagement.TeamsCallback() {
               @Override
               public void onTeamCallback(List<String> team) {

                   if(team.isEmpty())
                   {

                       imageView.setVisibility(View.VISIBLE);

                   }
                   adapter = new TeamGridAdapter(TeamsFragment.this.getContext(),team,getFragmentManager(),authentication,T_name);
                   gridView.setAdapter(adapter);

               }
           });
       }


        return view;
    }

}