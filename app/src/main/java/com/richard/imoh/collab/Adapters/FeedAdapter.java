package com.richard.imoh.collab.Adapters;

import android.arch.lifecycle.LiveData;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.richard.imoh.collab.Pojo.Property;
import com.richard.imoh.collab.R;

import java.util.List;

/**
 * Created by LENOVO on 6/4/2018.
 */

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.MyViewHolder> {
    List<Property>mProperty;

    public FeedAdapter(List<Property> property) {
        this.mProperty = property;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.feed_model,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Property property = mProperty.get(position);
            Log.d("property: "+property.getState(), property.getAgentName());
            holder.name.setText(property.getPropertyType());
            holder.location.setText(property.getCity() + ","+property.getState());
            holder.price.setText(property.getPrice());
            holder.bath.setText(property.getPlotNo());
            holder.room.setText(property.getRoomNo());
            Glide.with(holder.image.getContext())
                    .load(property.getPropertyImage2())
                    .into(holder.image);




    }
    public void updateList(List<Property> mPropertyList){
        mProperty = mPropertyList;
        Log.d("bing2",mProperty.get(0).getAgentName());
        notifyDataSetChanged();

    }
    @Override
    public int getItemCount() {
        return mProperty.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name,location,price,bath,room;
        ImageView image;


        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.feed_model_name);
            location = itemView.findViewById(R.id.feed_model_location);
            price = itemView.findViewById(R.id.prop_feed_price);
            bath = itemView.findViewById(R.id.prop_feed_bath);
            room = itemView.findViewById(R.id.prop_feed_rooms);
            image = itemView.findViewById(R.id.prop_feed_image);


        }
    }
}
