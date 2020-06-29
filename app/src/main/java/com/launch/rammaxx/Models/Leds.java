package com.launch.rammaxx.Models;

import com.launch.rammaxx.App.RammaxxApp;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Leds extends RealmObject {

    @PrimaryKey
    private int ID;
    private String cfgName;

    private int order;

    private int ledT;
    private int ledC;
    private int ledP;


    public Leds(int ledT, int ledC, int ledP) {
        this.ledT = ledT;
        this.ledC = ledC;
        this.ledP = ledP;
    }

    public Leds() {
    }

    public Leds(String cfgName, int order, int ledT, int ledC, int ledP) {
        this.ID = RammaxxApp.ledID.incrementAndGet();
        this.cfgName = cfgName;
        order = order;
        this.ledT = ledT;
        this.ledC = ledC;
        this.ledP = ledP;
    }


    public int getLedT() {
        return ledT;
    }

    public void setLedT(int ledT) {
        this.ledT = ledT;
    }

    public int getLedC() {
        return ledC;
    }

    public void setLedC(int ledC) {
        this.ledC = ledC;
    }

    public int getLedP() {
        return ledP;
    }

    public void setLedP(int ledP) {
        this.ledP = ledP;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getCfgName() {
        return cfgName;
    }

    public void setCfgName(String cfgName) {
        this.cfgName = cfgName;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
