package com.richard.imoh.collab.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
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
        }else {
            progressBar.setVisibility(View.VISIBLE);
            firebaseAuth.signInWithEmailAndPassword(loginUsername,loginPassword)
                    .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                try{
                                    throw task.getException();
                                }catch (FirebaseAuthInvalidUserException exception){
                                    Toast.makeText(getApplicationContext(),  "The User does not exist, Please Register" , Toast.LENGTH_LONG).show();

                                }catch (FirebaseAuthInvalidCredentialsException exception){
                                    Toast.makeText(getApplicationContext(),  "Wrong password, Please try again" , Toast.LENGTH_LONG).show();

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

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

    }

    public void registerInstead(View view){
        startActivity(new Intent(Login.this,Registration.class));
    }
    public void resetPassword(View view){
        Log.d("reset","Got to reset passwprd");
        LinearLayout linearLayout = findViewById(R.id.log_lay1);
        EditText emailEdit = findViewById(R.id.reset_email);
        Button reset = findViewById(R.id.rest_btn);
        linearLayout.setVisibility(View.GONE);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEdit.getText().toString();
                if (TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(), "Enter Email!", Toast.LENGTH_SHORT).show();
                }else {
                    firebaseAuth.sendPasswordResetEmail(email)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(), "Please Check your mail", Toast.LENGTH_SHORT).show();
                                        linearLayout.setVisibility(View.VISIBLE);
                                    }
                                }
                            });
                }
            }
        });



    }
}
