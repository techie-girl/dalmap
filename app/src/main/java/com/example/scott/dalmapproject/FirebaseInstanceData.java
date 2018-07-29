/**
 *
 * Creates the Firebase database references and instances.
 *
 * @author Jacob
 * @author Aqil
 * @author Arazoo
 * @author Chris
 * @author Scott
 * @author Jaewoong
 */
package com.example.scott.dalmapproject;

import android.app.Application;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseInstanceData extends Application{

    public DatabaseReference firebaseReferenceClasses;
    public DatabaseReference firebaseReferenceUsers;
    public DatabaseReference firebaseReferenceServices;
    public DatabaseReference firebaseUserClasses;
    public FirebaseDatabase firebaseDBInstance;
}