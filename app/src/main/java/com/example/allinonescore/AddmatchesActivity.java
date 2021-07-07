package com.example.allinonescore;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.avatarfirst.avatargenlib.AvatarConstants;
import com.avatarfirst.avatargenlib.AvatarGenerator;
import com.bumptech.glide.Glide;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.allinonescore.DataBase.DataManagement;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AddmatchesActivity extends AppCompatActivity {

    View view;
    FloatingActionButton teamA,teamB;
    TextView teamAname,teamBname,TeamVs;
    RelativeLayout item;
    LinearLayout form , badminton,basketball;
    //cricket
    TextInputLayout over,bover,city,ground,Date;
    //basketball
    TextInputLayout basketball_match_time, basketball_final_time,bb_city,bb_ground,bb_date;
    //badminton
    TextInputLayout badset,badpoint,badsetfinal,badpointfinal,bm_city,bm_ground,bm_date;
    MaterialButton submit;
    Spinner balltype;
    DataManagement date = new DataManagement();
    ImageView image_teamA,image_teamB;
    String Tournament,check="over";
    String[] finalTeams;
    String [] stradapter = {"Tennis Ball","Leather Ball","Pink Ball"};
    String SportName;
    Animation ani;
    Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_matches);

        Tournament = getIntent().getStringExtra("Tournament");

        balltype = (Spinner)findViewById(R.id.balltype);

        badminton = findViewById(R.id.badminton_info);
        basketball = findViewById(R.id.basketball_info);

        ani = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,stradapter);
        balltype.setAdapter(adapter);

        LocalDate time = LocalDate.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        dateTimeFormatter.format(time);

        over = findViewById(R.id.Total_over);
        bover = findViewById(R.id.over_bowler);
        city = findViewById(R.id.match_city);
        ground = findViewById(R.id.match_ground);
        Date = findViewById(R.id.match_Date);

        Date.getEditText().setText(time.toString());

        teamA = findViewById(R.id.Team_a_button);
        teamB = findViewById(R.id.Team_b_button);
        item = findViewById(R.id.Team_Select_page);
        form = findViewById(R.id.match_form);
        submit = findViewById(R.id.submit_match);
        image_teamA = findViewById(R.id.image_teamA);
        image_teamB = findViewById(R.id.image_teamB);
        TeamVs = findViewById(R.id.Teamvs);
        teamAname = findViewById(R.id.TeamA_text);
        teamBname = findViewById(R.id.TeamB_text);

        basketball_match_time = findViewById(R.id.timeoffootball);
        basketball_final_time = findViewById(R.id.timeoffootballfinal);
        bb_city = findViewById(R.id.basketball_match_city);
        bb_date = findViewById(R.id.basketball_match_Date);
        bb_ground = findViewById(R.id.basketball_match_ground);

        bb_date.getEditText().setText(time.toString());

        badset = findViewById(R.id.noofbadset);
        badpoint = findViewById(R.id.noofbadpoint);
        badsetfinal = findViewById(R.id.noofbadsetfinal);
        badpointfinal = findViewById(R.id.noofbadpointfinal);
        bm_city = findViewById(R.id.badminton_match_city);
        bm_ground = findViewById(R.id.badminton_match_ground);
        bm_date = findViewById(R.id.badminton_match_Date);

        bm_date.getEditText().setText(time.toString());

        date.getSport(Tournament, new DataManagement.GetSportCallBack() {
            @Override
            public void onSportCallBack(String sport) {
                SportName = sport;
            }
        });

        YoYo.with(Techniques.Pulse)
                .repeat(YoYo.INFINITE)
                .pivot(50,50)
                .playOn(teamA);

        YoYo.with(Techniques.Pulse)
                .repeat(YoYo.INFINITE)
                .pivot(50,50)
                .playOn(teamB);

        teamA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(AddmatchesActivity.this);
                builder.setTitle("Select TeamA");

                date.getTeams(Tournament, new DataManagement.TeamsUserCallback() {
                    @Override
                    public void onTeamuserCallback(List<String> team) {
                        List<String> test = new ArrayList<String>();
                        test = team;
                        String[] teams = new String[team.size()];
                        teams = test.toArray(teams);
                         finalTeams = teams;
                        builder.setItems(finalTeams, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                teamAname.setText(finalTeams[which]);
                                teamA.setVisibility(View.INVISIBLE);
                                Glide.with(AddmatchesActivity.this).load("http://brokenfortest")
                                        .placeholder(AvatarGenerator.Companion.avatarImage(AddmatchesActivity.this,200, AvatarConstants.Companion.getCIRCLE(),finalTeams[which]))
                                        .into(image_teamA);
                                teamAname.animate().translationY(100);
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                });



            }
        });

        teamB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(AddmatchesActivity.this);
                builder.setTitle("Select TeamB");

                date.getTeams(Tournament, new DataManagement.TeamsUserCallback() {
                    @Override
                    public void onTeamuserCallback(List<String> team) {
                        List<String> test = new ArrayList<String>();
                        test = team;
                        String[] teams = new String[team.size()];
                        teams = test.toArray(teams);
                        finalTeams = teams;
                        builder.setItems(finalTeams, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                teamBname.setText(finalTeams[which]);
                                teamB.setVisibility(View.INVISIBLE);
                                Glide.with(AddmatchesActivity.this).load("http://brokenfortest")
                                        .placeholder(AvatarGenerator.Companion.avatarImage(AddmatchesActivity.this,200, AvatarConstants.Companion.getCIRCLE(),finalTeams[which]))
                                        .into(image_teamB);
                                teamBname.animate().translationY(100);
                                if(teamAname.getText().toString() != "Team A" && teamBname.getText().toString() != "Team B")
                                {

                                    //date.addMatches(teamAname.getText().toString(),teamBname.getText().toString(),Tournament);
                                    item.animate().rotation(-90).translationY(-850).setDuration(1500);
                                    teamAname.animate().rotation(90).translationY(-95).translationX(-130).setDuration(1500);
                                    teamBname.animate().rotation(90).translationY(-100).translationX(-130).setDuration(1500);
                                    image_teamA.animate().rotation(90).setDuration(1500);
                                    image_teamB.animate().rotation(90).setDuration(1500);
                                    TeamVs.animate().rotation(90).translationY(-50).setDuration(1500);
                                    selectform();
                                }
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                });



            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (SportName){

                    case "Cricket":
                        date.addCricketMatches(
                                teamAname.getText().toString(),
                                teamBname.getText().toString(),
                                Tournament,
                                over.getEditText().getText().toString(),
                                bover.getEditText().getText().toString(),
                                city.getEditText().getText().toString(),
                                ground.getEditText().getText().toString(),
                                Date.getEditText().getText().toString(),
                                stradapter[balltype.getSelectedItemPosition()]
                        );
                        finish();
                        break;

                    case "Badminton":
                        date.addBadmintonMatches(
                                Tournament,
                                teamAname.getText().toString(),
                                teamBname.getText().toString(),
                                badset.getEditText().getText().toString(),
                                badpoint.getEditText().getText().toString(),
                                badsetfinal.getEditText().getText().toString(),
                                badpointfinal.getEditText().getText().toString(),
                                bm_date.getEditText().getText().toString(),
                                bm_city.getEditText().getText().toString(),
                                bm_ground.getEditText().getText().toString()
                        );
                        finish();
                        break;

                    case "Basketball":
                        basketball.startAnimation(ani);
                        basketball.setVisibility(View.VISIBLE);
                        submit.startAnimation(ani);
                        submit.setVisibility(View.VISIBLE);
                        break;
                    default:
                        Toast.makeText(AddmatchesActivity.this,"Check Your Tournament Setting Properly And add Sport of the Tournament",Toast.LENGTH_SHORT).show();


                }
                finish();
            }
        });


    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_button_1:
                if (checked) {
                    check = "over";
                    break;
                }
            case R.id.radio_button_2:
                if (checked) {
                    check = "days";
                    over.setHint("No. of Days");
                    break;
                }
        }
    }

    public void selectform(){

        switch (SportName){

            case "Cricket":
                form.startAnimation(ani);
                form.setVisibility(View.VISIBLE);
                submit.startAnimation(ani);
                submit.setVisibility(View.VISIBLE);
                break;

            case "Badminton":
                badminton.startAnimation(ani);
                badminton.setVisibility(View.VISIBLE);
                submit.startAnimation(ani);
                submit.setVisibility(View.VISIBLE);
                break;

            case "Basketball":
                basketball.startAnimation(ani);
                basketball.setVisibility(View.VISIBLE);
                submit.startAnimation(ani);
                submit.setVisibility(View.VISIBLE);
                break;
            default:
                Toast.makeText(this,"Check Your Tournament Setting Properly And add Sport of the Tournament",Toast.LENGTH_SHORT).show();


        }


    }
    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        switch(view.getId()) {
            case R.id.checkboxforfinal:
                if (checked){
                    findViewById(R.id.noofbadsetfinal).setVisibility(View.VISIBLE);
                    findViewById(R.id.noofbadpointfinal).setVisibility(View.VISIBLE);
                }
                else{
                    findViewById(R.id.noofbadsetfinal).setVisibility(View.GONE);
                    findViewById(R.id.noofbadpointfinal).setVisibility(View.GONE);
                }
        }
    }
    public void onbasketballCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        switch(view.getId()) {
            case R.id.checkboxforfootballfinal:
                if (checked){
                    findViewById(R.id.timeoffootballfinal).setVisibility(View.VISIBLE);
                }
                else{
                    findViewById(R.id.timeoffootballfinal).setVisibility(View.GONE);
                }
        }
    }

}
