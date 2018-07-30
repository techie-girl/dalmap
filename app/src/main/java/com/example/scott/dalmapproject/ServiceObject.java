/**
 * This creates a class object that will be stored in the class list.
 * It holds all the information for a specific class.
 *
 * @author Jacob
 * @author Aqil
 * @author Arazoo
 * @author Chris
 * @author Scott
 * @author Jaewoong
 */
package com.example.scott.dalmapproject;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ServiceObject {

    public String building, name;
    public ArrayList<Integer> hours;

    public ServiceObject() {
        //default constructor
    }

    //Main constructor for building UserObjects
    public ServiceObject(String building, String name, ArrayList<Integer> hours) {
        this.building = building;
        this.name = name;
        this.hours = hours;
    }

    /**
     * builds a hashmap out of the fields that make up the User object
     *
     * @return returns the hashmap of fields
     */
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("building", building);
        result.put("name", name);
        result.put("hours", hours);

        return result;
    }
}