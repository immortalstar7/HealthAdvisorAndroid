package com.learn2crack.sensefall;

/**
 * Created by Mansi Joshi on 3/27/15.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.learn2crack.R;

public class MainActivity extends  Activity {
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment1);
        
	}

   public void start_app(View view){
	
	   
	   Intent intent = new Intent(this, ReadAccelData.class);
        startActivity(intent);
	   
	
	   
   }
   public void contacts_view(View view){
	
	   
	   Intent intent = new Intent(this, LoadContacts.class);
        startActivity(intent);
	   
	
	   
   }
   
   public void exit_app(View view){
	   finish();
      
	   
   }





}
