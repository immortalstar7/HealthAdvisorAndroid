package com.learn2crack.sensefall;

/**
 * Created by Mansi Joshi on 4/15/15.
 */

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import com.learn2crack.Login;
import com.learn2crack.R;
import com.learn2crack.library.Profile;

public class TabBarExample extends TabActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab);
        
        /* TabHost will have Tabs */
        TabHost tabHost = (TabHost)findViewById(android.R.id.tabhost);
        
        /* TabSpec used to create a new tab. 
         * By using TabSpec only we can able to setContent to the tab.
         * By using TabSpec setIndicator() we can set name to tab. */
        
        /* tid1 is firstTabSpec Id. Its used to access outside. */
        TabSpec firstTabSpec = tabHost.newTabSpec("tid1");
        TabSpec secondTabSpec = tabHost.newTabSpec("tid1");
        TabSpec thirdTabSpec = tabHost.newTabSpec("tid1");
        TabSpec fourthTabSpec = tabHost.newTabSpec("tid1");
        /* TabSpec setIndicator() is used to set name for the tab. */
        /* TabSpec setContent() is used to set content for a particular tab. */
        firstTabSpec.setIndicator("Detect Fall").setContent(new Intent(this,MainActivity.class));
        secondTabSpec.setIndicator("Exercises").setContent(new Intent(this,Prediction.class));
        thirdTabSpec.setIndicator("Profile").setContent(new Intent(this,Profile.class));
        fourthTabSpec.setIndicator("Activity").setContent(new Intent(this,Profile.class));
        /* Add tabSpec to the TabHost to display. */
        tabHost.addTab(firstTabSpec);
        tabHost.addTab(secondTabSpec);
        tabHost.addTab(thirdTabSpec);
        tabHost.addTab(fourthTabSpec);
    }
}
