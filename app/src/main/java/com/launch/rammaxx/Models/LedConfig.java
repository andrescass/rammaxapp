package com.launch.rammaxx.Models;

import com.launch.rammaxx.App.RammaxxApp;

import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class LedConfig extends RealmObject {
    @PrimaryKey
    private int ID;

    private String name;
    private List<Leds> leds;

    public LedConfig()
    {

    }

    public LedConfig(String name, List<Leds> leds) {
        this.ID = RammaxxApp.CfgID.incrementAndGet();
        this.name = name;
        this.leds = leds;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Leds> getLeds() {
        return leds;
    }

    public void setLeds(List<Leds> leds) {
        this.leds = leds;
    }
}
