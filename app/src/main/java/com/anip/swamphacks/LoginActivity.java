package com.anip.swamphacks;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.anip.swamphacks.helper.DatabaseHelper;
import com.anip.swamphacks.model.Event;
import com.anip.swamphacks.model.Reps;
import com.anip.swamphacks.model.Sponsor;
import com.anip.swamphacks.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by anip on 03/11/17.
 */

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private String email;
    private String password;
    private EditText email_view;
    private EditText pass_view;
    private Button button;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private DatabaseReference ref;
    private DatabaseReference ref2;
    private DatabaseHelper db;
    public static ArrayList<Event> events;
    public static ArrayList<Sponsor> sponsors;
    public static ArrayList<Reps> reps;
    private ScrollView login_content;
    private ImageView splash;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences("profile", 0);
        editor = sharedPreferences.edit();
        setContentView(R.layout.activity_login);
        login_content = findViewById(R.id.home_content);
        ref = FirebaseDatabase.getInstance().getReference("events");
        ref2 = FirebaseDatabase.getInstance().getReference("sponsors");
        email_view = findViewById(R.id.email_view);
        pass_view =  findViewById(R.id.pass_view);
        button = findViewById(R.id.button);
        events = new ArrayList<>();
        sponsors = new ArrayList<>();
        reps = new ArrayList<>();
        auth = FirebaseAuth.getInstance();
        db = new DatabaseHelper(this);
        splash = findViewById(R.id.splash);
        if(sharedPreferences.getString("email", "") != ""){
            login_content.setVisibility(View.GONE);
            splash.setVisibility(View.VISIBLE);
            getServerData();

        }
        else{
            login_content.setVisibility(View.VISIBLE);
            splash.setVisibility(View.GONE);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = email_view.getText().toString();
//                Log.i("hell", email);
                button.setAlpha(0.75f);

                password = pass_view.getText().toString();
                auth.signInWithEmailAndPassword(email.trim(), password.trim()).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            ref = FirebaseDatabase.getInstance().getReference("confirmed");
                            Query query = ref.orderByChild("email").equalTo(email.trim());
                            query.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
//                                    Log.i("hell", String.valueOf(dataSnapshot));
                                    Log.i("hell login", String.valueOf(dataSnapshot));
                                    if(dataSnapshot.getChildren().iterator().hasNext()){
                                        DataSnapshot data  = dataSnapshot.getChildren().iterator().next();
                                        User profile = data.getValue(User.class);
                                        System.out.println(data.child("isVolunteer").getValue());
                                        profile.setVolunteer((Boolean) data.child("isVolunteer").getValue());
                                        Log.i("hell login", String.valueOf(data));
                                        editor.putString("email", profile.getEmail());
                                        editor.putString("team", profile.getTeam());
                                        editor.putBoolean("isVolunteer", profile.isVolunteer());
                                        Log.i("hell login", String.valueOf(profile.isVolunteer()));
                                        editor.apply();
                                        getServerData();
                                    }
                                    else{
                                        pass_view.setError("Not Confirmed Hacker, contact coordinator");
                                    }

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    Log.i("hell", String.valueOf(databaseError));
                                    email_view.setError("Invalid email");
                                    pass_view.setError("Invalid Password");

                                }
                            });
                        }
                        else{
                            email_view.setError("Invalid email");
                            pass_view.setError("Invalid Password");
                        }


                    }
                });


            }
        });


    }

    private void getServerData() {
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data  : dataSnapshot.getChildren()){
                    Event event = data.getValue(Event.class);
                    events.add(event);
                }
                ref2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot data  : dataSnapshot.getChildren()){
                            final Sponsor sponsor = data.getValue(Sponsor.class);
                            String eventId = data.getKey();
                            String path = "sponsors/" + eventId + "/reps";
                            DatabaseReference repsRef = FirebaseDatabase.getInstance().getReference(path);
                            repsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for(DataSnapshot data : dataSnapshot.getChildren()){
//                                        Log.i("hell reps data", String.valueOf(data));
                                        Reps rep = data.getValue(Reps.class);
                                        rep.setSponsor(sponsor.getName());
                                        reps.add(rep);


                                    }
                                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finish();
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                            sponsors.add(sponsor);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Log.i("hell  --->   ", databaseError.getMessage());
            }
        });
    }

}
