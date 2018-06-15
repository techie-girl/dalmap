package com.example.scott.dalmapproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class classLists extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.class_list);

        String[] list = {"CSCI 3130", "STAT 2610", "MATH 2060", "CSCI 3110", "MATH 2001"};

        ArrayAdapter <String> adapter;

        adapter = new ArrayAdapter<String> (this, android.R.layout.simple_list_item_1, list);

        ListView listView = (ListView) findViewById(R.id.classListView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent2 = new Intent(getApplicationContext(), detailMap.class);
                startActivity(intent2);
            }
        });


    }

    /*
     *This activity will be about array adaptor using Json data which gets from Firebase
     */

}
