package com.launch.rammaxx.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.launch.rammaxx.Models.RadioButtons;
import com.launch.rammaxx.R;

public class MainActivity extends AppCompatActivity {

    private Context mContex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContex = this;

        LinearLayout dialLay = findViewById(R.id.dial_layout);
        RadioButtons dialButt = new RadioButtons(mContex);

        dialLay.addView(dialButt);
    }
}