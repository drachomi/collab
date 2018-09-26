package com.richard.imoh.collab.DBUtils;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by LENOVO on 8/15/2018.
 */

@Entity(tableName = "connection")
public class Connection {
    @PrimaryKey
    @NonNull public String Uid;
    public String agentName;
    public String agentDp;
    public String chatRef;
    public String location;

    public Connection() {
    }


    public Connection(@NonNull String uid, String agentName, String agentDp, String chatRef, String location) {
        Uid = uid;
        this.agentName = agentName;
        this.agentDp = agentDp;
        this.chatRef = chatRef;
        this.location = location;
    }
}
