package com.example.bloodbankfirestore;

public class SignupDetail {

    String name;
    String Email;
    String addd;
    String dob;
    String bldgrp;
    String lastdate;
    String mobilee;
    String imageeurll;

   public SignupDetail ()
   {

   }


    public SignupDetail(String nme, String Email, String addd, String dobb, String lsttdate, String mobil, String bldgrpp,String imageeurll) {

        this.name = nme;
        this.Email = Email;
        this.addd = addd;
        this.dob = dobb;
        this.lastdate = lsttdate;
        this.mobilee = mobil;
        this.bldgrp = bldgrpp;
        this.imageeurll = imageeurll;



    }


    public String getName() {
        return name;
    }

    public String getBldgrp() {
        return bldgrp;
    }

    public String getEmail() {
        return Email;
    }

    public String getAddd() {
        return addd;
    }

    public String getDob() {
        return dob;
    }

    public String getLastdate() {
        return lastdate;
    }

    public String getMobilee() {
        return mobilee;
    }

    public String getImageeurll() {
        return imageeurll;
    }

}
