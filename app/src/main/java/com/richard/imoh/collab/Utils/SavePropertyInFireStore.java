package com.richard.imoh.collab.Utils;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.richard.imoh.collab.Pojo.Property;


public class SavePropertyInFireStore {
    FirebaseDatabase firebaseDatabase = FireBaseUtils.getDatabase();
    FirebaseFirestore firestore = FireBaseUtils.getFireStore();
    CollectionReference propertyRef;
    DatabaseReference agentDataRef;



    public void saveProp(Property property){
        propertyRef = firestore.collection("properties");
        propertyRef.add(property);

    }
    public void saveToAgentProp(Property property){
        agentDataRef = firebaseDatabase.getReference().child("agents").child(property.getUserId());
        agentDataRef.child("properties").push().setValue(property);
    }
}
