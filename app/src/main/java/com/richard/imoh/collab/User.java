package com.richard.imoh.collab;

/**
 * Created by LENOVO on 6/13/2018.
 */

public class User {
    String fullName;
    String image;
    String email;
    String phone;
    String occupation;
    String company;
    String ProfessionalQalification;
    String gender;
    String profileDescription;
    String location;
    String userName;
    String uId;

    public User() {
    }

    public User(String fullName, String image, String email, String phone, String occupation, String company, String professionalQalification, String gender, String profileDescription, String location) {
        this.fullName = fullName;
        this.image = image;
        this.email = email;
        this.phone = phone;
        this.occupation = occupation;
        this.company = company;
        ProfessionalQalification = professionalQalification;
        this.gender = gender;
        this.profileDescription = profileDescription;
        this.location = location;
    }

    public User(String fullName, String image, String email, String location, String userName,String uId) {
        this.fullName = fullName;
        this.image = image;
        this.email = email;
        this.location = location;
        this.userName = userName;
        this.uId = uId;
    }

    public User(String fullName, String email, String userName,String uId) {
        this.fullName = fullName;
        this.email = email;
        this.userName = userName;
        this.uId = uId;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getProfessionalQalification() {
        return ProfessionalQalification;
    }

    public void setProfessionalQalification(String professionalQalification) {
        ProfessionalQalification = professionalQalification;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProfileDescription() {
        return profileDescription;
    }

    public void setProfileDescription(String profileDescription) {
        this.profileDescription = profileDescription;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
