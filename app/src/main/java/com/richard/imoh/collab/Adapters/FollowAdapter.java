package com.richard.imoh.collab.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hbb20.GThumb;
import com.richard.imoh.collab.R;
import com.richard.imoh.collab.Pojo.User;

import java.util.List;

/**
 * Created by LENOVO on 6/13/2018.
 */

public class FollowAdapter extends RecyclerView.Adapter<FollowAdapter.MyViewHolder> {
    List<User>mUser;

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
        holder.name.setText(user.getFullName());
        if(user.getImage().equals("none")){
            user.setImage("");
        }
        holder.imageView.loadThumbForName(user.getImage(),user.getFullName());

//        Glide.with(holder.imageView.getContext())
//                .load(user.getImage())
//                .apply(RequestOptions.circleCropTransform())
//                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {

        return mUser.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        GThumb imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.follow_image);
            name = itemView.findViewById(R.id.follow_name);

        }
    }
}
