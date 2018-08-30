package com.richard.imoh.collab.Fragments;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.richard.imoh.collab.Activities.AgentActivity;
import com.richard.imoh.collab.Activities.PropertyDetails;
import com.richard.imoh.collab.Pojo.Property;
import com.richard.imoh.collab.R;
import com.richard.imoh.collab.Request.Request;
import com.richard.imoh.collab.Request.RequestAdapter;
import com.richard.imoh.collab.Utils.FireBaseUtils;
import com.richard.imoh.collab.Utils.FollowTouchListerner;
import com.richard.imoh.collab.Utils.Location;
import com.richard.imoh.collab.Utils.ViewModels.PropertyFeedViewModel;
import com.richard.imoh.collab.Utils.ViewModels.PropertyViewModelFactory;
import com.richard.imoh.collab.Utils.ViewModels.RequestFeedViewModel;
import com.richard.imoh.collab.Utils.ViewModels.RequestViewModelFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RequestFeeds extends Fragment {
    List<Request> mRequestList = new ArrayList<>();
    RequestAdapter requestAdapter;
    Spinner stateSpinner,citySpinner,propertyType;
    ArrayList<String> cityArray;
    ArrayList<String> stateArray;
    Location locale = new Location();
    private ArrayAdapter<String> cityArrayAdapter;
    private ArrayAdapter<String> stateArrayAdapter;
    FirebaseFirestore firestore = FireBaseUtils.getFireStore();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();




    public RequestFeeds() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_request_feeds, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requestAdapter = new RequestAdapter(mRequestList);
        fetchRequest("Lagos","Festac","Shop");
        RecyclerView recyclerView = view.findViewById(R.id.req_recycle);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addOnItemTouchListener(new FollowTouchListerner(getActivity(), recyclerView, new FollowTouchListerner.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                ImageView agentDp = view.findViewById(R.id.req_dp);

                agentDp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(!firebaseAuth.getUid().equals(mRequestList.get(position).getUserId())){
                            Intent intent = new Intent(getActivity(),AgentActivity.class);
                            intent.putExtra("userId",mRequestList.get(position).getUserId());
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(getActivity(),"Your Request",Toast.LENGTH_LONG).show();
                        }

                    }
                });


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        recyclerView.setAdapter(requestAdapter);


    }

    void alertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        AlertDialog alertDialog;
        View view1 = getLayoutInflater().inflate(R.layout.filter_popup,null);
        stateSpinner = view1.findViewById(R.id.filter_state);
        citySpinner = view1.findViewById(R.id.filter_city);
        propertyType = view1.findViewById(R.id.filter_prop_type);
        Button button = view1.findViewById(R.id.search);
        firstSpinners();
        builder.setView(view1);
        alertDialog = builder.create();
        alertDialog.show();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String state,city,type;
                state = String.valueOf(stateSpinner.getSelectedItem());
                city = String.valueOf(citySpinner.getSelectedItem());
                type = String.valueOf(propertyType.getSelectedItem());
                if(TextUtils.isEmpty(state) || state.equals("Choose State")){
                    Toast.makeText(getContext(), "Select State Please!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(city)|| city.equals("Choose City")){
                    Toast.makeText(getContext(), "Select City Please!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(type)){
                    Toast.makeText(getContext(), "Select Property Type!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    alertDialog.cancel();
                    if(!mRequestList.isEmpty()){
                        mRequestList.clear();
                    }
                    fetchRequest(state,city,type);
                }


            }
        });


    }

    public void firstSpinners(){
        stateArray = locale.getLocation("States");
        stateArrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, stateArray);
        stateArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateSpinner.setAdapter(stateArrayAdapter);
        stateSpinner.setOnItemSelectedListener(stateClickListerner);
    }
    private AdapterView.OnItemSelectedListener stateClickListerner = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            citySpinner();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };
    private AdapterView.OnItemSelectedListener cityClickListerner = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

    public void citySpinner(){
        cityArray = locale.getLocation(stateSpinner.getSelectedItem().toString());
        cityArrayAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, cityArray);
        cityArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySpinner.setAdapter(cityArrayAdapter);
        citySpinner.setOnItemSelectedListener(cityClickListerner);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            //TODO Add a dialog for selecting location and features
            alertDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void fetchRequest(String state, String city,String type){
        firestore.collection("request")
                .whereEqualTo("state", state)
                .whereEqualTo("city", city)
                .whereEqualTo("propertyType", type)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot documentSnapshot : task.getResult()) {
                            Request request = documentSnapshot.toObject(Request.class);
                            if (request != null) {
                                Log.d("property: ", request.getAgentName());
                                mRequestList.add(request);
                                Log.d("spread", mRequestList.get(0).getAgentName());
                                requestAdapter.updateList(mRequestList);
                            }
                        }
                    }
                });

    }

}
