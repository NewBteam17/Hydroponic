package com.kxland.hydrodonoremote;

/**
 * Created by Asus on 23/09/2017.
 */

public class Pengguna {
    public String email;
    public String[] idarduino;
    public String level;

    public Pengguna(){}

    public Pengguna(String email, String[] idarduino, String level){
        this.email = email;
        this.idarduino =  idarduino;
        this.level = level;
    }
}
