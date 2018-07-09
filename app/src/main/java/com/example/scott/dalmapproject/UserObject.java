package com.example.scott.dalmapproject;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserObject {

    public String bannerID;
    public String name;
    public ArrayList<ClassObject> classes;

    public UserObject(){
        //default constructor
    }

    //Main constructor for building UserObjects
    public UserObject(String bannerID, String name, ArrayList<ClassObject> classes){
        this.bannerID = bannerID;
        this.name = name;
        this.classes = classes;
    }

    /**
     * builds a hashmap out of the fields that make up the User object
     *
     * @return returns the hashmap of fields
     */
    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("bannerID", bannerID);
        result.put("name", name);
        result.put("classes", classes);

        return result;
    }
}
