package com.richard.imoh.collab.Utils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

/**
 * Created by LENOVO on 7/17/2018.
 */

public class FireBaseUtils {
        private static FirebaseStorage mStorage;
        private static FirebaseDatabase mDatabase;
        private static FirebaseFirestore mFireStore;
        private static FirebaseAuth mfirebaseAuth;

        public static FirebaseDatabase getDatabase() {
            if (mDatabase == null) {
                mDatabase = FirebaseDatabase.getInstance();
                mDatabase.setPersistenceEnabled(true);
            }
            return mDatabase;
        }
        public static FirebaseFirestore getFireStore(){
            if(mFireStore==null){
                mFireStore = FirebaseFirestore.getInstance();
            }
            return mFireStore;
        }

        public static FirebaseAuth getFirebaseAuth(){
            if(mfirebaseAuth==null){
                mfirebaseAuth = FirebaseAuth.getInstance();
            }
            return mfirebaseAuth;
        }
    public static FirebaseStorage getFirebaseStorage(){
        if(mStorage==null){
            mStorage = FirebaseStorage.getInstance();
        }
        return mStorage;
    }






}
