package com.richard.imoh.collab.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.richard.imoh.collab.ChatList;
import com.richard.imoh.collab.ChatListPojo;
import com.richard.imoh.collab.R;

import java.util.List;

/**
 * Created by LENOVO on 7/5/2018.
 */

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ChatListViewHolder> {
    List<ChatListPojo>chatList;

    public ChatListAdapter(List<ChatListPojo> chatList) {
        this.chatList = chatList;
    }

    @NonNull
    @Override
    public ChatListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.chat_list,parent,false);
        return new ChatListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatListViewHolder holder, int position) {
        ChatListPojo chat = chatList.get(position);
        holder.name.setText(chat.getDisplayName());
        holder.shortMsg.setText(chat.getDisplayMsg());
        holder.time.setText(chat.getDisplayTime());
        Glide.with(holder.imageView.getContext())
                .load(chat.getDisplayImg())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }


    class ChatListViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView name,time,shortMsg;

        public ChatListViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.chat_list_image);
            name = itemView.findViewById(R.id.chat_list_displayname);
            time = itemView.findViewById(R.id.chat_list_time);
            shortMsg = itemView.findViewById(R.id.chat_list_display_msg);
        }
    }
}
