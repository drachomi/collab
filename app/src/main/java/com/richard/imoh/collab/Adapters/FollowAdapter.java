package com.richard.imoh.collab.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.richard.imoh.collab.R;
import com.richard.imoh.collab.Pojo.User;
import com.richard.imoh.collab.Utils.ToCamdlCase;

import java.util.List;

/**
 * Created by LENOVO on 6/13/2018.
 */

public class FollowAdapter extends RecyclerView.Adapter<FollowAdapter.MyViewHolder> {
    List<User>mUser;

    //Class used to capitalize first letter of any word
    ToCamdlCase toCamdlCase = new ToCamdlCase();

    public FollowAdapter(List<User> mUser) {
        this.mUser = mUser;
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.follow_model,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        User user = mUser.get(position);
        holder.name.setText(toCamdlCase.camelCase(user.getFullName()));
        holder.location.setText(toCamdlCase.camelCase(user.getLocation()));

        Glide.with(holder.imageView.getContext())
                .load(user.getImage())
                .apply(RequestOptions.placeholderOf(R.drawable.home))
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {

        return mUser.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name,location;
        ImageView imageView;
        Button button;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.follow_image);
            name = itemView.findViewById(R.id.follow_name);
            location = itemView.findViewById(R.id.follower_list_location);

        }
    }
}
