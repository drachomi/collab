package com.richard.imoh.collab.Request;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.richard.imoh.collab.R;

import java.util.ArrayList;
import java.util.List;

public class RequestActivity extends AppCompatActivity {
    List<Request> requestList = new ArrayList<>();
    RequestAdapter requestAdapter;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        requestAdapter = new RequestAdapter(requestList);
        recyclerView = findViewById(R.id.req_recycle);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(requestAdapter);

        addReq();
    }

    void addReq(){
        Request request = new Request("Nothing much to say","200k - 3000k","Lagos","Yaba",true,"Flat","Rent",10,4,"Imoh Richard","https://firebasestorage.googleapis.com/v0/b/collab-428e6.appspot.com/o/profile_menu%2Fimage%3A44969?alt=media&token=3908fe6a-5ee1-421a-92c9-a66b630fce1c");
        requestList.add(request);
        request = new Request("Nothing much to say","200k - 3000k","Lagos","Yaba",true,"Single Rom","Rent",10,4,"Imoh Richard","https://firebasestorage.googleapis.com/v0/b/collab-428e6.appspot.com/o/profile_menu%2Fimage%3A44969?alt=media&token=3908fe6a-5ee1-421a-92c9-a66b630fce1c");
        requestList.add(request);
        request = new Request("Nothing much to say","200k - 3000k","Lagos","Yaba",true,"Self Contain","Rent",10,4,"Imoh Richard","https://firebasestorage.googleapis.com/v0/b/collab-428e6.appspot.com/o/profile_menu%2Fimage%3A44969?alt=media&token=3908fe6a-5ee1-421a-92c9-a66b630fce1c");
        requestList.add(request);
        request = new Request("Nothing much to say","200k - 3000k","Lagos","Yaba",true,"Flat","Rent",10,4,"Imoh Richard","https://firebasestorage.googleapis.com/v0/b/collab-428e6.appspot.com/o/profile_menu%2Fimage%3A44969?alt=media&token=3908fe6a-5ee1-421a-92c9-a66b630fce1c");
        requestList.add(request);
        request = new Request("Nothing much to say","200k - 3000k","Lagos","Yaba",true,"Single Room","Rent",10,4,"Imoh Richard","https://firebasestorage.googleapis.com/v0/b/collab-428e6.appspot.com/o/profile_menu%2Fimage%3A44969?alt=media&token=3908fe6a-5ee1-421a-92c9-a66b630fce1c");
        requestList.add(request);
        request = new Request("Nothing much to say","200k - 3000k","Lagos","Yaba",true,"Self Contain","Rent",10,4,"Imoh Richard","https://firebasestorage.googleapis.com/v0/b/collab-428e6.appspot.com/o/profile_menu%2Fimage%3A44969?alt=media&token=3908fe6a-5ee1-421a-92c9-a66b630fce1c");
        requestList.add(request);
        request = new Request("Nothing much to say","200k - 3000k","Lagos","Yaba",true,"Duplex","Rent",10,4,"Imoh Richard","https://firebasestorage.googleapis.com/v0/b/collab-428e6.appspot.com/o/profile_menu%2Fimage%3A44969?alt=media&token=3908fe6a-5ee1-421a-92c9-a66b630fce1c");
        requestList.add(request);
        request = new Request("Nothing much to say","200k - 3000k","Lagos","Yaba",true,"Land","Rent",10,4,"Imoh Richard","https://firebasestorage.googleapis.com/v0/b/collab-428e6.appspot.com/o/profile_menu%2Fimage%3A44969?alt=media&token=3908fe6a-5ee1-421a-92c9-a66b630fce1c");
        requestList.add(request);
        request = new Request("Nothing much to say","200k - 3000k","Lagos","Yaba",true,"Duplex","Rent",10,4,"Imoh Richard","https://firebasestorage.googleapis.com/v0/b/collab-428e6.appspot.com/o/profile_menu%2Fimage%3A44969?alt=media&token=3908fe6a-5ee1-421a-92c9-a66b630fce1c");
        requestList.add(request);
        request = new Request("Nothing much to say","200k - 3000k","Lagos","Yaba",true,"Farm House","Rent",10,4,"Imoh Richard","https://firebasestorage.googleapis.com/v0/b/collab-428e6.appspot.com/o/profile_menu%2Fimage%3A44969?alt=media&token=3908fe6a-5ee1-421a-92c9-a66b630fce1c");
        requestList.add(request);
        request = new Request("Nothing much to say","200k - 3000k","Lagos","Yaba",true,"Flat","Rent",10,4,"Imoh Richard","https://firebasestorage.googleapis.com/v0/b/collab-428e6.appspot.com/o/profile_menu%2Fimage%3A44969?alt=media&token=3908fe6a-5ee1-421a-92c9-a66b630fce1c");
        requestList.add(request);
        request = new Request("Nothing much to say","200k - 3000k","Lagos","Yaba",true,"Flat","Rent",10,4,"Imoh Richard","https://firebasestorage.googleapis.com/v0/b/collab-428e6.appspot.com/o/profile_menu%2Fimage%3A44969?alt=media&token=3908fe6a-5ee1-421a-92c9-a66b630fce1c");
        requestList.add(request);
        request = new Request("Nothing much to say","200k - 3000k","Lagos","Yaba",true,"Flat","Rent",10,4,"Imoh Richard","https://firebasestorage.googleapis.com/v0/b/collab-428e6.appspot.com/o/profile_menu%2Fimage%3A44969?alt=media&token=3908fe6a-5ee1-421a-92c9-a66b630fce1c");
        requestList.add(request);
        request = new Request("Nothing much to say","200k - 3000k","Lagos","Yaba",true,"Flat","Rent",10,4,"Imoh Richard","https://firebasestorage.googleapis.com/v0/b/collab-428e6.appspot.com/o/profile_menu%2Fimage%3A44969?alt=media&token=3908fe6a-5ee1-421a-92c9-a66b630fce1c");
        requestList.add(request);

        requestAdapter.notifyDataSetChanged();

    }

}
