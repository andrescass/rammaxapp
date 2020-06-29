package com.launch.rammaxx.Models;

import com.launch.rammaxx.App.RammaxxApp;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class LedConfig extends RealmObject {

    private int ID;

    private String name;
    private int launchIdx;
    private Leds led0;
    private Leds led1;
    private Leds led2;
    private Leds led3;
    private Leds led4;
    private Leds led5;
    private Leds led6;
    private Leds led7;
    private Leds led8;
    private Leds led9;

    public LedConfig()
    {

    }

    public LedConfig(String name, int launchIdx, Leds led0, Leds led1, Leds led2, Leds led3, Leds led4, Leds led5, Leds led6, Leds led7, Leds led8, Leds led9) {
        this.ID = RammaxxApp.CfgID.incrementAndGet();
        this.name = name;
        this.launchIdx = launchIdx;
        this.led0 = led0;
        this.led1 = led1;
        this.led2 = led2;
        this.led3 = led3;
        this.led4 = led4;
        this.led5 = led5;
        this.led6 = led6;
        this.led7 = led7;
        this.led8 = led8;
        this.led9 = led9;
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


    public int getLaunchIdx() {
        return launchIdx;
    }

    public void setLaunchIdx(int launchIdx) {
        this.launchIdx = launchIdx;
    }

    public List<Leds> getLeds()
    {
        List<Leds> ret = new ArrayList<>();
        ret.add(this.led0);
        ret.add(this.led1);
        ret.add(this.led2);
        ret.add(this.led3);
        ret.add(this.led4);
        ret.add(this.led5);
        ret.add(this.led6);
        ret.add(this.led7);
        ret.add(this.led8);
        ret.add(this.led9);

        return ret;
    }

}
