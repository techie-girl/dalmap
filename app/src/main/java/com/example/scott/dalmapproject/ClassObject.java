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

import java.util.HashMap;
import java.util.Map;

public class ClassObject {

    public String courseID;
    public String courseTitle;
    public String courseDuration;
    public int CRN;
    public int section;
    public String lectureType;
    public int crHrs;
    public boolean monday;
    public boolean tuesday;
    public boolean wednesday;
    public boolean thursday;
    public boolean friday;
    public int startHour;
    public int startMinute;
    public int endHour;
    public int endMinute;
    public String classLocation;
    public int seats;
    public int currentFull;
    public int availSeats;
    public String professor;
    public String tuitionCode;
    public int bHrs;


    public ClassObject() {
        // Default constructor required for calls to DataSnapshot.getValue
    }

    /**
     * Main constructor for building ClassObjects
     * @param courseID Unique ID for the course
     * @param courseTitle Course Name
     * @param courseDuration Duration of class
     * @param CRN
     * @param section Section number
     * @param lectureType Type of Lecture
     * @param crHrs credited hours
     * @param monday
     * @param tuesday
     * @param wednesday
     * @param thursday
     * @param friday
     * @param startHour Hour the class starts
     * @param startMinute minutes the class starts
     * @param endHour hour the class ends
     * @param endMinute minutes the class ends
     * @param classLocation location of class
     * @param seats available seats in class
     * @param currentFull How many of those seats are taken
     * @param availSeats Number of seats still available
     * @param professor Name of professor teaching the course
     * @param tuitionCode The tuition code
     * @param bHrs
     */
    public ClassObject(String courseID,String courseTitle,String courseDuration,int CRN,int section,
                       String lectureType,int crHrs,boolean monday,boolean tuesday,boolean wednesday,
                       boolean thursday,boolean friday,int startHour,int startMinute,int endHour,
                       int endMinute,String classLocation,int seats,int currentFull,int availSeats,
                       String professor,String tuitionCode,int bHrs) {
        this.courseID = courseID;
        this.courseTitle = courseTitle;
        this.courseDuration = courseDuration;
        this.CRN = CRN;
        this.section = section;
        this.lectureType = lectureType;
        this.crHrs = crHrs;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.endHour = endHour;
        this.endMinute = endMinute;
        this.classLocation = classLocation;
        this.seats = seats;
        this.currentFull = currentFull;
        this.availSeats = availSeats;
        this.professor = professor;
        this.tuitionCode = tuitionCode;
        this.bHrs = bHrs;
    }

    /**
     * builds a hashmap out of the fields that make up the class object
     *
     * @return returns the hashmap of fields
     */
    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("courseID", courseID);
        result.put("courseTitle", courseTitle);
        result.put("courseDuration", courseDuration);
        result.put("CRN", CRN);
        result.put("section", section);
        result.put("lectureType", lectureType);
        result.put("crHrs", crHrs);
        result.put("monday", monday);
        result.put("tuesday", tuesday);
        result.put("wednesday", wednesday);
        result.put("thursday", thursday);
        result.put("friday", friday);
        result.put("startHour", startHour);
        result.put("startMinute", startMinute);
        result.put("endHour", endHour);
        result.put("endMinute", endMinute);
        result.put("classLocation", classLocation);
        result.put("seats", seats);
        result.put("currentFull", currentFull);
        result.put("availSeats", availSeats);
        result.put("professor", professor);
        result.put("tuitionCode", tuitionCode);
        result.put("bHrs", bHrs);

        return result;
    }
}
