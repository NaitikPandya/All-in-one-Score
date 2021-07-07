package com.example.allinonescore.Data;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Tournaments {

   private String Tname ="";

    public Tournaments() {
    }

    public Tournaments(String tname) {
        Tname = tname;
    }

    public String getTname() {
        return Tname;
    }

    public void setTname(String tname) {
        Tname = tname;
    }
}
