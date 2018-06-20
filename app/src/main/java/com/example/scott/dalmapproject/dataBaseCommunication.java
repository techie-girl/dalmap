package com.example.scott.dalmapproject;

import android.app.Activity;

public class dataBaseCommunication extends Activity{


    /*
     * This Java file is all about the communication between Firebase
     * we need check id password function and load Json Function
     * At last, the fuction for User to modify jason
     */

    public dataBaseCommunication (){}

    public boolean loginValidCheck (String userID, String userPassword){

        /* In this, there would be the codes for valid checking ID and Password
         * Currently, this return only true for testing
         */
        return true;
    }

    /*
    public ArrayList readFirebase(String userID){

        ArrayList <String> details_class = new ArrayList();
        return details_class;

    }
    */

    public void modifyingData () {
        // This will be code modifying Json fir user
    }
}