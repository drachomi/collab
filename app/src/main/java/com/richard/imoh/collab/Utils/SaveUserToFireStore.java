package com.richard.imoh.collab.Utils;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.richard.imoh.collab.Pojo.User;

public class SaveUserToFireStore {
    FirebaseFirestore firestore = FireBaseUtils.getFireStore();
    CollectionReference reference = firestore.collection("users");
    public void saveUser(User user){
        reference.add(user);
    }
}
