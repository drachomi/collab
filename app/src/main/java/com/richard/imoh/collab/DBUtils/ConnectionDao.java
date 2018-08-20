package com.richard.imoh.collab.DBUtils;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by LENOVO on 8/16/2018.
 */
@Dao
public interface ConnectionDao {
    @Query("SELECT * FROM connection")
    List<Connection> getAllConnection();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Connection...connections);

    @Query("DELETE FROM connection")
     void nukeTable();
}
