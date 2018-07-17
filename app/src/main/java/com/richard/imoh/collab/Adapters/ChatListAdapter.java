package com.richard.imoh.collab.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hbb20.GThumb;
import com.richard.imoh.collab.Pojo.ChatMeta;
import com.richard.imoh.collab.R;

import java.util.List;

/**
 * Created by LENOVO on 7/5/2018.
 */

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ChatListViewHolder> {
    List<ChatMeta>chatList;

    public ChatListAdapter(List<ChatMeta> chatList) {
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
        ChatMeta chat = chatList.get(position);
        holder.name.setText(chat.getDisplayName());
        holder.shortMsg.setText(chat.getLastMessage());
        holder.time.setText(chat.getDisplayTime());

        holder.imageView.loadThumbForName(chat.getDisplayImg(),chat.getDisplayName());
        if(chat.getMessageCount() > 0){
            holder.chat_indicator.setImageResource(R.drawable.blue_button);
        }

//        imageView.loadThumbForName(imageURL, firstName, secondName)
//        Glide.with(holder.imageView.getContext())
//                .load(chat.getDisplayImg())
//                .apply(RequestOptions.circleCropTransform())
//                .into(holder.imageView);


    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }


    class ChatListViewHolder extends RecyclerView.ViewHolder{
        TextView time;
        TextView name,shortMsg;
        GThumb imageView;
        ImageView chat_indicator;

        public ChatListViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.chat_list_image);
            name = itemView.findViewById(R.id.chat_list_displayname);
            time = itemView.findViewById(R.id.chat_list_time);
            shortMsg = itemView.findViewById(R.id.chat_list_display_msg);
            chat_indicator = itemView.findViewById(R.id.chat_list_indicator);
        }
    }
}
