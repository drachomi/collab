package com.richard.imoh.collab;

/**
 * Created by LENOVO on 6/29/2018.
 */

public class UserMessage {
    String username;
    String imageString;
    String timeStamp;
    String messageBody;
    String photoUrl;

    public UserMessage() {
    }



    public UserMessage(String username, String imageString, String timeStamp, String messageBody, String photoUrl) {
        this.username = username;
        this.imageString = imageString;
        this.timeStamp = timeStamp;
        this.messageBody = messageBody;
        this.photoUrl = photoUrl;
    }
    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImageString() {
        return imageString;
    }

    public void setImageString(String imageString) {
        this.imageString = imageString;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }
}
