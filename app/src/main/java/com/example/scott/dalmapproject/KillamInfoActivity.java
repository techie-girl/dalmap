/**
 *
 * Creates the info activity for the Killam Library Building.
 * Lists information such as services, hours, and location.
 *
 * @author Scott
 * @author Aqil
 * @author Arazoo
 * @author Chris
 * @author Jacob
 * @author Jaewoong
 */
package com.example.scott.dalmapproject;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class KillamInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_killam_info);

        TextView Services = (TextView) findViewById(R.id.Services);
        TextView Building = (TextView) findViewById(R.id.building);

        //Set service info text
        Services.setText("Services: Bistro\nHours: 11-5 Mon-Fri\nLocation: Killam Library South Learning Commons");
        Building.setText("Hours:7:30am-9pm\n");

        //Define the functionality of the buttons
        floorPlanButtonFunction();
        visualAidButtonFunction();
        exteriorMapButtonFunctions();
    }

    /**
     * Functionality for the floor plan button
     */
    private void floorPlanButtonFunction(){
        Button kilFloorPlanButton = (Button) findViewById(R.id.kil_floor_plan_button);
        kilFloorPlanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent LaunchKillamFloorPlan = new Intent(v.getContext(), KillamFloorPlanActivity.class);
                startActivity(LaunchKillamFloorPlan);
            }
        });
    }

    /**
     * Functionality for the visual aid button
     */
    private void visualAidButtonFunction(){
        Button kilVisualAidButton = (Button) findViewById(R.id.kil_visual_aid_button);
        kilVisualAidButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent LaunchKillamVisualAid = new Intent(getApplicationContext(), KillamVisualAidActivity.class);
                startActivity(LaunchKillamVisualAid);
            }
        });
    }

    /**
     * Functionality for the exterior map button
     */
    private void exteriorMapButtonFunctions(){
        Button kilExteriorMapButton = (Button) findViewById(R.id.kil_exterior_map_button);
        kilExteriorMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent LaunchMapActivity = new Intent(getApplicationContext(), MapActivity.class);
                startActivity(LaunchMapActivity);
            }
        });
    }
}
