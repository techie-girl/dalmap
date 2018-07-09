package com.example.scott.dalmapproject;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FirebaseUserBuilder extends AppCompatActivity {

    protected void onCreate(Bundle savedBundleState){
        super.onCreate(savedBundleState);

        final FirebaseInstanceData firebaseInstanceData = (FirebaseInstanceData)getApplicationContext();

        //ValueEventListener that retrieves the classes data set from firebase as a DataSnapshot
        ValueEventListener getClassesFromDatabaseListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //An array list of ClassObjects to be passes to the new UserObjects
                ArrayList<String> classes = new ArrayList();

                //Adds every class in the Class tree to the classes ArrayList
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    classes.add(String.valueOf(ds.getValue(ClassObject.class).CRN));

                }

                //Create 3 UserObjects with fake data
                UserObject userObject = new UserObject("B00123456", "John Smith", classes);
                UserObject userObject2 = new UserObject("B00654321", "Sam Jones", classes);
                UserObject userObject3 = new UserObject("B00918273", "Frank Stevens", classes);

                //Push the UserObjects to the database
                firebaseInstanceData.firebaseReferenceUsers.child(userObject.bannerID).setValue(userObject);
                firebaseInstanceData.firebaseReferenceUsers.child(userObject2.bannerID).setValue(userObject2);
                firebaseInstanceData.firebaseReferenceUsers.child(userObject3.bannerID).setValue(userObject3);
            }

            //How to respond when the database lookup has been canceled. Ignored for now
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        //Add a ValueEventListener that will get all the children in the Classes section
        //Runs getClassesFromDatabaseListener once when the class is first run, then again when values change in the DB
        firebaseInstanceData.firebaseReferenceClasses.addListenerForSingleValueEvent(getClassesFromDatabaseListener);

        firebaseInstanceData.firebaseReferenceUsers.push();
        finish();
    }

}
