package com.example.bloodbankfirestore;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class AdminDetail implements Serializable {

    @Exclude
    private String id;

    String namee;
    String address;
    String mob;
    String bldgrp;
    String imageeurll;

    public AdminDetail(){
    }


    public AdminDetail(String namee, String address, String mob, String bldgrp,String imageeurll){

        this.namee = namee;
        this.address = address;
        this.mob = mob;
        this.bldgrp = bldgrp;
        this.imageeurll = imageeurll;
        //this.imageeurll = imageeurll;

    }




    public String getBldgrp() {
        return bldgrp;
    }

    public String getAddress() {
        return address;
    }

    public String getMob() {
        return mob;
    }

    public String getNamee() {
        return namee;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageeurll() {
        return imageeurll;
    }

}
