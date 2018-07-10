package com.richard.imoh.collab;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.richard.imoh.collab.Adapters.ChatListAdapter;

import java.util.ArrayList;
import java.util.List;

public class ChatList extends AppCompatActivity {
    List<ChatListPojo>chatList = new ArrayList<>();
    ChatListAdapter chatListAdapter;
    FirebaseDatabase mFirebaseDataBase;
    DatabaseReference mChatListReference;
    DatabaseReference otherChatListRef;
    String myUserId;
    String otherUserId;
    ChildEventListener myChatListEventListerner;
    ChildEventListener otherChatListEventListerner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);

        RecyclerView recyclerView = findViewById(R.id.chat_list_recycler);
        chatListAdapter = new ChatListAdapter(chatList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(chatListAdapter);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        myUserId = firebaseAuth.getUid();
        mFirebaseDataBase = FirebaseDatabase.getInstance();
        mChatListReference = mFirebaseDataBase.getReference().child("agents").child(myUserId).child("chats");
        //otherChatListRef = mFirebaseDataBase.getReference().child("agents").child(otherUserId).child("chats");

        attachedMyChatListerner();
        //attachedOtherChatListListerner();



    }

    void addChar(){
        ChatListPojo user = new ChatListPojo("imoh","Hello guy","https://pbs.twimg.com/profile_images/723476945879633920/N59ePNGs_400x400.jpg","22:30");
        chatList.add(user);
        user = new ChatListPojo("imoh","Hello guy","https://pbs.twimg.com/profile_images/723476945879633920/N59ePNGs_400x400.jpg","22:30");
        chatList.add(user);
        user = new ChatListPojo("imoh","Hello guy","https://pbs.twimg.com/profile_images/723476945879633920/N59ePNGs_400x400.jpg","22:30");
        chatList.add(user);
        user = new ChatListPojo("imoh","Hello guy","https://pbs.twimg.com/profile_images/723476945879633920/N59ePNGs_400x400.jpg","22:30");
        chatList.add(user);
        user = new ChatListPojo("imoh","Hello guy","https://pbs.twimg.com/profile_images/723476945879633920/N59ePNGs_400x400.jpg","22:30");
        chatList.add(user);
        user = new ChatListPojo("imoh","Hello guy","https://pbs.twimg.com/profile_images/723476945879633920/N59ePNGs_400x400.jpg","22:30");
        chatList.add(user);
        user = new ChatListPojo("imoh","Hello guy","https://pbs.twimg.com/profile_images/723476945879633920/N59ePNGs_400x400.jpg","22:30");
        chatList.add(user);
        user = new ChatListPojo("imoh","Hello guy","https://pbs.twimg.com/profile_images/723476945879633920/N59ePNGs_400x400.jpg","22:30");
        chatList.add(user);
        user = new ChatListPojo("imoh","Hello guy","https://pbs.twimg.com/profile_images/723476945879633920/N59ePNGs_400x400.jpg","22:30");
        chatList.add(user);
        user = new ChatListPojo("imoh","Hello guy","https://pbs.twimg.com/profile_images/723476945879633920/N59ePNGs_400x400.jpg","22:30");
        chatList.add(user);
        user = new ChatListPojo("imoh","Hello guy","https://pbs.twimg.com/profile_images/723476945879633920/N59ePNGs_400x400.jpg","22:30");
        chatList.add(user);
        user = new ChatListPojo("imoh","Hello guy","https://pbs.twimg.com/profile_images/723476945879633920/N59ePNGs_400x400.jpg","22:30");
        chatList.add(user);
        user = new ChatListPojo("imoh","Hello guy","https://pbs.twimg.com/profile_images/723476945879633920/N59ePNGs_400x400.jpg","22:30");
        chatList.add(user);
        user = new ChatListPojo("imoh","Hello guy","https://pbs.twimg.com/profile_images/723476945879633920/N59ePNGs_400x400.jpg","22:30");
        chatList.add(user);
        user = new ChatListPojo("imoh","Hello guy","https://pbs.twimg.com/profile_images/723476945879633920/N59ePNGs_400x400.jpg","22:30");
        chatList.add(user);
        user = new ChatListPojo("imoh","Hello guy","https://pbs.twimg.com/profile_images/723476945879633920/N59ePNGs_400x400.jpg","22:30");
        chatList.add(user);

        chatListAdapter.notifyDataSetChanged();
    }
    void attachedMyChatListerner(){
        myChatListEventListerner = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                ChatListPojo myChatList = dataSnapshot.getValue(ChatListPojo.class);
                chatList.add(myChatList);
                chatListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        mChatListReference.addChildEventListener(myChatListEventListerner);
    }

    void attachedOtherChatListListerner(){
        otherChatListEventListerner = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                ChatListPojo otherChatList = dataSnapshot.getValue(ChatListPojo.class);
                chatList.add(otherChatList);
                chatListAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        otherChatListRef.addChildEventListener(otherChatListEventListerner);
    }
}
