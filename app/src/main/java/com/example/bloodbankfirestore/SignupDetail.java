package com.example.bloodbankfirestore;

public class SignupDetail {

    String name;
    String pass;
    String addd;
    String dob;
    String bldgrp;
    String lastdate;
    String mobilee;

    public SignupDetail(String nme, String pass, String addd, String dobb, String lsttdate, String mobil, String bldgrpp) {

        this.name = nme;
        this.pass = pass;
        this.addd = addd;
        this.dob = dobb;
        this.lastdate = lsttdate;
        this.mobilee = mobil;
        this.bldgrp = bldgrpp;



    }


    public String getName() {
        return name;
    }

    public String getBldgrp() {
        return bldgrp;
    }

    public String getPass() {
        return pass;
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

}
