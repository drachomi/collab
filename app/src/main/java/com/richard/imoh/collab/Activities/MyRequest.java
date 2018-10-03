package com.richard.imoh.collab.Activities;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.richard.imoh.collab.Adapters.FeedAdapter;
import com.richard.imoh.collab.Pojo.Property;
import com.richard.imoh.collab.R;
import com.richard.imoh.collab.Request.Request;
import com.richard.imoh.collab.Request.RequestAdapter;
import com.richard.imoh.collab.Utils.FireBaseUtils;

import java.util.ArrayList;
import java.util.List;

public class MyRequest extends AppCompatActivity {
    RecyclerView recyclerView;
    FirebaseFirestore firestore;
    RequestAdapter requestAdapter;
    List<Request> mRequestList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_request);


        firestore = FireBaseUtils.getFireStore();
        requestAdapter = new RequestAdapter(mRequestList);
        recyclerView = findViewById(R.id.req_recycle);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(requestAdapter);
        fetchRequest();
    }

    public void fetchRequest(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String uid = preferences.getString("myUserId","");
        firestore.collection("request")
                .whereEqualTo("userId", uid)
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
