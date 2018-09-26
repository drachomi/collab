package com.richard.imoh.collab.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.GThumb;
import com.richard.imoh.collab.Adapters.ConnectionsAdapter;
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

public class ConnectionList extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ChildEventListener childEventListener;
    FirebaseAuth firebaseAuth;
    List<User>connections = new ArrayList<>();
    List<User> mUser = new ArrayList<>();
    RecyclerView recyclerView;
    ConnectionsAdapter connectionsAdapter;
    FollowAdapter mAdampter;
    DatabaseReference connectionReference;
    ValueEventListener connectionEventListerner;
    DatabaseReference otherPersonChatRef;
    String myUserId;
    Repository repository;
    List<Connection> connectionObj = new ArrayList<>();
    ConnectionDao connectionDao;
    ConnectionDB connectionDB;
    DatabaseReference connectionInfoRef;
    DatabaseReference mChatListReference;
    ValueEventListener valueEventListener;
    DatabaseReference allConnectionRef;
    List<String> connectionList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connection_list);
        firebaseDatabase = FireBaseUtils.getDatabase();
        firebaseAuth = FirebaseAuth.getInstance();
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Connections");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myUserId = firebaseAuth.getUid();
        databaseReference = firebaseDatabase.getReference().child("agents").child(myUserId);

        initRecyclerView();






    }




   void initRecyclerView(){
       recyclerView = findViewById(R.id.connection_list_recycler);
       connectionsAdapter = new ConnectionsAdapter(connectionObj);
       RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
       recyclerView.setLayoutManager(layoutManager);
       recyclerView.setItemAnimator(new DefaultItemAnimator());
       recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
       recyclerView.addOnItemTouchListener(new FollowTouchListerner(this, recyclerView, new FollowTouchListerner.ClickListener() {
           @Override
           public void onClick(View view, int position) {
               LinearLayout linear = view.findViewById(R.id.follow_list_linear);
               ImageView img =  view.findViewById(R.id.follower_list_chat);
               TextView personName = view.findViewById(R.id.follower_list_displayname);
               GThumb image = view.findViewById(R.id.follower_list_img);
               Connection touchedUser = connectionObj.get(position);
               String chatRef = touchedUser.chatRef;
               otherPersonChatRef = firebaseDatabase.getReference().child("agents").child(touchedUser.Uid).child("chats");


               linear.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       otherPersonChatRef.child(myUserId).setValue(chatRef);
                       databaseReference.child("chats").child(touchedUser.Uid).setValue(chatRef);
                       Intent intent = new Intent(ConnectionList.this,Chat.class);
                       intent.putExtra("chatRef",chatRef);
                       intent.putExtra("username",touchedUser.agentName);
                       intent.putExtra("personId",touchedUser.Uid);
                       startActivity(intent);

                   }
               });

               image.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       Intent intent = new Intent(ConnectionList.this,AgentActivity.class);
                       intent.putExtra("userId",touchedUser.Uid);
                       startActivity(intent);
                   }
               });




           }

           @Override
           public void onLongClick(View view, int position) {
           }
       }));
       recyclerView.setAdapter(connectionsAdapter);




       connectionDB = ConnectionDB.getInstance(this);
       connectionDao = connectionDB.connectionDao();
       mChatListReference = firebaseDatabase.getReference().child("agents").child(myUserId).child("connections");
       SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
       repository = new Repository(this);
       RecyclerView suggestView = findViewById(R.id.follow_recycle);
       mAdampter = new FollowAdapter(mUser);
       RecyclerView.LayoutManager fLayoutManager = new LinearLayoutManager(this, LinearLayout.HORIZONTAL,false);
       suggestView.setLayoutManager(fLayoutManager);
       suggestView.setItemAnimator(new DefaultItemAnimator());
       suggestView.addOnItemTouchListener(new FollowTouchListerner(getApplicationContext(), recyclerView, new FollowTouchListerner.ClickListener() {
           @Override
           public void onClick(View view, int position) {
               User newConnect = mUser.get(position);
               GThumb agentDp = view.findViewById(R.id.follow_image);
               Button connect = view.findViewById(R.id.follow_connect_btn);
               Log.d(" userO", "getting there");
               //TODO Add action to be taken when user clicks to follow
               String myNmae = preferences.getString("myFullname", "");
               String myId = preferences.getString("myUserId", "");
               String myDp = preferences.getString("myDp", "");
               String myLocation = preferences.getString("myLocation", "");
               String userId = newConnect.getuId();
               String agentName = newConnect.getFullName();
               String dp = newConnect.getImage();
               String userLocation = newConnect.getLocation();
               Timestamp timestamp = new Timestamp(System.currentTimeMillis());

               //Create chatref using time stamp and individual userId
               String chatRef = timestamp.getTime() + userId + myId;

               //Create new connection objects for both
               Connection otherUser = new Connection(userId,agentName,dp,chatRef,userLocation);
               Connection mine = new Connection(myId,myNmae,myDp,chatRef,myLocation);
               Log.d(" userO", userId);


               connect.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                         //Send the new connections to repository class  to add to their respective nodes
                       repository.addToConnectionNode(userId,mine);
                       repository.addToConnectionNode(myId,otherUser);
                       Toast.makeText(ConnectionList.this,"You are now connected with "+agentName,Toast.LENGTH_LONG).show();


                       //Make the user disappear from the list and update the adapter to reflect changes
                       mUser.remove(position);
                       mAdampter.notifyDataSetChanged();
                   }
               });




           }

           @Override
           public void onLongClick(View view, int position) {

           }
       }));

       suggestView.setAdapter(mAdampter);
       getAll();
    }

    private void getAll (){
        new AsyncTask<Void,Void,Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                //connectionObj = connectionDao.getAllConnection();
                connectionObj.addAll(connectionDao.getAllConnection());
                connectionsAdapter.notifyDataSetChanged();
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
        allConnectionRef = firebaseDatabase.getReference().child("agents");
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
        allConnectionRef.addChildEventListener(childEventListener);
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
