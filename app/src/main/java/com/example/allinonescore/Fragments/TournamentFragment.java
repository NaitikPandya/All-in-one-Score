package com.example.allinonescore.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.allinonescore.Adapter.TournamentAdapter;
import com.example.allinonescore.DataBase.DataManagement;
import com.example.allinonescore.R;

import java.util.List;

public class TournamentFragment extends Fragment {

    View view;
    TournamentAdapter adapter;
    DataManagement dataManagement = new DataManagement();
    ListView listView;
    ImageView imageView;
    int [] image = {R.mipmap.nothumbnail};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_list_tournament, container, false);

        listView = view.findViewById(R.id.tournamentlist);
        imageView = view.findViewById(R.id.nodata);

        dataManagement.getTournaments(new DataManagement.TournamentCallback() {
            @Override
            public void onCallback(List<String> Image,List<String> data, List<String> Sdate,List<String> Edate, List<String> Location) {
                if(data.isEmpty() || Sdate.isEmpty())
                {
                    imageView.setVisibility(View.VISIBLE);
                }
                else
                {
                    adapter = new TournamentAdapter(TournamentFragment.this.getContext(),Image,data,Location,Sdate,Edate,getFragmentManager(),"User");
                    listView.setAdapter(adapter);
                }

            }
        });
       /* listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = (String) parent.getItemAtPosition(position);
                Log.e("LIST DATA","I: " + position);
                Bundle bundle = new Bundle();
                bundle.putString("Tname",name);
                TeamFragment fragment = new TeamFragment();
                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.fragmentcontainer,fragment).addToBackStack("tournamentlist").commit();
            }
        });*/

        return view;
    }
}
