package com.example.scott.dalmapproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
    }
}
