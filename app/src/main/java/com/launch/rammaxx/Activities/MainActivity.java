package com.launch.rammaxx.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import petrov.kristiyan.colorpicker.ColorPicker;
//import eltos.simpledialogfragment.color.SimpleColorDialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.jaredrummler.android.colorpicker.ColorPickerDialog;
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener;
import com.launch.rammaxx.Models.RadioButtons;
import com.launch.rammaxx.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ColorPickerDialogListener, View.OnClickListener {

    private Context mContex;
    private int[] spinnerColors;
    List<Button> ledButList;
    int ledIdx = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mContex = this;
        String[] dummy = new String[]{"a", "b", "c", "c", "c", "c", "c"};
        spinnerColors = new int[]{R.color.led_red, R.color.led_blue, R.color.led_green, R.color.led_yellow,
                R.color.led_white, R.color.led_off, R.color.led_purple};

        LinearLayout dialLay = findViewById(R.id.dial_layout);
        RadioButtons dialButt = new RadioButtons(mContex);

        //Spinner colorSpinner = findViewById(R.id.col_sp);

        //colorSpinner.setAdapter(new CustomSpinnerAdapter(MainActivity.this, R.layout.color_pick,
        //        dummy, spinnerColors));

        dialLay.addView(dialButt);

        // Buttons
        final Button startBut = findViewById(R.id.start_but);
        startBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int COLOR_DIALOG = 0;
                ColorPicker colorPicker = new ColorPicker(MainActivity.this);
                colorPicker.setColors(R.array.led_colors);
                colorPicker.show();
                colorPicker.setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
                    @Override
                    public void onChooseColor(int position,int color) {

                    }

                    @Override
                    public void onCancel(){
                        // put code
                    }
                });

            }
        });

        // Leds
        Button led0But = findViewById(R.id.led_0_but);
        Button led1But = findViewById(R.id.led_1_but);
        Button led2But = findViewById(R.id.led_2_but);
        Button led3But = findViewById(R.id.led_3_but);
        Button led4But = findViewById(R.id.led_4_but);
        Button led5But = findViewById(R.id.led_5_but);
        Button led6But = findViewById(R.id.led_6_but);
        ledButList = new ArrayList<Button>();
        ledButList.add(led0But);
        ledButList.add(led1But);
        ledButList.add(led2But);
        ledButList.add(led3But);
        ledButList.add(led4But);
        ledButList.add(led5But);
        ledButList.add(led6But);

        for (Button b: ledButList
             ) {
            b.setOnClickListener(this);
        }


    }

    @Override
    public void onColorSelected(int dialogId, int color) {

    }

    @Override
    public void onDialogDismissed(int dialogId) {

    }

    @Override
    public void onClick(View view) {
        final int COLOR_DIALOG = 0;

        switch (view.getId())
        {
            case R.id.led_0_but:
                ledIdx = 0;
                break;
            case R.id.led_1_but:
                ledIdx = 1;
                break;
            case R.id.led_2_but:
                ledIdx = 2;
                break;
            case R.id.led_3_but:
                ledIdx = 3;
                break;
            case R.id.led_4_but:
                ledIdx = 4;
                break;
            case R.id.led_5_but:
                ledIdx = 5;
                break;
            case R.id.led_6_but:
                ledIdx = 6;
                break;
        }
        ColorPicker colorPicker = new ColorPicker(MainActivity.this);
        colorPicker.setColors(R.array.led_colors);
        colorPicker.show();
        colorPicker.setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
            @Override
            public void onChooseColor(int position, int color) {
                ledButList.get(ledIdx).setBackgroundColor(color);
            }

            @Override
            public void onCancel() {
                // put code
            }
        });
    }

    public class CustomSpinnerAdapter extends ArrayAdapter {

        private int[] colors;

        public CustomSpinnerAdapter(@NonNull Context context, int resource, @NonNull String[] objects,
                                    int[] colors) {
            super(context, resource, objects);
        }

        public View getCustomView(int position, View convertView,
                                  ViewGroup parent) {

// Inflating the layout for the custom Spinner
            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.color_pick, parent, false);

// Declaring and Typecasting the textview in the inflated layout
            TextView tvLanguage = (TextView) layout
                    .findViewById(R.id.col_pick_txt);

// Setting the color of the background
            tvLanguage.setBackgroundColor(spinnerColors[position]);

// Setting Special atrributes for 1st element
            if (position == 0) {
// Setting the size of the text
                tvLanguage.setTextSize(20f);
// Setting the text Color
                tvLanguage.setBackgroundColor(spinnerColors[0]);

            }

            return layout;
        }

        // It gets a View that displays in the drop down popup the data at the specified position
        @Override
        public View getDropDownView(int position, View convertView,
                                    ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        // It gets a View that displays the data at the specified position
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }
    }
}