package com.richard.imoh.collab.Request;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.richard.imoh.collab.Pojo.User;
import com.richard.imoh.collab.R;
import com.richard.imoh.collab.Utils.FireBaseUtils;
import com.richard.imoh.collab.Utils.Location;
import com.richard.imoh.collab.Utils.PropertyTypeList;
import com.richard.imoh.collab.Utils.SaveRequestInFireStore;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddRequest extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference userDatabaseReference;
    FirebaseFirestore firestore;
    CollectionReference propertyRequestReference;
    String agentName,agentDp,letType,state,city,propertyType,description,price,priority;
    Spinner letTypeSpinner,stateSpinner,citySpinner,purposeSpinner,propertyTySpinner,prioritySpiner;
    EditText plotEditText,priceEdit,additionalInfo;
    String plotNo;
    int roomNo;
    private ArrayAdapter<String> cityArrayAdapter;
    private ArrayAdapter<String> stateArrayAdapter;
    private ArrayAdapter<String>purposeAdapter;
    private ArrayAdapter<String>propertyTypeAdapter;
    Location location = new Location();
    ArrayList<String> cityArray;
    ArrayList<String> stateArray;
    List<String>propertyTypeList = new ArrayList<>();
    List<String> purposeList =  new ArrayList<String>();
    List<String>residentialList = new ArrayList<>();
    List<String>commercialList = new ArrayList<>();
    PropertyTypeList typeList = new PropertyTypeList();
    SaveRequestInFireStore inFireStore = new SaveRequestInFireStore();
    RelativeLayout first,second;
    String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_request);

        firebaseDatabase = FireBaseUtils.getDatabase();
        firestore = FireBaseUtils.getFireStore();
        firebaseAuth = FirebaseAuth.getInstance();
        String userId = firebaseAuth.getUid();
        userDatabaseReference = firebaseDatabase.getReference().child("agents").child(userId).child("info");
        propertyRequestReference = firestore.collection("request");
        letTypeSpinner = findViewById(R.id.add_prop_let_type);
        propertyTySpinner = findViewById(R.id.add_prop_prop_type);
        purposeSpinner = findViewById(R.id.add_prop_purpose);
        additionalInfo = findViewById(R.id.add_prop_description);
        plotEditText = findViewById(R.id.add_prop_plots);
        priceEdit = findViewById(R.id.add_prop_price);
        stateSpinner = findViewById(R.id.add_prop_state);
        citySpinner = findViewById(R.id.add_prop_city);
        prioritySpiner = findViewById(R.id.add_prop_priority);



        first = findViewById(R.id.add_prop_first_relative);
        second = findViewById(R.id.add_prop_second_relative);

        getUserName();
        stateSpinner();
        purposeSpinner();

    }

    void getUserName() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        agentName = preferences.getString("myFullname", "");
        agentDp = preferences.getString("myDp", "");
        userId = preferences.getString("myUserId", "");

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
        purposeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, typeList.getPurposeList());
        purposeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        purposeSpinner.setAdapter(purposeAdapter);
        purposeSpinner.setOnItemSelectedListener(purposeClickListerner);

    }
    void propertyTySpinner(List<String>propType){
        propertyTypeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, propType);
        propertyTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        propertyTySpinner.setAdapter(propertyTypeAdapter);
        propertyTySpinner.setOnItemSelectedListener(propertyClickListerner);
    }
    private AdapterView.OnItemSelectedListener purposeClickListerner = new AdapterView.OnItemSelectedListener(){


        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            if(purposeSpinner.getSelectedItem().toString().equals("Residential")){
                propertyTySpinner(typeList.getResidential());
            }
            if(purposeSpinner.getSelectedItem().toString().equals("Commercial")){
                propertyTySpinner(typeList.getCommercialList());
            }

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

    public void addRequestBtn(){

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = df.format(Calendar.getInstance().getTime());
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String reqId = timestamp.getTime() + firebaseAuth.getUid();
        //Set priority as true when user selects item at position 1 and false in other occasion

        Request request = new Request(description,price,state,city,priority,propertyType,letType,Integer.parseInt(plotNo),roomNo,agentName,agentDp,firebaseAuth.getUid(),currentDate,reqId);
        propertyRequestReference.add(request);
        inFireStore.saveReq(request);
        inFireStore.saveToAgentReq(request);
        Toast.makeText(AddRequest.this,"Added request",Toast.LENGTH_LONG).show();
        finish();

    }

    public void reqBtn1(View view){
        plotNo = plotEditText.getText().toString();
        state = String.valueOf(stateSpinner.getSelectedItem());
        city = String.valueOf(citySpinner.getSelectedItem());
        if (TextUtils.isEmpty(state)) {
            Toast.makeText(getApplicationContext(), "Choose State Please", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(city)) {
            Toast.makeText(getApplicationContext(), "Choose City Please", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(plotNo)) {
            Toast.makeText(getApplicationContext(), "Please enter roomNo or PlotNo Please", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(propertyType)) {
            Toast.makeText(getApplicationContext(), "Choose Property Type Please", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            first.setVisibility(View.GONE);
            second.setVisibility(View.VISIBLE);
        }



    }
    public void reqBtn2(View view){
        priority = String.valueOf(prioritySpiner.getSelectedItem());
        propertyType = String.valueOf(propertyTySpinner.getSelectedItem());
        price = priceEdit.getText().toString();
        letType = String.valueOf(letTypeSpinner.getSelectedItem());
        description = additionalInfo.getText().toString();
        if (TextUtils.isEmpty(price)) {
            Toast.makeText(getApplicationContext(), "Choose Price Please", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(priority)|| priority.equals("Priority Level")){
            Toast.makeText(getApplicationContext(), "Choose Suitable For Please", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(letType)) {
            Toast.makeText(getApplicationContext(), "Choose Let Type Please", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(description)) {
            Toast.makeText(getApplicationContext(), "Additional Info Should not be blank", Toast.LENGTH_SHORT).show();
            return;
        }else {
            addRequestBtn();
        }



    }
}
