package com.example.allinonescore.adapters;

import android.graphics.Movie;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.allinonescore.Data.TeamData;
import com.example.allinonescore.R;

import java.util.List;

public class TeamscorecardAdapter extends RecyclerView.Adapter<TeamscorecardAdapter.TeamVH> {

    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    List<TeamData> TeamList;

    public TeamscorecardAdapter(List<TeamData> TeamList) {
        this.TeamList = TeamList;
    }

    @NonNull
    @Override
    public TeamVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nested_recycler, parent, false);
        return new TeamVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeamVH holder, int position) {

        TeamData teamData = TeamList.get(position);
        holder.titleTextView.setText(teamData.getTitle());

        boolean isExpanded = TeamList.get(position).isExpandable();
        holder.expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

        LinearLayoutManager layoutManager = new LinearLayoutManager(
                holder.subitem.getContext(),
                LinearLayoutManager.VERTICAL,
                false
        );
        layoutManager.setInitialPrefetchItemCount(teamData.getSubItemList().size());

        // Create sub item view adapter
        SubTeamAdapter subItemAdapter = new SubTeamAdapter(teamData.getSubItemList());

        holder.subitem.setLayoutManager(layoutManager);
        holder.subitem.setAdapter(subItemAdapter);
        holder.subitem.setRecycledViewPool(viewPool);

    }

    @Override
    public int getItemCount() {
        return TeamList.size();
    }


    class TeamVH extends RecyclerView.ViewHolder {
        RecyclerView subitem;

        ConstraintLayout expandableLayout;
        TextView titleTextView;

        public TeamVH(@NonNull final View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.titleTextView);
            expandableLayout = itemView.findViewById(R.id.expandableLayout);
            subitem = itemView.findViewById(R.id.sub_item);


            titleTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    TeamData teamdata = TeamList.get(getAdapterPosition());
                    teamdata.setExpandable(!teamdata.isExpandable());
                    notifyItemChanged(getAdapterPosition());

                }
            });
        }
    }
}
