/**
 * This creates a class object that will be stored in the class list.
 * It holds all the information for a specific class.
 *
 * @author Jacob
 * @author Aqil
 * @author Arazoo
 * @author Chris
 * @author Scott
 * @author Jaewoong
 */
package com.example.scott.dalmapproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

public class FirebaseServiceBuilder extends AppCompatActivity {

    protected void onCreate(Bundle savedBundleState) {
        super.onCreate(savedBundleState);

        FirebaseInstanceData firebaseInstanceData = (FirebaseInstanceData) getApplicationContext();

        ArrayList<Integer> hours = new ArrayList<>();

        //Sunday 9:00 - 5:00
        //Monday 8:00 - 12:00
        //Tuesday 8:00 - 12:00
        //Wednesday 8:00 - 12:00
        //Thursday 8:00 - 12:00
        //Friday 8:00 - 12:00
        //Saturday 9:00 - 5:00
        hours.add(9); hours.add(0); hours.add(5); hours.add(0); //sunday
        hours.add(8); hours.add(0); hours.add(12); hours.add(0); //monday
        hours.add(8); hours.add(0); hours.add(12); hours.add(0); //tuesday
        hours.add(8); hours.add(0); hours.add(12); hours.add(0); //wednesday
        hours.add(8); hours.add(0); hours.add(12); hours.add(0); //thursday
        hours.add(8); hours.add(0); hours.add(12); hours.add(0); //friday
        hours.add(9); hours.add(0); hours.add(5); hours.add(0); //sunday

        //Create 9 ServiceObjects using dummy data
        ServiceObject serviceObject1 = new ServiceObject("Killam", "Circulation Desk", hours);
        ServiceObject serviceObject2 = new ServiceObject("Killam", "IT Help Desk", hours);
        ServiceObject serviceObject3 = new ServiceObject("Killam", "Second Cup", hours);
        ServiceObject serviceObject4 = new ServiceObject("Goldberg", "Second Cup", hours);
        ServiceObject serviceObject5 = new ServiceObject("Goldberg", "Help Desk", hours);
        ServiceObject serviceObject6 = new ServiceObject("Goldberg", "Learning Center", hours);
        ServiceObject serviceObject7 = new ServiceObject("Henry Hicks", "Registrars Office", hours);
        ServiceObject serviceObject8 = new ServiceObject("Henry Hicks", "Student Accounts", hours);
        ServiceObject serviceObject9 = new ServiceObject("Henry Hicks", "Financial Services", hours);

        //Push the ServiceObjects to the database
        firebaseInstanceData.firebaseReferenceServices.child(String.valueOf(serviceObject1.building + serviceObject1.name)).setValue(serviceObject1);
        firebaseInstanceData.firebaseReferenceServices.child(String.valueOf(serviceObject2.building + serviceObject2.name)).setValue(serviceObject2);
        firebaseInstanceData.firebaseReferenceServices.child(String.valueOf(serviceObject3.building + serviceObject3.name)).setValue(serviceObject3);
        firebaseInstanceData.firebaseReferenceServices.child(String.valueOf(serviceObject4.building + serviceObject4.name)).setValue(serviceObject4);
        firebaseInstanceData.firebaseReferenceServices.child(String.valueOf(serviceObject5.building + serviceObject5.name)).setValue(serviceObject5);
        firebaseInstanceData.firebaseReferenceServices.child(String.valueOf(serviceObject6.building + serviceObject6.name)).setValue(serviceObject6);
        firebaseInstanceData.firebaseReferenceServices.child(String.valueOf(serviceObject7.building + serviceObject7.name)).setValue(serviceObject7);
        firebaseInstanceData.firebaseReferenceServices.child(String.valueOf(serviceObject8.building + serviceObject8.name)).setValue(serviceObject8);
        firebaseInstanceData.firebaseReferenceServices.child(String.valueOf(serviceObject9.building + serviceObject9.name)).setValue(serviceObject9);

        firebaseInstanceData.firebaseReferenceServices.push();
        finish();
    }

}
