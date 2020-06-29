package com.launch.rammaxx.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;
import io.realm.Realm;
import io.realm.RealmResults;
import petrov.kristiyan.colorpicker.ColorPicker;
//import eltos.simpledialogfragment.color.SimpleColorDialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jaredrummler.android.colorpicker.ColorPickerDialog;
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener;
import com.launch.rammaxx.App.RammaxxApp;
import com.launch.rammaxx.Models.LedConfig;
import com.launch.rammaxx.Models.Leds;
import com.launch.rammaxx.Models.MenuSPItem;
import com.launch.rammaxx.Models.RadioButtons;
import com.launch.rammaxx.R;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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
    RadioButtons dialButt;

    Toolbar toolbar;

    private Realm mRealm;

    private String configName;
    Button loadBut;
    Button saveBut;

    int[] ledColorArray;

    // green button
    private CountDownTimer startButTimer;
    private boolean onlaunch;

    private Menu menu;
    private boolean[] menuSt;

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
            ledColors[col] = 8;
        }

        LinearLayout dialLay = findViewById(R.id.dial_layout);
        dialButt = new RadioButtons(mContex);

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
        final Button uploadBut = findViewById(R.id.upload_but);

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
            b.getBackground().setColorFilter(getResources().getColor(R.color.led_off), PorterDuff.Mode.MULTIPLY);
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
        spTList = new ArrayList<>();
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

        Spinner spP0 = findViewById(R.id.led_0_sp_p);
        Spinner spP1 = findViewById(R.id.led_1_sp_p);
        Spinner spP2 = findViewById(R.id.led_2_sp_p);
        Spinner spP3 = findViewById(R.id.led_3_sp_p);
        Spinner spP4 = findViewById(R.id.led_4_sp_p);
        Spinner spP5 = findViewById(R.id.led_5_sp_p);
        Spinner spP6 = findViewById(R.id.led_6_sp_p);
        Spinner spP7 = findViewById(R.id.led_7_sp_p);
        Spinner spP8 = findViewById(R.id.led_8_sp_p);
        Spinner spP9 = findViewById(R.id.led_9_sp_p);
        spPList = new ArrayList<>();
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

        loadBut.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                DeleteCfg();
                return true;
            }
        });

        ledColorArray = getResources().getIntArray(R.array.led_colors);

        // Start button green
        startBut.getBackground().setColorFilter(getResources().getColor(R.color.butColor), PorterDuff.Mode.MULTIPLY);
        uploadBut.getBackground().setColorFilter(getResources().getColor(R.color.butColor), PorterDuff.Mode.MULTIPLY);
        uploadBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startButTimer.start();
            }
        });
        onlaunch = false;
        startButTimer = new CountDownTimer(3000, 600) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                if(!onlaunch) {
                    uploadBut.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);
                    onlaunch = true;
                    startButTimer.start();
                }
                else
                {
                    uploadBut.getBackground().setColorFilter(getResources().getColor(R.color.butColor),PorterDuff.Mode.MULTIPLY);
                    onlaunch = true;
                    onlaunch = false;
                }
            }
        };

        // menu items status
        menuSt = new boolean[]{false, false, false};
    }

    private void DeleteCfg() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Configuration to Delete");
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.select_dialog_singlechoice);
        final List<String> cfgNames = new ArrayList<>();

        for (String st : readCfgNames(MainActivity.this)
        ) {
            arrayAdapter.add(st);
            cfgNames.add(st);
        }

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String cfgName = arrayAdapter.getItem(i);
                final int cfgNameIdx = i;
                AlertDialog.Builder deleteDialog = new AlertDialog.Builder(MainActivity.this);
                deleteDialog.setTitle("Deleting" + cfgName);
                deleteDialog.setMessage("are you sure you want to delete" + cfgName + "?");
                deleteDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                deleteDialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        cfgNames.remove(cfgNameIdx);
                        try {
                            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(MainActivity.this.openFileOutput("config.txt", Context.MODE_PRIVATE));
                            for (String st : cfgNames
                            ) {
                                outputStreamWriter.write(st + '\n');
                            }
                            outputStreamWriter.close();

                            Toast.makeText(MainActivity.this, "Configuration Deleted", Toast.LENGTH_SHORT).show();
                        }
                        catch (IOException e) {
                            Log.e("Exception", "File write failed: " + e.toString());
                        }

                    }
                });

                deleteDialog.show();
            }
        });

        builder.show();
    }

    private void LoadCfg() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Configuration");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.select_dialog_singlechoice);
        final RealmResults<LedConfig> cfg = mRealm.where(LedConfig.class).findAll();

        /*for (LedConfig cfgId: cfg
             ) {
            arrayAdapter.add(cfgId.getName());
        }*/
        for (String st : readCfgNames(MainActivity.this)
             ) {
            arrayAdapter.add(st);
        }

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String cfgName = arrayAdapter.getItem(i);
                //LedConfig cfgC = mRealm.where(LedConfig.class).equalTo("name", cfgName).findFirst();

                List<String> ret = new ArrayList<>();
                int lSlice;

                try {
                    InputStream inputStream = MainActivity.this.openFileInput(cfgName+".txt");

                    if ( inputStream != null ) {
                        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                        String receiveString = "";

                        if((receiveString = bufferedReader.readLine()) != null)
                        {
                            lSlice = Integer.parseInt(receiveString.split(" ")[0]);
                            dialButt.reDraw(lSlice);
                        }

                        int strIdx = 0;
                        while ( (receiveString = bufferedReader.readLine()) != null ) {
                            String[] ledStr = receiveString.split(" ");
                            spTList.get(strIdx).setSelection(Integer.parseInt(ledStr[0]));
                            ledButList.get(strIdx).getBackground().setColorFilter(ledColorArray[Integer.parseInt(ledStr[1])], PorterDuff.Mode.MULTIPLY);
                            spPList.get(strIdx).setSelection(Integer.parseInt(ledStr[2]));

                            strIdx++;
                        }
                        inputStream.close();

                        Toast.makeText(MainActivity.this, "Configuration Loaded", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (FileNotFoundException e) {
                    Log.e("login activity", "File not found: " + e.toString());
                    Toast.makeText(MainActivity.this, "Configuration Load Failed", Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    Log.e("login activity", "Can not read file: " + e.toString());
                    Toast.makeText(MainActivity.this, "Configuration Load Failed", Toast.LENGTH_LONG).show();
                }

                /*if(cfgC != null)
                {
                    dialButt.reDraw(cfgC.getLaunchIdx());
                    List<Leds> cfgLeds = cfgC.getLeds();

                    for(int j = 0; j < 10; j++)
                    {
                        spPList.get(j).setSelection(cfgLeds.get(j).getLedP());
                        spTList.get(j).setSelection(cfgLeds.get(i).getLedT());
                        ledButList.get(i).getBackground().setColorFilter(cfgLeds.get(i).getLedC(), PorterDuff.Mode.MULTIPLY);;
                    }

                    Toast.makeText(MainActivity.this, "Configuration Loaded", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Configuration Load Failed", Toast.LENGTH_SHORT).show();
                }*/
            }
        });

        builder.show();
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

                int launcherSlice = dialButt.getTouchedSlice();
                int ledIds = RammaxxApp.ledID.incrementAndGet();
                ledIds++;

                final Leds led0 = new Leds(ledIds, configName, 0, spTList.get(0).getSelectedItemPosition(), ledColors[0], spPList.get(0).getSelectedItemPosition());
                final Leds led1 = new Leds(ledIds + 1, configName, 1, spTList.get(1).getSelectedItemPosition(), ledColors[1], spPList.get(1).getSelectedItemPosition());
                final Leds led2 = new Leds(ledIds + 2, configName, 2, spTList.get(2).getSelectedItemPosition(), ledColors[2], spPList.get(2).getSelectedItemPosition());
                final Leds led3 = new Leds(ledIds + 3, configName, 3, spTList.get(3).getSelectedItemPosition(), ledColors[3], spPList.get(3).getSelectedItemPosition());
                final Leds led4 = new Leds(ledIds + 4, configName, 4, spTList.get(4).getSelectedItemPosition(), ledColors[4], spPList.get(4).getSelectedItemPosition());
                final Leds led5 = new Leds(ledIds + 5, configName, 5, spTList.get(5).getSelectedItemPosition(), ledColors[5], spPList.get(5).getSelectedItemPosition());
                final Leds led6 = new Leds(ledIds + 6, configName, 6, spTList.get(6).getSelectedItemPosition(), ledColors[6], spPList.get(6).getSelectedItemPosition());
                final Leds led7 = new Leds(ledIds + 7, configName, 7, spTList.get(7).getSelectedItemPosition(), ledColors[7], spPList.get(7).getSelectedItemPosition());
                final Leds led8 = new Leds(ledIds + 8, configName, 8, spTList.get(8).getSelectedItemPosition(), ledColors[8], spPList.get(8).getSelectedItemPosition());
                final Leds led9 = new Leds(ledIds + 9, configName, 9, spTList.get(9).getSelectedItemPosition(), ledColors[9], spPList.get(9).getSelectedItemPosition());

                LedConfig ledCfg = new LedConfig(configName, launcherSlice, led0, led1,led2, led3, led4, led5, led6,
                        led7, led8, led9);

                /*mRealm.beginTransaction();
                mRealm.copyToRealm(ledCfg);
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
                mRealm.commitTransaction();*/

                try {
                    List<String> names = readCfgNames(MainActivity.this);
                    names.add(configName);
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(MainActivity.this.openFileOutput("config.txt", Context.MODE_PRIVATE));
                    for (String st : names
                         ) {
                        outputStreamWriter.write(st + '\n');
                    }
                    outputStreamWriter.close();
                }
                catch (IOException e) {
                    Log.e("Exception", "File write failed: " + e.toString());
                }

                WriteLeds(MainActivity.this, ledCfg);

                Toast.makeText(MainActivity.this, "Configuration Saved", Toast.LENGTH_SHORT).show();

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        builder.show();

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
        colorPicker.setDefaultColorButton(R.color.led_off);
        colorPicker.show();
        colorPicker.setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
            @Override
            public void onChooseColor(int position, int color) {
                //ledButList.get(ledIdx).setBackgroundColor(color);
                ledButList.get(ledIdx).getBackground().setColorFilter(color, PorterDuff.Mode.MULTIPLY);
                if(position >= 0)
                    ledColors[ledIdx] = position;
            }

            @Override
            public void onCancel() {
                // put code
            }
        });
    }

    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.add(0, 1, 1, menuIconWithText(getResources().getDrawable(R.drawable.rocket_not), getResources().getString(R.string.rocket1)));
        menu.add(0, 2, 2, menuIconWithText(getResources().getDrawable(R.drawable.rocket_not), getResources().getString(R.string.rocket2)));
        menu.add(0, 3, 3, menuIconWithText(getResources().getDrawable(R.drawable.rocket_not), getResources().getString(R.string.rocket3)));
        menu.add(0, 4, 4, getResources().getString(R.string.addrocket));
        menu.add(0, 5, 5, getResources().getString(R.string.delrocket));
        return true;
    }

    private CharSequence menuIconWithText(Drawable r, String title) {

        r.setBounds(0, 0, r.getIntrinsicWidth(), r.getIntrinsicHeight());
        SpannableString sb = new SpannableString("    " + title);
        ImageSpan imageSpan = new ImageSpan(r, ImageSpan.ALIGN_BOTTOM);
        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return sb;
    }

    private List<String> readCfgNames(Context context) {

        List<String> ret = new ArrayList<>();

        try {
            InputStream inputStream = context.openFileInput("config.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    ret.add(receiveString);
                }
                inputStream.close();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }

    private void WriteLeds(Context context, LedConfig ledCfg)
    {
        String outputSt = "";
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(MainActivity.this.openFileOutput(ledCfg.getName() + ".txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(String.valueOf(ledCfg.getLaunchIdx()) + '\n');
            for ( Leds led : ledCfg.getLeds() ) {
                outputStreamWriter.write(String.valueOf(led.getLedT()) + ' ' + String.valueOf(led.getLedC()) + ' ' + String.valueOf(led.getLedP()) + ' ' + '\n');
            }
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                if(!menuSt[0]) {
                    item.setTitle(menuIconWithText(getResources().getDrawable(R.drawable.rocket_linked), getResources().getString(R.string.rocket1)));
                    menuSt[0] = true;
                }
                else
                {
                    item.setTitle(menuIconWithText(getResources().getDrawable(R.drawable.rocket_not), getResources().getString(R.string.rocket1)));
                    menuSt[0] = false;
                }
                return true;
            case 2:
                if(!menuSt[1]) {
                    item.setTitle(menuIconWithText(getResources().getDrawable(R.drawable.rocket_linked), getResources().getString(R.string.rocket2)));
                    menuSt[1] = true;
                }
                else
                {
                    item.setTitle(menuIconWithText(getResources().getDrawable(R.drawable.rocket_not), getResources().getString(R.string.rocket2)));
                    menuSt[1] = false;
                }
                return true;
            case 3:
                if(!menuSt[2]) {
                    item.setTitle(menuIconWithText(getResources().getDrawable(R.drawable.rocket_linked), getResources().getString(R.string.rocket3)));
                    menuSt[2] = true;
                }
                else
                {
                    item.setTitle(menuIconWithText(getResources().getDrawable(R.drawable.rocket_not), getResources().getString(R.string.rocket3)));
                    menuSt[2] = false;
                }
                return true;
        }
        return true;
    }

}