package com.richard.imoh.collab.Activities;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.richard.imoh.collab.Pojo.User;
import com.richard.imoh.collab.R;
import com.richard.imoh.collab.Utils.FireBaseUtils;

public class EditProfile extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    EditText name,company,phoneNo,occupation,profBody;
    Spinner state,city;
    ImageView imageView;
    String editName,editCompany,editPhone,editLocation,editImage,editOccupation,editProfBody;
    User user;
    static final int EDIT_IMAGE_PICKER = 12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        firebaseDatabase = FireBaseUtils.getDatabase();
        firebaseAuth = FirebaseAuth.getInstance();
        String userId = firebaseAuth.getUid();
        databaseReference = firebaseDatabase.getReference().child("agents").child(userId);
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference().child("profile_menu");
        city = findViewById(R.id.edit_city);
        state = findViewById(R.id.edit_state);
        name = findViewById(R.id.edit_name);
        company = findViewById(R.id.edit_company);
        phoneNo = findViewById(R.id.edit_phone);
        profBody = findViewById(R.id.edit_profBody);
        occupation = findViewById(R.id.edit_occupation);
        imageView = findViewById(R.id.edit_image);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Fire an intent to show an image picker
                Log.d("dayo","Got to the onclick listerner method");
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), EDIT_IMAGE_PICKER);
            }
        });
        listenToInfoDb();



    }

    void listenToInfoDb(){
        ValueEventListener childEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                if(user!=null){
                    editName = user.getFullName();
                    editCompany = user.getCompany();
                    editLocation = user.getLocation();
                    editPhone = user.getPhone();
                    editImage = user.getImage();
                    editProfBody = user.getProfessionalQalification();
                    editOccupation = user.getOccupation();
                    name.setHint(editName);
                    company.setHint(editCompany);
                    phoneNo.setHint(editPhone);
                    occupation.setHint(user.getOccupation());
                    profBody.setHint(user.getProfessionalQalification());
                    Glide.with(imageView.getContext())
                            .load(editImage)
                            .apply(RequestOptions.circleCropTransform())
                            .apply(RequestOptions.placeholderOf(R.drawable.placeholder))
                            .into(imageView);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        databaseReference.child("info").addValueEventListener(childEventListener);
    }
    public void updateProfile(View view){
        if(!TextUtils.isEmpty(name.getText())){
            editName = name.getText().toString();
        }
        if(!TextUtils.isEmpty(company.getText())){
            editCompany = company.getText().toString();
        }
        if(!TextUtils.isEmpty(phoneNo.getText())){
            editPhone = company.getText().toString();
        }
        if(!TextUtils.isEmpty(profBody.getText())){
            editProfBody = profBody.getText().toString();
        }
        if(!TextUtils.isEmpty(occupation.getText())){
            editOccupation = occupation.getText().toString();
        }
        else {
            User newUser = new User(editName,editImage,user.getEmail(),editPhone,editOccupation,editCompany,editProfBody,user.getGender(),user.getProfileDescription(),editLocation,user.getUserName(),user.getuId());
            databaseReference.child("info").setValue(newUser, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                    finish();
                }
            });

        }
    }

    public void cancel(View view){
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == EDIT_IMAGE_PICKER && resultCode == RESULT_OK){
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
                        editImage = downloadUri.toString();
                        Glide.with(imageView.getContext())
                                .load(editImage)
                                .apply(RequestOptions.circleCropTransform())
                                .apply(RequestOptions.placeholderOf(R.drawable.placeholder))
                                .into(imageView);


                    }
                }
            });

        }
    }
}
