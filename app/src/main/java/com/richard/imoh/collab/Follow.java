package com.richard.imoh.collab;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.richard.imoh.collab.Adapters.FollowAdapter;
import com.richard.imoh.collab.Pojo.User;

import java.util.ArrayList;
import java.util.List;

public class Follow extends AppCompatActivity {
    FollowAdapter mAdampter;
    List<User> mUser = new ArrayList<>();
    String myUserId;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ChildEventListener childEventListener;
    DatabaseReference mChatListReference;
    DatabaseReference otherChatListRef;
    DatabaseReference connectionInfoRef;
    FirebaseAuth firebaseAuth;
    User obj;
    ChildEventListener valueEventListener;
    List<String> suggestionsList = new ArrayList<>();
    //String[] suggestionsList = {};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow);
        Bundle extras = getIntent().getExtras();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("agents");



        firebaseAuth = FirebaseAuth.getInstance();
        myUserId = firebaseAuth.getUid();
//        SharedPreferences sharedPref = getPreferences(MODE_PRIVATE);
//        Gson gson = new Gson();
//        String json = sharedPref.getString("user", "");
//        obj = gson.fromJson(json, User.class);
//        Log.d("json",obj.uId);


        mChatListReference = firebaseDatabase.getReference().child("agents").child(myUserId).child("connections");

        RecyclerView recyclerView = findViewById(R.id.follow_recycle);
        mAdampter = new FollowAdapter(mUser);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 4);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addOnItemTouchListener(new FollowTouchListerner(getApplicationContext(), recyclerView, new FollowTouchListerner.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Log.d(" userO", "getting there");
                //TODO Add action to be taken when user clicks to follow
                String userId = mUser.get(position).getuId();
                Log.d(" userO", userId);
                mUser.remove(position);
                otherChatListRef = firebaseDatabase.getReference().child("agents").child(userId).child("connections");
                otherChatListRef.push().setValue(firebaseAuth.getUid());
                mChatListReference.push().setValue(userId);
                mAdampter.notifyDataSetChanged();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        recyclerView.setAdapter(mAdampter);

        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String user =dataSnapshot.getKey();
                Log.d("jonah",user);
                connectionInfo(user);


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
        databaseReference.addChildEventListener(childEventListener);



    }

    void connectionInfo(String conId){
        Log.d("yonah","got here");
        connectionInfoRef = firebaseDatabase.getReference().child("agents").child(conId).child("info");
        valueEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                User user = dataSnapshot.getValue(User.class);
                if (user != null) {
                    if(!user.getuId().equals(firebaseAuth.getUid())){
                        Log.d("yonah",user.getuId());

                        mUser.add(user);
                    }
                }
                mAdampter.notifyDataSetChanged();

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
        connectionInfoRef.addChildEventListener(valueEventListener);
    }


}
