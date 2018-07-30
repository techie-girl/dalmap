/**
 * Creates a class list that will display all available classes.
 * Accomplished by retrieving data from Firebase database.
 *
 * @author Jacob
 * @author Aqil
 * @author Arazoo
 * @author Chris
 * @author Scott
 * @author Jaewoong
 */
package com.example.scott.dalmapproject;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
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
    UserObject userObject = new UserObject();
    String sid;

    /**
     * OnCreate call back for main section of the activity
     *
     * @param savedInstanceState -
     */
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_class_list);

        /**Only uncomment when you want to add more classes to the database
         * Delete what was already there first
         * Manually remove the values using the cloud terminal to ensure things are cleaned up properly
         */
        //launchClassBuilder();


        /**Only uncomment when you want to add more users to the database
         * Delete what was already there first
         * Manually remove the values using the cloud terminal to ensure things are cleaned up properly
         */
        //launchUserBuilder();
    }

    /**
     * OnCreate call back for the top options menu
     *
     * @param menu - The menu of the current activity
     * @return - True if createSearchView() didn't crash the application
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        createSearchView(menu);

        return true;
    }

    /**
     * Main callback for the ClassListActivity
     *
     * The logout button is managed
     * The classes list is populated
     * The classes list is displayed in a list view
     * The list view can be filtered based on the listViewFilters textChangedListener
     * The list view on item click response in managed
     */
    @Override
    protected void onStart(){
        super.onStart();

        //Manage logout button
        logoutButton();

        getUser();

        //empty the class lists
        classes.clear();
        classObjects.clear();

        //Populate the classes list and the ClassObjects list
        getClasses();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                buildLayout();
            }
        }, 500);
    }

    /**
     * Used to launch the FirebaseClassBuilder activity which adds classes to firebase
     */
    private void launchClassBuilder(){
        //populate database with classes
        Intent launchClassBuilder = new Intent(getApplicationContext(), FirebaseClassBuilder.class);
        startActivity(launchClassBuilder);
    }

    /**
     * Used to launch the FirebaseUserBuilder activity which adds users to firebase
     */
    private void launchUserBuilder(){
        //populate database with users
        Intent launchUserBuilder = new Intent(getApplicationContext(), FirebaseUserBuilder.class);
        startActivity(launchUserBuilder);
    }

    private void buildLayout(){
        //Gets the ListView from the layout resource file by id
        final ListView listView = this.findViewById(R.id.Class_List_List_View);
        listViewFilter = findViewById(R.id.List_View_Filter);

        final ArrayAdapter<String> firebaseAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, classes);

        //Populate the list
        listView.setAdapter(firebaseAdapter);

        //Text watcher for the textChangedListener
        TextWatcher textWatcher = new TextWatcher() {
            /**
             * Does nothing while text is being changed.
             * @param s
             * @param start
             * @param before
             * @param count
             */
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            /**
             * Do nothing before the text is changed.
             * @param s
             * @param start
             * @param count
             * @param after
             */
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,int after) {
            }

            /**
             * After the text is changed, apply the filter to the list
             * @param s
             */
            @Override
            public void afterTextChanged(Editable s) {
                // Call back the Adapter with current character to Filter
                firebaseAdapter.getFilter().filter(s.toString());
            }
        };

        //onItemClickListener for the list view
        AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {

            /**
             * How to respond to a list item being clicked
             * @param parent
             * @param view
             * @param position
             * @param id
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                inflatePopup(view, position);
            }
        };

        //How to respond to the filter being changed
        listViewFilter.addTextChangedListener(textWatcher);

        //How to respond to a list item being clicked
        listView.setOnItemClickListener(onItemClickListener);
    }

    /**
     * Creates a searchView in the activities options menu.
     * Manages the text in the search and sends it to the text filter
     *
     * @param menu - The menu object which contains the search view
     */
    private void createSearchView(Menu menu){
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
    }

    /**
     * TODO refactor
     *
     * Manages the logout button
     */
    private void logoutButton(){
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
    }

    private void getUser(){

        final Bundle user = getIntent().getExtras();
        sid = (String)user.get("id");

        final FirebaseInstanceData firebaseInstanceData = (FirebaseInstanceData)getApplicationContext();
        ValueEventListener getUserFromDatabaseListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    if(ds.getValue(UserObject.class).bannerID.toLowerCase().equals(sid.toLowerCase())){
                        userObject = ds.getValue(UserObject.class);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        firebaseInstanceData.firebaseReferenceUsers.addListenerForSingleValueEvent(getUserFromDatabaseListener);
    }

    /**
     * Gets the class objects from firebase for use in the class list
     */
    private void getClasses(){
        final FirebaseInstanceData firebaseInstanceData = (FirebaseInstanceData)getApplicationContext();

        // display all class lists on firebase
        //ValueEventListener that retrieves the classes data set from firebase as a DataSnapshot
        ValueEventListener getClassesFromDatabaseListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //Adds every class in the Class tree to the classes ArrayList
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
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

    }

    /**
     * Inflates a popup window layout to display information about the class
     * Sets the text values in the popup window
     * Sets the animation style for the popup window
     * Sets the location for the popup window
     * Sets the functionality of the cancel button
     *
     * @param view - The current view of the application
     * @param position - The position in the list view that was clicked
     */
    private void inflatePopup(View view, final int position){
        //LayoutInflater to create a new view from a layout
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.class_info_popup, null);

        //Create the popup window
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);

        final FirebaseInstanceData firebaseInstanceData = (FirebaseInstanceData)getApplicationContext();

        //sets the text values in the popup window
        setPopupText(popupWindow, position);

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

        Button addClassButton = popupWindow.getContentView().findViewById(R.id.popup_add_class_button);
        addClassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String newClass = String.valueOf(classObjects.get(position).CRN);
                boolean duplicate = false;

                if(userObject.classes != null) {
                    for (String s : userObject.classes) {
                        if (s.equals(newClass)) {
                            duplicate = true;
                        }
                    }

                    if (!duplicate) {
                        userObject.classes.add(newClass);
                        firebaseInstanceData.firebaseReferenceUsers.child(userObject.bannerID).setValue(userObject);
                    }
                }else{
                    userObject.classes = new ArrayList<>();
                    userObject.classes.add(newClass);
                    firebaseInstanceData.firebaseReferenceUsers.child(userObject.bannerID).setValue(userObject);
                }
            }
        });
    }

    /**
     * Gets the classObject that was selected from the list view
     * Sets the text on the popup window to the text of the classObject
     *
     * @param popupWindow - The layout that is being modified
     * @param position - The position in the listview that was selected
     */
    private void setPopupText(PopupWindow popupWindow, int position){
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
    }


}