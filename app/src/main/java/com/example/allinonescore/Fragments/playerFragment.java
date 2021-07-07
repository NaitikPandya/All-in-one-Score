package com.example.allinonescore.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.allinonescore.DataBase.DataManagement;
import com.example.allinonescore.R;

import java.util.List;


public class playerFragment extends Fragment {

    View view;
    ListView listView;
    ImageView imageView;
    String Team,T_name;
    DataManagement dataManagement = new DataManagement();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.fragment_player, container, false);

        listView = view.findViewById(R.id.playerlist);
        imageView = view.findViewById(R.id.image_none);

        Team = getArguments().getString("Team");
        T_name = getArguments().getString("Tname");

        dataManagement.getPlayers(T_name, Team, new DataManagement.PlayerCallBack() {
            @Override
            public void onPlayerCallBack(List<String> Players) {
                if(Players.isEmpty()){
                    imageView.setVisibility(View.VISIBLE);
                }else{
                    String name [] = Players.toArray(new String[0]);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(playerFragment.this.getContext(),R.layout.player_list_item,R.id.player_name,name);
                    listView.setAdapter(adapter);
                    imageView.setVisibility(View.INVISIBLE);
                }
            }
        });


        return  view;
    }
}