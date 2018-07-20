package com.richard.imoh.collab.Utils;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by LENOVO on 7/17/2018.
 */

public class FireBaseUtils {
        private static FirebaseDatabase mDatabase;

        public static FirebaseDatabase getDatabase() {
            if (mDatabase == null) {
                mDatabase = FirebaseDatabase.getInstance();
                mDatabase.setPersistenceEnabled(true);
            }
            return mDatabase;
        }


}
