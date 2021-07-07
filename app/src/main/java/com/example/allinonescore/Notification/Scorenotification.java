package com.example.allinonescore.Notification;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.util.Log;

public class Scorenotification extends Application {

    public static final String CHANNEL_ID = "ScoreChannel";
    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }
    private void createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.e("HEllo","WELcome");
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Score Channel",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("Hello");
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }


}
