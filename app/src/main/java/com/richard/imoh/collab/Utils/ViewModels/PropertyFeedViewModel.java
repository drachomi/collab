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
    LiveData<List<Property>>allProperty;
    String mCity,mState;
    public PropertyFeedViewModel(@NonNull Application application,String state,String city) {
        super(application);
        mState = state;
        mCity = city;
        repository = new Repository(application);
       // allProperty = repository.getAllProperty(mState,mCity);
    }

    public LiveData<List<Property>>getAllProperty(){
//        Log.d("binga ",allHotels.getValue().get(0).getAgentName());
        Log.d("chukc","in the viewmodel method");
        Log.d("chukc","in the view model class, city is "+mCity);

        return allProperty;
    }

}
