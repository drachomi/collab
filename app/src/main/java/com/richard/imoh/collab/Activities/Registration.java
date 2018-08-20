package com.richard.imoh.collab.Activities;

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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.richard.imoh.collab.Pojo.ChatMeta;
import com.richard.imoh.collab.Pojo.User;
import com.richard.imoh.collab.Pojo.UserMessage;
import com.richard.imoh.collab.R;
import com.richard.imoh.collab.Utils.FireBaseUtils;
import com.richard.imoh.collab.Utils.Location;

import java.util.ArrayList;
import java.util.List;

public class Registration extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    EditText email,firstname,username,phonenumber,occupation,company,profbody,password;
    ProgressBar progressBar;
    ImageView profiledp;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    FirebaseStorage firebaseStorage;
    DatabaseReference userObjectRef;
    String currentUser;
    ImageView photo;
    private ArrayAdapter<String> cityArrayAdapter;
    private ArrayAdapter<String> stateArrayAdapter;
    LinearLayout first,second,third;
    String regName,regEmail,regUsername,regPassword,regOccupation,regCompany,regProfbody,regPhoneNumber,regGender,regCountry,regState,regCity,regLocation,regImage;
    Spinner gender,country,state,city;
    static final int IMAGE_PICKER = 9;
    ArrayList<String> cityArray;
    ArrayList<String> stateArray;
    Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        first = findViewById(R.id.first_linear);
        second = findViewById(R.id.second_linear);
        third = findViewById(R.id.last_linear);
        firebaseAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.reg_email);
        firstname = findViewById(R.id.reg_first_name);
        phonenumber = findViewById(R.id.reg_phone_number);
        occupation = findViewById(R.id.reg_occupation);
        company = findViewById(R.id.reg_company);
        profbody = findViewById(R.id.reg_prof_body);
        username = findViewById(R.id.reg_username);
        password = findViewById(R.id.reg_password);
        gender = findViewById(R.id.reg_gender);
        state = findViewById(R.id.reg_state);
        city = findViewById(R.id.reg_city);
        progressBar = findViewById(R.id.reg_progress);
        photo = findViewById(R.id.reg_dp);
        firebaseDatabase = FireBaseUtils.getDatabase();
        databaseReference = firebaseDatabase.getReference().child("agents");
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference().child("profile_menu");
        location = new Location();





        firstSpinners();


    }

   public void register(View view){
       regCompany = company.getText().toString();
       regOccupation = occupation.getText().toString();
       regProfbody = profbody.getText().toString();

        if(TextUtils.isEmpty(regCompany)){
            Toast.makeText(getApplicationContext(), "Enter company name if any or none if none!", Toast.LENGTH_SHORT).show();
            return;
        }
       if(TextUtils.isEmpty(regOccupation)){
           Toast.makeText(getApplicationContext(), "Enter Occupation!", Toast.LENGTH_SHORT).show();
           return;
       }
       if(TextUtils.isEmpty(regProfbody)){
           Toast.makeText(getApplicationContext(), "Enter any professional body or enter none!", Toast.LENGTH_SHORT).show();
           return;
       }
       if(TextUtils.isEmpty(regImage)){
           Toast.makeText(getApplicationContext(), "Please upload an image!", Toast.LENGTH_SHORT).show();
           return;
       }
       else {
           Log.d("visi-thir","name is : "+regName);
           Log.d("visi-thir","location is : "+regLocation);
           Log.d("visi-thir","occupa is : "+regOccupation);

           currentUser =  firebaseAuth.getUid();
           User user = new User(regName,regImage,regEmail,regPhoneNumber,regOccupation,regCompany,regProfbody,regGender,"none",regLocation,regUsername,currentUser);
           //User user = new User(regName,"none",regEmail,"lagos",regUsername,currentUser);
           databaseReference.child(currentUser).child("info").setValue(user);
           Intent intent = new Intent(Registration.this,MainActivity.class);
           intent.putExtra("userId", currentUser);
           startActivity(intent);
           finish();


       }


   }
   public void secondReg(View view){
       regPhoneNumber = phonenumber.getText().toString();
       regUsername = username.getText().toString();
       regGender = String.valueOf(gender.getSelectedItem());
       if(TextUtils.isEmpty(regUsername)){
           Toast.makeText(getApplicationContext(), "Enter unique username!", Toast.LENGTH_SHORT).show();
           return;
       }
       if(TextUtils.isEmpty(regPhoneNumber)){
           Toast.makeText(getApplicationContext(), "Enter phone number!", Toast.LENGTH_SHORT).show();
           return;
       }
       if(TextUtils.isEmpty(regLocation)){
           Toast.makeText(getApplicationContext(), "Choose State and City!", Toast.LENGTH_SHORT).show();
           return;
       }
       else {
           Log.d("visi-sec","name is : "+regName);
           Log.d("visi-sec","location is : "+regLocation);
           second.setVisibility(View.GONE);
           third.setVisibility(View.VISIBLE);
       }

   }
   public void firstReg(View view){
       regName = firstname.getText().toString();
       regEmail = email.getText().toString();
       regPassword = password.getText().toString();
       if(TextUtils.isEmpty(regEmail)){
           Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
           return;
       }
       if(TextUtils.isEmpty(regName)){
           Toast.makeText(getApplicationContext(), "Enter Fullname!", Toast.LENGTH_SHORT).show();
           return;
       }
       if(TextUtils.isEmpty(regPassword)){
           Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
           return;
       }
       else {

           progressBar.setVisibility(View.VISIBLE);
           firebaseAuth.createUserWithEmailAndPassword(regEmail,regPassword)
                   .addOnCompleteListener(Registration.this, new OnCompleteListener<AuthResult>() {
                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task) {
                           progressBar.setVisibility(View.GONE);
                           if(!task.isSuccessful()){
                               try {
                                   throw task.getException();

                               } catch (FirebaseAuthWeakPasswordException weakPassword) {
                                   Toast.makeText(getApplicationContext(),  "Password must be up to 6 Characters." , Toast.LENGTH_SHORT).show();
                               }catch (FirebaseAuthInvalidCredentialsException e){
                                   Toast.makeText(getApplicationContext(),  "Please choose a correct Email." , Toast.LENGTH_SHORT).show();
                               }catch (FirebaseAuthUserCollisionException e){
                                   Toast.makeText(getApplicationContext(),  "Email already in use by another user" , Toast.LENGTH_SHORT).show();
                               } catch (Exception e) {
                                   e.printStackTrace();
                               }

                           }
                           else if(task.isSuccessful()) {
                               Log.d("visi-fir","name is : "+regName);
                               //Get current userID and send to next activity
                               progressBar.setVisibility(View.GONE);
                               first.setVisibility(View.GONE);
                               second.setVisibility(View.VISIBLE);
                           }

                       }
                   });


       }



   }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        first.setVisibility(View.GONE);
        second.setVisibility(View.GONE);
        third.setVisibility(View.VISIBLE);
        if(requestCode == IMAGE_PICKER && resultCode == RESULT_OK){
            Uri selectedImageUri = data.getData();
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
                        regImage = downloadUri.toString();


                    }
                }
            });

        }
    }
    public void pickImage(View view){
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Fire an intent to show an image picker
                Log.d("dayo","Got to the onclick listerner method");
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), IMAGE_PICKER);
            }
        });
    }


    public void secondSpinners(){

        if(state.getSelectedItem().toString().equals("Lagos")){
            cityArray = location.getLagos();
        }
        else {
            cityArray = location.getDefaultCity();
        }
        cityArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cityArray);
        cityArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        city.setAdapter(cityArrayAdapter);
        city.setOnItemSelectedListener(cityClickListerner);
    }

    public void firstSpinners(){

        stateArray = location.getStates();



        stateArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, stateArray);
        stateArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        state.setAdapter(stateArrayAdapter);
        state.setOnItemSelectedListener(stateClickListerner);
    }
   private AdapterView.OnItemSelectedListener stateClickListerner = new AdapterView.OnItemSelectedListener() {
       @Override
       public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
           secondSpinners();
       }

       @Override
       public void onNothingSelected(AdapterView<?> adapterView) {

       }
   };

    private AdapterView.OnItemSelectedListener cityClickListerner = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            regCity = city.getSelectedItem().toString();
            regState = state.getSelectedItem().toString();

            regLocation = regCity + "," + regState;
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("name", regName);
        outState.putString("username", regUsername);
        outState.putString("email",regEmail);
        outState.putString("password", regPassword);
        outState.putString("gender", regGender);
        outState.putString("location",regLocation);
        outState.putString("phone",regPhoneNumber);
        super.onSaveInstanceState(outState);
    }
    public void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        regName = savedInstanceState.getString("name");
        regUsername = savedInstanceState.getString("username");
        regEmail = savedInstanceState.getString("email");
        regPassword = savedInstanceState.getString("password");
        regGender = savedInstanceState.getString("gender");
        regLocation = savedInstanceState.getString("location");
        regPhoneNumber = savedInstanceState.getString("phone");

    }
}
