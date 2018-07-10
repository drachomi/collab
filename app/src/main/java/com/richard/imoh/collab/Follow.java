package com.richard.imoh.collab;

import android.content.SharedPreferences;
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
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.richard.imoh.collab.Adapters.FollowAdapter;

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
    FirebaseAuth firebaseAuth;
    ValueEventListener valueEventListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow);
        Bundle extras = getIntent().getExtras();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("agents");


        firebaseAuth = FirebaseAuth.getInstance();
        myUserId = firebaseAuth.getUid();
        SharedPreferences sharedPref = getPreferences(MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPref.getString("user", "");
        User obj = gson.fromJson(json, User.class);


        mChatListReference = firebaseDatabase.getReference().child("agents").child(myUserId).child("connections");

        RecyclerView recyclerView = findViewById(R.id.follow_recycle);
        mAdampter = new FollowAdapter(mUser);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addOnItemTouchListener(new FollowTouchListerner(getApplicationContext(), recyclerView, new FollowTouchListerner.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Log.d(" userO", "getting there");
                //TODO Add action to be taken when user clicks to follow
                String userId = mUser.get(position).getuId();
                Log.d(" userO", userId);
                otherChatListRef = firebaseDatabase.getReference().child("agents").child(userId).child("connections");
                otherChatListRef.push().setValue(obj);
                mChatListReference.push().setValue(mUser);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        recyclerView.setAdapter(mAdampter);

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    User user = ds.getValue(User.class);
                    if(user!=null){
                        if(user.getuId() != obj.uId){
                            mUser.add(user);
                        }
                    }
                }


                mAdampter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        databaseReference.addValueEventListener(valueEventListener);
    }

}
