package com.example.allinonescore.Service;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.allinonescore.DataBase.DataManagement;
import com.example.allinonescore.R;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.List;

import static com.example.allinonescore.Notification.Scorenotification.CHANNEL_ID;

public class notificationService extends Service {

    FirebaseFirestore firebaseFirestore;
    RemoteViews scoreView;
    DataManagement dataManagement = new DataManagement();
    Notification notification;
    int count = 0;
    String name,Team1,Team2,score1,over1,score2,over2;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        firebaseFirestore = FirebaseFirestore.getInstance();
        name = intent.getStringExtra("Name");
        Team1 = intent.getStringExtra("Team1");
        Team2 = intent.getStringExtra("Team2");
        startnotification();
        return START_NOT_STICKY;
    }

    public void startnotification()
    {
        scoreView = new RemoteViews(getPackageName(),
                R.layout.scorenotification);

        dataManagement.getScore(name, Team1, new DataManagement.ScoreCallBack() {
            @Override
            public void onScoreCallBack(String Score, String Over) {
                score1 = Score;
                over1 = Over;

                dataManagement.getScore(name, Team2, new DataManagement.ScoreCallBack() {
                    @Override
                    public void onScoreCallBack(String Score, String Over) {
                        score2 = Score;
                        over2 =Over;
                        scoreView.setTextViewText(R.id.T1_notificationscore,score1);
                        scoreView.setTextViewText(R.id.T1_notificationover,over1);
                        scoreView.setTextViewText(R.id.T2_notificationscore,score2);
                        scoreView.setTextViewText(R.id.T2_notificationover,over2);

                        scoreView.setTextViewText(R.id.n_teamname,Team1);
                        scoreView.setTextViewText(R.id.n_temaname2,Team2);
                        scoreView.setTextViewText(R.id.t_name,name);


                        notification = new NotificationCompat.Builder(notificationService.this,CHANNEL_ID)
                                .setContentTitle(name)
                                .setCustomBigContentView(scoreView)
                                .setSmallIcon(R.mipmap.cricket_96px)
                                .build();
                            startForeground(1,notification);




                    }
                });
            }
        });



    }

}
