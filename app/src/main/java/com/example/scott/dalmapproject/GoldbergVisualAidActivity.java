/**
 * This is the activity for the Visual Aid of the Goldberg Computer Science Building.
 * It displays images of the Goldberg Computer Science Building.
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

public class GoldbergVisualAidActivity extends AppCompatActivity{

    private final String imageTitleArr[] = { "Exterior Facade", "Main Lobby"};

    private final Integer imageIDArr[]={ R.mipmap.goldberg_front, R.mipmap.goldberg_lobby};

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_goldberg_visual_aid);

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
