package com.richard.imoh.collab.DBUtils;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by LENOVO on 8/16/2018.
 */
@Database(entities = Connection.class, version = 1)
public abstract class ConnectionDB extends RoomDatabase {
    public abstract ConnectionDao connectionDao();
    public static final String DATABASE_NAME = "connection";

    private static final Object LOCK = new Object();
    private static volatile ConnectionDB sInstance;


    public static ConnectionDB getInstance(Context context){
        if(sInstance == null){
            synchronized (LOCK){
                if(sInstance == null){
                    sInstance = Room.databaseBuilder(context.getApplicationContext(),ConnectionDB.class,DATABASE_NAME).build();
                }
            }

        }
        return sInstance;
    }
}
