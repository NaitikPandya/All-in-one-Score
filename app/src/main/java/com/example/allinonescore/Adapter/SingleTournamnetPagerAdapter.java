package com.example.allinonescore.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.allinonescore.Fragments.AboutFragment;
import com.example.allinonescore.Fragments.MatchesFragment;
import com.example.allinonescore.Fragments.MytournamentFragment;
import com.example.allinonescore.Fragments.PointTableFragment;
import com.example.allinonescore.Fragments.TeamsFragment;
import com.example.allinonescore.Fragments.TournamentFragment;

public class SingleTournamnetPagerAdapter extends FragmentPagerAdapter {

    private Context myContext;
    int totalTabs;
    String Tournamentname;
    String identity;

    public SingleTournamnetPagerAdapter(Context context, FragmentManager fm, int totalTabs,String T_name,String identity) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
        Tournamentname = T_name;
        this.identity = identity;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                MatchesFragment matchesFragment = new MatchesFragment();
                Bundle data = new Bundle();
                data.putString("Tname",Tournamentname);
                data.putString("Identity",identity);
                matchesFragment.setArguments(data);
                return matchesFragment;
            case 1:
                PointTableFragment pointTableFragment = new PointTableFragment();
                Bundle data1 = new Bundle();
                data1.putString("Tname",Tournamentname);
                pointTableFragment.setArguments(data1);
                return pointTableFragment;
            case 2:
                TeamsFragment teamsFragment = new TeamsFragment();
                Bundle bundle = new Bundle();
                bundle.putString("T_name",Tournamentname);
                bundle.putString("Identity",identity);
                teamsFragment.setArguments(bundle);
                return teamsFragment;
            case 3:
                AboutFragment aboutFragment = new AboutFragment();
                return aboutFragment;
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