package com.example.scott.dalmapproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

public class FirebaseBuildingBuilder extends AppCompatActivity {

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
        BuildingObject buildingObject1 = new BuildingObject("Goldberg", hours);
        BuildingObject buildingObject2 = new BuildingObject("Henry Hicks", hours);
        BuildingObject buildingObject3 = new BuildingObject("Killam", hours);

        //Push the ServiceObjects to the database
        firebaseInstanceData.firebaseReferenceBuilding.child(buildingObject1.name).setValue(buildingObject1);
        firebaseInstanceData.firebaseReferenceBuilding.child(buildingObject2.name).setValue(buildingObject2);
        firebaseInstanceData.firebaseReferenceBuilding.child(buildingObject3.name).setValue(buildingObject3);

        firebaseInstanceData.firebaseReferenceServices.push();
        finish();
    }

}