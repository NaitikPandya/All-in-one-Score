package com.example.allinonescore.CustomFont;

import android.app.Application;

import com.example.allinonescore.R;

public class Customfont extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FontsOverride.overrideFont(this,"sans-serif", "fonts/sourcecodepro_semibolditalic.ttf");
    }
}
