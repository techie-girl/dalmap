/**
 * ArrayListAdapter for recycler view.
 * Used for an arraylist of the visual aids.
 *
 * @author Aqil
 * @author Arazoo
 * @author Chris
 * @author Scott
 * @author Jacob
 * @author Jaewoong
 */
package com.example.scott.dalmapproject;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerViewArrayListAdapter extends RecyclerView.Adapter<RecyclerViewArrayListAdapter.ViewHolder> {

    private ArrayList<CreateVisualAidList> galleryList;
    private Context context;

    public RecyclerViewArrayListAdapter(Context context, ArrayList<CreateVisualAidList> galleryList){
        this.galleryList = galleryList;
        this.context = context;
    }

    /**
     * Creates the view holder for the arraylist.
     * @param viewGroup
     * @param i
     * @return the new view holder.
     */
    @Override
    public RecyclerViewArrayListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    /**
     * Sets items in the view holder.
     * @param viewHolder
     * @param i
     */
    @Override
    public void onBindViewHolder(RecyclerViewArrayListAdapter.ViewHolder viewHolder, int i) {
        viewHolder.title.setText(galleryList.get(i).getImageTitle());
        viewHolder.img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        viewHolder.img.setImageResource((galleryList.get(i).getImageID()));
    }

    /**
     * gets the item count (size) of the list.
     * @return integer number representing the size of the list.
     */
    @Override
    public int getItemCount(){
        return galleryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private ImageView img;

        /**
         * constructor for ViewHolder.
         * Sets the title and the image.
         * @param view the view.
         */
        public ViewHolder(View view){
            super(view);

            title = (TextView)view.findViewById(R.id.image_gallery_image_title);
            img = (ImageView)view.findViewById(R.id.image_gallery_image);
        }
    }
}