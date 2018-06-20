package com.example.scott.dalmapproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class HenryHicksInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_henry_hicks_info);

        TextView Services = (TextView) findViewById(R.id.Services);
        TextView Building = (TextView) findViewById(R.id.building);
        //Set service info text
        Services.setText("Services: Registrars Office\nHours: 9-5 Mon-Sun\nLocation: Henry Hicks First Floor Room 130");
        Building.setText("Hours:8am-5pm\n");
    }
}

