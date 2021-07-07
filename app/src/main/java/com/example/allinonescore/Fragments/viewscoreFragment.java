package com.example.allinonescore.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.example.allinonescore.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class viewscoreFragment extends Fragment {
    RecyclerView recyclerView;
    View view;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_viewscore, container, false);

        // get the listview
        expListView = (ExpandableListView) view.findViewById(R.id.lvExp);
        //recyclerView = view.findViewById(R.id.recyclerview);

        /*LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        TeamscorecardAdapter teamscorecardAdapter = new TeamscorecardAdapter(buildTeamList());
        recyclerView.setAdapter(teamscorecardAdapter);
        recyclerView.setLayoutManager(layoutManager);*/
        // preparing list data
        prepareListData();


        return view;
    }

    /*private List<TeamData> buildTeamList() {
        List<TeamData> itemList = new ArrayList<>();
        for (int i=0; i<10; i++) {
            TeamData item = new TeamData("Item "+i, buildSubItemList());
            itemList.add(item);
        }
        return itemList;
    }

    private List<SubTeam> buildSubItemList() {
        List<SubTeam> subItemList = new ArrayList<>();
        for (int i=0; i<3; i++) {
            SubTeam subItem = new SubTeam("Sub Item "+i, "Description "+i,"h"+i,"j"+i,"u"+i,"n"+i);
            subItemList.add(subItem);
        }
        return subItemList;
    }*/

    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Team 1");
        listDataHeader.add("Team 2");

        // Adding child data
        List<String> Team1 = new ArrayList<String>();
        Team1.add("The Shawshank Redemption");
        Team1.add("The Godfather");
        Team1.add("The Godfather: Part II");
        Team1.add("Pulp Fiction");
        Team1.add("The Good, the Bad and the Ugly");
        Team1.add("The Dark Knight");
        Team1.add("12 Angry Men");

        List<String> Team2 = new ArrayList<String>();
        Team2.add("The Conjuring");
        Team2.add("Despicable Me 2");
        Team2.add("Turbo");
        Team2.add("Grown Ups 2");
        Team2.add("Red 2");
        Team2.add("The Wolverine");

        listDataChild.put(listDataHeader.get(0), Team1); // Header, Child data
        listDataChild.put(listDataHeader.get(1), Team2);
    }

}