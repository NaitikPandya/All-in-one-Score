package com.example.allinonescore.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.allinonescore.R;

import java.util.ArrayList;
import java.util.List;

public class Cricketlist2 extends Fragment {

    View view;
    List<String> bowler = new ArrayList<>();
    ListView listView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.cricket_player_list2,container,false);

        listView = view.findViewById(R.id.Player_list2);

        bowler.add("b1");
        bowler.add("b2");
        bowler.add("b3");
        bowler.add("b4");
        bowler.add("b5");
        bowler.add("b6");
        bowler.add("b7");
        bowler.add("b8");
        bowler.add("b9");
        bowler.add("b10");
        bowler.add("b11");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,bowler);
        listView.setAdapter(adapter);

        return view;
    }

}
