package com.richard.imoh.collab.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.richard.imoh.collab.R;

/**
 * Created by LENOVO on 7/7/2018.
 */

public class Login extends AppCompatActivity {
    EditText email,password;
    FirebaseAuth firebaseAuth;
    ProgressBar progressBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.log_email);
        password = findViewById(R.id.log_password);
        firebaseAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.log_progress_bar);

    }
    public void login(View view){
        String loginUsername = email.getText().toString();
        String loginPassword = password.getText().toString();
        if(TextUtils.isEmpty(loginPassword)){
            Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(loginUsername)){
            Toast.makeText(getApplicationContext(), "Enter Email!", Toast.LENGTH_SHORT).show();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        firebaseAuth.signInWithEmailAndPassword(loginUsername,loginPassword)
                .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),  "Authentication failed." + task.getException(), Toast.LENGTH_SHORT).show();

                        }
                        else {
                            progressBar.setVisibility(View.GONE);
                            //Get current userID and send to next activity
                            String currentUser =  firebaseAuth.getUid();
                            Intent intent = new Intent(Login.this,MainActivity.class);
                            intent.putExtra("userId", currentUser);
                            startActivity(intent);
                        }

                    }
                });

    }

    public void registerInstead(View view){
        startActivity(new Intent(Login.this,Registration.class));
    }
}
