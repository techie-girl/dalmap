package com.example.scott.dalmapproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class Main4Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        TextView Services = (TextView) findViewById(R.id.Services);
        //Set service info text
        Services.setText("Services3:    Bistro Hours: 11-5 Mon-Fri    Location: Killam Library");

    }
}
