package com.richard.imoh.collab.Fragments;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.richard.imoh.collab.Activities.MainActivity;
import com.richard.imoh.collab.Adapters.FeedAdapter;
import com.richard.imoh.collab.Pojo.Property;
import com.richard.imoh.collab.R;
import com.richard.imoh.collab.Utils.FireBaseUtils;
import com.richard.imoh.collab.Utils.ViewModels.PropertyFeedViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PropertyFeed extends Fragment {
    FeedAdapter mFeedAdapter;
    List<Property> mProperty = new ArrayList<>();
    PropertyFeedViewModel viewModel;





    public PropertyFeed() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_property_feed, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFeedAdapter = new FeedAdapter(mProperty);
        viewModel = ViewModelProviders.of(this).get(PropertyFeedViewModel.class);
        viewModel.getAllHotels().observe(this, new Observer<List<Property>>() {
            @Override
            public void onChanged(@Nullable List<Property> propertyList) {
                Log.d("bingo",propertyList.get(0).getAgentName());
                mFeedAdapter.updateList(propertyList);
            }
        });
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.feed_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mFeedAdapter);



    }


}
