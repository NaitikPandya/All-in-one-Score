package com.example.allinonescore;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.allinonescore.DataBase.DataManagement;


public class ProfileActivity extends AppCompatActivity {

   View view;
   TextView Name,Email,Gender,Sport;
   DataManagement dataManagement = new DataManagement();
   ProgressBar bar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Name = findViewById(R.id.Profile_name);
        Email = findViewById(R.id.Profile_Email);
        Gender = findViewById(R.id.Profile_Gender);
        Sport = findViewById(R.id.Profile_Sport);
        bar = findViewById(R.id.Profile_progress);

        dataManagement.getUserProfile(new DataManagement.UserProfile() {
            @Override
            public void onUserProfileCallback(String name, String email, String gender, String sport) {

                bar.setVisibility(View.INVISIBLE);

                Name.setText(name);
                Email.setText(email);
                Gender.setText(gender);
                Sport.setText(sport);
            }
        });
        
        
    }
}