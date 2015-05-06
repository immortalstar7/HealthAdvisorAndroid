package com.learn2crack.sensefall;

/**
 * Created by Mansi Joshi on 3/15/15.
 */
import java.util.UUID;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.learn2crack.R;

//import com.getpebble.android.kit.PebbleKit;
//import com.getpebble.android.kit.util.PebbleDictionary;


public class Configure extends Activity {

    private SeekBar thresholdBar;
    private String PREF_CONFIG = "configuration";
    int thresholdLevel;
    private String currentMessage;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public void save(View view) {
        SharedPreferences config = getSharedPreferences(PREF_CONFIG, 0);
        SharedPreferences.Editor editor = config.edit();

        editor.putString("msg",
                ((EditText) findViewById(R.id.customMessage)).getText()
                        .toString());

        editor.commit();
        ((TextView) findViewById(R.id.current_message))
                .setText("Current Message: "
                        + ((EditText) findViewById(R.id.customMessage))
                                .getText().toString());
    }

    /*public void sendDataToWatch() {
        // Build up a Pebble dictionary
        PebbleDictionary data = new PebbleDictionary();

        data.addUint8(0, (byte) tresholdLevel);

        // Send the assembled dictionary to the weather watch-app;
        PebbleKit.sendDataToPebble(getApplicationContext(),
                UUID.fromString("5ed10362-a625-41e6-b35c-e6b10feb71e6"),
                data);
    }*/

    protected void loadConfig() {
        SharedPreferences config = getSharedPreferences(PREF_CONFIG, 0);
        thresholdLevel = Integer.parseInt(config.getString("thresholdValue",
                "50"));
        currentMessage = config.getString("msg", "Help Me!");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure);
        loadConfig();
        thresholdBar = (SeekBar) findViewById(R.id.treshold);
        RadioGroup tresholdGroup = (RadioGroup) findViewById(R.id.treshold_defaults);

        thresholdBar
                .setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

                    @Override
                    public void onProgressChanged(SeekBar seekBar,
                            int progress, boolean fromUser) {
                        thresholdLevel = progress;
                        ((TextView) findViewById(R.id.fall_treshold_display))
                                .setText("Fall Treshold: " + thresholdLevel);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                        saveTreshold();

                        Toast.makeText(Configure.this,
                                "seek bar progress:" + thresholdLevel,
                                Toast.LENGTH_SHORT).show();
                        thresholdBar.post(new Runnable() {

                            @Override
                            public void run() {
                                thresholdBar
                                        .setSecondaryProgress(thresholdLevel);
                                //sendDataToWatch();

                            }

                        });
                    }
                });
        tresholdGroup
                .setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(RadioGroup group,
                            int checkedId) {
                        //switch (checkedId) {
                        if( checkedId == R.id.young){
                            thresholdLevel = 65;
                            }else
                            
                        if(checkedId== R.id.above60F){
                            thresholdLevel = 40;
                            }else
                            
                        if(checkedId== R.id.above60M){
                            thresholdLevel = 45;
                            }else
                            
                        if( checkedId==R.id.car_crash){
                            thresholdLevel = 70;
                            }else
                            
                        if(checkedId== R.id.osteoporosis){
                            thresholdLevel = 40;
                            }else
                            
                        if(checkedId== R.id.osteopenia){
                            thresholdLevel = 45;
                            }else
                            
                        if(checkedId== R.id.less12){
                            thresholdLevel = 55;
                            }
                            
                        if(checkedId== R.id.fractured){
                            thresholdLevel = 40;
                            }else
                            
                        if( checkedId==R.id.default_tresh){
                            thresholdLevel = 50;
                            }
                            
                        
                            
                        
                        saveTreshold();
                        thresholdBar.post(new Runnable() {

                            @Override
                            public void run() {
                                thresholdBar
                                        .setSecondaryProgress(thresholdLevel);
                                thresholdBar.setProgress(thresholdLevel);
                                //sendDataToWatch();

                            }

                        });
                    }

                });

    }

    @Override
    protected void onResume() {
        super.onResume();
        thresholdBar.setProgress(thresholdLevel);
        thresholdBar.setSecondaryProgress(thresholdLevel);
        ((TextView) findViewById(R.id.current_message))
                .setText("Current Message: " + currentMessage);
    }

    protected void saveTreshold() {
        SharedPreferences config = getSharedPreferences(PREF_CONFIG, 0);
        SharedPreferences.Editor editor = config.edit();
        editor.putString("thresholdValue", Integer.toString(thresholdLevel));
        editor.commit();
    }
}
