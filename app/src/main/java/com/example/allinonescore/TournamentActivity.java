package com.example.allinonescore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.allinonescore.Adapter.HomePagerAdapter;
import com.example.allinonescore.Adapter.SingleTournamnetPagerAdapter;
import com.example.allinonescore.Adapter.TeamGridAdapter;
import com.example.allinonescore.DataBase.DataManagement;
import com.example.allinonescore.Dialogs.TeamnameDialog;
import com.example.allinonescore.Fragments.TeamsFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.util.zip.DataFormatException;

public class TournamentActivity extends AppCompatActivity implements TeamnameDialog.TeamnameDialogListner {

    TabLayout tabLayout;
    ViewPager viewPager;
    ImageView imageView,icon,icon_black;
    TextView name,date,tab_title,tab_color;
    CardView tab_card;
    TabItem item;
    int image[] = {R.mipmap.trophy_48px,R.mipmap.edit_column_100px,R.mipmap.jersey_60px,R.mipmap.more_info_64px};
    int blackimage[] = {R.mipmap.trophy_black_48px,R.mipmap.edit_column_black_100px,R.mipmap.jersey_black_60px,R.mipmap.more_info_black_64px};
    String tab_name[] = {"MATCHES","POINT TABLE","TEAMS","ABOUT"};
    String intent_name,intent_image,intent_date;
    DataManagement dataManagement = new DataManagement();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournament);
        tabLayout=(TabLayout)findViewById(R.id.inside_tournament_tabs);
        viewPager=(ViewPager)findViewById(R.id.inside_tournament_pager);
        imageView = findViewById(R.id.appbar_image);
        name = findViewById(R.id.t_name_app_bar);
        date = findViewById(R.id.date_app_bar);

        intent_name = getIntent().getStringExtra("name");
        intent_image = getIntent().getStringExtra("Image");
        intent_date = getIntent().getStringExtra("Date");

        name.setText(intent_name);
        date.setText(intent_date);
        tab_card  = tabLayout.getTabAt(0).getCustomView().findViewById(R.id.tab_card);
        tab_card.setCardBackgroundColor(getResources().getColor(R.color.purple_500));
        tab_title = tabLayout.getTabAt(0).getCustomView().findViewById(R.id.tab_text);
        tab_title.setText(tab_name[0]);
        tab_title.setTextColor(Color.WHITE);
        for(int i =1;i<4;i++)
        {
            tab_title = tabLayout.getTabAt(i).getCustomView().findViewById(R.id.tab_text);
            tab_title.setText(tab_name[i]);
            tab_title.setTextColor(Color.BLACK);
            icon_black = tabLayout.getTabAt(i).getCustomView().findViewById(R.id.tab_icon);
            icon_black.setImageResource(blackimage[i]);
        }



        String identity = getIntent().getStringExtra("Identity");

        final SingleTournamnetPagerAdapter adapter = new SingleTournamnetPagerAdapter(this,getSupportFragmentManager(), tabLayout.getTabCount(),intent_name,identity);
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab_color = tab.getCustomView().findViewById(R.id.tab_text);
                tab_color.setTextColor(Color.WHITE);
                icon = tab.getCustomView().findViewById(R.id.tab_icon);
                icon.setImageResource(image[tab.getPosition()]);
                tab_card = tab.getCustomView().findViewById(R.id.tab_card);
                tab_card.setCardBackgroundColor(getResources().getColor(R.color.purple_500));
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

                tab_color = tab.getCustomView().findViewById(R.id.tab_text);
                tab_color.setTextColor(Color.BLACK);
                icon = tab.getCustomView().findViewById(R.id.tab_icon);
                icon.setImageResource(blackimage[tab.getPosition()]);
                tab_card = tab.getCustomView().findViewById(R.id.tab_card);
                tab_card.setCardBackgroundColor(Color.WHITE);

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        final Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        AppBarLayout appBarLayout = findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);

        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapse_toolbar);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.BaseOnOffsetChangedListener() {
            boolean isshow = false;
            int scrollrange = -1;
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if(scrollrange == -1)
                {
                    Glide.with(getApplicationContext()).load(intent_image).into(imageView);
                    scrollrange = appBarLayout.getTotalScrollRange();
                }
                if(verticalOffset < -200)
                {
                    collapsingToolbarLayout.setTitle(intent_name);
                    collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
                    isshow = false;
                }
                else
                {
                    collapsingToolbarLayout.setTitle("");
                    isshow = true;
                }
            }
        });

    }

    @Override
    public void applyTexts(String teamname) {
        Log.e("Tounament Activity" , teamname);
        dataManagement.setTeams(teamname,intent_name).addOnSuccessListener(aVoid -> Toast.makeText(TournamentActivity.this,"Team Added",Toast.LENGTH_SHORT).show());

    }
}