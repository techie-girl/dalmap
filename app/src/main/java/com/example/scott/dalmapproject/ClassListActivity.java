package com.example.scott.dalmapproject;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;

public class ClassListActivity extends AppCompatActivity {

    EditText listViewFilter;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_list);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the options menu from XML
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false); // Do not iconify the widget;

        //How to respond to changes in the search boxes text
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            //Do nothing on submit, its handled during text change
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            //Set the filter to be the value of the query
            @Override
            public boolean onQueryTextChange(String newText) {
                listViewFilter.setText(searchView.getQuery());
                return false;
            }
        });

        return true;
    }

    @Override
    protected void onStart(){
        super.onStart();

        //Gets the ListView from the layout resource file by id
        final ListView listView = this.findViewById(R.id.Class_List_List_View);
        listViewFilter = findViewById(R.id.List_View_Filter);

        //ArrayList used by adapter to build the list in the ListView
        ArrayList<String> List = new ArrayList<>();

        //Temp information to populate ListView
        for(int i = 0; i < 50; i++){
            List.add("CSCI 100"+ i);
        }

        //Adapter to populate the list
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                List );

        //Populate the list
        listView.setAdapter(arrayAdapter);

        //How to respond to the filter being changed
        listViewFilter.addTextChangedListener(new TextWatcher() {

            //Do nothing while the text is being changed
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            //Do nothing before the text is changed
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,int after) {
            }

            //After the text is changed, apply the filter to the list
            @Override
            public void afterTextChanged(Editable s) {
                // Call back the Adapter with current character to Filter
                arrayAdapter.getFilter().filter(s.toString());
            }
        });


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
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);

                //Get the selected item from the list
                String selectedItem = listView.getItemAtPosition(position).toString();

                //Set the textViews in the popup window to be proper values
                ((TextView) popupWindow.getContentView().findViewById(R.id.ClassCode)).setText(selectedItem);
                ((TextView) popupWindow.getContentView().findViewById(R.id.ClassTitle)).setText("Intro to Computer Programming");
                ((TextView) popupWindow.getContentView().findViewById(R.id.ClassDuration)).setText("SUMMER (1): 07-MAY-2018 - 03-AUG-2018");
                ((TextView) popupWindow.getContentView().findViewById(R.id.ClassLocation)).setText("Studley COMPUTER SCIENCE 134");

                //Tell the popup window how to animate its creation and destruction
                popupWindow.setAnimationStyle(R.style.Animation);

                //Show the popup window on the current activity
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);


                final Button button = popupWindow.getContentView().findViewById(R.id.popup_window_button);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });

                //How to respond when the popup window is clicked
                popupView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        //popupWindow.dismiss();
                        return true;
                    }
                });
            }
        });
    }
}
