package com.example.scott.buildings_services_listview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Main3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        TextView Services = (TextView) findViewById(R.id.Services);
        //Set service info text
        Services.setText("Services2:   Second Cup Hours: 10-6 Mon-Sun    Location: Killam Library");

    }
}
