package com.example.scott.dalmapproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.lists);
        final String[] values = new String[]{"Henry Kicks", "Goldberg", "Killam Library"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.activity_list_item, android.R.id.text1, values);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (i == 0)
                {
                    Intent myIntent = new Intent(view.getContext(),Main2Activity.class);
                    startActivity(myIntent);
                }
                if (i == 1)
                {
                    Intent myIntent = new Intent(view.getContext(),Main3Activity.class);
                    startActivity(myIntent);
                }
                if (i == 2)
                {
                    Intent myIntent = new Intent(view.getContext(),Main4Activity.class);
                    startActivity(myIntent);
                }
            }
        });
    }
}