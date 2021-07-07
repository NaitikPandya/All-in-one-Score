package com.example.allinonescore.Adapter;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.allinonescore.Fragments.MytournamentFragment;
import com.example.allinonescore.Fragments.TournamentFragment;

public class HomePagerAdapter extends FragmentPagerAdapter {

    private Context myContext;
    int totalTabs;

    public HomePagerAdapter(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                MytournamentFragment mytournamentFragment = new MytournamentFragment();
                return mytournamentFragment;
            case 1:
                TournamentFragment tournamentFragment = new TournamentFragment();
                return tournamentFragment;
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
