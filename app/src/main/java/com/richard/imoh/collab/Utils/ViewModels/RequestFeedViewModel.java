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
    public RequestFeedViewModel(@NonNull Application application) {
        super(application);
        String state = "Lagos";
        String city = "Festac";
        repository = new Repository(application);
        allRequest = repository.getAllRequest(state,city);

    }
    public LiveData<List<Request>>getAllRequest(){
        return allRequest;
    }



}
