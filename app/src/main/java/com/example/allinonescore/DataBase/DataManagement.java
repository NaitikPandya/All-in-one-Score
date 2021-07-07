package com.example.allinonescore.DataBase;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DataManagement {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        int over =0,ball =0;
        int count = 0;

        public void getTournaments(TournamentCallback tournamentCallback)
        {
                List<String> tournament = new ArrayList<>();
                List<String> iamge = new ArrayList<>();
                List<String> Sdate = new ArrayList<>();
                List<String> Edate = new ArrayList<>();
                List<String> loaction = new ArrayList<>();
                firebaseFirestore.collection("Tournaments").orderBy("StartDate", Query.Direction.DESCENDING)
                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                @Override
                                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                        tournament.clear();
                                        iamge.clear();
                                        Sdate.clear();
                                        Edate.clear();
                                        loaction.clear();

                                        for(DocumentSnapshot documentSnapshot : value.getDocuments())
                                        {
                                                tournament.add(documentSnapshot.getId());
                                                iamge.add(String.valueOf(documentSnapshot.get("Image")));
                                                long timestamp = documentSnapshot.getTimestamp("StartDate").getSeconds();
                                                long timestamp1 = documentSnapshot.getTimestamp("EndDate").getSeconds();
                                                Calendar c = Calendar.getInstance();
                                                c.setTimeInMillis(timestamp*1000);
                                                Date d = c.getTime();
                                                c.setTimeInMillis(timestamp1*1000);
                                                Date d1 = c.getTime();
                                                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                                                Sdate.add(sdf.format(d));
                                                Edate.add(sdf.format(d1));
                                                loaction.add(String.valueOf(documentSnapshot.get("Location")));
                                        }
                                        tournamentCallback.onCallback(iamge,tournament,Sdate,Edate,loaction);
                                }
                        });
        }

        public void getOtournament(TournamentOcallback tournamentOcallback)
        {
                List<String> tournament = new ArrayList<>();
                List<String> iamge = new ArrayList<>();
                List<String> Sdate = new ArrayList<>();
                List<String> Edate = new ArrayList<>();
                List<String> loaction = new ArrayList<>();
                firebaseFirestore.collection("Tournaments").whereEqualTo("Organizer Id",user.getUid()).orderBy("StartDate", Query.Direction.DESCENDING)
                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                @Override
                                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                        tournament.clear();
                                        iamge.clear();
                                        Sdate.clear();
                                        Edate.clear();
                                        loaction.clear();

                                        for(DocumentSnapshot documentSnapshot : value.getDocuments())
                                        {
                                                tournament.add(documentSnapshot.getId());
                                                iamge.add(documentSnapshot.get("Image").toString());
                                                long timestamp = documentSnapshot.getTimestamp("StartDate").getSeconds();
                                                long timestamp1 = documentSnapshot.getTimestamp("EndDate").getSeconds();
                                                Calendar c = Calendar.getInstance();
                                                c.setTimeInMillis(timestamp*1000);
                                                Date d = c.getTime();
                                                c.setTimeInMillis(timestamp1*1000);
                                                Date d1 = c.getTime();
                                                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                                                Sdate.add(sdf.format(d));
                                                Edate.add(sdf.format(d1));
                                                loaction.add(documentSnapshot.get("Location").toString());
                                        }
                                        tournamentOcallback.OnTournamentOcallback(iamge,tournament,Sdate,Edate,loaction);
                                }
                        });
        }

        public void getUserTeams(String Tournament , TeamsCallback teamsCallback)
        {
                List<String> teams = new ArrayList<>();
                firebaseFirestore.collection("Tournaments").document(Tournament)
                        .collection("Teams")
                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                @Override
                                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                        teams.clear();
                                                for(DocumentSnapshot documentSnapshot : value.getDocuments())
                                                {
                                                        teams.add(documentSnapshot.getId());
                                                }
                                                        teamsCallback.onTeamCallback(teams);


                                }
                        });

        }

        public void updateScore(int Score , int wicket,String Team,String T,int overnum , int ballnum)
        {

                if(ballnum == 5)
                {
                        overnum = overnum +1;
                        ballnum = 0;

                }else
                {
                        ballnum = ballnum +1;
                }
                String Over = String.valueOf(overnum) + "." + String.valueOf(ballnum);
                firebaseFirestore.collection("Tournaments").document(T).collection("Teams")
                        .document(Team)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if(task.isSuccessful())
                                        {
                                                DocumentSnapshot documentSnapshot = task.getResult();
                                                assert documentSnapshot != null;
                                                int run = Integer.parseInt(Objects.requireNonNull(documentSnapshot.get("Score")).toString())+ Score;
                                                int out = Integer.parseInt(Objects.requireNonNull(documentSnapshot.get("Wicket")).toString()) + wicket;
                                                firebaseFirestore.collection("Tournaments").document(T).collection("Teams")
                                                        .document(Team)
                                                        .update("Score",run,"Over", Over ,"Wicket",out);
                                        }


                                }
                        });

        }
        public Task<Void> setTeams(String Teamname, String Tournamentname)
        {
                Map<String,Object> data = new HashMap<>();
                data.put("Over","0.0");
                data.put("Score",00);
                data.put("Wicket",0);
                data.put("Matches",0);
                data.put("Win",0);
                data.put("Loss",0);
                data.put("Tied",0);
                return firebaseFirestore.collection("Tournaments").document(Tournamentname)
                        .collection("Teams")
                        .document(Teamname)
                        .set(data);
        }

        public Task<Void> SetUserProfile(String name,String Gender,String Sport,String Email)
        {
                UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                        .setDisplayName(name)
                        .build();
                user.updateProfile(request);
                user.updateEmail(Email);
                Map<String,Object> data = new HashMap<>();
                data.put("Number",user.getPhoneNumber());
                data.put("Name",name);
                data.put("Gender",Gender);
                data.put("Sport",Sport);
                data.put("Email",Email);
                return firebaseFirestore.collection("User")
                        .document(user.getUid())
                        .set(data);
        }

        public void getUserProfile(UserProfile userProfile)
        {
                firebaseFirestore.collection("User")
                        .document(user.getUid())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if(task.isSuccessful())
                                        {
                                                DocumentSnapshot documentSnapshot = task.getResult();

                                                userProfile.onUserProfileCallback(documentSnapshot.get("Name").toString(),
                                                        documentSnapshot.get("Email").toString(),
                                                        documentSnapshot.get("Gender").toString(),
                                                        documentSnapshot.get("Sport").toString());

                                        }
                                }
                        });
        }

        public void getAdminTeams(String Tournament,TeamsAdminCallback teamsAdminCallback)
        {
                List<String> teams = new ArrayList<>();
                teams.add("ADD TEAMS");
                firebaseFirestore.collection("Tournaments").document(Tournament)
                        .collection("Teams")
                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                @Override
                                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                        teams.clear();
                                        teams.add("ADD Teams");
                                        for(DocumentSnapshot documentSnapshot : value.getDocuments())
                                        {
                                                teams.add(documentSnapshot.getId());
                                        }
                                        teamsAdminCallback.onTeamCallback(teams);


                                }
                        });
        }

        public void SetTournament(String Tname, String City, String Oname, String Onumber, Timestamp sdate,Timestamp edate, String Sport, Uri Image)
        {
                StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Tournament_Banner/" + Tname);
                UploadTask uploadTask = storageReference.putFile(Image);

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                
                        }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                                if (task.isSuccessful()) {
                                        Uri downloadUri = task.getResult();
                                        Map<String,Object> Data = new HashMap<>();
                                        Data.put("Organizer Name",Oname);
                                        Data.put("Location",City);
                                        Data.put("Organizer Id",user.getUid());
                                        Data.put("Organizer Number",Onumber);
                                        Data.put("StartDate",sdate);
                                        Data.put("EndDate",edate);
                                        Data.put("Image",downloadUri.toString().trim());
                                        Data.put("Sport",Sport);
                                        firebaseFirestore.collection("Tournaments")
                                                .document(Tname).set(Data);
                                } else {
                                        // Handle failures
                                        // ...
                                }
                        }
                });

        }

        public void SetStringTournament(String Tname, String City, String Oname, String Onumber, Timestamp sdate,Timestamp edate, String Sport, String Image)
        {
                Map<String,Object> Data = new HashMap<>();
                Data.put("Organizer Name",Oname);
                Data.put("Location",City);
                Data.put("Organizer Id",user.getUid());
                Data.put("Organizer Number",Onumber);
                Data.put("StartDate",sdate);
                Data.put("EndDate",edate);
                Data.put("Image",Image);
                Data.put("Sport",Sport);
                firebaseFirestore.collection("Tournaments")
                        .document(Tname).set(Data);

        }


        public void getImages(ImageGet imageGet)
        {
                List<String> data = new ArrayList<>();
                firebaseFirestore.collection("Banner")
                        .document("7CcreID3XPH1q2xZhO5S")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                                        if(task.isSuccessful())
                                        {
                                                DocumentSnapshot doc = task.getResult();
                                                data.add(doc.get("img1").toString());
                                                data.add(doc.get("img2").toString());
                                                data.add(doc.get("img3").toString());
                                                data.add(doc.get("img4").toString());
                                                data.add(doc.get("img5").toString());
                                                data.add(doc.get("img6").toString());
                                                data.add(doc.get("img7").toString());
                                                data.add(doc.get("img8").toString());
                                                data.add(doc.get("img9").toString());
                                                data.add(doc.get("img10").toString());
                                                data.add(doc.get("img11").toString());
                                                data.add(doc.get("img12").toString());

                                                imageGet.onImageGet(data);
                                        }
                                }
                        });
        }

        public void addCricketMatches(String A,String B,String T,String over,String bowlerover,String city,String Ground,String Date,String balltype)
        {
                Map<String,Object> data = new HashMap<>();
                data.put("Team A",A);
                data.put("Team B",B);
                data.put("Over",over);
                data.put("Over per Bowler",bowlerover);
                data.put("City",city);
                data.put("Ground",Ground);
                data.put("Date",Date);
                data.put("Ball",balltype);
                firebaseFirestore.collection("Tournaments")
                        .document(T)
                        .collection("Matches")
                        .add(data);
        }

        public void addBadmintonMatches(String T,String A,String B,String no_set,String points,String finalset,String finalpoint,String Date,String city,String Ground){
                Map<String,String> badminton = new HashMap<>();
                badminton.put("Team A",A);
                badminton.put("Team B",B);
                badminton.put("No of Sets",no_set);
                badminton.put("Point Per Set",points);
                badminton.put("City",city);
                badminton.put("Ground",Ground);
                badminton.put("Date",Date);
                if(finalpoint != null && finalset != null){
                        badminton.put("Set in Final",finalset);
                        badminton.put("final set point",finalpoint);
                }
                firebaseFirestore.collection("Tournaments")
                        .document(T)
                        .collection("Matches")
                        .add(badminton);

        }
        public void addBasketBallMatches(String T,String A,String B,String Time,String FinalTime){
                Map<String,String> basketball = new HashMap<>();
                basketball.put("Team A",A);
                basketball.put("Team B",B);
                basketball.put("Time",Time);
                if(FinalTime != null){
                        basketball.put("Final Match Time",FinalTime);
                }
                firebaseFirestore.collection("Tournaments")
                        .document(T)
                        .collection("Matches")
                        .add(basketball);
        }

        public void getBadmintonMatches(String T, GetBadmintonMatchCallBack badmintonMatchCallBack){

                List<String> TeamA = new ArrayList<>();
                List<String> TeamB = new ArrayList<>();
                List<String> ground = new ArrayList<>();
                List<String> city = new ArrayList<>();
                List<String> date = new ArrayList<>();
                List<String> set = new ArrayList<>();

                firebaseFirestore.collection("Tournaments")
                        .document(T)
                        .collection("Matches")
                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                @Override
                                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                        if(!value.isEmpty()){
                                                TeamA.clear();
                                                TeamB.clear();
                                                ground.clear();
                                                city.clear();
                                                date.clear();
                                                set.clear();
                                                for(DocumentSnapshot doc : value.getDocuments()){

                                                        TeamA.add(String.valueOf(doc.get("Team A")));
                                                        TeamB.add(String.valueOf(doc.get("Team B")));
                                                        ground.add(String.valueOf(doc.get("Ground")));
                                                        city.add(String.valueOf(doc.get("City")));
                                                        date.add(String.valueOf(doc.get("Date")));
                                                        set.add(String.valueOf(doc.get("No of Sets")));
                                                        Log.e("data",String.valueOf(doc.get("No.Sets")));

                                                }

                                                badmintonMatchCallBack.onBadmintonMatchCallBack(TeamA,TeamB,city,date,ground,set);

                                        }
                                }
                        });

        }

        public void getTeams(String T,TeamsUserCallback teamsuserCallback)
        {
                List<String> ListTeams = new ArrayList<>();
                firebaseFirestore.collection("Tournaments")
                        .document(T)
                        .collection("Teams")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                        if(task.isSuccessful())
                                        {
                                                for(QueryDocumentSnapshot doc : task.getResult())
                                                {
                                                        ListTeams.add(doc.getId());
                                                }

                                                teamsuserCallback.onTeamuserCallback(ListTeams);
                                        }

                                }
                        });
        }

        public void getMatches(String T,MatchCallback matchCallback)
        {
                List<String> TeamA = new ArrayList<>();
                List<String> TeamB = new ArrayList<>();
                List<String> Date = new ArrayList<>();
                List<String> over = new ArrayList<>();
                List<String> location = new ArrayList<>();
                firebaseFirestore.collection("Tournaments")
                        .document(T)
                        .collection("Matches")
                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                @Override
                                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                                        TeamA.clear();
                                        TeamB.clear();
                                        Date.clear();
                                        over.clear();
                                        location.clear();
                                        if(!value.isEmpty())
                                        {
                                                for(DocumentSnapshot doc : value.getDocuments())
                                                {
                                                                TeamA.add(String.valueOf(doc.get("Team A")));
                                                                TeamB.add(String.valueOf(doc.get("Team B")));
                                                                Date.add(String.valueOf(doc.get("Date")));
                                                                over.add(String.valueOf(doc.get("Over")));
                                                                location.add(String.valueOf(doc.get("Ground") + " , " + doc.get("City")));
                                                }
                                                matchCallback.onmatchcallback(TeamA,TeamB,Date,location,over);
                                        }


                                }
                        });
        }

        public void getPointTable(String T,PointCallBack pointCallBack)
        {
                List<String> ListTeams = new ArrayList<>();
                List<String> Win = new ArrayList<>();
                List<String> Match = new ArrayList<>();
                List<String> Loss = new ArrayList<>();
                List<String> Tied = new ArrayList<>();
                firebaseFirestore.collection("Tournaments")
                        .document(T)
                        .collection("Teams")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                        if(task.isSuccessful())
                                        {
                                                for(QueryDocumentSnapshot doc : task.getResult())
                                                {
                                                        ListTeams.add(doc.getId());
                                                        Win.add(Objects.requireNonNull(doc.get("Win")).toString());
                                                        Match.add(Objects.requireNonNull(doc.get("Matches")).toString());
                                                        Loss.add(Objects.requireNonNull(doc.get("Loss")).toString());
                                                        Tied.add(Objects.requireNonNull(doc.get("Tied")).toString());
                                                }

                                                pointCallBack.onpointcallback(ListTeams,Match,Win,Loss,Tied);
                                        }

                                }
                        });
        }

        public void getPlayers(String T,String Team,PlayerCallBack playerCallBack){
                List<String> players = new ArrayList<>();
                firebaseFirestore.collection("Tournaments")
                        .document(T)
                        .collection("Teams")
                        .document(Team)
                        .collection("Players")
                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                @Override
                                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                                        if(!value.isEmpty())
                                        {
                                                players.clear();
                                                for(DocumentSnapshot doc : value.getDocuments())
                                                {
                                                        players.add(String.valueOf(doc.get("Name")));
                                                }
                                                playerCallBack.onPlayerCallBack(players);
                                        }
                                }
                        });
        }

        public void getfollow(FollowCallBack followCallBack ){
                firebaseFirestore.collection("User")
                        .document(user.getUid())
                        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if(task.isSuccessful()){

                                        followCallBack.onfollowCallback(String.valueOf(task.getResult().get("Follow")));
                                }
                        }
                });
        }

        public void getScore(String T,String team,ScoreCallBack scoreCallBack){
                firebaseFirestore.collection("Tournaments").document(T)
                        .collection("Teams").document(team)
                        .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                @Override
                                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                        String score = value.get("Score").toString() + "-" + value.get("Wicket").toString();
                                        String over = "(" + value.get("Over").toString() + ")";

                                        scoreCallBack.onScoreCallBack(score,over);


                                }
                        });
        }

        public void getSport(String T,GetSportCallBack getSportCallBack){

                firebaseFirestore.collection("Tournaments")
                        .document(T)
                        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                                if(task.isSuccessful()){

                                        getSportCallBack.onSportCallBack(String.valueOf(task.getResult().get("Sport")));
                                }

                        }
                });
        }

        public interface GetSportCallBack{
                void onSportCallBack(String sport);
        }
        public interface ScoreCallBack {
                void onScoreCallBack(String Score, String Over);
        }

        public interface FollowCallBack{
                void onfollowCallback(String Name);
        }

        public interface TournamentCallback
        {
                void onCallback(List<String> Image,List<String> name,List<String> Sdate,List<String> Edate,List<String> location);
        }

        public interface TeamsCallback
        {
                void onTeamCallback(List<String> team);
        }

        public interface TeamsUserCallback
        {
                void onTeamuserCallback(List<String> team);
        }

        public interface TeamsAdminCallback
        {
                void onTeamCallback(List<String> team);
        }

        public interface UserProfile
        {
                void onUserProfileCallback(String name,String email,String gender,String sport);
        }

        public interface TournamentOcallback
        {
                void OnTournamentOcallback(List<String> Image,List<String> name,List<String> Sdate,List<String> Edate,List<String> location);
        }

        public interface MatchCallback
        {
                void onmatchcallback(List<String> TeamA,List<String> TeamB,List<String> Date,List<String> Location,List<String> over);
        }

        public interface PointCallBack
        {
                void onpointcallback(List<String> Team,List<String> matches,List<String> win,List<String> loss , List<String> tied);
        }

        public interface ImageGet
        {
                void onImageGet(List<String> image);
        }

        public interface PlayerCallBack
        {
                void onPlayerCallBack(List<String> Players);
        }

        public interface GetBadmintonMatchCallBack{
                void onBadmintonMatchCallBack(List<String> TeamA,List<String> Team,List<String> city,List<String> date,List<String> ground,List<String> set);
        }








}
