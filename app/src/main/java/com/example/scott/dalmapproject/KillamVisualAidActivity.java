package com.example.scott.dalmapproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class KillamVisualAidActivity extends AppCompatActivity {

    private final String imageTitleArr[] = { "Exterior Facade", "Main Lobby", "third", "fourth", "fifth", "sixth"};

    private final Integer imageIDArr[]={ R.mipmap.kil_basement, R.mipmap.kil_1, R.mipmap.kil_2, R.mipmap.kil_3, R.mipmap.kil_4, R.mipmap.kil_5};

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_killam_visual_aid);

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
