package com.launch.rammaxx.Models;

public class Leds {
    private int ledT;
    private int ledC;
    private int ledP;


    public Leds(int ledT, int ledC, int ledP) {
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
}
