package com.richard.imoh.collab;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.richard.imoh.collab.Adapters.FeedAdapter;

import java.util.ArrayList;
import java.util.List;

public class FeedPage extends AppCompatActivity {
    FeedAdapter mFeedAdapter;
    List<Property>mProperty = new ArrayList<>();
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_page);
        mDrawerLayout = findViewById(R.id.drawwerLayout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        //mDrawerLayout.closeDrawers();

        NavigationView navigationView = findViewById(R.id.navBar);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
        RecyclerView recyclerView = findViewById(R.id.feed_recycler_view);
        mFeedAdapter = new FeedAdapter(mProperty);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mFeedAdapter);
     startActivity(new Intent(FeedPage.this,Follow.class));

        addProp();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();
        if(id==android.R.id.home){
            mDrawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        if (id == R.id.feed) {
            startActivity(new Intent(this,FeedPage.class));
            return true;
        } else if (id == R.id.profile) {
            startActivity(new Intent(this,AgentActivity.class));
            return true;
        } else if (id == R.id.login) {
            startActivity(new Intent(this,Login.class));
            return true;

        } else if (id == R.id.reg) {
            startActivity(new Intent(this,Registration.class));
            return true;

        } else if (id == R.id.details) {
            startActivity(new Intent(this,PropertyDetails.class));
            return true;

        } else if (id == R.id.follows) {
            startActivity(new Intent(this,Follow.class));
            return true;
        }


        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void addProp(){
        Property property = new Property("http://www.interior-fun.com/wp-content/uploads/2018/01/Fresh-Amazing-Beautiful-House-Design-Pictures-hj.jpg","5 Bedroom flat in Lekki","23,000,00","imoh","Surulere Aguda","1","2");
        mProperty.add(property);
        property = new Property("http://ofirsrl.com/wp-content/uploads/2018/03/pictures-beautiful-houses-modern-architecture-beautiful-house-designs-1324-free-app-for-drawing-house-plans.jpg","1 room flat in Magodo","10,000,00","imoh","Bariga Lagos","2","1");
        mProperty.add(property);
        property = new Property("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRwKCVtt1fK5orsgthOF3gocbyHK-q4KUy8pFHYl5EZZhwWJ5Uu","1 room flat in Magodo","10,000,00","imoh","Bariga Lagos","3","5");
        mProperty.add(property);
        property = new Property("http://www.interior-fun.com/wp-content/uploads/2018/01/Fresh-Amazing-Beautiful-House-Design-Pictures-hj.jpg","5 Bedroom flat in Lekki","23,000,00","imoh","Surulere Aguda","1","2");
        mProperty.add(property);
        property = new Property("http://ofirsrl.com/wp-content/uploads/2018/03/pictures-beautiful-houses-modern-architecture-beautiful-house-designs-1324-free-app-for-drawing-house-plans.jpg","1 room flat in Magodo","10,000,00","imoh","Bariga Lagos","2","1");
        mProperty.add(property);
        property = new Property("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRwKCVtt1fK5orsgthOF3gocbyHK-q4KUy8pFHYl5EZZhwWJ5Uu","1 room flat in Magodo","10,000,00","imoh","Bariga Lagos","3","5");
        mProperty.add(property);
        property = new Property("http://www.interior-fun.com/wp-content/uploads/2018/01/Fresh-Amazing-Beautiful-House-Design-Pictures-hj.jpg","5 Bedroom flat in Lekki","23,000,00","imoh","Surulere Aguda","1","2");
        mProperty.add(property);
        property = new Property("http://ofirsrl.com/wp-content/uploads/2018/03/pictures-beautiful-houses-modern-architecture-beautiful-house-designs-1324-free-app-for-drawing-house-plans.jpg","1 room flat in Magodo","10,000,00","imoh","Bariga Lagos","2","1");
        mProperty.add(property);
        property = new Property("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRwKCVtt1fK5orsgthOF3gocbyHK-q4KUy8pFHYl5EZZhwWJ5Uu","1 room flat in Magodo","10,000,00","imoh","Bariga Lagos","3","5");
        mProperty.add(property);
        property = new Property("http://www.interior-fun.com/wp-content/uploads/2018/01/Fresh-Amazing-Beautiful-House-Design-Pictures-hj.jpg","5 Bedroom flat in Lekki","23,000,00","imoh","Surulere Aguda","1","2");
        mProperty.add(property);
        property = new Property("http://ofirsrl.com/wp-content/uploads/2018/03/pictures-beautiful-houses-modern-architecture-beautiful-house-designs-1324-free-app-for-drawing-house-plans.jpg","1 room flat in Magodo","10,000,00","imoh","Bariga Lagos","2","1");
        mProperty.add(property);
        property = new Property("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRwKCVtt1fK5orsgthOF3gocbyHK-q4KUy8pFHYl5EZZhwWJ5Uu","1 room flat in Magodo","10,000,00","imoh","Bariga Lagos","3","5");
        mProperty.add(property);
        property = new Property("http://www.interior-fun.com/wp-content/uploads/2018/01/Fresh-Amazing-Beautiful-House-Design-Pictures-hj.jpg","5 Bedroom flat in Lekki","23,000,00","imoh","Surulere Aguda","1","2");
        mProperty.add(property);
        property = new Property("http://ofirsrl.com/wp-content/uploads/2018/03/pictures-beautiful-houses-modern-architecture-beautiful-house-designs-1324-free-app-for-drawing-house-plans.jpg","1 room flat in Magodo","10,000,00","imoh","Bariga Lagos","2","1");
        mProperty.add(property);
        property = new Property("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRwKCVtt1fK5orsgthOF3gocbyHK-q4KUy8pFHYl5EZZhwWJ5Uu","1 room flat in Magodo","10,000,00","imoh","Bariga Lagos","3","5");
        mProperty.add(property);
        property = new Property("http://www.interior-fun.com/wp-content/uploads/2018/01/Fresh-Amazing-Beautiful-House-Design-Pictures-hj.jpg","5 Bedroom flat in Lekki","23,000,00","imoh","Surulere Aguda","1","2");
        mProperty.add(property);
        property = new Property("http://ofirsrl.com/wp-content/uploads/2018/03/pictures-beautiful-houses-modern-architecture-beautiful-house-designs-1324-free-app-for-drawing-house-plans.jpg","1 room flat in Magodo","10,000,00","imoh","Bariga Lagos","2","1");
        mProperty.add(property);
        property = new Property("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRwKCVtt1fK5orsgthOF3gocbyHK-q4KUy8pFHYl5EZZhwWJ5Uu","1 room flat in Magodo","10,000,00","imoh","Bariga Lagos","3","5");
        mProperty.add(property);
        property = new Property("http://www.interior-fun.com/wp-content/uploads/2018/01/Fresh-Amazing-Beautiful-House-Design-Pictures-hj.jpg","5 Bedroom flat in Lekki","23,000,00","imoh","Surulere Aguda","1","2");
        mProperty.add(property);
        property = new Property("http://ofirsrl.com/wp-content/uploads/2018/03/pictures-beautiful-houses-modern-architecture-beautiful-house-designs-1324-free-app-for-drawing-house-plans.jpg","1 room flat in Magodo","10,000,00","imoh","Bariga Lagos","2","1");
        mProperty.add(property);
        property = new Property("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRwKCVtt1fK5orsgthOF3gocbyHK-q4KUy8pFHYl5EZZhwWJ5Uu","1 room flat in Magodo","10,000,00","imoh","Bariga Lagos","3","5");
        mProperty.add(property);
        property = new Property("http://www.interior-fun.com/wp-content/uploads/2018/01/Fresh-Amazing-Beautiful-House-Design-Pictures-hj.jpg","5 Bedroom flat in Lekki","23,000,00","imoh","Surulere Aguda","1","2");
        mProperty.add(property);
        property = new Property("http://ofirsrl.com/wp-content/uploads/2018/03/pictures-beautiful-houses-modern-architecture-beautiful-house-designs-1324-free-app-for-drawing-house-plans.jpg","1 room flat in Magodo","10,000,00","imoh","Bariga Lagos","2","1");
        mProperty.add(property);
        property = new Property("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRwKCVtt1fK5orsgthOF3gocbyHK-q4KUy8pFHYl5EZZhwWJ5Uu","1 room flat in Magodo","10,000,00","imoh","Bariga Lagos","3","5");
        mProperty.add(property);
        property = new Property("http://www.interior-fun.com/wp-content/uploads/2018/01/Fresh-Amazing-Beautiful-House-Design-Pictures-hj.jpg","5 Bedroom flat in Lekki","23,000,00","imoh","Surulere Aguda","1","2");
        mProperty.add(property);
        property = new Property("http://ofirsrl.com/wp-content/uploads/2018/03/pictures-beautiful-houses-modern-architecture-beautiful-house-designs-1324-free-app-for-drawing-house-plans.jpg","1 room flat in Magodo","10,000,00","imoh","Bariga Lagos","2","1");
        mProperty.add(property);
        property = new Property("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRwKCVtt1fK5orsgthOF3gocbyHK-q4KUy8pFHYl5EZZhwWJ5Uu","1 room flat in Magodo","10,000,00","imoh","Bariga Lagos","3","5");
        mProperty.add(property);
        property = new Property("http://www.interior-fun.com/wp-content/uploads/2018/01/Fresh-Amazing-Beautiful-House-Design-Pictures-hj.jpg","5 Bedroom flat in Lekki","23,000,00","imoh","Surulere Aguda","1","2");
        mProperty.add(property);
        property = new Property("http://ofirsrl.com/wp-content/uploads/2018/03/pictures-beautiful-houses-modern-architecture-beautiful-house-designs-1324-free-app-for-drawing-house-plans.jpg","1 room flat in Magodo","10,000,00","imoh","Bariga Lagos","2","1");
        mProperty.add(property);
        property = new Property("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRwKCVtt1fK5orsgthOF3gocbyHK-q4KUy8pFHYl5EZZhwWJ5Uu","1 room flat in Magodo","10,000,00","imoh","Bariga Lagos","3","5");
        mProperty.add(property);
        property = new Property("http://www.interior-fun.com/wp-content/uploads/2018/01/Fresh-Amazing-Beautiful-House-Design-Pictures-hj.jpg","5 Bedroom flat in Lekki","23,000,00","imoh","Surulere Aguda","1","2");
        mProperty.add(property);
        property = new Property("http://ofirsrl.com/wp-content/uploads/2018/03/pictures-beautiful-houses-modern-architecture-beautiful-house-designs-1324-free-app-for-drawing-house-plans.jpg","1 room flat in Magodo","10,000,00","imoh","Bariga Lagos","2","1");
        mProperty.add(property);
        property = new Property("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRwKCVtt1fK5orsgthOF3gocbyHK-q4KUy8pFHYl5EZZhwWJ5Uu","1 room flat in Magodo","10,000,00","imoh","Bariga Lagos","3","5");
        mProperty.add(property);
        property = new Property("http://www.interior-fun.com/wp-content/uploads/2018/01/Fresh-Amazing-Beautiful-House-Design-Pictures-hj.jpg","5 Bedroom flat in Lekki","23,000,00","imoh","Surulere Aguda","1","2");
        mProperty.add(property);
        property = new Property("http://ofirsrl.com/wp-content/uploads/2018/03/pictures-beautiful-houses-modern-architecture-beautiful-house-designs-1324-free-app-for-drawing-house-plans.jpg","1 room flat in Magodo","10,000,00","imoh","Bariga Lagos","2","1");
        mProperty.add(property);
        property = new Property("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRwKCVtt1fK5orsgthOF3gocbyHK-q4KUy8pFHYl5EZZhwWJ5Uu","1 room flat in Magodo","10,000,00","imoh","Bariga Lagos","3","5");
        mProperty.add(property);
        property = new Property("http://www.interior-fun.com/wp-content/uploads/2018/01/Fresh-Amazing-Beautiful-House-Design-Pictures-hj.jpg","5 Bedroom flat in Lekki","23,000,00","imoh","Surulere Aguda","1","2");
        mProperty.add(property);
        property = new Property("http://ofirsrl.com/wp-content/uploads/2018/03/pictures-beautiful-houses-modern-architecture-beautiful-house-designs-1324-free-app-for-drawing-house-plans.jpg","1 room flat in Magodo","10,000,00","imoh","Bariga Lagos","2","1");
        mProperty.add(property);
        property = new Property("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRwKCVtt1fK5orsgthOF3gocbyHK-q4KUy8pFHYl5EZZhwWJ5Uu","1 room flat in Magodo","10,000,00","imoh","Bariga Lagos","3","5");
        mProperty.add(property);
        property = new Property("http://www.interior-fun.com/wp-content/uploads/2018/01/Fresh-Amazing-Beautiful-House-Design-Pictures-hj.jpg","5 Bedroom flat in Lekki","23,000,00","imoh","Surulere Aguda","1","2");
        mProperty.add(property);
        property = new Property("http://ofirsrl.com/wp-content/uploads/2018/03/pictures-beautiful-houses-modern-architecture-beautiful-house-designs-1324-free-app-for-drawing-house-plans.jpg","1 room flat in Magodo","10,000,00","imoh","Bariga Lagos","2","1");
        mProperty.add(property);
        property = new Property("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRwKCVtt1fK5orsgthOF3gocbyHK-q4KUy8pFHYl5EZZhwWJ5Uu","1 room flat in Magodo","10,000,00","imoh","Bariga Lagos","3","5");
        mProperty.add(property);
        property = new Property("http://www.interior-fun.com/wp-content/uploads/2018/01/Fresh-Amazing-Beautiful-House-Design-Pictures-hj.jpg","5 Bedroom flat in Lekki","23,000,00","imoh","Surulere Aguda","1","2");
        mProperty.add(property);
        property = new Property("http://ofirsrl.com/wp-content/uploads/2018/03/pictures-beautiful-houses-modern-architecture-beautiful-house-designs-1324-free-app-for-drawing-house-plans.jpg","1 room flat in Magodo","10,000,00","imoh","Bariga Lagos","2","1");
        mProperty.add(property);
        property = new Property("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRwKCVtt1fK5orsgthOF3gocbyHK-q4KUy8pFHYl5EZZhwWJ5Uu","1 room flat in Magodo","10,000,00","imoh","Bariga Lagos","3","5");
        mProperty.add(property);



        mFeedAdapter.notifyDataSetChanged();
    }
}
