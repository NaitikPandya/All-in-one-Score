package com.example.allinonescore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

import com.example.allinonescore.Adapter.HomePagerAdapter;
import com.example.allinonescore.Adapter.PlayerPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class AddPlayerActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    String team,T_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_player);

        tabLayout=(TabLayout)findViewById(R.id.PlayerTab);
        viewPager=(ViewPager)findViewById(R.id.PlayerPager);

        team = getIntent().getStringExtra("Team");
        T_name = getIntent().getStringExtra("Tname");

        final PlayerPagerAdapter adapter = new PlayerPagerAdapter(getApplicationContext(),getSupportFragmentManager(), tabLayout.getTabCount(),team,T_name);
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}