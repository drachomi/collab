package com.richard.imoh.collab.Activities;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.richard.imoh.collab.Adapters.ChatAdapter;
import com.richard.imoh.collab.Pojo.ChatMeta;
import com.richard.imoh.collab.Pojo.UserMessage;
import com.richard.imoh.collab.R;
import com.richard.imoh.collab.Utils.FireBaseUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
    String time;
    String isRead;

    static final int DEFAULT_MSG_LENGTH_LIMIT = 1000;
    static final int RC_PHOTO_PICKER = 2;
    ChildEventListener mChildEventListerner;
    private FirebaseStorage mFireBaseStorage;
    private StorageReference chatPhotoRference;
    String myUserId;
    int messageCount;
    String personUserId;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Toolbar myToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        editText  = findViewById(R.id.edittext_chatbox);
        sendBtn = findViewById(R.id.button_chatbox_send);
        mFirebaseDataBase = FireBaseUtils.getDatabase();
        mFireBaseStorage = FirebaseStorage.getInstance();
        chatPhotoRference = mFireBaseStorage.getReference().child("chat-images");
        firebaseAuth = FirebaseAuth.getInstance();
        myUserId = firebaseAuth.getUid();
        username = firebaseAuth.getCurrentUser().getDisplayName();
        messageCount =0;
        Bundle bundle = getIntent().getExtras();
        String chatRef = bundle.getString("chatRef");
        String personName = bundle.getString("username");
        personUserId = bundle.getString("personId");
        getSupportActionBar().setTitle(personName);
        mChatReference = mFirebaseDataBase.getReference().child("chats").child(chatRef);
        recyclerView = findViewById(R.id.reyclerview_message_list);
        mChatAdpater = new ChatAdapter(getApplicationContext(), messages);
        Log.d("recyclerview", "Create chat adapter");
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        Log.d("recyclerview", "Finished Creating layout manager");
        recyclerView.setAdapter(mChatAdpater);
        Log.d("recyclerview", "Finish set recycler view to adapter");

        //Indicate that i have read  the messages in this chats
        mChatReference.child(myUserId+"unread").setValue(0);


        DateFormat df = new SimpleDateFormat("h:mm a");
         time = df.format(Calendar.getInstance().getTime());

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

       sendBtn.setOnClickListener(view -> {
           UserMessage userMessage = new UserMessage(username,null,time,editText.getText().toString(),null,firebaseAuth.getUid());
           mChatReference.child("messages").push().setValue(userMessage);
           messageCount++;
           ChatMeta chatMeta = new ChatMeta(editText.getText().toString(),time,messageCount,firebaseAuth.getUid());
           mChatReference.child("chatMeta").setValue(chatMeta);

           //Increment message count for the recipient
           mChatReference.child(personUserId+"unread").setValue(messageCount);
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
            mChatReference.child("messages").addChildEventListener(mChildEventListerner);
        }

    }
    void detachedDbReadListener(){
        if(mChildEventListerner != null){
            mChatReference.child("messages").removeEventListener(mChildEventListerner);
        }
        mChildEventListerner = null;
    }

    @Override
    protected void onPause() {
        super.onPause();
        detachedDbReadListener();
        mChatReference.child(myUserId+"unread").setValue(0);
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

        if(resultCode == RESULT_OK){
            Uri selectedImageUri = data.getData();
            switch (requestCode){
                case RC_PHOTO_PICKER:
                    sentItemToServer(selectedImageUri,"Image");
                default:
                    sentItemToServer(selectedImageUri,"Image");
            }


        }
    }


    void sentItemToServer(Uri data,String caller){
        final StorageReference storageReference = chatPhotoRference.child(data.getLastPathSegment());
        UploadTask uploadTask = storageReference.putFile(data);

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
                    UserMessage friendlyMessage = new UserMessage(username,null,time,null,downloadUri.toString(),firebaseAuth.getUid());
                    mChatReference.child("messages").push().setValue(friendlyMessage);
                    messageCount++;
                    ChatMeta chatMeta = new ChatMeta("Image",time,messageCount,firebaseAuth.getUid());

                    //Increment message count for the recipient
                    mChatReference.child(personUserId+"unread").setValue(messageCount);

                    mChatReference.child("chatMeta").setValue(chatMeta);

                }
            }
        });

    }

    public void getPdf(View view){
        Toast.makeText(this,"We are working hard to make this feature available soon",Toast.LENGTH_LONG).show();
//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//        intent.setType("application/pdf");
//        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
//        startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER);

    }
    public void getMsword(View view){
        Toast.makeText(this,"We are working hard to make this feature available soon",Toast.LENGTH_LONG).show();
//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//        intent.setType("application/msword");
//        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
//        startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER);
    }
    public void getCamera(View view){
        Toast.makeText(this,"We are working hard to make this feature availale soon",Toast.LENGTH_LONG).show();

    }
    public void getGallery(View view){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/jpeg");
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER);

    }
    public void getMic(View view){
        Toast.makeText(this,"We are working hard to make this feature available soon",Toast.LENGTH_LONG).show();
    }
    public void getVideo(View view){
        Toast.makeText(this,"We are working hard to make this feature available soon",Toast.LENGTH_LONG).show();
    }
}