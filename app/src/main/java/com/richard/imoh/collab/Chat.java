package com.richard.imoh.collab;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.richard.imoh.collab.Adapters.ChatAdapter;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Chat extends AppCompatActivity {
    RecyclerView recyclerView;
    ChatAdapter mChatAdpater;
    List<UserMessage> messages = new ArrayList<>();
    FirebaseDatabase mFirebaseDataBase;
    DatabaseReference mChatReference;
    FirebaseAuth firebaseAuth;
    EditText editText;
    Button sendBtn;
    String username;
    static final int DEFAULT_MSG_LENGTH_LIMIT = 1000;
    static final int RC_PHOTO_PICKER = 2;
    ChildEventListener mChildEventListerner;
    private FirebaseStorage mFireBaseStorage;
    private StorageReference chatPhotoRference;
    private ImageView mPhotoPicker;
    String myUserId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Log.d("recyclerview", "Got to oncreate");
        editText  = findViewById(R.id.edittext_chatbox);
        sendBtn = findViewById(R.id.button_chatbox_send);
        mPhotoPicker = findViewById(R.id.gallery_send_btn);
        String otherPersonId = "maduka";
        String timeStamp;
        String chatPath = username + otherPersonId;
        mFirebaseDataBase = FirebaseDatabase.getInstance();
        mFireBaseStorage = FirebaseStorage.getInstance();
        chatPhotoRference = mFireBaseStorage.getReference().child("chat-images");
        firebaseAuth = FirebaseAuth.getInstance();
        myUserId = firebaseAuth.getUid();
        mChatReference = mFirebaseDataBase.getReference().child("agents").child(myUserId);

        username = "ANONYMOUS";


//        Date date = new Date(location.getTime());
//        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(mContext);
//        mTimeText.setText("Time: " + dateFormat.format(date));
        recyclerView = findViewById(R.id.reyclerview_message_list);
        mChatAdpater = new ChatAdapter(getApplicationContext(), messages);
        Log.d("recyclerview", "Create chat adapter");
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        Log.d("recyclerview", "Finished Creating layout manager");
        recyclerView.setAdapter(mChatAdpater);
        Log.d("recyclerview", "Finish set recycler view to adapter");



        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0) {
                    sendBtn.setEnabled(true);
                } else {
                    sendBtn.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(DEFAULT_MSG_LENGTH_LIMIT)});

        mPhotoPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Fire an intent to show an image picker
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER);
            }
        });

       sendBtn.setOnClickListener(view -> {
           UserMessage userMessage = new UserMessage(username,"https://pbs.twimg.com/profile_images/723476945879633920/N59ePNGs_400x400.jpg","12:00",editText.getText().toString(),null);
           mChatReference.push().setValue(userMessage);
           editText.setText("");
       });
       attachDbReadListener();



    }
    void attachDbReadListener(){
        if(mChildEventListerner == null){

            mChildEventListerner = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    UserMessage userMessage = dataSnapshot.getValue(UserMessage.class);
                    messages.add(userMessage);
                    mChatAdpater.notifyDataSetChanged();
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };
            mChatReference.addChildEventListener(mChildEventListerner);
        }

    }
    void detachedDbReadListener(){
        if(mChildEventListerner != null){
            mChatReference.removeEventListener(mChildEventListerner);
        }
        mChildEventListerner = null;
    }

    @Override
    protected void onPause() {
        super.onPause();
        detachedDbReadListener();
        messages.clear();
    }

    @Override
    protected void onResume() {
        super.onResume();
        attachDbReadListener();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_PHOTO_PICKER && resultCode == RESULT_OK){
            Uri selectedImageUri = data.getData();
            final StorageReference storageReference = chatPhotoRference.child(selectedImageUri.getLastPathSegment());
            UploadTask uploadTask = storageReference.putFile(selectedImageUri);

            Task<Uri> task = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if(!task.isSuccessful()){
                        throw  task.getException();
                    }
                    return storageReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if(task.isSuccessful()){
                        Uri downloadUri = task.getResult();
                        UserMessage friendlyMessage = new UserMessage(username,null,"10:22",null,downloadUri.toString());
                        mChatReference.push().setValue(friendlyMessage);
                    }
                }
            });

        }
    }
}