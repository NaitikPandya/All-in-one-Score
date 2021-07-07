package com.example.allinonescore.Adapter;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.allinonescore.Fragments.playerFragment;
import com.example.allinonescore.Fragments.SearchplayerFragment;

public class PlayerPagerAdapter extends FragmentPagerAdapter {

    private Context myContext;
    int totalTabs;
    String team,tname;

    public PlayerPagerAdapter(Context context, FragmentManager fm, int totalTabs,String team,String tname) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
        this.team = team;
        this.tname = tname;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                playerFragment playerFragment = new playerFragment();
                Bundle data1 = new Bundle();
                data1.putString("Tname",tname);
                data1.putString("Team",team);
                playerFragment.setArguments(data1);
                return playerFragment;
            case 1:
                SearchplayerFragment searchplayerFragment = new SearchplayerFragment();
                Bundle data = new Bundle();
                data.putString("Tname",tname);
                data.putString("Team",team);
                searchplayerFragment.setArguments(data);
                return searchplayerFragment;
            default:
                return null;
        }
    }
    // this counts total number of tabs
    @Override
    public int getCount() {
        return totalTabs;
    }
}
