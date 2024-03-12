package com.example.loginapp;

public class UserDetails {
    public String fname, fgender, fphone;

    public UserDetails() {

    }


    public UserDetails(String textname, String textphone, String textgender) {
        this.fname = textname;
        this.fphone = textphone;
        this.fgender = textgender;
    }



    public void setName(String name) {

        this.fname = name;
    }



    public void setPhone(String phone) {

        this.fphone = phone;
    }

    public void setGender(String gender) {
        this.fgender = gender;
    }
}

