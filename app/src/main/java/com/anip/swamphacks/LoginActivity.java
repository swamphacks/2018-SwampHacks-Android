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
//    private Event event;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences("profile", 0);
        editor = sharedPreferences.edit();
        setContentView(R.layout.activity_login);
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
        if(sharedPreferences.getString("email", "") != ""){
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot data  : dataSnapshot.getChildren()){
                        Event event = data.getValue(Event.class);
                        events.add(event);
                        Log.i("hell  --->   ", String.valueOf(event.getStartTime()));
                    }
//                    Log.i("hell  --->   ", String.valueOf(events.size()));
                    ref2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot data  : dataSnapshot.getChildren()){
                                final Sponsor sponsor = data.getValue(Sponsor.class);
                                String eventId = data.getKey();
                                String path = "sponsors/" + eventId + "/reps";
                                DatabaseReference repsRef = FirebaseDatabase.getInstance().getReference(path);
                                repsRef.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        for(DataSnapshot data : dataSnapshot.getChildren()){
                                            Reps rep = data.getValue(Reps.class);
                                            rep.setSponsor(sponsor.getName());
                                            reps.add(rep);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                                sponsors.add(sponsor);
                            }







                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
//                    Gson gson = new Gson();
//                    String json = gson.toJson(events);
//                    intent.putExtra("events", events);
                            startActivity(intent);
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
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = email_view.getText().toString();
//                Log.i("hell", email);

                password = pass_view.getText().toString();
                auth.signInWithEmailAndPassword(email.trim(), password.trim()).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        ref = FirebaseDatabase.getInstance().getReference("confirmed");
                        Query query = ref.orderByChild("email").equalTo(email.trim());
                        query.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Log.i("hell", String.valueOf(dataSnapshot));
                                DataSnapshot data  = dataSnapshot.getChildren().iterator().next();
                                User profile = data.getValue(User.class);
                                editor.putString("email", profile.getEmail());
                                editor.putString("team", profile.getTeam());
                                editor.putBoolean("isVolunteer",profile.isVolunteer());
                                Log.i("hell login",profile.getTeam());
                                editor.apply();
                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(intent);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                        Log.i("hell", task.getResult().getUser().getEmail());

                    }
                });


            }
        });


    }
}
