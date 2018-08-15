package com.richard.imoh.collab.Fragments;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.richard.imoh.collab.R;
import com.richard.imoh.collab.Request.Request;
import com.richard.imoh.collab.Request.RequestAdapter;
import com.richard.imoh.collab.Utils.ViewModels.RequestFeedViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RequestFeeds extends Fragment {
    List<Request> requestList = new ArrayList<>();
    RequestAdapter requestAdapter;
    RecyclerView recyclerView;
    RequestFeedViewModel viewModel;


    public RequestFeeds() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_request_feeds, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requestAdapter = new RequestAdapter(requestList);
        viewModel = ViewModelProviders.of(this).get(RequestFeedViewModel.class);
        viewModel.getAllRequest().observe(this, new Observer<List<Request>>() {
            @Override
            public void onChanged(@Nullable List<Request> requests) {
                requestAdapter.updateList(requests);
            }
        });
        RecyclerView recyclerView = view.findViewById(R.id.req_recycle);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(requestAdapter);


    }


}
