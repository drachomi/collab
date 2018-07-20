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
import com.richard.imoh.collab.Utils.FollowTouchListerner;
import com.richard.imoh.collab.Pojo.ChatMeta;
import com.richard.imoh.collab.Pojo.User;
import com.richard.imoh.collab.R;
import com.richard.imoh.collab.Utils.FireBaseUtils;

import java.util.ArrayList;
import java.util.List;

public class ChatList extends AppCompatActivity {
    List<ChatMeta>chatList = new ArrayList<>();
    ChatListAdapter chatListAdapter;
    FirebaseDatabase mFirebaseDataBase;
    DatabaseReference myChatListReference;
    String myUserId;
    String otherUserId;
    ChildEventListener myChatListEventListerner;
    ValueEventListener otherChatListEventListerner;
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
                Intent intent = new Intent(ChatList.this,Chat.class);
                intent.putExtra("chatRef",chatList.get(position).getChatRef());
                intent.putExtra("username",chatList.get(position).getDisplayName());
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        recyclerView.setAdapter(chatListAdapter);

        mFirebaseDataBase = FireBaseUtils.getDatabase();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        myUserId = firebaseAuth.getUid();

        myChatListReference = mFirebaseDataBase.getReference().child("agents").child(myUserId).child("chats");




        attachedMyChatListerner();
        //attachedOtherChatListListerner();



    }


    void attachedMyChatListerner(){
        if (myChatListEventListerner == null){
            myChatListEventListerner = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    String chatRef = (String) dataSnapshot.getValue();
                    String personUid = dataSnapshot.getKey();
                    Log.d("chatRefv",chatRef);
                    Log.d("chatRefk",personUid);
                    getProfileInfo(personUid,chatRef);

//                ChatMeta myChatList = dataSnapshot.getValue(ChatMeta.class);
//                chatList.add(myChatList);
//                chatListAdapter.notifyDataSetChanged();
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
            myChatListReference.addChildEventListener(myChatListEventListerner);
        }
    }

    void detachedDbReadListener(){
        if(myChatListEventListerner != null){
            myChatListReference.removeEventListener(myChatListEventListerner);
        }
        myChatListEventListerner = null;
    }
    void getProfileInfo(String uId, String chatRef){
        DatabaseReference querry = mFirebaseDataBase.getReference().child("agents").child(uId).child("info");
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                User user = dataSnapshot.getValue(User.class);
                Log.d("luke", user.getFullName());
                listenToChat(user.getUserName(),user.getImage(),chatRef);
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
        querry.addChildEventListener(childEventListener);
    }

    void listenToChat(String name,String picture, String chatRef){
        DatabaseReference chatMetaRef;
        chatMetaRef = mFirebaseDataBase.getReference().child("chats").child(chatRef).child("chatMeta");
        if (otherChatListEventListerner == null){
            otherChatListEventListerner = new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Log.d("reeeeeeee",chatRef);
                    ChatMeta chatMeta = dataSnapshot.getValue(ChatMeta.class);
                    if(chatMeta!= null){
                        Log.d("chatMeta",chatMeta.getLastMessage());
                        chatList.add(new ChatMeta(name,chatMeta.getLastMessage(),picture,chatMeta.getDisplayTime(),chatRef,chatMeta.getMessageCount(),chatMeta.getuId()));
                        chatListAdapter.notifyDataSetChanged();
                    }
                    else {
                        Log.d("chatmeta","chat meta returned null");
                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };
            chatMetaRef.addValueEventListener(otherChatListEventListerner);
        }

    }
    @Override
    protected void onPause() {
        super.onPause();
        detachedDbReadListener();
      chatList.clear();
    }

    @Override
    protected void onResume() {
        super.onResume();
        attachedMyChatListerner();
    }

}
