package com.richard.imoh.collab.Utils;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.richard.imoh.collab.DBUtils.Connection;
import com.richard.imoh.collab.DBUtils.ConnectionDB;
import com.richard.imoh.collab.DBUtils.ConnectionDao;
import com.richard.imoh.collab.Pojo.Property;
import com.richard.imoh.collab.Pojo.User;
import com.richard.imoh.collab.Request.Request;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LENOVO on 8/14/2018.
 */

public class Repository {
    FirebaseDatabase database = FireBaseUtils.getDatabase();
    DatabaseReference propertyRef;
    DatabaseReference requestRef;
    DatabaseReference connectionRef;
    FirebaseAuth firebaseAuth;
    ChildEventListener connectionListerner;
    ChildEventListener propListerner;
    ChildEventListener reqListerner;
    Context context;
    List<Property> propertyList = new ArrayList<>();
    MutableLiveData<List<Property>> propertyLiveDataList = new MutableLiveData<>();
    List<Request> requestList = new ArrayList<>();
    MutableLiveData<List<Request>> requestLiveDataList = new MutableLiveData<>();
    ConnectionDao connectionDao;
    List<Connection> connectionList;
    FirebaseFirestore firestore = FireBaseUtils.getFireStore();
    SaveUserToFireStore userToFireStore = new SaveUserToFireStore();

    public Repository(Context context) {
        this.context = context;
        ConnectionDB connectionDB = ConnectionDB.getInstance(context);
        connectionDao = connectionDB.connectionDao();
        firebaseAuth = FirebaseAuth.getInstance();

    }

    public List<Property> getAllProperty(String state, String city) {
        fetchProperty(state, city);
        return propertyList;
    }

    public LiveData<List<Request>> getAllRequest(String state, String city) {
        fetchRequest(state, city);
        return requestLiveDataList;
    }

    public List<Connection> getAllConnection() {
        new AsyncTask<Void, Void, List<Connection>>() {
            @Override
            protected List<Connection> doInBackground(Void... voids) {
                connectionList = connectionDao.getAllConnection();
                Log.d("connect ", connectionList.get(0).agentName);
                return connectionList;
            }
        }.execute();
        return connectionList;
    }

    public void fetchProperty(String state, String city) {

        propertyRef = database.getReference().child("properties").child(state).child(city);

        propListerner = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Property property = dataSnapshot.getValue(Property.class);
                if (property != null) {
                    Log.d("property: ", property.getAgentName());
                    propertyList.add(property);
                    //propertyLiveDataList.setValue(propertyList);

                }
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
        propertyRef.addChildEventListener(propListerner);
    }

    public void fetchRequest(String state, String city) {
        requestRef = database.getReference().child("request").child(state).child(city);
        reqListerner = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Request request = dataSnapshot.getValue(Request.class);
                if (request != null) {
                    requestList.add(request);
                    requestLiveDataList.setValue(requestList);
                }
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
        requestRef.addChildEventListener(reqListerner);

    }

    public void fetchConnection() {
        nukeTable();
        connectionRef = database.getReference().child("agents").child(firebaseAuth.getUid()).child("connections");
        connectionListerner = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Connection connection =  dataSnapshot.getValue(Connection.class);
                if (dataSnapshot.exists()) {
                    Log.d(getClass().getCanonicalName(),"Finished getting connections from firebase");
                    addConnnection(connection);
                }
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
        connectionRef.addChildEventListener(connectionListerner);

    }

    public void specificUser(String user) {

        firestore.collection("users")
                .whereEqualTo("userName",user)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot documentSnapshot : task.getResult()) {
                            User mUser = documentSnapshot.toObject(User.class);
                            if (mUser != null) {
                                Log.d("madox","user is :"+mUser.getUserName());
                            }
                        }
                    }
                });
    }

    void addConnnection(Connection connection) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                Log.d(getClass().getCanonicalName(),"About to insert connection");
                Log.d(getClass().getCanonicalName(),"Inserted connection :"+connection.agentName);
                connectionDao.insertAll(connection);
                return null;
            }
        }.execute();
    }

    void nukeTable() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                connectionDao.nukeTable();
                return null;
            }
        }.execute();
    }

    public void getCurrentUser() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        Log.d("userY","gote here");
        database.getReference().child("agents").child(firebaseAuth.getUid()).child("info").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (user != null) {
                        Log.d("userY",user.getuId());
                        editor.putString("myUserId", user.getuId());
                        editor.putString("myDp", user.getImage());
                        editor.putString("myFullname", user.getFullName());
                        editor.putString("myUserName", user.getUserName());
                        editor.putString("myLocation", user.getLocation());
                        editor.putString("myState",user.getLocation().split(",")[1]);
                        editor.putBoolean("notNewUser",true);
                        editor.apply();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("userY","error here");
            }
        });


    }



    public void addToConnectionNode(String userId,Connection connection){
        Log.d("userId",userId);
        database.getReference().child("agents").child(userId).child("connections").push().setValue(connection);

    }


}
