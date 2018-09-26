package com.richard.imoh.collab.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.richard.imoh.collab.Adapters.ChatListAdapter;
import com.richard.imoh.collab.DBUtils.Connection;
import com.richard.imoh.collab.Utils.FollowTouchListerner;
import com.richard.imoh.collab.Pojo.ChatMeta;
import com.richard.imoh.collab.Pojo.User;
import com.richard.imoh.collab.R;
import com.richard.imoh.collab.Utils.FireBaseUtils;

import java.util.ArrayList;
import java.util.List;

public class ChatList extends AppCompatActivity {
    List<ChatMeta> chatList = new ArrayList<>();
    ChatListAdapter chatListAdapter;
    FirebaseDatabase mFirebaseDataBase;
    DatabaseReference myConnectionListRef;
    String myUserId;
    String otherUserId;
    List<Connection> connectionList = new ArrayList<>();
    ChildEventListener myChatListEventListerner;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView recyclerView = findViewById(R.id.chat_list_recycler);
        chatListAdapter = new ChatListAdapter(chatList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.addOnItemTouchListener(new FollowTouchListerner(this, recyclerView, new FollowTouchListerner.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                if (!chatList.isEmpty()) {
                    Intent intent = new Intent(ChatList.this, Chat.class);
                    intent.putExtra("personId",chatList.get(position).getuId());
                    intent.putExtra("chatRef", chatList.get(position).getChatRef());
                    intent.putExtra("username", chatList.get(position).getDisplayName());
                    startActivity(intent);
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        recyclerView.setAdapter(chatListAdapter);

        mFirebaseDataBase = FireBaseUtils.getDatabase();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        myUserId = firebaseAuth.getUid();

        myConnectionListRef = mFirebaseDataBase.getReference().child("agents").child(myUserId).child("connections");
        attachedMyChatListerner();
    }


    void attachedMyChatListerner() {
        if (myChatListEventListerner == null) {
            myChatListEventListerner = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    Connection connection = dataSnapshot.getValue(Connection.class);
                    Log.d("chatRefv", "Gotten the connecton object of " + connection.agentName);
                    connectionList.add(connection);

                    listenToChat(connection);

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
            myConnectionListRef.addChildEventListener(myChatListEventListerner);
        }

    }


    void listenToChat(Connection connection) {
        mFirebaseDataBase.getReference().child("chats").child(connection.chatRef).child("chatMeta").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("ChatMeta", "Gotten inside on dataChanged");
                ChatMeta chatMeta = dataSnapshot.getValue(ChatMeta.class);
                if (chatMeta != null) {
                    Log.d("ChatMeta", "Chat Meta is not null");

                    //Checks for old mesg when a new one arrives and removes the old ones
                    for (ChatMeta meta : chatList) {
                        if (meta.getChatRef().equals(chatMeta.getChatRef())) {
                            Log.d("chatMeta", "removed " + meta.getDisplayName());
                            chatList.remove(meta);
                        }

                    }
                    Log.d("chatMeta", "about to add " + chatMeta.getuId());
                    //chatList.add(chatMeta);
                    //Check for the last message for that chat
                    mFirebaseDataBase.getReference().child("chats").child(connection.chatRef).child(myUserId+"unread").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()){
                                int msgCount = Integer.parseInt(dataSnapshot.getValue().toString()) ;
                                chatList.add(new ChatMeta(connection.agentName, chatMeta.getLastMessage(), connection.agentDp, chatMeta.getDisplayTime(), connection.chatRef, msgCount, connection.Uid));
                                chatListAdapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                } else {
                    Log.d("chatmeta", "chat meta returned null");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


}
