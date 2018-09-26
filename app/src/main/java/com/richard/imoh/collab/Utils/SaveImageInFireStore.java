package com.richard.imoh.collab.Utils;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.richard.imoh.collab.Pojo.Property;
import com.richard.imoh.collab.R;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Timestamp;


public class SaveImageInFireStore {
    Property mProperty;
    String img1,img2,img3;
    FirebaseAuth firebaseAuth = FireBaseUtils.getFirebaseAuth();
    FirebaseStorage firebaseStorage = FireBaseUtils.getFirebaseStorage();
   StorageReference storageReference = firebaseStorage.getReference().child("propertyImage");

    public void saveImage(Property property){
        mProperty = property;

        //I need to save the images one by one so that i can retrive the download uri specifically
        //The purpose of the index is to help me know which is which
        save(Uri.parse(mProperty.getPropertyImage1()),1);

    }

    private void save(Uri prop,int a){
        final StorageReference ref = storageReference.child(firebaseAuth.getUid()).child(getTime()+".jpeg");
        InputStream stream = null;
        try {
            stream = new FileInputStream(prop.toString());
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        }
        UploadTask uploadTask = ref.putStream(stream);
        Task<Uri> task = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return ref.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();

                    //Perform a check
                    //If the current image is 1 it saves it as image one and does a recursive call to fetch image 2
                    //If current image is 3, it should leave the method and save the whole data
                    if(a==1){
                        img1 = downloadUri.toString();
                        save(Uri.parse(mProperty.getPropertyImage2()),2);
                    }
                    if(a==2){
                        img2 = downloadUri.toString();
                        save(Uri.parse(mProperty.getPropertyImage3()),3);
                    }
                    if(a==3){
                        img3 = downloadUri.toString();
                        sendToFireStore();
                    }

                }
            }
        });
    }

    private void sendToFireStore(){
        SavePropertyInFireStore store = new SavePropertyInFireStore();
        Property property = new Property(mProperty.getAgentName(),mProperty.getAgentDp(),img1,img2,img3,mProperty.getPrice(),mProperty.getState(),mProperty.getCity(),mProperty.getRoomNo(),mProperty.getPlotNo(),mProperty.getSuitableFor(),mProperty.getPropertyType(),mProperty.getPropertyKind(),mProperty.getLetType(),mProperty.getAdditionalInfo(),mProperty.getUserId(),mProperty.getTime(),mProperty.getPropId());
        store.saveToAgentProp(property);
        store.saveProp(property);
    }
    private long getTime() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return timestamp.getTime();

    }
}
