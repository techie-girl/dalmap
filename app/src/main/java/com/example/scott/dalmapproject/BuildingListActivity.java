/**
 * Creates a listview used to see a list of buildings.
 * The values of the building list are hard-coded.
 * A button is created for logout on this activity.
 * Activity listens for listview item clicks.
 * If an item is clicked, it displays building info.
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
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class BuildingListActivity extends AppCompatActivity {
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building_list);

        Button logoutButton = (Button)findViewById(R.id.building_list_log_out_button);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.logout_layout);
                Button confirmButton = (Button)findViewById(R.id.confirm_log_out_button);

                confirmButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent relaunch = new Intent(getApplicationContext(), Login.class);
                        startActivity(relaunch);
                    }
                });
                finish();
            }
        });

        listView = (ListView) findViewById(R.id.lists);

        final String[] values = new String[]{"Henry Hicks", "Goldberg", "Killam Library"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, values);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (i == 0)
                {
                    Intent myIntent = new Intent(view.getContext(),HenryHicksInfoActivity.class);
                    startActivity(myIntent);
                }
                if (i == 1)
                {
                    Intent myIntent = new Intent(view.getContext(),GoldbergInfoActivity.class);
                    startActivity(myIntent);
                }
                if (i == 2)
                {
                    Intent myIntent = new Intent(view.getContext(),KillamInfoActivity.class);
                    startActivity(myIntent);
                }
            }
        });
    }
}