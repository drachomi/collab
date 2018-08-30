package com.richard.imoh.collab.Pojo;




import java.util.HashMap;

import java.util.Map;

/**
 * Created by LENOVO on 6/4/2018.
 */

public class Property {
     String agentName;
     String agentDp;
     String propertyImage1;
     String propertyImage2;
     String propertyImage3;
     String price;
     String state;
     String city;
     String roomNo;
     String plotNo;
     String suitableFor;
     String propertyType;
     String propertyKind;
     String letType;
     String additionalInfo;
     String userId;
     String time;
     String propId;

    public Property() {
    }

    public Property(String agentName, String agentDp, String propertyImage1, String propertyImage2, String propertyImage3, String price, String state, String city, String roomNo, String plotNo, String suitableFor, String propertyType,String  propertyKind,String letType, String additionalInfo, String userId, String time, String propId) {
        this.agentName = agentName;
        this.agentDp = agentDp;
        this.propertyImage1 = propertyImage1;
        this.propertyImage2 = propertyImage2;
        this.propertyImage3 = propertyImage3;
        this.price = price;
        this.state = state;
        this.city = city;
        this.roomNo = roomNo;
        this.plotNo = plotNo;
        this.suitableFor = suitableFor;
        this.propertyType = propertyType;
        this.propertyKind = propertyKind;
        this.letType = letType;
        this.additionalInfo = additionalInfo;
        this.userId = userId;
        this.time = time;
        this.propId = propId;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getAgentDp() {
        return agentDp;
    }

    public void setAgentDp(String agentDp) {
        this.agentDp = agentDp;
    }

    public String getPropertyImage1() {
        return propertyImage1;
    }

    public void setPropertyImage1(String propertyImage1) {
        this.propertyImage1 = propertyImage1;
    }

    public String getPropertyImage2() {
        return propertyImage2;
    }

    public void setPropertyImage2(String propertyImage2) {
        this.propertyImage2 = propertyImage2;
    }

    public String getPropertyImage3() {
        return propertyImage3;
    }

    public void setPropertyImage3(String propertyImage3) {
        this.propertyImage3 = propertyImage3;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public String getPlotNo() {
        return plotNo;
    }

    public void setPlotNo(String plotNo) {
        this.plotNo = plotNo;
    }

    public String getSuitableFor() {
        return suitableFor;
    }

    public void setSuitableFor(String suitableFor) {
        this.suitableFor = suitableFor;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public String getLetType() {
        return letType;
    }

    public void setLetType(String letType) {
        this.letType = letType;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPropId() {
        return propId;
    }

    public void setPropId(String propId) {
        this.propId = propId;
    }

    public String getPropertyKind() {
        return propertyKind;
    }

    public void setPropertyKind(String propertyKind) {
        this.propertyKind = propertyKind;
    }
}
