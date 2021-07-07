package com.example.allinonescore;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.allinonescore.DataBase.DataManagement;
import com.example.allinonescore.Dialogs.imageselectDialog;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static maes.tech.intentanim.CustomIntent.customType;

public class AddTournamentActivity extends AppCompatActivity implements imageselectDialog.ImageDialogListner {
    public RadioButton rt1,rt2;
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int Galley_Image = 2;
    private ImageView mImageView;
    public Uri mImageUri;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DataManagement data = new DataManagement();
    private int mYear, mMonth, mDay, mHour, mMinute;
    DatePickerDialog picker;
    String date;
    Timestamp sdate,edate;
    String result = "empty";
    TextInputLayout tx1,tx2,tx3,tx4,tx5,tx6;
    Spinner sports;
    ScrollView sview;
    imageselectDialog image = new imageselectDialog();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tournament);
        rt1 = (RadioButton) findViewById(R.id.radio_button_1);
        rt2 = (RadioButton) findViewById(R.id.radio_button_2);
        mImageView = (ImageView) findViewById(R.id.UploadImage);
        tx1 = (TextInputLayout) findViewById(R.id.outlinedTextField6);
        tx2 = (TextInputLayout) findViewById(R.id.outlinedTextField7);
        tx3 = (TextInputLayout) findViewById(R.id.outlinedTextField);
        tx4 = (TextInputLayout) findViewById(R.id.outlinedTextField1);
        tx5 = (TextInputLayout) findViewById(R.id.outlinedTextField2);
        tx6 = (TextInputLayout) findViewById(R.id.outlinedTextField3);
        tx6.getEditText().setText(user.getPhoneNumber());
        tx5.getEditText().setText(user.getDisplayName());
        sview = findViewById(R.id.Activity_tournament);
        sports = (Spinner)findViewById(R.id.Spinner);
        String [] sport = {"Cricket","Badminton","Basketball"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,sport);
        sports.setAdapter(adapter);
    }
    public void uploadimage(View view) {

        image.show(getSupportFragmentManager(),"Select Dialog");
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();
            Glide.with(this).load(mImageUri).into(mImageView);
        }
        if (requestCode == Galley_Image) {
            if (resultCode == RESULT_OK) {
                result = data.getStringExtra("result");
                Glide.with(this).load(result).into(mImageView);

            }

        }
    }

    public void StartDate(View view) {
        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        // date picker dialog
        picker = new DatePickerDialog(AddTournamentActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        Date dt = null;
                        try {
                            dt = sdf.parse(date);
                            sdate = new Timestamp(dt);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        tx1.getEditText().setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                }, year, month, day);
        picker.show();
    }

    public void EndDate(View view) {
        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        // date picker dialog
        picker = new DatePickerDialog(AddTournamentActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        Date dt = null;
                        try {
                            dt = sdf.parse(date);
                            edate = new Timestamp(dt);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        tx2.getEditText().setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                }, year, month, day);
        picker.show();
    }

    public void Next(View view) {
        Intent i1;

        if(tx1.getEditText().getText().toString().isEmpty() ||
                tx2.getEditText().getText().toString().isEmpty() ||
                tx3.getEditText().getText().toString().isEmpty() ||
                tx4.getEditText().getText().toString().isEmpty()||
                tx5.getEditText().getText().toString().isEmpty() ||
                tx6.getEditText().getText().toString().isEmpty() ||
                sports.getSelectedItem().toString().isEmpty() ||
                ( mImageUri == null && result.equals("empty") ) )
        {
            Snackbar snackbar = Snackbar.make(sview, "Fill The Data", Snackbar.LENGTH_INDEFINITE)
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    })
                    .setActionTextColor(Color.RED);
            snackbar.show();

        }
        else
        {
            if(mImageUri != null)
            {
                data.SetTournament(tx3.getEditText().getText().toString(),
                        tx4.getEditText().getText().toString(),
                        tx5.getEditText().getText().toString(),
                        tx6.getEditText().getText().toString(),
                        sdate,
                        edate,
                        sports.getSelectedItem().toString(),
                        mImageUri);
            }
            else
            {
                data.SetStringTournament(tx3.getEditText().getText().toString(),
                        tx4.getEditText().getText().toString(),
                        tx5.getEditText().getText().toString(),
                        tx6.getEditText().getText().toString(),
                        sdate,
                        edate,
                        sports.getSelectedItem().toString(),
                        result);
            }


            i1 = new Intent( AddTournamentActivity.this,MainActivity.class);
            startActivity(i1);
            customType(AddTournamentActivity.this,"right-to-left");
            finish();
        }

    }

    @Override
    public void applyTexts(String choice) {

        if(choice.equals("Device"))
        {
            image.dismiss();
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        }
        else
        {
            image.dismiss();
            Intent intent = new Intent(AddTournamentActivity.this,GalleryActivity.class);
            startActivityForResult(intent,Galley_Image);

        }

    }
}