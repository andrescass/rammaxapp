package com.launch.rammaxx.Models;

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

    public LedConfig(int ID, String name, List<Leds> leds) {
        this.ID = ID;
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
