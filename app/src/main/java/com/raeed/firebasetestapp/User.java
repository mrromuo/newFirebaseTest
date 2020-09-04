package com.raeed.firebasetestapp;

public class User {

    public String username;
    public String email;
    public String Sex;
    public int Age;

    public User(String username,String sex,int Age, String email) {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
        this.username = username;
        this.email = email;
        this.Sex = sex;
        this.Age = Age;
    }



}
// [END blog_user_class]
