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
import android.widget.EditText;

import com.anip.swamphacks.helper.DatabaseHelper;
import com.anip.swamphacks.model.Event;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    private FloatingActionButton button;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private DatabaseReference ref;
    private DatabaseHelper db;
    private ArrayList<Event> events;
//    private Event event;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getPreferences(MODE_PRIVATE);
        editor = sharedPreferences.edit();
        setContentView(R.layout.activity_login);
        ref = FirebaseDatabase.getInstance().getReference("events");
        email_view = findViewById(R.id.email_view);
        pass_view =  findViewById(R.id.pass_view);
        button = findViewById(R.id.button);
        events = new ArrayList<>();
        auth = FirebaseAuth.getInstance();
        db = new DatabaseHelper(this);
        if(sharedPreferences.getString("email", "") != ""){
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot data  : dataSnapshot.getChildren()){
                        Event event = data.getValue(Event.class);
                        events.add(event);
                    }
                    Log.i("hell  --->   ", String.valueOf(events.size()));
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = email_view.getText().toString();
                password = pass_view.getText().toString();
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.i("hell", task.getResult().getUser().getEmail());
                        editor.putString("email", task.getResult().getUser().getEmail());
                        editor.apply();
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                    }
                });
            }
        });


    }
}
