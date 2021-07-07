package com.example.allinonescore.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.allinonescore.Adapter.PointTableAdapter;
import com.example.allinonescore.DataBase.DataManagement;
import com.example.allinonescore.R;

import java.util.List;


public class PointTableFragment extends Fragment {

    View view;
    ListView listView;
    TextView text;
    ImageView image;
    String name;
    PointTableAdapter adapter;

    DataManagement data = new DataManagement();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_point_table, container, false);

        listView = view.findViewById(R.id.point_table_list);
        text = view.findViewById(R.id.notext);
        image = view.findViewById(R.id.nodata);

        name = getArguments().getString("Tname");

        data.getPointTable(name, new DataManagement.PointCallBack() {
            @Override
            public void onpointcallback(List<String> Team, List<String> matches, List<String> win, List<String> loss, List<String> tied) {

                if(!Team.isEmpty())
                {
                    text.setVisibility(View.INVISIBLE);
                    image.setVisibility(View.INVISIBLE);
                    adapter = new PointTableAdapter(PointTableFragment.this.getContext(),Team,matches,win,loss,tied);
                    listView.setAdapter(adapter);
                }else
                {
                    text.setVisibility(View.VISIBLE);
                    image.setVisibility(View.VISIBLE);
                }

            }
        });


        return view;
    }
}