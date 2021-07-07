package com.example.allinonescore.Adapter;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.allinonescore.Data.OverData;
import com.example.allinonescore.R;

import java.util.ArrayList;

public class overRecyclerAdapter extends RecyclerView.Adapter<overRecyclerAdapter.overViewHolder> {

    private ArrayList<OverData> overData;
    int[] num = {R.drawable.num004x,R.drawable.num014x,R.drawable.num024x,R.drawable.num034x,R.drawable.num044x,R.drawable.num054x,R.drawable.num064x,R.drawable.num074x};
    int[] wicket = {R.drawable.wicket14x,R.drawable.wicket24x,R.drawable.wicket34x,R.drawable.wicket44x,R.drawable.wicket64x};
    int[] wide = {R.drawable.wide14x,R.drawable.wide24x,R.drawable.wide34x,R.drawable.wide44x,R.drawable.wide64x};
    int[] lb = {R.drawable.lb14x,R.drawable.lb24x,R.drawable.lb34x,R.drawable.lb44x,R.drawable.lb64x};
    int[] nb = {R.drawable.nb14x,R.drawable.nb24x,R.drawable.nb34x,R.drawable.nb44x,R.drawable.nb64x};
    int[] b = {R.drawable.byes14x,R.drawable.byes24x,R.drawable.byes34x,R.drawable.byes44x,R.drawable.byes64x};


    public static class overViewHolder extends RecyclerView.ViewHolder {
        public ImageView mTextView1;
        public overViewHolder(View itemView) {
            super(itemView);
            mTextView1 = itemView.findViewById(R.id.over_Text);
        }
    }

    public overRecyclerAdapter()
    {

    }

    public overRecyclerAdapter(ArrayList<OverData> overData)
    {
        this.overData = overData;
    }

    @NonNull
    @Override
    public overViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.over_layout, parent, false);
        overViewHolder evh = new overViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull overViewHolder holder, int position) {
        OverData item = overData.get(position);

        switch (item.getCheck()) {
            case "R":
                holder.mTextView1.setImageResource(num[item.getRun()]);
                break;
            case "W":
                holder.mTextView1.setImageResource(wicket[item.getRun() - 1]);
                break;
            case "Wd":
                holder.mTextView1.setImageResource(wide[item.getRun() - 1]);
                break;
            case "LB":
                holder.mTextView1.setImageResource(lb[item.getRun() - 1]);
                break;
            case "NB":
                holder.mTextView1.setImageResource(nb[item.getRun() - 1]);
                break;
            case "B":
                holder.mTextView1.setImageResource(b[item.getRun() - 1]);
                break;
        }

    }

    @Override
    public int getItemCount() {
        if(overData == null)
        {
            return 0;
        }
        else
        {
            return overData.size();
        }
    }
}
