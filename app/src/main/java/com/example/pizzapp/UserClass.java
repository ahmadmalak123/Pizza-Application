package com.example.pizzapp;

public class UserClass {

    String Name,Email,PhoneNb,Password;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhoneNb() {
        return PhoneNb;
    }

    public void setPhoneNb(String phoneNb) {
        PhoneNb = phoneNb;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public UserClass(String name, String email, String phoneNb, String password) {
        Name = name;
        Email = email;
        PhoneNb = phoneNb;
        Password = password;
    }

    public UserClass() {
    }
}
