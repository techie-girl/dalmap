/*based on CreateList code from
  https://www.androidauthority.com/how-to-build-an-image-gallery-app-718976/
*/

package com.example.scott.dalmapproject;


import java.util.ArrayList;

public class CreateVisualAidList {

    private String imageTitle;
    private Integer imageID;

    /*getters and setters to add/retrieve elements from array
      and insert into array list
    */
    public String getImageTitle() {
        return imageTitle;
    }

    public void setImageTitle(String androidVersionName){
        this.imageTitle = androidVersionName;
    }

    public Integer getImageID(){
        return imageID;
    }

    public void setImageID(Integer androidImageURL){
        this.imageID = androidImageURL;
    }

    //import array list
    private ArrayList<CreateVisualAidList> prepData(){
        ArrayList<CreateVisualAidList> theImage = new ArrayList<>();
        for(int i = 0; i < imageTitleArr.length; i++){
            CreateVisualAidList createList = new CreateVisualAidList();
            createList.setImageTitle(imageTitleArr[i]);
        }
    }
}
