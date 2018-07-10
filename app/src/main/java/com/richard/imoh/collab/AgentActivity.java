package com.richard.imoh.collab;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.richard.imoh.collab.Adapters.FeedAdapter;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AgentActivity extends AppCompatActivity {
    CircleImageView circleImageView;
    RecyclerView recyclerView;
    FeedAdapter feedAdapter;
    List<Property> mProperty = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent);
        circleImageView = findViewById(R.id.profile_image);
        circleImageView.setImageResource(R.drawable.richy);
        recyclerView = findViewById(R.id.agent_proprties);
        feedAdapter = new FeedAdapter(mProperty);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayout.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(feedAdapter);



        addProp();
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



        feedAdapter.notifyDataSetChanged();
    }
}

