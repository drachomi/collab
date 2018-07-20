package com.richard.imoh.collab.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.richard.imoh.collab.Pojo.User;
import com.richard.imoh.collab.R;
import com.richard.imoh.collab.Utils.FireBaseUtils;

public class Registration extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    EditText email,firstname,lastname,username,phonenumber,occupation,company,profbody,password;
    ProgressBar progressBar;
    ImageView profiledp;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    DatabaseReference userObjectRef;
    String currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        firebaseAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.reg_email);
        firstname = findViewById(R.id.reg_first_name);
        lastname = findViewById(R.id.reg_last_name);
        phonenumber = findViewById(R.id.reg_phone_number);
        occupation = findViewById(R.id.reg_occupation);
        company = findViewById(R.id.reg_company);
        profbody = findViewById(R.id.reg_prof_body);
        username = findViewById(R.id.reg_username);
        password = findViewById(R.id.reg_password);
        progressBar = findViewById(R.id.reg_progress_bar);
        firebaseDatabase = FireBaseUtils.getDatabase();
        databaseReference = firebaseDatabase.getReference().child("agents");


    }

   public void register(android.view.View view){
        String regName,regEmail,regUsername,regPassword,regOccupation,regCompany,regProfbody,regPhoneNumber;

        regName = firstname.getText().toString() + " " + lastname.getText().toString();
        regEmail = email.getText().toString();
        regUsername = username.getText().toString();
        regPassword = password.getText().toString();
        regCompany = company.getText().toString();
        regOccupation = occupation.getText().toString();
        regProfbody = profbody.getText().toString();
        regPhoneNumber = phonenumber.getText().toString();


        if(TextUtils.isEmpty(regEmail)){
            Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
            return;
        }
       if(TextUtils.isEmpty(regName)){
           Toast.makeText(getApplicationContext(), "Enter Fullname!", Toast.LENGTH_SHORT).show();
           return;
       }
       if(TextUtils.isEmpty(regUsername)){
           Toast.makeText(getApplicationContext(), "Enter unique username!", Toast.LENGTH_SHORT).show();
           return;
       }
       if(TextUtils.isEmpty(regPassword)){
           Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
           return;
       }

       progressBar.setVisibility(View.VISIBLE);
       firebaseAuth.createUserWithEmailAndPassword(regEmail,regPassword)
               .addOnCompleteListener(Registration.this, new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                       progressBar.setVisibility(View.GONE);
                       if(!task.isSuccessful()){
                           Toast.makeText(getApplicationContext(),  "Authentication failed." + task.getException(), Toast.LENGTH_SHORT).show();
                       }
                       else {
                           //Get current userID and send to next activity
                           currentUser =  firebaseAuth.getUid();
                           User user = new User(regName,"none",regEmail,"lagos",regUsername,currentUser);
                           databaseReference.child(currentUser).child("info").push().setValue(user);

                           Intent intent = new Intent(Registration.this,MainActivity.class);
                           intent.putExtra("userId", currentUser);
                           startActivity(intent);
                       }

                   }
               });
   }

}
