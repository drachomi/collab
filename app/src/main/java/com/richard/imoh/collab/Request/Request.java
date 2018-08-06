package com.richard.imoh.collab.Request;

/**
 * Created by LENOVO on 8/4/2018.
 */

public class Request {
    String description;
    String price;
    String state;
    String city;
    Boolean isUrgent;
    //Property type being either flat, self contain as in the case of residential or office space in the case of commercial
    String propertyType;
    //Let type being rent, lease or sell
    String letType;
    int size;
    //resRoomNo being residence, room number
    int resRoomNo;
    String agentName;
    String agentDp;

    public Request(String description, String price, String state, String city,Boolean isUrgent, String propertyType, String letType, int size, int resRoomNo, String agentName, String agentDp) {
        this.description = description;
        this.price = price;
        this.state = state;
        this.city = city;
        this.isUrgent = isUrgent;
        this.propertyType = propertyType;
        this.letType = letType;
        this.size = size;
        this.resRoomNo = resRoomNo;
        this.agentName = agentName;
        this.agentDp = agentDp;
    }
}
