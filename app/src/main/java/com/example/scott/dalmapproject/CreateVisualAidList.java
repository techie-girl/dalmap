/**
 * based on CreateList code from
 *https://www.androidauthority.com/how-to-build-an-image-gallery-app-718976/
 *
 * This creates a list of images that are used as visual aids to a location.
 *
 * @author Aqil
 * @author Arazoo
 * @author Chris
 * @author Scott
 * @author Jacob
 * @author Jaewoong
 */
package com.example.scott.dalmapproject;

public class CreateVisualAidList {

    private String imageTitle;
    private Integer imageID;

    /**
     * Constructor for visual aid list
     * @param imageTitle Title of image.
     * @param imageID ID of the image.
     */
    public CreateVisualAidList(String imageTitle, Integer imageID){
        this.imageTitle = imageTitle;
        this.imageID = imageID;
    }

    /*getters and setters to add/retrieve elements from array
       and insert into array list
    */

    /**
     *Gets the title of the image.
     * @return the title of the image.
     */
    public String getImageTitle() {

        return imageTitle;
    }

    /**
     * gets the ID of the image.
     * @return the image ID.
     */
    public Integer getImageID(){

        return imageID;
    }
}