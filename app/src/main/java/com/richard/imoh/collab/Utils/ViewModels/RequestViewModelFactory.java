package com.richard.imoh.collab.Utils.ViewModels;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

/**
 * Created by LENOVO on 8/20/2018.
 */

public class RequestViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private Application mApplication;
    private String mState;
    private String mCity;

    public RequestViewModelFactory(Application mApplication, String mState, String mCity) {
        this.mApplication = mApplication;
        this.mState = mState;
        this.mCity = mCity;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new RequestFeedViewModel(mApplication, mState,mCity);
    }
}
