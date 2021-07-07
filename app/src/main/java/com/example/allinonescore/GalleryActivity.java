package com.example.allinonescore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.allinonescore.Adapter.Galleryadapter;
import com.example.allinonescore.DataBase.DataManagement;

import java.util.ArrayList;
import java.util.List;

public class GalleryActivity extends AppCompatActivity {

    ListView listView;
    DataManagement data = new DataManagement();
    List<String> imagedata = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        listView = findViewById(R.id.Gallery_List);

        data.getImages(new DataManagement.ImageGet() {
            @Override
            public void onImageGet(List<String> image) {
                imagedata = image;
                Galleryadapter galleryadapter = new Galleryadapter(getApplicationContext(),image);
                listView.setAdapter(galleryadapter);

            }
        });



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent resultIntent = new Intent();
                resultIntent.putExtra("result", String.valueOf(imagedata.get(position)));
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

    }
}