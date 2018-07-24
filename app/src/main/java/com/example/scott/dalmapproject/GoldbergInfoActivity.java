/**
 *
 * Creates the info activity for the Goldberg Building.
 * Lists information such as services, hours, and location.
 *
 * @author Scott
 * @author Chris
 * @author Aqil
 * @author Arazoo
 * @author Jacob
 * @author Jaewoong
 */
package com.example.scott.dalmapproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class GoldbergInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goldberg_info);

        TextView Services = (TextView) findViewById(R.id.Services);
        TextView Building = (TextView) findViewById(R.id.building);
        //Set service info text
        Services.setText("Services: Second Cup\nHours: 10-6 Mon-Sun\nLocation: Goldberg Computer Science Atrium");
        Building.setText("Hours:7am-2am\n");

    }
}
