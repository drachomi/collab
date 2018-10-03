package com.richard.imoh.collab.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.richard.imoh.collab.Adapters.OnBoardAdapter;
import com.richard.imoh.collab.R;
import com.richard.imoh.collab.Utils.FireBaseUtils;
import com.richard.imoh.collab.Utils.Repository;

public class BlankActivity extends AppCompatActivity {
    Repository repo;
    FirebaseAuth firebaseAuth;
    ViewPager viewPager;
    OnBoardAdapter onBoardAdapter;
    TextView[] mDots;
    LinearLayout linearLayout;
    Button previous,next;
    private int currentPage;
    static String COMPLETED_ONBOARDING_PREF_NAME = "onBoard";
//    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blank);
        firebaseAuth = FireBaseUtils.getFirebaseAuth();
        linearLayout = findViewById(R.id.onboard_linear);



//This activity should execute just once, The following code ensures that. The editor is added in the repository class

        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        // Check if we need to display our OnboardingFragment
        if (!sharedPreferences.getBoolean(
                BlankActivity.COMPLETED_ONBOARDING_PREF_NAME, false)) {
            // The user hasn't seen the OnboardingFragment yet, so show it
            onBoard();
        }else {
            if(firebaseAuth.getCurrentUser()==null){
                startActivity(new Intent(BlankActivity.this,Login.class));
                finish();
            }
            else {
                getAllConnection();
                startActivity(new Intent(BlankActivity.this,MainActivity.class));
                finish();
            }
        }



    }

    void initViewPager(){
        onBoardAdapter = new OnBoardAdapter(this);
        viewPager = findViewById(R.id.viewpager);

        viewPager.setAdapter(onBoardAdapter);
        viewPager.addOnPageChangeListener(viewListerner);


    }
    void addDots(int position){
        mDots = new TextView[4];
        linearLayout.removeAllViews();
        for (int i=0; i<mDots.length;i++){
            mDots[i]= new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.colorSecondaryText));
            linearLayout.addView(mDots[i]);
        }

        if(mDots.length>0){
            mDots[position].setTextColor(getResources().getColor(R.color.g_white));
        }
    }

    ViewPager.OnPageChangeListener viewListerner = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDots(position);
            currentPage = position;
            if(position==0){
                next.setEnabled(true);
                previous.setEnabled(false);
                previous.setVisibility(View.INVISIBLE);
                next.setText("Next");
                previous.setText("");
            } else if(position == mDots.length-1) {
                next.setEnabled(true);
                previous.setEnabled(true);
                previous.setVisibility(View.VISIBLE);
                next.setText("Finish");
                previous.setText("Previous");
            }
            else {
                next.setEnabled(true);
                previous.setEnabled(true);
                previous.setVisibility(View.VISIBLE);
                next.setText("Next");
                previous.setText("Previous");
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    public void onBoardPrev(View view){
        viewPager.setCurrentItem(currentPage-1);
    }
    public void onBoardNext(View view){
        if (currentPage != mDots.length-1){
            viewPager.setCurrentItem(currentPage + 1);
        }else {
            SharedPreferences.Editor sharedPreferencesEditor =
                    PreferenceManager.getDefaultSharedPreferences(BlankActivity.this).edit();
            sharedPreferencesEditor.putBoolean(
                    COMPLETED_ONBOARDING_PREF_NAME, true);
            sharedPreferencesEditor.apply();

            startActivity(new Intent(BlankActivity.this,Login.class));
        }

    }
    void onBoard(){
        addDots(0);
        initViewPager();
        previous = findViewById(R.id.onboard_previous_btn);
        next = findViewById(R.id.onboard_next_btn);
    }
    void getAllConnection(){
        repo = new Repository(this);
        repo.fetchConnection();
        repo.getCurrentUser();
        repo.specificUser("yeeba");
    }

}
