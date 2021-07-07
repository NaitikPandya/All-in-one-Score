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

import com.example.allinonescore.DataBase.DataManagement;
import com.example.allinonescore.R;

import java.util.ArrayList;
import java.util.List;

public class Cricketlist1 extends Fragment {

    View view;
    List<String> batsman = new ArrayList<>();
    DataManagement data = new DataManagement();
    ListView listView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.cricket_player_list1,null,false);

        listView = view.findViewById(R.id.Player_list1);



        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,batsman);
        listView.setAdapter(adapter);

      return view;
    }

}
