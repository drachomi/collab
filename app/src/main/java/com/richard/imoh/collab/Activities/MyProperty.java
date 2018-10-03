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
import com.richard.imoh.collab.Utils.FireBaseUtils;

import java.util.ArrayList;
import java.util.List;

public class MyProperty extends AppCompatActivity {
    RecyclerView recyclerView;
    FirebaseFirestore firestore;
    FeedAdapter mFeedAdapter;
    List<Property>mProperty = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_property);
        firestore = FireBaseUtils.getFireStore();
        mFeedAdapter = new FeedAdapter(mProperty);
        recyclerView = findViewById(R.id.feed_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(mFeedAdapter);
        fetchAllProperty();
    }


    public void fetchAllProperty() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String uid = preferences.getString("myUserId","");
        firestore.collection("properties")
                .whereEqualTo("userId",uid )
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot documentSnapshot : task.getResult()) {
                            Property property = documentSnapshot.toObject(Property.class);
                            if (property != null) {
                                Log.d("property: ", property.getAgentName());
                                mProperty.add(property);
                                Log.d("spread", mProperty.get(0).getAgentName());
                                mFeedAdapter.updateList(mProperty);
                            }
                        }
                    }
                });


    }
}
