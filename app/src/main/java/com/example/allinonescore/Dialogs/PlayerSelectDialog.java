package com.example.allinonescore.Dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.allinonescore.Adapter.DialogPagerAdapter;
import com.example.allinonescore.Fragments.Cricketlist1;
import com.example.allinonescore.Fragments.Cricketlist2;
import com.example.allinonescore.R;
import com.example.allinonescore.Transformation.DepthTransformation;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class PlayerSelectDialog extends DialogFragment {

    TextView title;
    ViewPager viewPager;
    View view;
    MaterialButton next;
    PagerAdapter pagerAdapter;
    DepthTransformation depthTransformation = new DepthTransformation();
    List<Fragment> fragmentList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.player_select_dialog,null,false);

        viewPager = view.findViewById(R.id.dialog_pager_view);
        next = view.findViewById(R.id.dialog_button_next);
        title = view.findViewById(R.id.dialog_title);

        viewPager.setPageTransformer(true,depthTransformation);
        fragmentList.add(new Cricketlist1());
        fragmentList.add(new Cricketlist2());

        pagerAdapter = new DialogPagerAdapter(getChildFragmentManager(),fragmentList);
        viewPager.setAdapter(pagerAdapter);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               viewPager.setCurrentItem(1);
               title.setText("Team B");

            }
        });

        return view;
    }
}
