package com.example.allinonescore.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.avatarfirst.avatargenlib.AvatarConstants;
import com.avatarfirst.avatargenlib.AvatarGenerator;
import com.bumptech.glide.Glide;
import com.example.allinonescore.AddPlayerActivity;
import com.example.allinonescore.Dialogs.TeamnameDialog;
import com.example.allinonescore.R;

import java.util.ArrayList;
import java.util.List;

public class TeamGridAdapter extends ArrayAdapter<String> {


    private List<String> teamname = new ArrayList<>();
    private Context context;
    private FragmentManager fragmentManager;
    String id;
    String T_name;

    public TeamGridAdapter(Context context, List<String> teamname,FragmentManager fragmentManager,String id,String T_name)
    {
        super(context, R.layout.grid_card_team,teamname);
        this.context = context;
        this.teamname = teamname;
        this.fragmentManager = fragmentManager;
        this.id = id;
        this.T_name = T_name;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View card_view =inflater.inflate(R.layout.grid_card_team, null,true);
        ImageView imageView = card_view.findViewById(R.id.grid_team_image);
        Button button = card_view.findViewById(R.id.grid_team_button);


            Glide.with(context).load("http://brokenfortest")
                .placeholder(AvatarGenerator.Companion.avatarImage(context,330,AvatarConstants.Companion.getRECTANGLE(),teamname.get(position)))
                .into(imageView);

            button.setText(teamname.get(position));


        card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(teamname.get(position).equals("ADD Teams"))
                {
                    openDialog();
                }
                else if(id.equals("Admin"))
                {
                    Intent intent = new Intent(context, AddPlayerActivity.class);
                    intent.putExtra("Team",teamname.get(position));
                    intent.putExtra("Tname",T_name);
                    context.startActivity(intent);
                }

            }
        });


        return card_view;
    }

    public void openDialog()
    {
        TeamnameDialog teamnameDialog = new TeamnameDialog();
        teamnameDialog.show(fragmentManager,"Add teams");
    }

}
