package com.richard.imoh.collab.Property;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.richard.imoh.collab.Pojo.Property;
import com.richard.imoh.collab.Pojo.User;
import com.richard.imoh.collab.R;
import com.richard.imoh.collab.Utils.FireBaseUtils;
import com.richard.imoh.collab.Utils.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddProperty extends AppCompatActivity {
    ImageView imageView ;
    ProgressBar progressBar;
    Toolbar toolbar;
    FirebaseDatabase firebaseDatabase;
    FirebaseStorage firebaseStorage;
    FirebaseAuth firebaseAuth;
    StorageReference storageReference;
    DatabaseReference propertyRef;
    DatabaseReference agentDataRef;
    ValueEventListener valueEventListener;
    String propertyImage1;
    String propertyImage2;
    String propertyImage3;
    Spinner letTypeSpinner,stateSpinner,citySpinner,purposeSpinner,propertyTySpinner;
    EditText plotEditText,roomEditText,addInfo,priceEdit;
    String agentName,agentDp,price,state,city,roomNo,plotNo,suitableFor,propertyType,letType,additionalInfo;
    private ArrayAdapter<String> cityArrayAdapter;
    private ArrayAdapter<String> stateArrayAdapter;
    private ArrayAdapter<String>purposeAdapter;
    private ArrayAdapter<String>propertyTypeAdapter;
    RelativeLayout relativeLayout;
    Boolean priority;
    Location location = new Location();
    ArrayList<String> cityArray;
    ArrayList<String> stateArray;
    List<String>propertyTypeList = new ArrayList<>();
    List<String> purposeList =  new ArrayList<String>();
    List<String>residentialList = new ArrayList<>();
    List<String>commercialList = new ArrayList<>();
    ScrollView scrollView;
    static final int IMAGE_PICKER = 9;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_property);
        imageView = findViewById(R.id.add_prop_image);
        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add Property");
        plotEditText = findViewById(R.id.add_prop_plots);
        roomEditText = findViewById(R.id.add_prop_rooms);
        addInfo = findViewById(R.id.add_prop_description);
        priceEdit = findViewById(R.id.add_prop_price);
        letTypeSpinner = findViewById(R.id.add_prop_let_type);
        stateSpinner = findViewById(R.id.add_prop_state);
        citySpinner = findViewById(R.id.add_prop_city);
        purposeSpinner = findViewById(R.id.add_prop_purpose);
        propertyTySpinner = findViewById(R.id.add_prop_prop_type);
        scrollView = findViewById(R.id.add_prop_scroll);
        relativeLayout = findViewById(R.id.add_prop_relative);
        progressBar = findViewById(R.id.add_prop_progress);
        suitableFor = "Both Family and Bachelor";
        propertyImage1 = "";
        propertyImage2 = "";
        propertyImage3 = "";
        initializeFirebaseAuth();
        addSpinnerString();
        stateSpinner();
        purposeSpinner();

    }

   public void addPictureBtn(View view){

       price = priceEdit.getText().toString();
       state = String.valueOf(stateSpinner.getSelectedItem());
       city = String.valueOf(citySpinner.getSelectedItem());
       roomNo = roomEditText.getText().toString();
       plotNo = plotEditText.getText().toString();
       propertyType = String.valueOf(propertyTySpinner.getSelectedItem());
       letType = String.valueOf(letTypeSpinner.getSelectedItem());
       additionalInfo = addInfo.getText().toString();
        if(TextUtils.isEmpty(price)){
            Toast.makeText(getApplicationContext(), "Choose Price Please", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(state)){
            Toast.makeText(getApplicationContext(), "Choose State Please", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(city)){
            Toast.makeText(getApplicationContext(), "Choose City Please", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(roomNo)||TextUtils.isEmpty(plotNo)){
            Toast.makeText(getApplicationContext(), "Choose Either roomNo or PlotNo Please", Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(propertyType)){
            Toast.makeText(getApplicationContext(), "Choose Property Type Please", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(letType)){
            Toast.makeText(getApplicationContext(), "Choose Let Type Please", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(additionalInfo)){
            Toast.makeText(getApplicationContext(), "Additional Info Should not be blank", Toast.LENGTH_SHORT).show();
            return;
        }
        else {

            scrollView.setVisibility(View.GONE);
            relativeLayout.setVisibility(View.VISIBLE);

        }
    }

    public  void addPropertyImages(View view){
        // TODO: Fire an intent to show an image picker
        Log.d("dayo","Got to the onclick listerner method");
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/jpeg");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        startActivityForResult(Intent.createChooser(intent, "Complete action using"), IMAGE_PICKER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        imageView.setVisibility(View.GONE);

        if(requestCode == IMAGE_PICKER && resultCode == RESULT_OK){
            if(data.getClipData() != null){
                int count = data.getClipData().getItemCount(); //evaluate the count before the for loop --- otherwise, the count is evaluated every loop.
                for(int i = 0; i < count; i++){
                    Uri selectedImageUri = data.getClipData().getItemAt(i).getUri();
                    getMultipleImage(selectedImageUri,i,count);
                }
            }
            else if(data.getData() != null){
                Uri selectedImageUri = data.getData();
                getMultipleImage(selectedImageUri,0,1);

            }

        }
    }

    public void sendPropToServer(View view){
        Property property = new Property(agentName,agentDp,propertyImage1,propertyImage2,propertyImage3,price,state,city,roomNo,plotNo,suitableFor,propertyType,letType,additionalInfo);
        propertyRef.child(state).child(city).push().setValue(property);
        agentDataRef.child("properties").push().setValue(property);

        finish();


    }

    void initializeFirebaseAuth(){
        firebaseDatabase = FireBaseUtils.getDatabase();
        firebaseStorage = FirebaseStorage.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        agentDataRef = firebaseDatabase.getReference().child("agents").child(firebaseAuth.getUid());
         propertyRef = firebaseDatabase.getReference().child("properties");
        storageReference = firebaseStorage.getReference().child("propertyImage");

        getUserName();

    }
    void getUserName(){
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
        agentDataRef.child("info").addValueEventListener(valueEventListener);

    }
   public void suitableButton(View view){
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.bachelor:
                if (checked)
                    suitableFor = "Bachelor";
                break;
            case R.id.family:
                if (checked)
                    suitableFor = "Family";
                break;
            case R.id.bachelor_family:
                if (checked)
                    suitableFor = "Both Family and Bachelor";
                break;
        }
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("name", agentName);
        outState.putString("agentDp", agentDp);
        outState.putString("price",price);
        outState.putString("state", state);
        outState.putString("city", city);
        outState.putString("roomNo",roomNo);
        outState.putString("plotNo",plotNo);
        outState.putString("suitableFor", suitableFor);
        outState.putString("propertyType", propertyType);
        outState.putString("letType",letType);
        outState.putString("addInfo",additionalInfo);
        super.onSaveInstanceState(outState);
    }
    public void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        agentName = savedInstanceState.getString("name");
        agentDp = savedInstanceState.getString("agentDp");
        price = savedInstanceState.getString("price");
        state = savedInstanceState.getString("state");
        city = savedInstanceState.getString("city");
        roomNo = savedInstanceState.getString("roomNo");
        plotNo = savedInstanceState.getString("plotNo");
        suitableFor = savedInstanceState.getString("suitableFor");
        propertyType = savedInstanceState.getString("propertyType");
        letType = savedInstanceState.getString("letType");
        additionalInfo = savedInstanceState.getString("addInfo");

    }


    void getMultipleImage(Uri selectedImageUri,int i, int count){
        final StorageReference ref = storageReference.child(selectedImageUri.getLastPathSegment());
        UploadTask uploadTask = ref.putFile(selectedImageUri);
        Task<Uri> task = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if(!task.isSuccessful()){
                    throw  task.getException();
                }
                return ref.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if(task.isSuccessful()){
                    Uri downloadUri = task.getResult();
                    switch (i){
                        case 1:
                            propertyImage1 = downloadUri.toString();
                        case 2:
                            propertyImage2 = downloadUri.toString();
                        case 3:
                            propertyImage3 = downloadUri.toString();
                    }

                    if(i == count-1){
                        Button button = findViewById(R.id.sendProToServerBtn);
                        button.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        imageView.setVisibility(View.VISIBLE);
                        imageView.setImageResource(R.drawable.complete);
                        imageView.setOnClickListener(null);

                    }

                }
            }
        });
    }
}
