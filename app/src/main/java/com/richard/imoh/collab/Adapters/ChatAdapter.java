package com.richard.imoh.collab.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.richard.imoh.collab.R;
import com.richard.imoh.collab.Pojo.UserMessage;

import java.util.List;

/**
 * Created by LENOVO on 6/29/2018.
 */

public class ChatAdapter extends RecyclerView.Adapter{

        private static final int VIEW_TYPE_MESSAGE_SENT = 1;
        private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;

        private Context mContext;
        private List<UserMessage> mMessageList;

        public ChatAdapter(Context context, List<UserMessage> messageList) {
            mContext = context;
            mMessageList = messageList;
        }

        @Override
        public int getItemCount() {
            return mMessageList.size();
        }

        // Determines the appropriate ViewType according to the sender of the message.
        @Override
        public int getItemViewType(int position) {
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            String myUid = firebaseAuth.getUid();
            UserMessage message =  mMessageList.get(position);
            Log.d("adapter","Got to get view type");
            if (message.getUid().equals(myUid)) {
                Log.d("adapter","view type is message sent");

                // If the current user is the sender of the message
                return VIEW_TYPE_MESSAGE_SENT;
            } else {
                Log.d("adapter","view type is message received");
                // If some other user sent the message
                return VIEW_TYPE_MESSAGE_RECEIVED;
            }
        }

        // Inflates the appropriate layout according to the ViewType.
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view;

            if (viewType == VIEW_TYPE_MESSAGE_SENT) {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_sent_message, parent, false);
                return new SentMessageHolder(view);
            } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_received_message, parent, false);
                return new ReceivedMessageHolder(view);
            }

            return null;
        }

        // Passes the message object to a ViewHolder so that the contents can be bound to UI.
        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            UserMessage message = mMessageList.get(position);
            Log.d("adapter","got to bind viewhlder");

            switch (holder.getItemViewType()) {
                case VIEW_TYPE_MESSAGE_SENT:
                    ((SentMessageHolder) holder).bind(message);
                    Log.d("adapter","bind first view");
                    break;
                case VIEW_TYPE_MESSAGE_RECEIVED:
                    ((ReceivedMessageHolder) holder).bind(message);
            }
        }

        private class SentMessageHolder extends RecyclerView.ViewHolder {
            TextView messageText, timeText;
            ImageView imgMsg;

            SentMessageHolder(View itemView) {
                super(itemView);

                messageText = (TextView) itemView.findViewById(R.id.text_message_body);
                timeText = (TextView) itemView.findViewById(R.id.text_message_time);
                imgMsg = itemView.findViewById(R.id.img_message_body);

            }

            void bind(UserMessage message) {
                boolean isPhoto = message.getImageMessage() != null;
                //if it is a photoMessage
                if(isPhoto){
                    messageText.setVisibility(View.GONE);
                    Glide.with(imgMsg.getContext())
                            .load(message.getImageMessage())
                            .into(imgMsg);
                }
                //Not a photo message
                else {
                    imgMsg.setVisibility(View.GONE);
                    messageText.setText(message.getMessageBody());
                }
                // Format the stored timestamp into a readable String using method.
                timeText.setText(message.getTimeStamp());
            }
        }

        private class ReceivedMessageHolder extends RecyclerView.ViewHolder {
            TextView messageText, timeText, nameText;
            ImageView profileImage,imgMsg;

            ReceivedMessageHolder(View itemView) {
                super(itemView);

                messageText = (TextView) itemView.findViewById(R.id.text_message_body);
                timeText = (TextView) itemView.findViewById(R.id.text_message_time);
//                nameText = (TextView) itemView.findViewById(R.id.text_message_name);
                profileImage = (ImageView) itemView.findViewById(R.id.image_message_profile);
                imgMsg = itemView.findViewById(R.id.img_message_body);
            }

            void bind(UserMessage message) {
                boolean isPhoto = message.getImageMessage() != null;
                //if its a photo message
                if(isPhoto){
                    messageText.setVisibility(View.GONE);
                    Glide.with(imgMsg.getContext())
                            .load(message.getImageMessage())
                            .into(imgMsg);
                }
                //not a photo message
                else {
                    imgMsg.setVisibility(View.GONE);
                    messageText.setText(message.getMessageBody());
                }
                // Format the stored timestamp into a readable String using method.
                timeText.setText(message.getTimeStamp());

                //nameText.setText(message.getUsername());
                profileImage.setVisibility(View.GONE);

                // Insert the profile_menu image from the URL into the ImageView.

//                Glide.with(profileImage.getContext())
//                        .load(message.getProfilePicture())
//                        .apply(RequestOptions.circleCropTransform())
//                        .into(profileImage);
            }
        }
    }

