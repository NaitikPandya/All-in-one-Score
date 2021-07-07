package com.example.allinonescore.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.allinonescore.Fragments.SearchplayerFragment;
import com.example.allinonescore.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    Context context;
    List<String> name = new ArrayList<>();
    List<String> sport = new ArrayList<>();
    String Team,T_name;
    public SearchAdapter(Context context, List<String> name, List<String> sport,String Team,String T_name) {
        this.context = context;
        this.name = name;
        this.sport = sport;
        this.Team = Team;
        this.T_name = T_name;
    }

    class SearchViewHolder extends RecyclerView.ViewHolder{

        TextView sname , ssport;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            sname = itemView.findViewById(R.id.search_name);
            ssport = itemView.findViewById(R.id.search_sport);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                        int position = getAdapterPosition();
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setTitle("Confirmation");
                                builder.setMessage("U want to Add ' " + name.get(position) + " ' in your team");
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Map<String,String> player = new HashMap<>();
                                        player.put("Name",name.get(position));
                                        FirebaseFirestore.getInstance()
                                                .collection("Tournaments")
                                                .document(T_name)
                                                .collection("Teams")
                                                .document(Team)
                                                .collection("Players")
                                                .add(player).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                                Toast.makeText(context,"Player Added",Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                });
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                        });
                    }
                }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_result_list_item,parent,false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        holder.sname.setText(name.get(position));
        holder.ssport.setText(sport.get(position));

    }


    @Override
    public int getItemCount() {
        return name.size();
    }
}
