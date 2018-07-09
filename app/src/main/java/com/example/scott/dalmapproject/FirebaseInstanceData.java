package com.example.scott.dalmapproject;

import android.app.Application;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseInstanceData extends Application{

    public DatabaseReference firebaseReferenceClasses;
    public DatabaseReference firebaseReferenceUsers;
    public FirebaseDatabase firebaseDBInstance;
}