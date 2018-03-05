package com.kxland.hydrodonoremote;

/**
 * Created by Asus on 15/09/2017.
 */

public class Register {
    String email;
    String token;
    String latti;
    String longi;


    public Register() {

    }

    //public Controlling(String idarduino, String diffTIme, String status, String  manual, String lastOn) {
    public Register(String email, String token, String latti, String longi) {
        this.email = email;
        this.token = token;
        this.latti = latti;
        this.longi = longi;
    }

    public String getEmail() {
        return email;
    }

    public String getToken() {
        return token;
    }
    public String getLatti() {
        return latti;
    }
    public String getLongi() {
        return longi;
    }
}