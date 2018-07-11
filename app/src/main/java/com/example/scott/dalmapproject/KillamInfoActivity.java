package com.example.scott.dalmapproject;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class KillamInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_killam_info);

        TextView Services = (TextView) findViewById(R.id.Services);
        TextView Building = (TextView) findViewById(R.id.building);
        ImageButton kilFloorPlanButton = (ImageButton) findViewById(R.id.kil_floor_plan_button);

        //Set service info text
        Services.setText("Services: Bistro\nHours: 11-5 Mon-Fri\nLocation: Killam Library South Learning Commons");
        Building.setText("Hours:7:30am-9pm\n");

        kilFloorPlanButton.setImageResource(R.mipmap.kil_1);

        kilFloorPlanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent LaunchKillamFloorPlan = new Intent(v.getContext(), KillamFloorPlanActivity.class);
                startActivity(LaunchKillamFloorPlan);
            }
        });
    }
}
