package com.example.scott.dalmapproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;

public class ClassListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_list);

    }

    @Override
    protected void onStart(){
        super.onStart();

        final ListView listView = this.findViewById(R.id.Class_List_List_View);

        ArrayList<String> List = new ArrayList<>();
        List.add("Item 1");
        List.add("Item 2");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                List );

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                LayoutInflater inflater = (LayoutInflater)
                        getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.class_info_popup, null);

                int width = LinearLayout.LayoutParams.MATCH_PARENT;
                int height = LinearLayout.LayoutParams.MATCH_PARENT;
                boolean focusable = true;
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                String selectedItem = listView.getItemAtPosition(position).toString();

                ((TextView) popupWindow.getContentView().findViewById(R.id.ClassCode)).setText(selectedItem);
                ((TextView) popupWindow.getContentView().findViewById(R.id.ClassTitle)).setText(selectedItem);
                ((TextView) popupWindow.getContentView().findViewById(R.id.ClassDuration)).setText(selectedItem);
                ((TextView) popupWindow.getContentView().findViewById(R.id.ClassLocation)).setText(selectedItem);

                popupWindow.setAnimationStyle(R.style.Animation);

                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

                // dismiss the popup window when touched
                popupView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        popupWindow.dismiss();
                        return true;
                    }
                });
            }
        });
    }
}
