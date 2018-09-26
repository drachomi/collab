package com.richard.imoh.collab.Activities;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
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
import com.richard.imoh.collab.Adapters.FollowAdapter;
import com.richard.imoh.collab.DBUtils.Connection;
import com.richard.imoh.collab.DBUtils.ConnectionDB;
import com.richard.imoh.collab.DBUtils.ConnectionDao;
import com.richard.imoh.collab.Utils.FollowTouchListerner;
import com.richard.imoh.collab.Pojo.User;
import com.richard.imoh.collab.R;
import com.richard.imoh.collab.Utils.FireBaseUtils;
import com.richard.imoh.collab.Utils.Repository;

import java.sql.Timestamp;
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
    DatabaseReference connectionInfoRef;
    FirebaseAuth firebaseAuth;
    User obj;
    ValueEventListener valueEventListener;
    List<String> connectionList = new ArrayList<>();
    List<Connection> connectionObj = new ArrayList<>();
    ConnectionDao connectionDao;
    ConnectionDB connectionDB;
    Toolbar toolbar;
    Repository repository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow);
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Suggestions");
        Bundle extras = getIntent().getExtras();
        firebaseDatabase = FireBaseUtils.getDatabase();
        databaseReference = firebaseDatabase.getReference().child("agents");
        firebaseAuth = FirebaseAuth.getInstance();
        myUserId = firebaseAuth.getUid();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        repository = new Repository(this);



        connectionDB = ConnectionDB.getInstance(this);
        connectionDao = connectionDB.connectionDao();
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
                String myNmae = preferences.getString("myFullname", "");
                String myId = preferences.getString("myUserId", "");
                String myDp = preferences.getString("myDp", "");
                String userId = mUser.get(position).getuId();
                String agentName = mUser.get(position).getFullName();
                String dp = mUser.get(position).getImage();
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                String myLocation = preferences.getString("myLocation", "");
                String userLocation = mUser.get(position).getLocation();

                //Create chatref using time stamp and individual userId
                String chatRef = timestamp.getTime() + userId + myId;

                //Create new connection objects for both
                Connection otherUser = new Connection(userId,agentName,dp,chatRef,userLocation);
                Connection mine = new Connection(myId,myNmae,myDp,chatRef,myLocation);
                Log.d(" userO", userId);

                //Send the new connections to repository class  to add to their respective nodes
                repository.addToConnectionNode(userId,mine);
                repository.addToConnectionNode(myId,otherUser);

                //Make the user disappear from the list and update the adapter to reflect changes
                mUser.remove(position);
                mAdampter.notifyDataSetChanged();



            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        recyclerView.setAdapter(mAdampter);



    getAll();

    }

    private void getAll (){
        new AsyncTask<Void,Void,Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                connectionObj = connectionDao.getAllConnection();
                for (int i=0; i<connectionObj.size();i++){
                    connectionList.add(connectionObj.get(i).Uid);
                    Log.d("conlist",connectionList.get(i));
                    Log.d("conObj",connectionObj.get(i).Uid + "  But size is "+connectionObj.size());

                }
                connectionUid();
                return null;
            }
        }.execute();
    }


    void connectionUid(){
        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String user =dataSnapshot.getKey();
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
        connectionInfoRef = firebaseDatabase.getReference().child("agents").child(conId).child("info");
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (user != null) {
                    if(!user.getuId().equals(firebaseAuth.getUid())){
                        mUser.add(user);
                        getInfo(user);
                    }
                    else {
                        Log.d("ljonah",user.getFullName());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        };
        connectionInfoRef.addValueEventListener(valueEventListener);
    }

    void getInfo(User user){
        for (String frien : connectionList){
            if(frien.equals(user.getuId())){
                Log.d("ejonah",user.getuId());
                mUser.remove(user);
            }
            else {
                Log.d("jonah",user.getuId());
            }
        }
        mAdampter.notifyDataSetChanged();

    }





}



