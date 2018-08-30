package com.richard.imoh.collab.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.richard.imoh.collab.DBUtils.Connection;
import com.richard.imoh.collab.DBUtils.ConnectionDao;
import com.richard.imoh.collab.Pojo.Property;
import com.richard.imoh.collab.R;
import com.richard.imoh.collab.Utils.FireBaseUtils;
import com.richard.imoh.collab.Utils.Repository;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class PropertyDetails extends AppCompatActivity {
    CarouselView imageSlide;
    ImageListener imageListener;
    List<Bitmap> imageToDisplay = new ArrayList<>();
    List<String> imageString = new ArrayList<>();
    Property property;
    TextView propTitle, propLocation, propPrice, propPriceDetail, bedroomNo, availableFor, datePosted, plotNo, addInfo;
    LinearLayout priceLayout, bedromLayout, urgencyLinear, plotLayout;
    String propId;
    String userId;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseFirestore firestore = FireBaseUtils.getFireStore();
    FirebaseDatabase firebaseDatabase = FireBaseUtils.getDatabase();
    List<Connection>connectionObj = new ArrayList<>();
    Repository repository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle extras = getIntent().getExtras();
        propId = extras.getString("propId");
        repository = new Repository(this);
        connectionObj = repository.getAllConnection();


        initView();
        fetchProperty();
    }


    private void initView() {
        //Carousel Slide
        imageSlide = findViewById(R.id.carouselView);

        //Linear Layouts
        priceLayout = findViewById(R.id.prop_details_linear_price);
        bedromLayout = findViewById(R.id.prop_details_linear_bedroom);
        urgencyLinear = findViewById(R.id.prop_details_linear_urgency);
        plotLayout = findViewById(R.id.prop_details_linear_plotNo);

        //TextViews
        propTitle = findViewById(R.id.prop_details_title);
        propLocation = findViewById(R.id.prop_details_location);
        propPrice = findViewById(R.id.prop_details_price);
        propPriceDetail = findViewById(R.id.prop_details_price_details);
        bedroomNo = findViewById(R.id.prop_details_roomNo);
        availableFor = findViewById(R.id.prop_details_availablefor);
        datePosted = findViewById(R.id.prop_details_date_posted);
        plotNo = findViewById(R.id.prop_details_plotNo);
        addInfo = findViewById(R.id.additional_info);
    }

    void displayOnView(Property property) {
        imageString.add(property.getPropertyImage1());
        imageString.add(property.getPropertyImage2());
        imageString.add(property.getPropertyImage3());
        String kind = property.getPropertyKind();
        if (kind.equals("Commercial")) {
            bedromLayout.setVisibility(View.GONE);

        }
        if (kind.equals("Residential")) {
            bedroomNo.setText(property.getRoomNo());
            availableFor.setText(property.getSuitableFor());
        }
        if (property.getPropertyType().equals("Land")) {
            plotNo.setText(property.getPlotNo());
        }
        if (!property.getPropertyType().equals("Land")) {
            plotLayout.setVisibility(View.GONE);
        }
        propTitle.setText(property.getPropertyType());
        propLocation.setText(property.getState() + ", " + property.getCity());
        propPrice.setText(property.getPrice());
        propPriceDetail.setText(property.getPrice() + "(Negotiable)");
        datePosted.setText(property.getTime());
        addInfo.setText(property.getAdditionalInfo());
        addImage();
    }

    void addImage() {
        for (int i = 0; i < imageString.size(); i++) {
            Glide.with(this)
                    .asBitmap()
                    .load(imageString.get(i))
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            imageToDisplay.add(resource);
                            imageSlide.setImageListener(imageListener);
                            imageSlide.setPageCount(imageToDisplay.size());
                        }
                    });

            imageListener = (position, imageView) -> imageView.setImageBitmap(imageToDisplay.get(position));
        }
    }

    void fetchProperty() {
        firestore.collection("properties")
                .whereEqualTo("propId", propId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot querry = task.getResult();
                            for (DocumentSnapshot document : querry.getDocuments()) {
                                Property property = document.toObject(Property.class);
                                if (property != null) {
                                    Log.d("querry", property.getAgentName());

                                    displayOnView(property);
                                }
                            }
                        } else {
                            Log.d("querry", "Querry not successful");
                        }
                    }
                });
    }

    public void connectWithAgent(View view) {
        //TODO add a condition that compares the user to DB;
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String chatRef = timestamp.getTime() + userId + firebaseAuth.getUid();
        int counter = -1;
        for (Connection conn : connectionObj){
            if(conn.Uid.equals(userId)){
                counter++;
                //User is already a connection;
                chatRef = conn.chatRef;
                Intent intent = new Intent(PropertyDetails.this,Chat.class);
                intent.putExtra("chatRef",chatRef);
                startActivity(intent);
            }
        }
        if(counter == -1){
            firebaseDatabase.getReference().child("agents").child(userId).child("chats").child(firebaseAuth.getUid()).setValue(chatRef);
            firebaseDatabase.getReference().child("agents").child(firebaseAuth.getUid()).child("chats").child(userId).setValue(chatRef);
            Intent intent = new Intent(PropertyDetails.this,Chat.class);
            intent.putExtra("chatRef",chatRef);
            startActivity(intent);
        }
    }


}

