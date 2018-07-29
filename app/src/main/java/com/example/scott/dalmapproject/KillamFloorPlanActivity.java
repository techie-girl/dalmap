/**
 * Creates the floor plan for the Killam library.
 * Shows the rooms on the floors.
 * Shows the length of time between two selected points.
 *
 * @author Jacob
 * @author Aqil
 * @author Arazoo
 * @author Chris
 * @author Scott
 * @author Jaewoong
 */
package com.example.scott.dalmapproject;

import android.content.Intent;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class KillamFloorPlanActivity extends AppCompatActivity implements View.OnTouchListener{

    // These matrices will be used to scale points of the image
    Matrix matrix = new Matrix();
    Matrix savedMatrix = new Matrix();

    // The 3 states (events) which the user is trying to perform
    static final int NONE = 0;
    static final int DRAG = 1;
    static final int ZOOM = 2;
    int mode = NONE;

    // these PointF objects are used to record the point(s) the user is touching
    PointF start = new PointF();
    PointF mid = new PointF();
    float oldDist = 1f;

    boolean maping = false;
    int currentImage = 1;

    protected void onCreate(Bundle savedBundleState){
        super.onCreate(savedBundleState);
        setContentView(R.layout.activity_killam_floor_plan_imageview);

        final ImageView killamFloorPlanImage = (ImageView) findViewById(R.id.killam_floor_plan_image);

        killamFloorPlanImage.setOnTouchListener(this);

        killamFloorPlanImage.setImageResource(R.mipmap.kil_1);

        //button functionality
        mappingButtonFunction();
        nextFloorButtonFunction();
        exteriorMapButtonFunction();
        previousFloorButtonFunction();

    }

    /**
     * Response to touch events in the image view
     *
     * @param v - Image view
     * @param event - Touch event
     * @return - acknowledgment of the event
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if(!maping) {
            onTouchNotMapping(v, event);
        }
        else{
            onTouchMapping(event);
        }

        return true; // indicate event was handled
    }

    /**
     * Distance between 2 points functionality
     *
     * @param floorOfInterest - Current floor
     * @param xOfInterest - Destination x
     * @param yOfInterest - Destination y
     * @param sourceX - Source x
     * @param sourceY - Source y
     */
    public void applyDijkstra(int floorOfInterest, int xOfInterest, int yOfInterest, int sourceX, int sourceY){

        final int[] BWs = {R.mipmap.kil_basement_bw, R.mipmap.kil_1_bw, R.mipmap.kil_2_bw,
                R.mipmap.kil_3_bw, R.mipmap.kil_4_bw, R.mipmap.kil_5_bw};

        //Bitmap currentFloor = BitmapFactory.decodeResource(getResources(), BWs[floorOfInterest]);

        TextView travelTimeText = (TextView) findViewById(R.id.killam_floor_plan_traveltime_text);

        //int[][] pixels = new int[currentFloor.getWidth()/2][currentFloor.getHeight()/2];
        //System.out.println("made empty array");
        //for(int i = 0; i < currentFloor.getWidth(); i+=2){
        //    for(int j = 0; j < currentFloor.getHeight(); j+=2){
        //        pixels[i][j] = Color.red(currentFloor.getPixel(i, j)); //all RGB values are the same in the BW images
        //    }
        //}

        int currentX = sourceX;
        int currentY = sourceY;

        float distance = (float)Math.sqrt(((currentX-xOfInterest)*(currentX-xOfInterest))+((currentY-yOfInterest)*(currentY-yOfInterest)));

        //21 pixels per meter, 1m/s walking speed
        travelTimeText.setText(String.valueOf(distance/21));

    }

    /**
     * Functionality for mapping button
     */
    private void mappingButtonFunction(){

        Button mappingButton = (Button) findViewById(R.id.killam_floor_plan_mapping_button);
        final TextView travelTimeText = (TextView) findViewById(R.id.killam_floor_plan_traveltime_text);

        mappingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                maping = true;
                travelTimeText.setText("Click your current \nlocation and destination at the same time");
            }
        });
    }

    /**
     * Functionality for next floor button
     */
    private void nextFloorButtonFunction(){

        final ImageView killamFloorPlanImage = (ImageView) findViewById(R.id.killam_floor_plan_image);
        final int[] levels = {R.mipmap.kil_basement, R.mipmap.kil_1, R.mipmap.kil_2,
                R.mipmap.kil_3, R.mipmap.kil_4, R.mipmap.kil_5};

        Button nextFloorButton = (Button) findViewById(R.id.killam_floor_plan_next_button);
        nextFloorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentImage == 5){
                    currentImage = -1;
                }

                killamFloorPlanImage.setImageResource(levels[++currentImage]);
            }
        });
    }

    /**
     * Functionality for exterior map button
     */
    private void exteriorMapButtonFunction(){

        Button exteriorMapButton = (Button) findViewById(R.id.killam_floor_plan_to_exterior);
        exteriorMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MapActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    /**
     * Functionality fir previous floor button
     */
    private void previousFloorButtonFunction(){

        final ImageView killamFloorPlanImage = (ImageView) findViewById(R.id.killam_floor_plan_image);
        final int[] levels = {R.mipmap.kil_basement, R.mipmap.kil_1, R.mipmap.kil_2,
                R.mipmap.kil_3, R.mipmap.kil_4, R.mipmap.kil_5};

        Button previousFloorButton = (Button) findViewById(R.id.killam_floor_plan_back_button);
        previousFloorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentImage == 0){
                    currentImage = 6;
                }
                killamFloorPlanImage.setImageResource(levels[--currentImage]);
            }
        });
    }

    /**
     * Reaction to touch events while mapping isn't turned on
     *
     * @param v - Current image view
     * @param event - Touch event
     */
    private void onTouchNotMapping(View v, MotionEvent event){
        ImageView view = (ImageView) v;
        view.setScaleType(ImageView.ScaleType.MATRIX);
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:   // first finger down only
                onActionDown(event);
                break;

            case MotionEvent.ACTION_UP: // first finger lifted
            case MotionEvent.ACTION_POINTER_UP: // second finger lifted
                onActionUp();
                break;

            case MotionEvent.ACTION_POINTER_DOWN: // first and second finger down
                onPointerDown(event);
                break;

            case MotionEvent.ACTION_MOVE:
                onActionMove(event);
                break;
        }
        view.setImageMatrix(matrix); // display the transformation on screen
    }

    /**
     * Reaction to touch events while mapping is turned on
     *
     * @param event - Touch event
     */
    private void onTouchMapping(MotionEvent event){
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_POINTER_DOWN: // first and second finger down
                onMappingPointerDown(event);
                break;
        }
    }

    /**
     * Reaction to a down action touch event
     *
     * @param event - Touch event
     */
    private void onActionDown(MotionEvent event){
        savedMatrix.set(matrix);
        start.set(event.getX(), event.getY());
        mode = DRAG;
    }

    /**
     * Reaction to a up action event
     */
    private void onActionUp(){
        mode = NONE;
    }

    /**
     * Reaction to a pointer down touch event
     *
     * @param event - Touch event
     */
    private void onPointerDown(MotionEvent event){
        float x, y;

        x = event.getX(0) - event.getX(1);
        y = event.getY(0) - event.getY(1);
        oldDist = (float) Math.sqrt(x * x + y * y);
        if (oldDist > 5f) {
            savedMatrix.set(matrix);

            x = event.getX(0) + event.getX(1);
            y = event.getY(0) + event.getY(1);
            mid.set(x / 2, y / 2);
            mode = ZOOM;
        }
    }

    /**
     * Reaction to a move action event
     *
     * @param event - Touch event
     */
    private void onActionMove(MotionEvent event){
        float scale, x, y;

        if (mode == DRAG) {
            matrix.set(savedMatrix);
            matrix.postTranslate(event.getX() - start.x, event.getY() - start.y); // create the transformation in the matrix  of points
        } else if (mode == ZOOM) {
            // pinch zooming
            x = event.getX(0) - event.getX(1);
            y = event.getY(0) - event.getY(1);
            float newDist = (float) Math.sqrt(x * x + y * y);
            if (newDist > 5f) {
                matrix.set(savedMatrix);
                scale = newDist / oldDist; // setting the scaling of the
                // matrix...if scale > 1 means
                // zoom in...if scale < 1 means
                // zoom out
                matrix.postScale(scale, scale, mid.x, mid.y);
            }
        }
    }

    /**
     * Reaction to a pointer down event while mapping is active
     *
     * @param event - Touch Event
     */
    private void onMappingPointerDown(MotionEvent event){
        float sourceX = event.getX(0);
        float sourceY = event.getY(0);
        float xOfInterest = event.getX(1);
        float yOfInterest = event.getY(1);
        applyDijkstra(currentImage, (int)xOfInterest, (int)yOfInterest, (int)sourceX, (int)sourceY);
        maping = false;
    }
}
