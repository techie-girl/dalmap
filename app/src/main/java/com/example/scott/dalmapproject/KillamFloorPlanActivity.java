package com.example.scott.dalmapproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class KillamFloorPlanActivity extends AppCompatActivity{

    protected void onCreate(Bundle savedBundleState){
        super.onCreate(savedBundleState);
        setContentView(R.layout.activity_killam_floor_plan_imageview);

        final ImageView killamFloorPlanImage = (ImageView) findViewById(R.id.killam_floor_plan_image);

        final int[] levels = {R.drawable.kil_basement, R.drawable.kil_1, R.drawable.kil_2,
                R.drawable.kil_3, R.drawable.kil_4, R.drawable.kil_5};

        killamFloorPlanImage.setImageResource(R.drawable.kil_1);

        killamFloorPlanImage.setOnClickListener(new View.OnClickListener() {

            int imageTracker = 1;

            @Override
            public void onClick(View v) {
                killamFloorPlanImage.setImageResource(levels[++imageTracker]);

                if(imageTracker == 5){
                    imageTracker = -1;
                }
            }
        });
    }
}
