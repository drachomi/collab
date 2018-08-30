package com.richard.imoh.collab.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.richard.imoh.collab.Adapters.ConnectionsAdapter;
import com.richard.imoh.collab.Adapters.FeedAdapter;
import com.richard.imoh.collab.Pojo.Property;
import com.richard.imoh.collab.Pojo.User;
import com.richard.imoh.collab.R;
import com.richard.imoh.collab.Utils.FireBaseUtils;

import java.util.ArrayList;
import java.util.List;

public class AgentActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FeedAdapter feedAdapter;
    ConnectionsAdapter connectionsAdapter;
    List<Property> mProperty = new ArrayList<>();
    List<User>connections = new ArrayList<>();
    RecyclerView connectionListRecycler;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    ChildEventListener childEventListener;
    ValueEventListener connectionEventListerner;
    DatabaseReference connectionReference;
    ValueEventListener infoEventListerner;
    ChildEventListener propertyListerner;
    Toolbar toolbar;
    String userId;
    String myUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        firebaseDatabase = FireBaseUtils.getDatabase();
        connectionListRecycler = findViewById(R.id.connection_list_recycler);
        recyclerView = findViewById(R.id.agent_proprties);
        feedAdapter = new FeedAdapter(mProperty);
        connectionsAdapter = new ConnectionsAdapter(connections);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayout.HORIZONTAL,false);
        RecyclerView.LayoutManager connectionManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(feedAdapter);
        connectionListRecycler.setLayoutManager(connectionManager);
        connectionListRecycler.setItemAnimator(new DefaultItemAnimator());
        connectionListRecycler.setAdapter(connectionsAdapter);
        Bundle extras = getIntent().getExtras();
        userId = extras.getString("userId");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        firebaseAuth = FirebaseAuth.getInstance();
        myUserId = firebaseAuth.getUid();
        databaseReference = firebaseDatabase.getReference().child("agents").child(userId);

        tabHost();
        infoListen();



        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String user = (String) dataSnapshot.getValue();
                Log.d("ebojele",user.trim());
                connectionListen(user.trim());

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
        databaseReference.child("connections").addChildEventListener(childEventListener);

        propertyListen();
    }

    void connectionListen(String user){
        Log.d("ben","The user is "+user);
        connectionReference = firebaseDatabase.getReference().child("agents").child(user).child("info");
        connectionEventListerner = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User mUser = dataSnapshot.getValue(User.class);
                Log.d("ben",mUser.getuId());
                connections.add(mUser);
                connectionsAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        };
        connectionReference.addValueEventListener(connectionEventListerner);
    }


    void bindAgentInfo(User user){
        ImageView profile_pics;
        getSupportActionBar().setTitle(user.getUserName());
        TextView username,fullname,location,email,phoneNo,occupation,profBody;
        username = findViewById(R.id.profile_name);
        fullname = findViewById(R.id.profile_fullname);
        location = findViewById(R.id.profile_location);
        email = findViewById(R.id.profile_email);
        phoneNo = findViewById(R.id.profile_telephone);
        occupation = findViewById(R.id.profile_occupation);
        profBody = findViewById(R.id.profile_prof_body);
        profile_pics = findViewById(R.id.profile_image);

        username.setText(user.getUserName());
        fullname.setText(user.getFullName());
        location.setText(user.getLocation());
        email.setText(user.getEmail());
        phoneNo.setText(user.getPhone());
        occupation.setText("A " + user.getOccupation()+ " based in " + user.getLocation());
        profBody.setText("Member of the "+user.getProfessionalQalification());
        Glide.with(profile_pics.getContext())
                .load(user.getImage())
                .apply(RequestOptions.circleCropTransform())
                .apply(RequestOptions.placeholderOf(R.drawable.placeholder))
                .into(profile_pics);
       // profile_pics.loadThumbForName(user.getImage(),user.getUserName());



    }
    void tabHost(){
        TabHost tabHost = findViewById(android.R.id.tabhost);
        tabHost.setup();
        TabHost.TabSpec mSpec = tabHost.newTabSpec("Info");
        mSpec.setContent(R.id.details_tab);
        mSpec.setIndicator("Info");
        tabHost.addTab(mSpec);

        mSpec = tabHost.newTabSpec("List");
        mSpec.setContent(R.id.agent_proprties);
        mSpec.setIndicator("Properties");
        tabHost.addTab(mSpec);

        mSpec = tabHost.newTabSpec("Connection");
        mSpec.setContent(R.id.connection_list_recycler);
        mSpec.setIndicator("Connections");
        tabHost.addTab(mSpec);

        for(int i=0;i<tabHost.getTabWidget().getChildCount();i++)
        {
            TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            tv.setTextColor(Color.parseColor("#ffffff"));
            tv.setTextSize(12);
        }
    }
    void infoListen(){
        infoEventListerner = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("bind","got to bind listerener");
                User user = dataSnapshot.getValue(User.class);
                if(user!= null){
                    Log.d("bind","got to bind " + user.getFullName());
                }
                bindAgentInfo(user);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        };
        databaseReference.child("info").addValueEventListener(infoEventListerner);
    }
    void propertyListen(){
        propertyListerner = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Property property = dataSnapshot.getValue(Property.class);
                mProperty.add(property);
                feedAdapter.notifyDataSetChanged();
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
        databaseReference.child("properties").addChildEventListener(propertyListerner);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.agent_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_chat) {
            startChat();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    void startChat(){
        DatabaseReference myRef = firebaseDatabase.getReference().child("agents").child(myUserId).child("chats").child(userId);
        String chatRef = userId + myUserId;

        //TODO RUN A QUERRY TO DB AND CHECK IF USER IS ALREADY A CONNECTION BEFORE CREATING NEW REF
        myRef.setValue(chatRef);
        databaseReference.child("chats").child(myUserId).setValue(chatRef);
        Intent intent = new Intent(AgentActivity.this,Chat.class);
        intent.putExtra("chatRef",chatRef);
        startActivity(intent);
    }

}

