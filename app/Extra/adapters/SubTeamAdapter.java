package com.example.allinonescore.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.allinonescore.Data.SubTeam;
import com.example.allinonescore.R;

import java.util.List;

public class SubTeamAdapter extends RecyclerView.Adapter<SubTeamAdapter.SubTeamVH> {

    private List<SubTeam> subTeamList;

    SubTeamAdapter(List<SubTeam> subTeamList)
    {
        this.subTeamList = subTeamList;
    }

    @NonNull
    @Override
    public SubTeamVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_card,parent,false);

        return new SubTeamVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubTeamVH holder, int position) {
        SubTeam subTeam = subTeamList.get(position);
        holder.name.setText(subTeam.getNmae());

    }

    @Override
    public int getItemCount() {
        return subTeamList.size();
    }

    class SubTeamVH extends RecyclerView.ViewHolder{

        TextView name;

        SubTeamVH(View view){
            super(view);

            name = view.findViewById(R.id.playername);
        }
    }
}
