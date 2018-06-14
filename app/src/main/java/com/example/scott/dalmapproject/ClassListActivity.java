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

        //Gets the ListView from the layout resource file by id
        final ListView listView = this.findViewById(R.id.Class_List_List_View);

        //ArrayList used by adapter to build the list in the ListView
        ArrayList<String> List = new ArrayList<>();

        //Temp information to populate ListView
        List.add("Item 1");
        List.add("Item 2");

        //Adapter to populate the list
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_2,
                List );

        //Populate the list
        listView.setAdapter(arrayAdapter);

        //How to respond to a list item being clicked
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                //LayoutInflater to create a new view from a layout
                LayoutInflater inflater = (LayoutInflater)
                        getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.class_info_popup, null);

                //Create the popup window
                int width = LinearLayout.LayoutParams.MATCH_PARENT;
                int height = LinearLayout.LayoutParams.MATCH_PARENT;
                boolean focusable = true;
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                //Get the selected item from the list
                String selectedItem = listView.getItemAtPosition(position).toString();

                //Set the textViews in the popup window to be proper values
                ((TextView) popupWindow.getContentView().findViewById(R.id.ClassCode)).setText(selectedItem);
                ((TextView) popupWindow.getContentView().findViewById(R.id.ClassTitle)).setText(selectedItem);
                ((TextView) popupWindow.getContentView().findViewById(R.id.ClassDuration)).setText(selectedItem);
                ((TextView) popupWindow.getContentView().findViewById(R.id.ClassLocation)).setText(selectedItem);

                //Tell the popup window how to animate its creation and destruction
                popupWindow.setAnimationStyle(R.style.Animation);

                //Show the popup window on the current activity
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

                //How to respond when the popup window is clicked
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
