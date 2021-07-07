package com.example.allinonescore.Data;

import java.util.List;

public class TeamData {

    private String Title;
    private List<SubTeam> subItemList;
    private boolean Expandable;

    public TeamData(String title, List<SubTeam> subItemList) {
        Title = title;
        this.subItemList = subItemList;
        this.Expandable=false;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public List<SubTeam> getSubItemList() {
        return subItemList;
    }

    public void setSubItemList(List<SubTeam> subItemList) {
        this.subItemList = subItemList;
    }

    public boolean isExpandable() {
        return Expandable;
    }

    public void setExpandable(boolean expandable) {
        Expandable = expandable;
    }
}
