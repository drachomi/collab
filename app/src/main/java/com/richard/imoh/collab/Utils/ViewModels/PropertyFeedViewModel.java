package com.richard.imoh.collab.Utils.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.richard.imoh.collab.Pojo.Property;
import com.richard.imoh.collab.Utils.Repository;

import java.util.List;

/**
 * Created by LENOVO on 8/14/2018.
 */

public class PropertyFeedViewModel extends AndroidViewModel {
    Repository repository;
    LiveData<List<Property>>allHotels;
    public PropertyFeedViewModel(@NonNull Application application) {
        super(application);
        String state = "Lagos";
        String city = "Festac";
        repository = new Repository(application);
        allHotels = repository.getAllProperty(state,city);
    }

    public LiveData<List<Property>>getAllHotels(){
//        Log.d("binga ",allHotels.getValue().get(0).getAgentName());
        return allHotels;
    }

}
