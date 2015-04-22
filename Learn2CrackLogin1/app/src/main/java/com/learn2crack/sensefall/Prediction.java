package com.learn2crack.sensefall;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.learn2crack.R;

public class Prediction extends Activity {
    
@Override
   public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.fragment2);
       Log.d("Prediction","onCreate");
       //Generate list View from ArrayList
       displayListView();
 
}
 
private void displayListView() {

 //Array list of countries
 List<String> countryList = new ArrayList<String>();
 countryList.add("Planks");
 countryList.add("Chest Press");
 countryList.add("Chair Squats");
 countryList.add("Bent Knee PushUp");
 countryList.add("Lying Row");
/* countryList.add("Biceps Curl");
 countryList.add("Push Up");
 countryList.add("Lateral Shoulder Raise");
 countryList.add("Triceps Extensions");
 countryList.add("Dips");
 countryList.add("Hammer Curl");
 countryList.add("Squats");
 countryList.add("Regular Crunch");
 countryList.add("Reverse Crunch");
 countryList.add("Side Crunch");
 countryList.add("Wall Pushups");
 countryList.add("Toe Stands");
 countryList.add("Finger Marching");
 countryList.add("Step Ups");
 countryList.add("Overhead Press");
 countryList.add("Hips Abduction");
 countryList.add("Knee Extension");
 countryList.add("Knee Curl");
 countryList.add("Pelvic Tilt");
 countryList.add("Floor Back Extension");
 countryList.add("Quadriceps Stretch");
 countryList.add("Hamstring/Calf Stretch");
 countryList.add("Chest and Arm Stretch");
 countryList.add("Neck, Upper Back, and Shoulder Stretch");
 countryList.add("Abdominal Curl");
 countryList.add("Chest Press");
 countryList.add("Lunges");
 countryList.add("Upright Row");
 countryList.add("Gaining Grip Strength");*/
    //create an ArrayAdaptar from the String Array
 ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
   R.layout.country_list, countryList);
 ListView listView = (ListView) findViewById(R.id.listView1);
 // Assign adapter to ListView
 listView.setAdapter(dataAdapter);
  
 //enables filtering for the contents of the given ListView
 listView.setTextFilterEnabled(true);

 listView.setOnItemClickListener(new OnItemClickListener() {
  public void onItemClick(AdapterView<?> parent, View view,
    int position, long id) {
      // When clicked, show a toast with the TextView text
   Toast toast = Toast.makeText(getApplicationContext(),
     ((TextView) view).getText(), Toast.LENGTH_LONG);
   toast.setGravity(Gravity.TOP, 25, 300); 
   toast.show();
  }
 });
  
}
}
