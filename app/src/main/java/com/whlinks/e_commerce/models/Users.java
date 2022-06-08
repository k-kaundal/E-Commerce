package com.whlinks.e_commerce.models;

public class Users {
    private String email, fName, lName, phone, gender, imageUrl;
    private int userType;

    public Users(String email, String fName, String lName, String phone, String gender, String imageUrl, int userType) {
        this.email = email;
        this.fName = fName;
        this.lName = lName;
        this.phone = phone;
        this.gender = gender;
        this.imageUrl = imageUrl;
        this.userType = userType;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }


    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public String getfName() {
        return fName;
    }

    public String getGender() {
        return gender;
    }

    public String getlName() {
        return lName;
    }


    public String getPhone() {
        return phone;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }
}
