/*code written with references to
  https://www.androidauthority.com/how-to-build-an-image-gallery-app-718976/
 */
/*images will be of the Killam, Henry Hicks, Goldberg*/
package com.example.scott.dalmapproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;


public class VisualAidActivity_Killam extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visual_aid);

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.imageGallery);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),2);
        recyclerView.setLayoutManager(layoutManager);
        ArrayList<CreateVisualAidList> createLists = prepData();
        MyAdapter adapter = new MyAdapter(getApplicationContext(), createLists);
        recyclerView.setAdapter(adapter);
    }

    private final String imageTitleArr[] = {
            "Exterior Facade",
            "Main Lobby",
    };

    private final Integer imageIDArr[] = {
            R.drawable.facadeImg,
            R.drawable.lobbyImg,
    };
}