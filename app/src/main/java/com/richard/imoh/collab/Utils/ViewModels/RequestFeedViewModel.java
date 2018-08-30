package com.richard.imoh.collab.Utils.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.richard.imoh.collab.Request.Request;
import com.richard.imoh.collab.Utils.Repository;

import java.util.List;

/**
 * Created by LENOVO on 8/14/2018.
 */

public class RequestFeedViewModel extends AndroidViewModel {
    Repository repository;
    LiveData<List<Request>>allRequest;
    String mCity,mState;
    public RequestFeedViewModel(@NonNull Application application,String state,String city) {
        super(application);
        mState = state;
        mCity = city;
        repository = new Repository(application);
        allRequest = repository.getAllRequest(mState,mCity);

    }
    public LiveData<List<Request>>getAllRequest(){
        return allRequest;
    }



}
