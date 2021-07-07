package com.example.allinonescore.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.allinonescore.Adapter.TournamentAdapter;
import com.example.allinonescore.AddTournamentActivity;
import com.example.allinonescore.DataBase.DataManagement;
import com.example.allinonescore.Dialogs.PlayerSelectDialog;
import com.example.allinonescore.MainActivity;
import com.example.allinonescore.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.List;

import static maes.tech.intentanim.CustomIntent.customType;

public class MytournamentFragment extends Fragment {

    View view;
    ListView listView;
    ImageView imageView;
    TournamentAdapter adapter;
    DataManagement dataManagement = new DataManagement();

    ExtendedFloatingActionButton button;
    Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list_mytournament,container,false);

        imageView = view.findViewById(R.id.nodata);
        button = view.findViewById(R.id.addtournament);
        listView = view.findViewById(R.id.tournamentlist);
        context = MytournamentFragment.this.getContext();


        dataManagement.getOtournament(new DataManagement.TournamentOcallback() {
            @Override
            public void OnTournamentOcallback(List<String> Image, List<String> data, List<String> Sdate , List<String> Edate, List<String> Location) {
                if(data.isEmpty() || Sdate.isEmpty())
                {
                    imageView.setVisibility(View.VISIBLE);
                    //adapter.clear();
                }
                else
                {
                    adapter = new TournamentAdapter(context,Image,data,Location,Sdate,Edate,getFragmentManager(),"Admin");
                    listView.setAdapter(adapter);
                }

            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), AddTournamentActivity.class);
                startActivity(intent);
                customType(getContext(),"fadein-to-fadeout");

            }
        });

        return view;
    }
}
