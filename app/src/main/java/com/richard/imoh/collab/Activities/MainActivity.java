package com.richard.imoh.collab.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.richard.imoh.collab.Fragments.PropertyFeed;
import com.richard.imoh.collab.Fragments.RequestFeeds;
import com.richard.imoh.collab.Pojo.Property;
import com.richard.imoh.collab.Pojo.User;
import com.richard.imoh.collab.Property.AddProperty;
import com.richard.imoh.collab.R;
import com.richard.imoh.collab.Request.AddRequest;
import com.richard.imoh.collab.Request.Request;
import com.richard.imoh.collab.Request.RequestActivity;
import com.richard.imoh.collab.Utils.FireBaseUtils;
import com.richard.imoh.collab.Utils.Repository;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    DatabaseReference propertyRef;
    DatabaseReference requestRef;
    public static final String MyPREFERENCES = "MyPrefs" ;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    Repository repo;

    ValueEventListener requestListerner;
    String location;
    String state = "Lagos";
    String city = "Festac";
    List<Request> mRequests = new ArrayList<>();
   // PropertyFeed propertyFeedFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolboxUtils();
        ChildEventListener childEventListener;
        repo = new Repository(this);

        firebaseDatabase = FirebaseDatabase.getInstance();
        FireBaseUtils.getDatabase();
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()==null){
            startActivity(new Intent(MainActivity.this,Login.class));
        }
        else {
            String userId = firebaseAuth.getUid();
            Log.d("mere","User id is "+userId);
            databaseReference = firebaseDatabase.getReference().child("agents").child(userId).child("info");
            SharedPreferences sharedPref = getSharedPreferences(MyPREFERENCES,Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            childEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
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
            databaseReference.addChildEventListener(childEventListener);
        }

        //TODO Change location to match what user select and delete this line
        location = "Badagry";

       // fetchFromRepo();
        requestRef = firebaseDatabase.getReference().child("requests").child(location);

        requestListerner = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Request request = dataSnapshot.getValue(Request.class);
                mRequests.add(request);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        requestRef.addValueEventListener(requestListerner);








    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            //TODO Add a dialog for selecting location and features
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            startActivity(new Intent(MainActivity.this,AgentActivity.class));
        } else if (id == R.id.nav_connection) {
            startActivity(new Intent(MainActivity.this,ConnectionList.class));
        } else if (id == R.id.nav_post_property) {
            startActivity(new Intent(MainActivity.this,AddProperty.class));
        } else if (id == R.id.nav_post_request) {
            startActivity(new Intent(MainActivity.this,AddRequest.class));
        } else if (id == R.id.nav_chats) {
            startActivity(new Intent(MainActivity.this,ChatList.class));
        }
        else if (id == R.id.nav_sign_out){
            signOut();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    void toolboxUtils(){
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new PropertyFeed(), "Properties");
        adapter.addFragment(new RequestFeeds(), "Requests");

        viewPager.setAdapter(adapter);

    }
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
    void signOut(){
        firebaseAuth.signOut();
        startActivity(new Intent(MainActivity.this,Login.class));

    }
    void fetchFromRepo(){
        repo.fetchProperty(state,city);
    }


}
