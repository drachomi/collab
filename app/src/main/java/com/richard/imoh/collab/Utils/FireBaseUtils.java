package com.richard.imoh.collab.Utils;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Created by LENOVO on 7/17/2018.
 */

public class FireBaseUtils {
        private static FirebaseDatabase mDatabase;
        private static FirebaseFirestore mFireStore;

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




}
