package com.kxland.hydrodonoremote;

/**
 * Created by Asus on 15/09/2017.
 */

public class Controlling {
    String idarduino;
    String diffTime;
    String status;
    String manual;
    String lastOn;

    public Controlling() {

    }

    //public Controlling(String idarduino, String diffTIme, String status, String  manual, String lastOn) {
    public Controlling(String idarduino, String diffTIme, String manual) {
        this.idarduino = idarduino;
        this.diffTime = diffTIme;
        this.status = status;
        this.manual = manual;
        this.lastOn = lastOn;
    }

    public String getIdArduino() {
        return idarduino;
    }

    public String getDiffTime() {
        return diffTime;
    }

    public String getStatus() {
        return status;
    }

    public String getManual() {
        return manual;
    }
    public String getLastOn() {
        return lastOn;
    }
}