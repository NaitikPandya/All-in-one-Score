package com.example.allinonescore.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.example.allinonescore.DataBase.DataManagement;
import com.example.allinonescore.R;
import com.example.allinonescore.ScorecardActivity;
import com.example.allinonescore.Service.notificationService;
import com.example.allinonescore.TournamentActivity;
import com.google.android.material.chip.Chip;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TournamentAdapter  extends ArrayAdapter<String> {

        private Context context;
        private List<String> imageurl;
        private List<String> name;
        private List<String> Location;
        private List<String> SDate;
        private List<String> EDate;
        String team1,team2;
        String[] teams;
        int fill = R.mipmap.alarm_filled_50px;
        int icon = R.mipmap.alarm_50px;
        DataManagement dataManagement = new DataManagement();
        String identity;
        FragmentManager fragmentManager;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public TournamentAdapter(Context context1, List<String> imageurl, List<String> name, List<String> Location, List<String> SDate,List<String> EDate,FragmentManager fragmentManager,String identity) {
        super(context1, R.layout.item_tournament_list,name);

        this.context = context1;
        this.imageurl = imageurl;
        this.name = name;
        this.Location = Location;
        this.SDate = SDate;
        this.EDate = EDate;
        this.fragmentManager = fragmentManager;
        this.identity = identity;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View rowView=inflater.inflate(R.layout.item_tournament_list, null,true);

        TextView titleText = (TextView) rowView.findViewById(R.id.Title);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.banner);
        TextView Sdate = (TextView) rowView.findViewById(R.id.Sdate);
        Chip status = rowView.findViewById(R.id.statuschip);
        TextView location = (TextView) rowView.findViewById(R.id.location);
        ImageButton notification = rowView.findViewById(R.id.notification);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date dt = null;
        java.util.Date td = null;
        java.util.Date currentdate = new Date();
        try {
            dt = sdf.parse(SDate.get(position));
            td = sdf.parse(EDate.get(position));

            com.google.firebase.Timestamp temp1 = new com.google.firebase.Timestamp(dt);
            com.google.firebase.Timestamp cdate = new com.google.firebase.Timestamp(currentdate);
            com.google.firebase.Timestamp temp2 = new Timestamp(td);

            if(temp2.compareTo(cdate) < 0)
            {
                status.setText("End");
                status.setChipBackgroundColor(ColorStateList.valueOf(Color.RED));
            }else if(temp2.compareTo(cdate) > 0 && temp1.compareTo(cdate) > 0 )
            {
                status.setText("Up Coming");
            }
            else
            {
                status.setText("On Going");
                status.setChipBackgroundColor(ColorStateList.valueOf(Color.GREEN));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        dataManagement.getfollow(new DataManagement.FollowCallBack() {
            @Override
            public void onfollowCallback(String Name) {
                if(Name.equals(name.get(position)))
                {

                    notification.setImageResource(fill);
                }
            }
        });

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dataManagement.getfollow(new DataManagement.FollowCallBack() {
                    @Override
                    public void onfollowCallback(String Name) {
                        if(Name.equals(name.get(position))){

                            Toast.makeText(context,"U are already Following the Tournament",Toast.LENGTH_SHORT).show();
                        }else{
                            dataManagement.getMatches(name.get(position), new DataManagement.MatchCallback() {
                                @Override
                                public void onmatchcallback(List<String> TeamA, List<String> TeamB, List<String> Date, List<String> Location, List<String> over) {

                                    teams = new String[TeamA.size()];
                                    for(int i = 0;i<TeamA.size();i++){
                                        teams[i] = TeamA.get(i) + " V/s " + TeamB.get(i);
                                    }
                                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                    builder.setTitle("Select Match");

                                    builder.setItems(teams, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            team1 = TeamA.get(which);
                                            team2 = TeamB.get(which);
                                        }
                                    });
                                    AlertDialog dialog = builder.create();
                                    dialog.show();
                                    dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                        @Override
                                        public void onDismiss(DialogInterface dialog) {
                                            if(notification.getDrawable().getConstantState() == context.getResources().getDrawable(icon).getConstantState())
                                            {
                                                FirebaseFirestore.getInstance().collection("User")
                                                        .document(user.getUid())
                                                        .update("Follow",name.get(position));
                                                notification.setImageResource(fill);
                                                Intent service = new Intent(context, notificationService.class);
                                                service.putExtra("Name",name.get(position));
                                                service.putExtra("Team1",team1);
                                                service.putExtra("Team2",team2);
                                                context.startService(service);


                                            }
                                            else if(notification.getDrawable().getConstantState() == context.getResources().getDrawable(fill).getConstantState())
                                            {
                                                notification.setImageResource(icon);
                                            }
                                        }
                                    });
                                }
                            });

                        }
                    }
                });


            }
        });

        titleText.setText(name.get(position));
        Glide.with(context).load(imageurl.get(position)).into(imageView);
        Sdate.setText(SDate.get(position) + " To " + EDate.get(position));
        location.setText(Location.get(position));

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TournamentActivity.class);
                intent.putExtra("name",name.get(position));
                intent.putExtra("Image",imageurl.get(position));
                intent.putExtra("Date", SDate.get(position) + " To " + EDate.get(position));
                intent.putExtra("Identity",identity);
                context.startActivity(intent);

            }
        });

        return rowView;

    };
}
