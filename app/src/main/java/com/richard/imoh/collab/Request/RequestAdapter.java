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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
        holder.letType.setText(request.getLetType());
        holder.regTime.setText(getDate(request.getRequestTime())+"d");
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

    //This method checks how long a request has been in the DB. It gets the date from db and compare with current date and returns the difference.
    private String getDate(String requestDate){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = df.format(Calendar.getInstance().getTime());
        String howLong;
        Date date1=null;
        Date date2 = null;
        try {
            date1 = df.parse(currentDate);
            date2 = df.parse(requestDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long dateInMilis = Math.abs(date2.getTime() - date1.getTime());
        howLong =Long.toString(TimeUnit.DAYS.convert(dateInMilis,TimeUnit.MILLISECONDS));

        return howLong;

    }
    class RequestViewModel extends RecyclerView.ViewHolder{
        TextView title,location,agentName,price,regTime,letType;
        ImageView agentDp;
        public RequestViewModel(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.req_title);
            location = itemView.findViewById(R.id.req_location);
            agentName = itemView.findViewById(R.id.req_agent_name);
            price = itemView.findViewById(R.id.req_price);
            regTime = itemView.findViewById(R.id.req_time);
            letType = itemView.findViewById(R.id.req_let_type);
            agentDp = itemView.findViewById(R.id.req_dp);
        }
    }

}
