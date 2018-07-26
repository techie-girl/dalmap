/**
 * This is the object that holds the information for the user.
 * Constructs a user that has multiple attributes that define a user.
 *
 *  * @author Aqil
 * @author Arazoo
 * @author Chris
 * @author Scott
 * @author Jacob
 * @author Jaewoong
 */
package com.example.scott.dalmapproject;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserObject {

    public String bannerID, name, password;
    public ArrayList<String> classes;

    public UserObject(){
        //default constructor
    }

    //Main constructor for building UserObjects
    public UserObject(String bannerID, String name, ArrayList<String> classes, String password){
        this.bannerID = bannerID;
        this.name = name;
        this.classes = classes;
        this.password = password;
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
        result.put("password", password);

        return result;
    }
}