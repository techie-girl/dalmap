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

public class BuildingListActivity extends AppCompatActivity {
    ListView listView;
    EditText listViewFilter;
    SearchView searchView;

    ArrayList<String> buildings = new ArrayList<>();
    ArrayList<BuildingObject> buildingObjects = new ArrayList<>();


    /**
     * OnCreate call back for main section of the activity
     *
     * @param savedInstanceState -
     */
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_building_list);

        /**Only uncomment when you want to add more Services to the database
         * Delete what was already there first
         * Manually remove the values using the cloud terminal to ensure things are cleaned up properly
         */
        launchBuildingBuilder();
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
     * Main callback for the BuildingListActivity
     *
     * The buildings list is populated
     * The buildings list is displayed in a list view
     * The list view can be filtered based on the listViewFilters textChangedListener
     * The list view on item click response in managed
     */
    @Override
    protected void onStart(){
        super.onStart();

        //empty the service lists
        buildings.clear();
        buildingObjects.clear();

        //Populate the classes list and the ClassObjects list
        getBuilding();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                buildLayout();
            }
        }, 500);
    }

    private void buildLayout(){
        //Gets the ListView from the layout resource file by id
        final ListView listView = this.findViewById(R.id.Building_List_List_View);
        listViewFilter = findViewById(R.id.List_View_Filter);

        final ArrayAdapter<String> firebaseAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, buildings);

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
     * Used to launch the FirebaseBuildingBuilder activity which adds buildings to firebase
     */
    private void launchBuildingBuilder(){
        //populate database with classes
        Intent launchBuildingBuilder = new Intent(getApplicationContext(), FirebaseBuildingBuilder.class);
        startActivity(launchBuildingBuilder);
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
     * Gets the building objects from firebase for use in the class list
     */
    private void getBuilding() {
        final FirebaseInstanceData firebaseInstanceData = (FirebaseInstanceData) getApplicationContext();

        //ValueEventListener that retrieves the services data set from firebase as a DataSnapshot
        ValueEventListener getBuildingFromDatabaseListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //Adds every service in the Service tree to the service ArrayList
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    buildings.add(ds.getValue(BuildingObject.class).name);
                    //also adds the ServiceObjects to another array list used to access the rest of the service information
                    buildingObjects.add(ds.getValue(BuildingObject.class));
                }
            }

            //How to respond when the database lookup has been canceled. Ignored for now
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        //Add a ValueEventListener that will get all the children in the Building section
        //Runs getBuildingFromDatabaseListener once when the class is first run, then again when values change in the DB
        firebaseInstanceData.firebaseReferenceBuilding.addListenerForSingleValueEvent(getBuildingFromDatabaseListener);
    }

    /**
     * Inflates a popup window layout to display information about the service
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
        View popupView = inflater.inflate(R.layout.building_info_popup, null);

        //Create the popup window
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);

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

        //button functionality
        interiorButtionFunctionality(popupWindow, position);
        visualAidButtonFunctionality(popupWindow, position);
        exteriorButtonFunctionality(popupWindow, position);
    }

    private void interiorButtionFunctionality(PopupWindow popupWindow, final int position){
        Button interiorButton = popupWindow.getContentView().findViewById(R.id.popup_window_interior_button);
        interiorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BuildingObject building = buildingObjects.get(position);
                if(building.name.toLowerCase().equals("killam")){
                    Intent LaunchKillamFloorPlan = new Intent(v.getContext(), KillamFloorPlanActivity.class);
                    startActivity(LaunchKillamFloorPlan);
                }
                else if(building.name.toLowerCase().equals("henry hicks")){

                }
                else if(building.name.toLowerCase().equals("goldberg")){

                }
            }
        });
    }

    private void visualAidButtonFunctionality(PopupWindow popupWindow, final int position){
        Button visualAidButton = popupWindow.getContentView().findViewById(R.id.popup_window_visual_aid_button);
        visualAidButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BuildingObject building = buildingObjects.get(position);
                if(building.name.toLowerCase().equals("killam")){
                    Intent LaunchKillamVisualAid = new Intent(getApplicationContext(), KillamVisualAidActivity.class);
                    startActivity(LaunchKillamVisualAid);
                }
                else if(building.name.toLowerCase().equals("henry hicks")){
                    Intent LaunchHenryHicksVisualAid = new Intent(getApplicationContext(), HenryHicksVisualAidActivity.class);
                    startActivity(LaunchHenryHicksVisualAid);
                }
                else if(building.name.toLowerCase().equals("goldberg")){
                    Intent LaunchGoldbergVisualAid = new Intent(getApplicationContext(), GoldbergVisualAidActivity.class);
                    startActivity(LaunchGoldbergVisualAid);
                }
            }
        });
    }

    private void exteriorButtonFunctionality(PopupWindow popupWindow, final int position){
        Button exteriorButton = popupWindow.getContentView().findViewById(R.id.popup_window_exterior_button);
        exteriorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BuildingObject building = buildingObjects.get(position);
                if(building.name.toLowerCase().equals("killam")){
                    Intent LaunchMapActivity = new Intent(getApplicationContext(), MapActivity.class);
                    startActivity(LaunchMapActivity);
                }
                else if(building.name.toLowerCase().equals("henry hicks")){
                    Intent LaunchMapActivity = new Intent(getApplicationContext(), MapActivity.class);
                    startActivity(LaunchMapActivity);
                }
                else if(building.name.toLowerCase().equals("goldberg")){
                    Intent LaunchMapActivity = new Intent(getApplicationContext(), MapActivity.class);
                    startActivity(LaunchMapActivity);
                }
            }
        });
    }

    /**
     * Gets the buildingObject that was selected from the list view
     * Sets the text on the popup window to the text of the buildingObject
     *
     * @param popupWindow - The layout that is being modified
     * @param position - The position in the listview that was selected
     */
    private void setPopupText(PopupWindow popupWindow, int position){
        //build a ServiceObject from the firebase adapter
        BuildingObject buildingObject = buildingObjects.get(position);

        ArrayList<Integer> hours = buildingObject.hours;

        //define the hours
        String sundayOpen = String.valueOf(hours.get(0) + ":" +  hours.get(1));
        String sundayClose = String.valueOf(hours.get(2) + ":" + hours.get(3));
        String mondayOpen = String.valueOf(hours.get(4) + ":" + hours.get(5));
        String mondayClose = String.valueOf(hours.get(6) + ":" + hours.get(7));
        String tuesdayOpen = String.valueOf(hours.get(8) + ":" + hours.get(9));
        String tuesdayClose = String.valueOf(hours.get(10) + ":" + hours.get(11));
        String wednesdayOpen = String.valueOf(hours.get(12) + ":" + hours.get(13));
        String wednesdayClose = String.valueOf(hours.get(14) + ":" + hours.get(15));
        String thursdayOpen = String.valueOf(hours.get(16) + ":" + hours.get(17));
        String thursdayClose = String.valueOf(hours.get(18) + ":" + hours.get(19));
        String fridayOpen = String.valueOf(hours.get(20) + ":" + hours.get(21));
        String fridayClose = String.valueOf(hours.get(22) + ":" + hours.get(23));
        String saturdayOpen = String.valueOf(hours.get(24) + ":" + hours.get(25));
        String saturdayClose = String.valueOf(hours.get(26) + ":" + hours.get(27));


        //so long as the selected item is valid, get a view for each TextView in the class_info_popup
        if(buildingObject != null) {
            TextView buildingName = popupWindow.getContentView().findViewById(R.id.buildingName);
            TextView buildingSunday = popupWindow.getContentView().findViewById(R.id.buildingSunday);
            TextView buildingMonday = popupWindow.getContentView().findViewById(R.id.buildingMonday);
            TextView buildingTuesday = popupWindow.getContentView().findViewById(R.id.buildingTuesday);
            TextView buildingWednesday = popupWindow.getContentView().findViewById(R.id.buildingWednesday);
            TextView buildingThursday = popupWindow.getContentView().findViewById(R.id.buildingThursday);
            TextView buildingFriday = popupWindow.getContentView().findViewById(R.id.buildingFriday);
            TextView buildingSaturday = popupWindow.getContentView().findViewById(R.id.buildingSaturday);

            //apply the updated text to the class_info_popup
            buildingName.setText(String.valueOf(buildingObject.name));
            buildingSunday.setText(String.valueOf(sundayOpen + " - " + sundayClose));
            buildingMonday.setText(String.valueOf(mondayOpen + " - " + mondayClose));
            buildingTuesday.setText(String.valueOf(tuesdayOpen + " - " + tuesdayClose));
            buildingWednesday.setText(String.valueOf(wednesdayOpen + " - " + wednesdayClose));
            buildingThursday.setText(String.valueOf(thursdayOpen + " - " + thursdayClose));
            buildingFriday.setText(String.valueOf(fridayOpen + " - " + fridayClose));
            buildingSaturday.setText(String.valueOf(saturdayOpen + " - " + saturdayClose));
        }
    }
}