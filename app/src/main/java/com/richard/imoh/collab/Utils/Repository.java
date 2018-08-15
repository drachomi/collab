package com.richard.imoh.collab.Utils;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.richard.imoh.collab.Pojo.Property;
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
    ChildEventListener propListerner;
    ChildEventListener reqListerner;
    Context context;
    List<Property>propertyList = new ArrayList<>();
    MutableLiveData<List<Property>> propertyLiveDataList = new MutableLiveData<>();
    List<Request>requestList = new ArrayList<>();
    MutableLiveData<List<Request>> requestLiveDataList = new MutableLiveData<>();

    public Repository(Context context) {
        this.context = context;
    }

    public LiveData<List<Property>>getAllProperty(String state, String city){
        fetchProperty(state,city);
        return propertyLiveDataList;
    }
    public LiveData<List<Request>>getAllRequest(String state, String city){
        fetchRequest(state,city);
        return requestLiveDataList;
    }



   public void fetchProperty(String state, String city){

        propertyRef = database.getReference().child("properties").child(state).child(city);

        propListerner = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Property property = dataSnapshot.getValue(Property.class);
                if(property!=null){
                    Log.d("property: ",property.getAgentName());
                    propertyList.add(property);
                    propertyLiveDataList.setValue(propertyList);

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
   public void fetchRequest(String state, String city){
       requestRef = database.getReference().child("request").child(state).child(city);
       reqListerner = new ChildEventListener() {
           @Override
           public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
               Request request = dataSnapshot.getValue(Request.class);
               if(request!=null){
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
}
