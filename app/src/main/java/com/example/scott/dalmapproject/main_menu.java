package com.example.scott.dalmapproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.os.Handler;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class main_menu extends AppCompatActivity {

    private ArrayList<String> userObjects = new ArrayList<>();
    private String sid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        Bundle ids = getIntent().getExtras();
        sid = (String)ids.get("id");

        final FirebaseInstanceData firebaseInstanceData = (FirebaseInstanceData)getApplicationContext();
        String location = "users/"+sid+"/classes";
        firebaseInstanceData.firebaseUserClasses = firebaseInstanceData.firebaseDBInstance.getReference(location);

        Button logoutButton = (Button)findViewById(R.id.menu_log_out_button);
        Button bt1 = findViewById(R.id.select1);
        Button bt2 = findViewById(R.id.select2);
        Button bt3 = findViewById(R.id.select3);
        Button bt4 = findViewById(R.id.select4);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.logout_layout);
                Button confirmButton = (Button)findViewById(R.id.confirm_log_out_button);

                confirmButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent relaunch = new Intent(getApplicationContext(), Login.class);
                        startActivity(relaunch);
                    }
                });
                finish();
            }
        });

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //ValueEventListener that retrieves the classes data set from firebase as a DataSnapshot
                final ValueEventListener myClassesListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        userObjects.clear();

                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            userObjects.add(ds.getValue(String.class));
                        }
                    }
                    //How to respond when the database lookup has been canceled. Ignored for now
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                };
                //Add a ValueEventListener that will get all the children in the Classes section
                //Runs getClassesFromDatabaseListener once when the class is first run, then again when values change in the DB

                firebaseInstanceData.firebaseUserClasses.addListenerForSingleValueEvent(myClassesListener);

                // Waiting for the firebase reading process to complete
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        // Pass the lists of specified user to ClassListActivity
                        Intent intent1 = new Intent(getApplicationContext(), ClassListActivity.class);
                        intent1.putExtra("myclasses", userObjects);

                        startActivity(intent1);
                    }
                }, 500);
            }
        });

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent2 = new Intent(getApplicationContext(), ClassListActivity.class);
                intent2.putExtra("id", sid);
                startActivity(intent2);
            }
        });

        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(getApplicationContext(), BuildingListActivity.class);
                intent3.putExtra("id", sid);
                startActivity(intent3);

            }
        });

        bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    /*
                    Intent intent4 = new Intent(getApplicationContext(), ServiceListActivity.class);
                    intent2.putExtra("id", sid);
                    startActivity(intent4);
                    */
            }
        });
    }
}






