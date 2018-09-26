package com.richard.imoh.collab.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.richard.imoh.collab.R;

/**
 * Created by LENOVO on 9/3/2018.
 */

public class OnBoardAdapter extends PagerAdapter {
    Context context;
    LayoutInflater inflater;



    public OnBoardAdapter(Context context) {
        this.context = context;
    }

    public int[]images = {
            R.drawable.house,
            R.drawable.home,
            R.drawable.bath,
            R.drawable.add
    };
    public String[]heading = {
            "Collaborate",
            "Share Property",
            "Post Request",
            "Chat"
    };
    public String[]description = {
            "Lorem Ipsum may mean many thing to many user, basically according to ow you see it",
            "Lorem Ipsum may mean many thing to many user, basically according to ow you see it",
            "Lorem Ipsum may mean many thing to many user, basically according to ow you see it",
            "Lorem Ipsum may mean many thing to many user, basically according to ow you see it"
    };




    @Override
    public int getCount() {
        return heading.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.onboard_layout,container,false);
        ImageView imageView = view.findViewById(R.id.onboard_image);
        TextView headingtext = view.findViewById(R.id.onboard_heading);
        TextView desc = view.findViewById(R.id.onboard_text);

        imageView.setImageResource(images[position]);
        headingtext.setText(heading[position]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);
    }
}
