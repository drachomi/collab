package com.richard.imoh.collab.Pojo;

/**
 * Created by LENOVO on 6/29/2018.
 */

public class UserMessage {
    String username;
    String profilePicture;
    String timeStamp;
    String messageBody;
    String imageMessage;
    String uid;

    public UserMessage() {
    }

    public UserMessage(String username, String profilePicture, String timeStamp, String messageBody, String imageMessage, String uid) {
        this.username = username;
        this.profilePicture = profilePicture;
        this.timeStamp = timeStamp;
        this.messageBody = messageBody;
        this.imageMessage = imageMessage;
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
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

    public String getImageMessage() {
        return imageMessage;
    }

    public void setImageMessage(String imageMessage) {
        this.imageMessage = imageMessage;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}

