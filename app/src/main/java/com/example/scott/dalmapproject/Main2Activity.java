package com.example.scott.dalmapproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        TextView Services = (TextView) findViewById(R.id.Services);
        //Set service info text
        Services.setText("Services1:    Subway Hours: 9-5 Mon-Sun    Location: Killam Library");

    }
}

