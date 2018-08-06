package com.richard.imoh.collab.Request;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.richard.imoh.collab.Pojo.User;
import com.richard.imoh.collab.R;
import com.richard.imoh.collab.Utils.FireBaseUtils;
import com.richard.imoh.collab.Utils.Location;

import java.util.ArrayList;
import java.util.List;

public class AddRequest extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference userDatabaseReference;
    ValueEventListener valueEventListener;
    String agentName,agentDp,letType,state,city;
    Spinner letTypeSpinner,prioritySpinner,stateSpinner,citySpinner;
    private ArrayAdapter<String> cityArrayAdapter;
    private ArrayAdapter<String> stateArrayAdapter;
    private ArrayAdapter<String>purposeAdapter;
    private ArrayAdapter<String>propertyTypeAdapter;
    Boolean priority;
    Location location = new Location();
    ArrayList<String> cityArray;
    ArrayList<String> stateArray;
    List<String> purposeList =  new ArrayList<String>();
    List<String>residentialList = new ArrayList<>();
    List<String>commercialList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_request);
        firebaseDatabase = FireBaseUtils.getDatabase();
        firebaseAuth = FirebaseAuth.getInstance();
        String userId = firebaseAuth.getUid();
        userDatabaseReference = firebaseDatabase.getReference().child("agents").child(userId).child("info");
        prioritySpinner = findViewById(R.id.add_req_priority);
        letTypeSpinner = findViewById(R.id.add_req_let_type);

        addSpinnerString();
        attachValueListerner();
        stateSpinner();

    }

    void attachValueListerner(){
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                agentName = user.getFullName();
                agentDp = user.getImage();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        userDatabaseReference.addValueEventListener(valueEventListener);
    }
    void stateSpinner(){
        stateArray = location.getStates();
        stateArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, stateArray);
        stateArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateSpinner.setAdapter(stateArrayAdapter);
        stateSpinner.setOnItemSelectedListener(stateClickListerner);

    }
    void citySpinner(){

        if(stateSpinner.getSelectedItem().toString().equals("Lagos")){
            cityArray = location.getLagos();
        }
        else {
            cityArray = location.getDefaultCity();
        }
        cityArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cityArray);
        cityArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySpinner.setAdapter(cityArrayAdapter);
        citySpinner.setOnItemSelectedListener(cityClickListerner);
    }
    void purposeSpinner(){

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
            state = String.valueOf(stateSpinner.getSelectedItem());
            city = String.valueOf(citySpinner.getSelectedItem());
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };
    void addSpinnerString(){
        purposeList.add("Commercial");
        purposeList.add("Residential");
        residentialList.add("Single Room");
        residentialList.add("Self Contain");
        residentialList.add("Mini Flats");
        residentialList.add("1 Bed Room Flats");
        residentialList.add("2 Bed Room Flats");
        residentialList.add("3 Bed Room Flats");
        residentialList.add("4 Bed Room Flats");
        residentialList.add("Duplex");
        residentialList.add("Estate Block");
        residentialList.add("Land");
        commercialList.add("Office Space");
        commercialList.add("Co Working Space");
        commercialList.add("Shop");
        commercialList.add("Ware House");
        commercialList.add("Cemetery");
    }
    public void addRequestBtn(View view){
        letType = String.valueOf(letTypeSpinner.getSelectedItem());
        //Set priority as true when user selects item at position 1 and false in other occasion
        priority = prioritySpinner.getSelectedItemPosition() == 1;


    }

}
