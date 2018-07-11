package com.example.scott.dalmapproject;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ClassListActivity extends AppCompatActivity {

    EditText listViewFilter;
    SearchView searchView;
    ArrayList<String> classes = new ArrayList<>();
    ArrayList<ClassObject> classObjects = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_list);

        /**Only uncomment when you want to add more classes to the database
         * Delete what was already there first
         * Manually remove the values using the cloud terminal to ensure things are cleaned up properly
         */

        //populate database with classes
        //Intent launchClassBuilder = new Intent(getApplicationContext(), FirebaseClassBuilder.class);
        //startActivity(launchClassBuilder);

        /**Only uncomment when you want to add more users to the database
         * Delete what was already there first
         * Manually remove the values using the cloud terminal to ensure things are cleaned up properly
         */
        //populate database with users
        //Intent launchUserBuilder = new Intent(getApplicationContext(), FirebaseUserBuilder.class);
        //startActivity(launchUserBuilder);
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

        Button logoutButton = (Button)findViewById(R.id.class_list_log_out_button);

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

        //Gets the ListView from the layout resource file by id
        final ListView listView = this.findViewById(R.id.Class_List_List_View);
        listViewFilter = findViewById(R.id.List_View_Filter);

        final FirebaseInstanceData firebaseInstanceData = (FirebaseInstanceData)getApplicationContext();

        //ValueEventListener that retrieves the classes data set from firebase as a DataSnapshot
        final ValueEventListener getClassesFromDatabaseListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //Adds every class in the Class tree to the classes ArrayList
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    classes.add(ds.getValue(ClassObject.class).courseID + " - " +
                            ds.getValue(ClassObject.class).courseTitle);
                    //also adds the ClassObjects to another array list used to access the rest of the class information
                    classObjects.add(ds.getValue(ClassObject.class));
                }
            }

            //How to respond when the database lookup has been canceled. Ignored for now
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        //Add a ValueEventListener that will get all the children in the Classes section
        //Runs getClassesFromDatabaseListener once when the class is first run, then again when values change in the DB
        firebaseInstanceData.firebaseReferenceClasses.addListenerForSingleValueEvent(getClassesFromDatabaseListener);

        final ArrayAdapter<String> firebaseAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, classes);

        //Populate the list
        listView.setAdapter(firebaseAdapter);

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
                firebaseAdapter.getFilter().filter(s.toString());
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

                //build a ClassObject from the firebase adapter
                ClassObject classObject = classObjects.get(position);

                //so long as the selected item is valid, get a view for each TextView in the class_info_popup
                if(classObject != null) {
                    TextView courseID = popupWindow.getContentView().findViewById(R.id.courseID);
                    TextView courseTitle = popupWindow.getContentView().findViewById(R.id.courseTitle);
                    TextView courseDuration = popupWindow.getContentView().findViewById(R.id.courseDuration);
                    TextView CRN = popupWindow.getContentView().findViewById(R.id.CRN);
                    TextView section = popupWindow.getContentView().findViewById(R.id.section);
                    TextView lectureType = popupWindow.getContentView().findViewById(R.id.lectureType);
                    TextView crHrs = popupWindow.getContentView().findViewById(R.id.crHrs);
                    TextView monday = popupWindow.getContentView().findViewById(R.id.monday);
                    TextView tuesday = popupWindow.getContentView().findViewById(R.id.tuesday);
                    TextView wednesday = popupWindow.getContentView().findViewById(R.id.wednesday);
                    TextView thursday = popupWindow.getContentView().findViewById(R.id.thursday);
                    TextView friday = popupWindow.getContentView().findViewById(R.id.friday);
                    TextView startHour = popupWindow.getContentView().findViewById(R.id.startHour);
                    TextView startMinute = popupWindow.getContentView().findViewById(R.id.startMinute);
                    TextView endHour = popupWindow.getContentView().findViewById(R.id.endHour);
                    TextView endMinute = popupWindow.getContentView().findViewById(R.id.endMinute);
                    TextView classLocation = popupWindow.getContentView().findViewById(R.id.classLocation);
                    TextView seats = popupWindow.getContentView().findViewById(R.id.seats);
                    TextView currentFull = popupWindow.getContentView().findViewById(R.id.currentFull);
                    TextView availSeats = popupWindow.getContentView().findViewById(R.id.availSeats);
                    TextView professor = popupWindow.getContentView().findViewById(R.id.professor);
                    TextView tuitionCode = popupWindow.getContentView().findViewById(R.id.tuitionCode);
                    TextView bHrs = popupWindow.getContentView().findViewById(R.id.bHrs);

                    //apply the updated text to the class_info_popup
                    courseID.setText(String.valueOf(classObject.courseID));
                    courseTitle.setText(String.valueOf(classObject.courseTitle));
                    courseDuration.setText(String.valueOf(classObject.courseDuration));
                    CRN.setText(String.valueOf(classObject.CRN));
                    section.setText(String.valueOf(classObject.section));
                    lectureType.setText(String.valueOf(classObject.lectureType));
                    crHrs.setText(String.valueOf(classObject.crHrs));
                    monday.setText(String.valueOf(classObject.monday));
                    tuesday.setText(String.valueOf(classObject.tuesday));
                    wednesday.setText(String.valueOf(classObject.wednesday));
                    thursday.setText(String.valueOf(classObject.thursday));
                    friday.setText(String.valueOf(classObject.friday));
                    startHour.setText(String.valueOf(classObject.startHour));
                    startMinute.setText(String.valueOf(classObject.startMinute));
                    endHour.setText(String.valueOf(classObject.endHour));
                    endMinute.setText(String.valueOf(classObject.endMinute));
                    classLocation.setText(String.valueOf(classObject.classLocation));
                    seats.setText(String.valueOf(classObject.seats));
                    currentFull.setText(String.valueOf(classObject.currentFull));
                    availSeats.setText(String.valueOf(classObject.availSeats));
                    professor.setText(String.valueOf(classObject.professor));
                    tuitionCode.setText(String.valueOf(classObject.tuitionCode));
                    bHrs.setText(String.valueOf(classObject.bHrs));
                }

                //Tell the popup window how to animate its creation and destruction
                popupWindow.setAnimationStyle(R.style.Animation);

                //Show the popup window on the current activity
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

                //close button for the class_info_popup
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
