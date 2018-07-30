/**
 * This is the activity for the Visual Aid of the Henry Hicks Building.
 * It displays images of the Henry Hicks Building.
 * It also provides labels for the different images.
 *
 * @author Aqil
 * @author Arazoo
 * @author Chris
 * @author Scott
 * @author Jacob
 * @author Jaewoong
 */
package com.example.scott.dalmapproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class HenryHicksVisualAidActivity extends AppCompatActivity{

    private final String imageTitleArr[] = { "Exterior Facade", "Main Lobby"};

    private final Integer imageIDArr[]={ R.mipmap.henry_hicks_front, R.mipmap.henry_hicks_lobby};

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_henry_hicks_visual_aid);

        RecyclerView imageGallery = (RecyclerView) findViewById(R.id.imageGallery);
        imageGallery.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        imageGallery.setLayoutManager(layoutManager);

        ArrayList<CreateVisualAidList> lists = prepData();

        RecyclerViewArrayListAdapter arrayAdapter = new RecyclerViewArrayListAdapter(getApplicationContext(), lists);

        imageGallery.setAdapter(arrayAdapter);
    }

    public ArrayList<CreateVisualAidList> prepData(){

        ArrayList<CreateVisualAidList> theImage = new ArrayList<>();

        for(int i = 0; i < imageIDArr.length; i++){
            CreateVisualAidList createList = new CreateVisualAidList(imageTitleArr[i], imageIDArr[i]);
            theImage.add(createList);
        }
        return theImage;
    }
}
