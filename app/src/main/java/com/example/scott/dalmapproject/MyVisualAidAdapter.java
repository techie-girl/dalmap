package com.example.scott.dalmapproject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyVisualAidAdapter extends RecyclerView.Adapter<MyVisualAidAdapter.ViewHolder> {
    private ArrayList<CreateVisualAidList> galleryList;
    private Context context;

    public MyVisualAidAdapter(Context context, ArrayList<CreateVisualAidList> galleryList){
        this.galleryList = galleryList;
        this.context = context;
    }

    @Override
    public MyVisualAidAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View view = LayoutInflater.from(viewGroup.getContext().inflate(R.layout.cell_layout, viewGroup, false));
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyVisualAidAdapter.ViewHolder viewHolder, int i){
        viewHolder.title.setText(galleryList.get(i).getImageTitle());
        viewHolder.img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        viewHolder.img.setImageResource((galleryList.get(i).getImageID()));
    }

    @Override
    public int getItemCount(){
        return galleryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private ImageView img;
        public ViewHolder(View view){
            super(view);

            title = (TextView)view.findViewById(R.id.title);
            img = (ImageView)view.findViewById(R.id.imageGallery);
        }
    }
}
