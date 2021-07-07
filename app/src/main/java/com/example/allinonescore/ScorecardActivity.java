package com.example.allinonescore;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.avatarfirst.avatargenlib.AvatarConstants;
import com.avatarfirst.avatargenlib.AvatarGenerator;
import com.bumptech.glide.Glide;
import com.example.allinonescore.Adapter.overRecyclerAdapter;
import com.example.allinonescore.Data.OverData;
import com.example.allinonescore.DataBase.DataManagement;
import com.example.allinonescore.Dialogs.PlayerSelectDialog;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class ScorecardActivity extends AppCompatActivity {

    View view;
    ImageView run0,run1,run2,run3,run4,run5,run6,run7;
    TextView name,s_score,s_over;
    CheckBox wide,noball,byes,legbyes,wicket;
    TextView p1name , p2name ,bowler;
    DataManagement dataManagement = new DataManagement();
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    ArrayList<OverData> data = new ArrayList<>();
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    String Tname;
    int over = 0 , type,balls;
    String[] finalTeams = new String[2];
    String [] player1;
    String [] player2;
    boolean[] check;
    AlertDialog dialog1;
    int select,flag1= 0,flag2=0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scorecard);
        name = findViewById(R.id.s_team);
        Tname = getIntent().getStringExtra("Tname");
        p1name = findViewById(R.id.s_player1);
        p2name = findViewById(R.id.s_player2);
        bowler = findViewById(R.id.s_bowler);



        AlertDialog.Builder builder = new AlertDialog.Builder(ScorecardActivity.this);
        builder.setTitle("Select Team To Score");
                finalTeams[0] = getIntent().getStringExtra("TeamA");
                finalTeams[1] = getIntent().getStringExtra("TeamB");

                builder.setItems(finalTeams, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        name.setText(finalTeams[which]);
                        select = which;
                        setScore();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                dataManagement.getPlayers(Tname, name.getText().toString(), new DataManagement.PlayerCallBack() {
                    @Override
                    public void onPlayerCallBack(List<String> Players) {
                        if(!Players.isEmpty()){
                            check = new boolean[Players.size()];
                            player1 = Players.toArray(new String[0]);
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(ScorecardActivity.this);
                            builder1.setTitle("Select Team " + finalTeams[select] + " Batsman");
                            builder1.setMultiChoiceItems(player1, check, new DialogInterface.OnMultiChoiceClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                    if(isChecked && flag1 == 0)
                                    {
                                        p1name.setText(player1[which]);
                                        flag1 = 1;

                                    }else if(isChecked && flag2 == 0)
                                    {
                                        p2name.setText(player1[which]);
                                        flag2 = 1;
                                    }
                                }
                            });
                            dialog1 = builder1.create();
                            dialog1.show();

                        }
                    }
                });

            }
        });





        recyclerView = findViewById(R.id.O_score_recycler);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new overRecyclerAdapter(data);
        recyclerView.setAdapter(adapter);


        s_score = findViewById(R.id.s_score);
        s_over = findViewById(R.id.s_over);
        wide = findViewById(R.id.Wide);
        noball = findViewById(R.id.Noball);
        byes = findViewById(R.id.byes);
        legbyes = findViewById(R.id.legbyes);
        wicket = findViewById(R.id.Wicket);

        run0 = findViewById(R.id.run0);
        run1 = findViewById(R.id.run1);
        run2= findViewById(R.id.run2);
        run3 = findViewById(R.id.run3);
        run4 = findViewById(R.id.run4);
        run5 = findViewById(R.id.run5);
        run6 = findViewById(R.id.run6);
        run7 = findViewById(R.id.run7);

        run0.setOnClickListener(v -> runclick(0));
        run1.setOnClickListener(v -> runclick(1));
        run2.setOnClickListener(v -> runclick(2));
        run3.setOnClickListener(v -> runclick(3));
        run4.setOnClickListener(v -> runclick(4));
        run5.setOnClickListener(v -> runclick(5));
        run6.setOnClickListener(v -> runclick(6));
        run7.setOnClickListener(v -> runclick(7));

    }

    public void setScore()
    {
        firebaseFirestore.collection("Tournaments").document(Tname)
                .collection("Teams").document(name.getText().toString())
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        String score = String.valueOf(value.get("Score")) + "-" + String.valueOf(value.get("Wicket"));
                        String over = "(" + String.valueOf(value.get("Over")) + ")";
                        double ball = Double.parseDouble(String.valueOf(value.get("Over")));
                         type = (int) ball;
                         balls = Integer.parseInt(Objects.requireNonNull(value.get("Over")).toString().split("\\.")[1]);
                        Log.e("SCORE", "I : " + balls);
                        s_score.setText(score);
                        s_over.setText(over);
                    }
                });
    }

    void runclick(int run)
    {
       if(over<5)
       {
           if(wicket.isChecked())
           {
               int i = adapter.getItemCount();
               dataManagement.updateScore(run,1,name.getText().toString(),Tname,type,balls);
               data.add(i,new OverData(run,"W"));
               adapter.notifyDataSetChanged();
               wicket.setChecked(false);
               over++;
           }
           else if(wide.isChecked())
           {
               int i = adapter.getItemCount();
               data.add(i,new OverData(run,"Wd"));
               adapter.notifyDataSetChanged();
               dataManagement.updateScore(run,0,name.getText().toString(),Tname,type,balls);
               wide.setChecked(false);
               over++;
           }else if(noball.isChecked())
           {
               int i = adapter.getItemCount();
               data.add(i,new OverData(run,"NB"));
               adapter.notifyDataSetChanged();
               dataManagement.updateScore(run,0,name.getText().toString(),Tname,type,balls);
               noball.setChecked(false);
               over++;
           }else if(legbyes.isChecked()){
               int i = adapter.getItemCount();
               data.add(i,new OverData(run,"LB"));
               adapter.notifyDataSetChanged();
               dataManagement.updateScore(run,0,name.getText().toString(),Tname,type,balls);
               legbyes.setChecked(false);
               over++;
           }else if(byes.isChecked()){
               int i = adapter.getItemCount();
               data.add(i,new OverData(run,"B"));
               adapter.notifyDataSetChanged();
               dataManagement.updateScore(run,0,name.getText().toString(),Tname,type,balls);
               byes.setChecked(false);
               over++;
           }else{
               int i = adapter.getItemCount();
               data.add(i,new OverData(run,"R"));
               adapter.notifyDataSetChanged();
               dataManagement.updateScore(run,0,name.getText().toString(),Tname,type,balls);
               over++;
           }
       }else{
           dataManagement.updateScore(run,0,name.getText().toString(),Tname,type,balls);
           data.clear();
           adapter.notifyDataSetChanged();
           over = 0;
       }
    }
}