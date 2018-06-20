package com.example.scott.dalmapproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class main_menu extends AppCompatActivity {

    private String sid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        {
            Button bt1 = findViewById(R.id.select1);
            Button bt2 = findViewById(R.id.select2);
            Button bt3 = findViewById(R.id.select3);
            Button bt4 = findViewById(R.id.select4);

            Bundle ids = getIntent().getExtras();
            sid = (String)ids.get("id");

            bt1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent1 = new Intent(getApplicationContext(), ClassListActivity.class);
                    intent1.putExtra("id", sid);
                    startActivity(intent1);

                }
            });

            bt2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent2 = new Intent(getApplicationContext(), ClassListActivity.class);
                    intent2.putExtra("id", sid);
                    startActivity(intent2);
                }
            });

            bt3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent3 = new Intent(getApplicationContext(), MainActivity.class);
                    intent3.putExtra("id", sid);
                    startActivity(intent3);

                }
            });

            bt4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /*
                    Intent intent4 = new Intent(getApplicationContext(), ServiceListActivity.class);
                    intent2.putExtra("id", sid);
                    startActivity(intent4);
                    */
                }
            });
        }
    }
}
