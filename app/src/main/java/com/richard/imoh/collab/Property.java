package com.richard.imoh.collab;

import android.graphics.Bitmap;

/**
 * Created by LENOVO on 6/4/2018.
 */

public class Property {
    public String image;
    public String name;
    public String price;
    public String owner;
    public String location;
    public String isBath;
    public String isRoom;
    public Property(){

    }

    public Property(String image, String name, String price, String owner, String location, String isBath, String isRoom) {
        this.image = image;
        this.name = name;
        this.price = price;
        this.owner = owner;
        this.location = location;
        this.isBath = isBath;
        this.isRoom = isRoom;
    }


}
