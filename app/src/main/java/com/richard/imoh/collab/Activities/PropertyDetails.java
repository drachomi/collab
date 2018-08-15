package com.richard.imoh.collab.Activities;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.richard.imoh.collab.Pojo.Property;
import com.richard.imoh.collab.R;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;
import java.util.List;

public class PropertyDetails extends AppCompatActivity {
    CarouselView carouselView;
    ImageListener imageListener;
    List<Bitmap>imageToDisplay = new ArrayList<>();

    //Bitmap[] imageToDisplay = new Bitmap[3];
    Property property;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        carouselView = findViewById(R.id.carouselView);

        List<String>imageString = new ArrayList<>();
        imageString.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRwKCVtt1fK5orsgthOF3gocbyHK-q4KUy8pFHYl5EZZhwWJ5Uu");
        imageString.add("http://ofirsrl.com/wp-content/uploads/2018/03/pictures-beautiful-houses-modern-architecture-beautiful-house-designs-1324-free-app-for-drawing-house-plans.jpg");
        imageString.add("http://www.interior-fun.com/wp-content/uploads/2018/01/Fresh-Amazing-Beautiful-House-Design-Pictures-hj.jpg");
        imageString.add("https://s-ec.bstatic.com/images/hotel/max1280x900/101/101430248.jpg");
        imageString.add("http://rodrepel.com/blog/wp-content/uploads/2017/04/HRH01.jpg");
        imageString.add("https://www.safarihotelsnamibia.com/wp-content/uploads/2014/11/Safari-Court-Hotel-Pool.jpg");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        for (int i =0; i<imageString.size(); i++) {

            Glide.with(this)
                    .asBitmap()
                    .load(imageString.get(i))
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            imageToDisplay.add(resource);
                            carouselView.setImageListener(imageListener);
                            carouselView.setPageCount(imageToDisplay.size());
                        }
                    });

            imageListener = (position, imageView) -> imageView.setImageBitmap(imageToDisplay.get(position));
        }
    }

    void addImage(){

    }

}
