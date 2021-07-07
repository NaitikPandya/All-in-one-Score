package com.example.allinonescore.Fragments;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.allinonescore.Adapter.SearchAdapter;
import com.example.allinonescore.R;
import com.example.allinonescore.ScorecardActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class SearchplayerFragment extends Fragment {

    View view;
    TextInputLayout text;
    RecyclerView recyclerView;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    List<String> name = new ArrayList<>();
    List<String> sport = new ArrayList<>();
    SearchAdapter adapter;
    String Team,T_name;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_searchplayer, container, false);

        text = view.findViewById(R.id.Player_Search);
        recyclerView = view.findViewById(R.id.Player_Search_list);
        assert getArguments() != null;
        Team = getArguments().getString("Team");
        T_name = getArguments().getString("Tname");

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(this.getContext(), LinearLayoutManager.VERTICAL));

        text.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(!s.toString().isEmpty())
                {
                    SetAdapter(s.toString());
                }
                else
                {
                    name.clear();
                    sport.clear();
                    recyclerView.removeAllViews();
                }


            }
        });


        return view;
    }

    public void SetAdapter(String svalue)
    {
        firebaseFirestore.collection("User")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        name.clear();
                        sport.clear();
                        recyclerView.removeAllViews();
                        for(DocumentSnapshot doc : value.getDocuments())
                        {
                            String number = Objects.requireNonNull(doc.get("Number")).toString();

                            if(number.contains(svalue))
                            {
                                name.add(doc.get("Name").toString());
                                sport.add(doc.get("Sport").toString());
                            }
                        }
                        adapter = new SearchAdapter(SearchplayerFragment.this.getContext(),name,sport,Team,T_name);
                        recyclerView.setAdapter(adapter);
                    }
                });
    }


}