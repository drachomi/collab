package com.richard.imoh.collab.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.hbb20.GThumb;
import com.richard.imoh.collab.Pojo.ChatMeta;
import com.richard.imoh.collab.R;

import java.util.List;

/**
 * Created by LENOVO on 7/5/2018.
 */

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ChatListViewHolder> {
    List<ChatMeta>chatList;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

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
        if((!chat.getuId().equals(firebaseAuth.getCurrentUser().getUid())&& chat.getMessageCount() > 0 )){
            Log.d("currentUser ","mine  "+firebaseAuth.getCurrentUser().getUid());
            Log.d("currentUser ","other  "+chat.getuId());
            holder.chat_indicator.setText(" "+Integer.toString(chat.getMessageCount())+" ");
        }
        else {
            holder.chat_indicator.setVisibility(View.GONE);
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
        TextView chat_indicator;

        public ChatListViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.chat_list_image);
            name = itemView.findViewById(R.id.chat_list_displayname);
            time = itemView.findViewById(R.id.chat_list_time);
            shortMsg = itemView.findViewById(R.id.chat_list_display_msg);
            chat_indicator = itemView.findViewById(R.id.chat_indicator);
        }
    }
}
