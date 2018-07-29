/**
 * This is the main menu activity.
 * This will be the main activity for navigation to the other activities in the app.
 * It will add buttons to navigate to the classes list, etc.
 * Pulls from the Firebase database to retrieve required information.
 *
 * @author Aqil
 * @author Arazoo
 * @author Chris
 * @author Scott
 * @author Jacob
 * @author Jaewoong
 */
package com.example.scott.dalmapproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.os.Handler;
import android.widget.ImageButton;

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
        setContentView(R.layout.main_menu);

        //Get user id
        Bundle ids = getIntent().getExtras();
        sid = (String)ids.get("id");

        //Button functions
        logoutButtonFunctionality();
        scheduleButtonFunctionality();
        classListButtonFunctionality();
        buildingListButtonFunctionality();
        servicesListButtonFunctionality();

    }

    /**
     * Logout button functionality
     * TODO move into top bar menu
     */
    private void logoutButtonFunctionality(){
        Button logoutButton = (Button)findViewById(R.id.menu_logout);
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
                        finish();
                    }
                });
            }
        });

    }

    /**
     * My Schedule Button functionality
     */
    private void scheduleButtonFunctionality(){
        final FirebaseInstanceData firebaseInstanceData = (FirebaseInstanceData)getApplicationContext();
        String location = "users/"+sid+"/classes";
        firebaseInstanceData.firebaseUserClasses = firebaseInstanceData.firebaseDBInstance.getReference(location);

        //TODO rework with schedule rework
        ImageButton scheduleButton = findViewById(R.id.scheduleButton);
        scheduleButton.setOnClickListener(new View.OnClickListener() {
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
                        Intent scheduleIntent = new Intent(getApplicationContext(), ClassListActivity.class);
                        scheduleIntent.putExtra("myclasses", userObjects);
                        startActivity(scheduleIntent);
                    }
                }, 500);
            }
        });
    }

    /**
     * Class List button functionality
     */
    private void classListButtonFunctionality(){
        ImageButton classListButton = findViewById(R.id.classListButton);
        classListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent classListIntent = new Intent(getApplicationContext(), ClassListActivity.class);
                classListIntent.putExtra("id", sid);
                startActivity(classListIntent);
            }
        });
    }

    /**
     * Building List button functionality
     */
    private void buildingListButtonFunctionality(){
        ImageButton buildingListButton = findViewById(R.id.buildingListButton);
        buildingListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent buildingListIntent = new Intent(getApplicationContext(), BuildingListActivity.class);
                buildingListIntent.putExtra("id", sid);
                startActivity(buildingListIntent);

            }
        });
    }

    /**
     * Services List button functionality
     */
    private void servicesListButtonFunctionality(){
        ImageButton servicesListButton = findViewById(R.id.servicesListButton);
        servicesListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent servicesListIntent = new Intent(getApplicationContext(), ServicesListActivity.class);
                startActivity(servicesListIntent);
            }
        });
    }
}