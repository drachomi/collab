package com.richard.imoh.collab.Utils;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.richard.imoh.collab.Pojo.Property;
import com.richard.imoh.collab.Request.Request;

public class SaveRequestInFireStore {
    FirebaseDatabase firebaseDatabase = FireBaseUtils.getDatabase();
    FirebaseFirestore firestore = FireBaseUtils.getFireStore();
    CollectionReference propertyRef;
    DatabaseReference agentDataRef;



    public void saveReq(Request request){
        propertyRef = firestore.collection("request");
        propertyRef.add(request);

    }
    public void saveToAgentReq(Request request){
        agentDataRef = firebaseDatabase.getReference().child("agents").child(request.getUserId());
        agentDataRef.child("request").push().setValue(request);
    }
}
