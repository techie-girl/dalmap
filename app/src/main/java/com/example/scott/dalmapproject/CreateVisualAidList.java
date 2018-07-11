/*based on CreateList code from
 https://www.androidauthority.com/how-to-build-an-image-gallery-app-718976/
 */

package com.example.scott.dalmapproject;

public class CreateVisualAidList {

    private String imageTitle;
    private Integer imageID;

    public CreateVisualAidList(String imageTitle, Integer imageID){
        this.imageTitle = imageTitle;
        this.imageID = imageID;
    }

    /*getters and setters to add/retrieve elements from array
       and insert into array list
    */
    public String getImageTitle() {

        return imageTitle;
    }

    public Integer getImageID(){

        return imageID;
    }
}