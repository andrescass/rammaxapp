package com.launch.rammaxx.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;
import io.realm.Realm;
import petrov.kristiyan.colorpicker.ColorPicker;
//import eltos.simpledialogfragment.color.SimpleColorDialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.jaredrummler.android.colorpicker.ColorPickerDialog;
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener;
import com.launch.rammaxx.Models.LedConfig;
import com.launch.rammaxx.Models.Leds;
import com.launch.rammaxx.Models.RadioButtons;
import com.launch.rammaxx.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ColorPickerDialogListener, View.OnClickListener {

    private Context mContex;
    private int[] spinnerColors;
    List<Button> ledButList;
    List<Spinner> spTList;
    List<Spinner> spPList;
    int ledIdx = 0;
    private int[] ledColors;

    Toolbar toolbar;

    private Realm mRealm;

    private String configName;
    Button loadBut;
    Button saveBut;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContex = this;

        mRealm = Realm.getDefaultInstance();

        String[] dummy = new String[]{"a", "b", "c", "c", "c", "c", "c"};
        spinnerColors = new int[]{R.color.led_red, R.color.led_blue, R.color.led_green, R.color.led_yellow,
                R.color.led_white, R.color.led_off, R.color.led_purple};
        ledColors = new int[10];
        for (int col = 0; col < 10; col++) {
            ledColors[col] = R.color.led_off;
        }

        LinearLayout dialLay = findViewById(R.id.dial_layout);
        RadioButtons dialButt = new RadioButtons(mContex);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);

        //Spinner colorSpinner = findViewById(R.id.col_sp);

        //colorSpinner.setAdapter(new CustomSpinnerAdapter(MainActivity.this, R.layout.color_pick,
        //        dummy, spinnerColors));

        dialLay.addView(dialButt);

        // Buttons
        final Button startBut = findViewById(R.id.start_but);

        // Leds
        Button led0But = findViewById(R.id.led_0_but);
        Button led1But = findViewById(R.id.led_1_but);
        Button led2But = findViewById(R.id.led_2_but);
        Button led3But = findViewById(R.id.led_3_but);
        Button led4But = findViewById(R.id.led_4_but);
        Button led5But = findViewById(R.id.led_5_but);
        Button led6But = findViewById(R.id.led_6_but);
        Button led7But = findViewById(R.id.led_7_but);
        Button led8But = findViewById(R.id.led_8_but);
        Button led9But = findViewById(R.id.led_9_but);
        ledButList = new ArrayList<Button>();
        ledButList.add(led0But);
        ledButList.add(led1But);
        ledButList.add(led2But);
        ledButList.add(led3But);
        ledButList.add(led4But);
        ledButList.add(led5But);
        ledButList.add(led6But);
        ledButList.add(led7But);
        ledButList.add(led8But);
        ledButList.add(led9But);

        for (Button b: ledButList
             ) {
            b.setOnClickListener(this);
        }

        // Spinners
        Spinner spT0 = findViewById(R.id.led0_sp_t);
        Spinner spT1 = findViewById(R.id.led1_sp_t);
        Spinner spT2 = findViewById(R.id.led2_sp_t);
        Spinner spT3 = findViewById(R.id.led3_sp_t);
        Spinner spT4 = findViewById(R.id.led4_sp_t);
        Spinner spT5 = findViewById(R.id.led5_sp_t);
        Spinner spT6 = findViewById(R.id.led6_sp_t);
        Spinner spT7 = findViewById(R.id.led7_sp_t);
        Spinner spT8 = findViewById(R.id.led8_sp_t);
        Spinner spT9 = findViewById(R.id.led9_sp_t);
        spTList.add(spT0);
        spTList.add(spT1);
        spTList.add(spT2);
        spTList.add(spT3);
        spTList.add(spT4);
        spTList.add(spT5);
        spTList.add(spT6);
        spTList.add(spT7);
        spTList.add(spT8);
        spTList.add(spT9);

        Spinner spP0 = findViewById(R.id.led0_sp_t);
        Spinner spP1 = findViewById(R.id.led1_sp_t);
        Spinner spP2 = findViewById(R.id.led2_sp_t);
        Spinner spP3 = findViewById(R.id.led3_sp_t);
        Spinner spP4 = findViewById(R.id.led4_sp_t);
        Spinner spP5 = findViewById(R.id.led5_sp_t);
        Spinner spP6 = findViewById(R.id.led6_sp_t);
        Spinner spP7 = findViewById(R.id.led7_sp_t);
        Spinner spP8 = findViewById(R.id.led8_sp_t);
        Spinner spP9 = findViewById(R.id.led9_sp_t);
        spPList.add(spP0);
        spPList.add(spP1);
        spPList.add(spP2);
        spPList.add(spP3);
        spPList.add(spP4);
        spPList.add(spP5);
        spPList.add(spP6);
        spPList.add(spP7);
        spPList.add(spP8);
        spPList.add(spP9);
        
        // Load and sve buttons
        saveBut = findViewById(R.id.save_btn);
        loadBut = findViewById(R.id.load_btn);
        
        saveBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveCfg();
            }
        });
        
        loadBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoadCfg();
            }
        });


    }

    private void LoadCfg() {
    }

    private void SaveCfg() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Configuration Save");

        final EditText input = new EditText(this);
        builder.setView(input);

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                configName = input.getText().toString();

                final Leds led0 = new Leds(configName, 0, spTList.get(0).getSelectedItemPosition(), ledColors[0], spPList.get(0).getSelectedItemPosition());
                final Leds led1 = new Leds(configName, 1, spTList.get(1).getSelectedItemPosition(), ledColors[1], spPList.get(1).getSelectedItemPosition());
                final Leds led2 = new Leds(configName, 2, spTList.get(2).getSelectedItemPosition(), ledColors[2], spPList.get(2).getSelectedItemPosition());
                final Leds led3 = new Leds(configName, 3, spTList.get(3).getSelectedItemPosition(), ledColors[3], spPList.get(3).getSelectedItemPosition());
                final Leds led4 = new Leds(configName, 4, spTList.get(4).getSelectedItemPosition(), ledColors[4], spPList.get(4).getSelectedItemPosition());
                final Leds led5 = new Leds(configName, 5, spTList.get(5).getSelectedItemPosition(), ledColors[5], spPList.get(5).getSelectedItemPosition());
                final Leds led6 = new Leds(configName, 6, spTList.get(6).getSelectedItemPosition(), ledColors[6], spPList.get(6).getSelectedItemPosition());
                final Leds led7 = new Leds(configName, 7, spTList.get(7).getSelectedItemPosition(), ledColors[7], spPList.get(7).getSelectedItemPosition());
                final Leds led8 = new Leds(configName, 8, spTList.get(8).getSelectedItemPosition(), ledColors[8], spPList.get(8).getSelectedItemPosition());
                final Leds led9 = new Leds(configName, 9, spTList.get(9).getSelectedItemPosition(), ledColors[9], spPList.get(9).getSelectedItemPosition());

                List<Leds> auxL = new ArrayList<>();
                auxL.add(led0);
                auxL.add(led1);
                auxL.add(led2);
                auxL.add(led3);
                auxL.add(led4);
                auxL.add(led5);
                auxL.add(led6);
                auxL.add(led7);
                auxL.add(led8);
                auxL.add(led9);

                LedConfig ledC = new LedConfig(configName, auxL);

                mRealm.beginTransaction();
                mRealm.copyToRealm(ledC);
                mRealm.copyToRealm(led0);
                mRealm.copyToRealm(led1);
                mRealm.copyToRealm(led2);
                mRealm.copyToRealm(led3);
                mRealm.copyToRealm(led4);
                mRealm.copyToRealm(led5);
                mRealm.copyToRealm(led6);
                mRealm.copyToRealm(led7);
                mRealm.copyToRealm(led8);
                mRealm.copyToRealm(led9);
                mRealm.commitTransaction();

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
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
            case R.id.led_7_but:
                ledIdx = 7;
                break;
            case R.id.led_8_but:
                ledIdx = 8;
                break;
            case R.id.led_9_but:
                ledIdx = 9;
                break;
        }
        ColorPicker colorPicker = new ColorPicker(MainActivity.this);
        colorPicker.setColors(R.array.led_colors);
        colorPicker.show();
        colorPicker.setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
            @Override
            public void onChooseColor(int position, int color) {
                //ledButList.get(ledIdx).setBackgroundColor(color);
                ledButList.get(ledIdx).getBackground().setColorFilter(color, PorterDuff.Mode.MULTIPLY);
                ledColors[position] = color;
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

    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}