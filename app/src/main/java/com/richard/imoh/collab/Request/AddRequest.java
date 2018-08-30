package com.richard.imoh.collab.Request;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.richard.imoh.collab.Pojo.User;
import com.richard.imoh.collab.R;
import com.richard.imoh.collab.Utils.FireBaseUtils;
import com.richard.imoh.collab.Utils.Location;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddRequest extends AppCompatActivity {
    Toolbar toolbar;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference userDatabaseReference;
    FirebaseFirestore firestore;
    CollectionReference propertyRequestReference;
    ValueEventListener requestEventListerner;
    ValueEventListener valueEventListener;
    String agentName,agentDp,letType,state,city,propertyType,description,price;
    Spinner letTypeSpinner,stateSpinner,citySpinner,purposeSpinner,propertyTySpinner,priceSpinner;
    EditText plotEditText,roomEditText,descriptionEditText;
    int roomNo,plotNo;
    private ArrayAdapter<String> cityArrayAdapter;
    private ArrayAdapter<String> stateArrayAdapter;
    private ArrayAdapter<String>purposeAdapter;
    private ArrayAdapter<String>propertyTypeAdapter;
    Boolean priority;
    Location location = new Location();
    ArrayList<String> cityArray;
    ArrayList<String> stateArray;
    List<String>propertyTypeList = new ArrayList<>();
    List<String> purposeList =  new ArrayList<String>();
    List<String>residentialList = new ArrayList<>();
    List<String>commercialList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_request);
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add Request");
        firebaseDatabase = FireBaseUtils.getDatabase();
        firestore = FireBaseUtils.getFireStore();
        firebaseAuth = FirebaseAuth.getInstance();
        String userId = firebaseAuth.getUid();
        userDatabaseReference = firebaseDatabase.getReference().child("agents").child(userId).child("info");
        propertyRequestReference = firestore.collection("request");
        letTypeSpinner = findViewById(R.id.add_req_let_type);
        propertyTySpinner = findViewById(R.id.add_req_prop_type);
        purposeSpinner = findViewById(R.id.add_req_purpose);
        priceSpinner = findViewById(R.id.add_req_price);
        descriptionEditText = findViewById(R.id.add_req_description);
        plotEditText = findViewById(R.id.add_req_plots);
        roomEditText = findViewById(R.id.add_req_rooms);
        stateSpinner = findViewById(R.id.add_req_state);
        citySpinner = findViewById(R.id.add_req_city);

        priority = false;


        addSpinnerString();
        attachValueListerner();
        stateSpinner();
        purposeSpinner();

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
        stateArray = location.getLocation("States");
        stateArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, stateArray);
        stateArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateSpinner.setAdapter(stateArrayAdapter);
        stateSpinner.setOnItemSelectedListener(stateClickListerner);

    }
    void citySpinner(){
        cityArray = location.getLocation(stateSpinner.getSelectedItem().toString());
        cityArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cityArray);
        cityArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySpinner.setAdapter(cityArrayAdapter);
        citySpinner.setOnItemSelectedListener(cityClickListerner);
    }
    void purposeSpinner(){
        purposeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, purposeList);
        purposeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        purposeSpinner.setAdapter(purposeAdapter);
        purposeSpinner.setOnItemSelectedListener(purposeClickListerner);

    }
    void propertyTySpinner(){
        propertyTypeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, propertyTypeList);
        propertyTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        propertyTySpinner.setAdapter(propertyTypeAdapter);
        propertyTySpinner.setOnItemSelectedListener(propertyClickListerner);
    }
    private AdapterView.OnItemSelectedListener purposeClickListerner = new AdapterView.OnItemSelectedListener(){


        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            if(purposeSpinner.getSelectedItem().toString().equals("Residential")){
                propertyTypeList = residentialList;
            }
            if(purposeSpinner.getSelectedItem().toString().equals("Commercial")){
                propertyTypeList = commercialList;
            }
            propertyTySpinner();

        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };
    private AdapterView.OnItemSelectedListener propertyClickListerner = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            propertyType = String.valueOf(propertyTySpinner.getSelectedItem());

        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };



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
        ProgressBar bar = findViewById(R.id.add_req_progress);
        bar.setVisibility(View.VISIBLE);
        letType = String.valueOf(letTypeSpinner.getSelectedItem());
        price = String.valueOf(priceSpinner.getSelectedItem());
        description = descriptionEditText.getText().toString();
        roomNo = Integer.valueOf(roomEditText.getText().toString());
        plotNo = Integer.valueOf(plotEditText.getText().toString());
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = df.format(Calendar.getInstance().getTime());
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String reqId = timestamp.getTime() + firebaseAuth.getUid();
        //Set priority as true when user selects item at position 1 and false in other occasion

        Request request = new Request(description,price,state,city,priority,propertyType,letType,plotNo,roomNo,agentName,agentDp,firebaseAuth.getUid(),currentDate,reqId);
        propertyRequestReference.add(request);
        Toast.makeText(AddRequest.this,"Added request",Toast.LENGTH_LONG).show();
        finish();

    }
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.not_urgent:
                if (checked)
                    // Pirates are the best
                    priority = true;
                    break;
            case R.id.urgent:
                if (checked)
                    // Ninjas rule
                    priority = false;
                    break;
        }
    }

}
