package com.example.allinonescore;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.allinonescore.DataBase.DataManagement;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

public class RegisterActivity extends AppCompatActivity {

    MaterialButton submit;
    EditText firstname,lastname,email;
    ChipGroup sport , gender;
    DataManagement data = new DataManagement();
    Chip Gender , Sport;
    String g1,s1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);

        sport = findViewById(R.id.chip_Sport);
        gender = findViewById(R.id.chip_Gender);
        firstname = findViewById(R.id.firstname);
        lastname = findViewById(R.id.lastname);
        email = findViewById(R.id.Email);
        submit = findViewById(R.id.button_submit);

        gender.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {

                Gender = group.findViewById(checkedId);
                if(Gender != null)
                {
                    g1 = Gender.getText().toString();
                }

            }
        });
        sport.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {

                Sport = group.findViewById(checkedId);
                if(Sport != null)
                {
                    s1 = Sport.getText().toString();
                }

            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        data.SetUserProfile(firstname.getText().toString()+ " " +lastname.getText().toString(),
                                g1,
                                s1,
                                email.getText().toString()
                        ).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);

                            }
                        });

                    }
                });
    }
}
