package com.example.socialmap;

/**
 * Created by Евгений on 28.02.2018.
 */

// TODO поля должны быть приватными
public class User {

    public String name;
    public String email;
    public String phone;


    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

}