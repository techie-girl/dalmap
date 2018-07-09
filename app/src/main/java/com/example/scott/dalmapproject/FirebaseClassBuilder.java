package com.example.scott.dalmapproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class FirebaseClassBuilder extends AppCompatActivity{

    protected void onCreate(Bundle savedBundleState){
        super.onCreate(savedBundleState);

        FirebaseInstanceData firebaseInstanceData = (FirebaseInstanceData)getApplicationContext();

        //Create 3 ClassObjects using fake data
        ClassObject classObject = new ClassObject("CSCI 1000", "Computing",
                "Summer", 123456, 1, "LEC", 3,
                true, false, true, false, true,
                13, 35, 14, 25,
                "Computer Science 127", 100, 49, 51,
                "Vlado", "T130", 3);

        ClassObject classObject2 = new ClassObject("CSCI 1001", "Computing 2",
                "Summer", 123645, 1, "LEC", 3,
                true, false, true, false, true,
                13, 35, 14, 25,
                "Computer Science 127", 100, 49, 51,
                "Vlado", "T130", 3);

        ClassObject classObject3 = new ClassObject("CSCI 2000", "Advanced Computing",
                "Summer", 321456, 1, "LEC", 3,
                true, false, true, false, true,
                13, 35, 14, 25,
                "Computer Science 127", 100, 49, 51,
                "Vlado", "T130", 3);



        //Push the ClassObjects to the database
        firebaseInstanceData.firebaseReferenceClasses.child(String.valueOf(classObject.CRN)).setValue(classObject);
        firebaseInstanceData.firebaseReferenceClasses.child(String.valueOf(classObject2.CRN)).setValue(classObject2);
        firebaseInstanceData.firebaseReferenceClasses.child(String.valueOf(classObject3.CRN)).setValue(classObject3);

        firebaseInstanceData.firebaseReferenceClasses.push();
        finish();
    }

}
