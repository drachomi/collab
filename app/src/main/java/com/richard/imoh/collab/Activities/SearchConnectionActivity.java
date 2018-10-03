package com.richard.imoh.collab.Activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.richard.imoh.collab.Adapters.FollowAdapter;
import com.richard.imoh.collab.Pojo.User;
import com.richard.imoh.collab.R;
import com.richard.imoh.collab.Utils.FireBaseUtils;
import com.richard.imoh.collab.Utils.Location;

import java.util.ArrayList;
import java.util.List;

public class SearchConnectionActivity extends AppCompatActivity {
    LinearLayout layout1,layout2;
    FollowAdapter followAdapter;
    List<User>mUserList = new ArrayList<>();
    FirebaseFirestore firestore = FireBaseUtils.getFireStore();
    Spinner state,city;
    String stateString, cityString,username;
    SearchView searchView;
    ArrayList<String> cityArray;
    ArrayList<String> stateArray;
    private ArrayAdapter<String> cityArrayAdapter;
    private ArrayAdapter<String> stateArrayAdapter;
    Location location = new Location();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_connection);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Search");

        followAdapter = new FollowAdapter(mUserList);
        RecyclerView recyclerView = findViewById(R.id.conn_search_recyle);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(followAdapter);

        displayList("Apapa,Lagos");



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search_c) {
            showDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertDialog alertDialog;
        View view = getLayoutInflater().inflate(R.layout.connection_search_dialog,null);
        Button button1 = view.findViewById(R.id.button);
        Button button2 = view.findViewById(R.id.button2);
        Button search1= view.findViewById(R.id.search1);
        Button search2 = view.findViewById(R.id.search2);
        layout1 = view.findViewById(R.id.search_connet_first);
        layout2 =view.findViewById(R.id.search_connet_second);
        state = view.findViewById(R.id.reg_state);
        city = view.findViewById(R.id.reg_city);
        searchView = view.findViewById(R.id.searchview);




        firstSpinners();

        builder.setView(view);
        alertDialog = builder.create();

        button1.setOnClickListener(view12 -> {
            layout2.setVisibility(View.GONE);
            layout1.setVisibility(View.VISIBLE);

        });
        button2.setOnClickListener(view13 -> {
            layout1.setVisibility(View.GONE);
            layout2.setVisibility(View.VISIBLE);
        });
        search1.setOnClickListener(view1 -> {
            username =searchView.getQuery().toString();
            if(TextUtils.isEmpty(username)){
                Toast.makeText(getApplicationContext(),"Please input username",Toast.LENGTH_LONG).show();
            }else {
                specificUser(username);
                alertDialog.dismiss();
            }

        });

        search2.setOnClickListener(view1 -> {
            cityString = String.valueOf(city.getSelectedItem());
            stateString = String.valueOf(state.getSelectedItem());
            if(TextUtils.isEmpty(stateString)|| TextUtils.isEmpty(cityString)){
                Toast.makeText(getApplicationContext(),"Please select state and city",Toast.LENGTH_LONG).show();
            }else {
                String location = city.getSelectedItem().toString() + ","+ state.getSelectedItem().toString();

                displayList(location);
                alertDialog.dismiss();
            }

        });

        alertDialog.show();
    }

    public void firstSpinners(){
        stateArray = location.getLocation("States");
        stateArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, stateArray);
        stateArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        state.setAdapter(stateArrayAdapter);
        state.setOnItemSelectedListener(stateClickListerner);
    }

    public void citySpinner(){
        cityArray = location.getLocation(state.getSelectedItem().toString());
        cityArrayAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item, cityArray);
        cityArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        city.setAdapter(cityArrayAdapter);
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

    public void specificUser(String user) {

        firestore.collection("users")
                .whereEqualTo("userName",user)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot documentSnapshot : task.getResult()) {
                            User mUser = documentSnapshot.toObject(User.class);
                            if (mUser != null) {
                                mUserList.clear();
                                mUserList.add(mUser);
                                Log.d("madox","user is :"+mUser.getUserName());
                                followAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
    }


    public void displayList(String location) {
        Log.d("location",location);

        firestore.collection("users")
                .whereEqualTo("location",location)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot documentSnapshot : task.getResult()) {
                            User mUser = documentSnapshot.toObject(User.class);
                            if (mUser != null) {
                                mUserList.clear();
                                mUserList.add(mUser);
                                Log.d("madox","user is :"+mUser.getUserName());
                                followAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
    }
}
