package com.example.scott.dalmapproject;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BuildingObject {

    public String name;
    public ArrayList<Integer> hours;

    public BuildingObject() {
        //default constructor
    }

    //Main constructor for building BuildingObjects
    public BuildingObject(String name, ArrayList<Integer> hours) {
        this.name = name;
        this.hours = hours;
    }

    /**
     * builds a hashmap out of the fields that make up the Building object
     *
     * @return returns the hashmap of fields
     */
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("hours", hours);

        return result;
    }
}
