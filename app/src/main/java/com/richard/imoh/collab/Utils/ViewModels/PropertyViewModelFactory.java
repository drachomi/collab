package com.richard.imoh.collab.Utils.ViewModels;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

public class PropertyViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private Application mApplication;
    private String mState;
    private String mCity;


    public PropertyViewModelFactory(Application application, String state, String city) {
        mApplication = application;
        mState = state;
        mCity = city;
    }


    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new PropertyFeedViewModel(mApplication, mState,mCity);
    }

}