package com.richard.imoh.collab.Request;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.richard.imoh.collab.R;

import java.util.List;

/**
 * Created by LENOVO on 8/4/2018.
 */

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.RequestViewModel> {
    List<Request> requestList;

    public RequestAdapter(List<Request> requestList) {
        this.requestList = requestList;
    }

    @NonNull
    @Override
    public RequestViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.request_model,parent,false);
        return new RequestViewModel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestViewModel holder, int position) {
        Request request = requestList.get(position);
        if(request.propertyType.equals("Flat")||request.propertyType.equals("Self Contain")){
            holder.title.setText(request.resRoomNo + " Bedroom " +request.propertyType);
        }
        if(request.propertyType.equals("Single Room")){
            holder.title.setText(request.resRoomNo + "  " +request.propertyType);
        }
        else {
            holder.title.setText(request.propertyType);
        }
        holder.location.setText(request.city + " " + request.state);
        holder.price.setText(request.price);
        holder.agentName.setText(request.agentName);
        Glide.with(holder.agentDp)
                .load(request.agentDp)
                .into(holder.agentDp);



    }

    @Override
    public int getItemCount() {
        return requestList.size();
    }

    public void updateList(List<Request>request){
        requestList = request;
        notifyDataSetChanged();

    }
    class RequestViewModel extends RecyclerView.ViewHolder{
        TextView title,location,agentName,price,regTime,verified;
        ImageView agentDp;
        public RequestViewModel(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.req_title);
            location = itemView.findViewById(R.id.req_location);
            agentName = itemView.findViewById(R.id.req_agent_name);
            price = itemView.findViewById(R.id.req_price);
            regTime = itemView.findViewById(R.id.req_time);
            verified = itemView.findViewById(R.id.req_verified);
            agentDp = itemView.findViewById(R.id.req_dp);
        }
    }

}
