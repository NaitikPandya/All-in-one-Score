package com.example.allinonescore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.allinonescore.Fragments.HomeFragment;
import com.example.allinonescore.Fragments.ProflieFiledsFragment;
import com.example.allinonescore.Fragments.viewscoreFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static maes.tech.intentanim.CustomIntent.customType;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {
    private DrawerLayout drawer;
    TextView number,name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Intent service = new Intent(this, notificationService.class);
        //startForegroundService(service);

        //toolbar

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleMarginStart(250);
        setSupportActionBar(toolbar);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        //drawerLayout

        drawer = findViewById(R.id.drawer_layout);

        //Navigation_View

        NavigationView navigationView = findViewById(R.id.navview);
        View headerview = navigationView.getHeaderView(0);
        number = headerview.findViewById(R.id.Drawer_number);
        name = headerview.findViewById(R.id.Drawer_name);
        name.setText(user.getDisplayName());
        number.setText(user.getPhoneNumber());
        headerview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ProfileActivity.class);
                startActivity(intent);
                customType(MainActivity.this,"left-to-right");
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();



        //I added this if statement to keep the selected fragment when rotating the device
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontainer,
                    new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_Home);
        }
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_Home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontainer,
                        new HomeFragment()).commit();
                break;
            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontainer,
                        new viewscoreFragment()).commit();
                break;
            case R.id.nav_Booking:


                break;
            case R.id.nav_Help:
                Toast.makeText(this, "Help_Support", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_ContactUs:
                Toast.makeText(this, "ContactUs", Toast.LENGTH_SHORT).show();
                break;
            case R.id.Logout:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            case R.id.Register:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontainer,
                        new ProflieFiledsFragment()).commit();
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void notificationcall(String name)
    {
        Log.e("MAIN ACTIVITY","ENTER");

    }
}