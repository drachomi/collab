package com.richard.imoh.collab;

/**
 * Created by LENOVO on 7/5/2018.
 */

public class ChatListPojo {
    String displayName;
    String displayMsg;
    String displayImg;
    String displayTime;

    public ChatListPojo(String displayName, String displayMsg, String displayImg, String displayTime) {
        this.displayName = displayName;
        this.displayMsg = displayMsg;
        this.displayImg = displayImg;
        this.displayTime = displayTime;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayMsg() {
        return displayMsg;
    }

    public void setDisplayMsg(String displayMsg) {
        this.displayMsg = displayMsg;
    }

    public String getDisplayImg() {
        return displayImg;
    }

    public void setDisplayImg(String displayImg) {
        this.displayImg = displayImg;
    }

    public String getDisplayTime() {
        return displayTime;
    }

    public void setDisplayTime(String displayTime) {
        this.displayTime = displayTime;
    }
}
