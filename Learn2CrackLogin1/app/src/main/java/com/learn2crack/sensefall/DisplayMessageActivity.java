package com.learn2crack.sensefall;
/**
 * Created by Mansi Joshi on 3/15/15.
 */

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.learn2crack.R;

public class DisplayMessageActivity extends Activity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.display_message);
		Intent intent = getIntent();
		String message = intent.getStringExtra(ReadAccelData.EXTRA_MESSAGE);
		  // Create the text view
		if(message.equalsIgnoreCase("fall")){
        	MediaPlayer.create(getBaseContext(), R.raw.fall).start();
        	Log.d("@@@@ FALLL ", " ");
        }
        if(message.equalsIgnoreCase("sitting")){
        	MediaPlayer.create(getBaseContext(), R.raw.sitting).start();
        	Log.d("@@@@ sitting" , "" );
        }
        if(message.equalsIgnoreCase("standing")){
        	MediaPlayer.create(getBaseContext(), R.raw.standing).start();
        	Log.d("@@@@ standing", " ");
        	
        }
        if(message.equalsIgnoreCase("walking")){
        	MediaPlayer.create(getBaseContext(), R.raw.walking).start();
        	Log.d("@@@@ walking", " ");
        }
        
	    TextView textView = new TextView(this);
	    textView.setTextSize(40);
	    textView.setText(message);

	    setContentView(textView);
	    finish();
	}
	
}
