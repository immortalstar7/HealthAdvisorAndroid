package com.learn2crack.library;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
<<<<<<< HEAD
=======
import android.os.Bundle;
//import android.support.v4.app.FragmentActivity;
//import android.support.v4.app.FragmentTabHost;
>>>>>>> origin/master

import com.learn2crack.R;
import com.learn2crack.Registered;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class Profile extends Activity {

    private static String KEY_SUCCESS = "success";
    private static String KEY_HEIGHT = "height";
    private static String KEY_WEIGHT = "weight";
    private static String KEY_AGE = "age";
    private static String KEY_ERROR = "error";


    EditText inputHeight;
    EditText inputWeight;
    EditText inputAge;
    Button btnUpdate;
    TextView upateerrmsg;
    Button cancel;



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);


        inputHeight = (EditText) findViewById(R.id.height);
        inputWeight = (EditText) findViewById(R.id.weight);
        inputAge = (EditText) findViewById(R.id.age);
        btnUpdate = (Button) findViewById(R.id.update);
        upateerrmsg = (TextView) findViewById(R.id.update_error);


        Button cancel = (Button) findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), Profile.class);
                startActivityForResult(myIntent, 0);
                finish();
            }

        });
        findViewById(R.id.update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((!inputHeight.getText().toString().equals("")) && (!inputWeight.getText().toString().equals("")) && (!inputAge.getText().toString().equals(""))) {
                    if (inputHeight.getText().toString().length() >=2) {
                        NetAsync(view);

                    } else {
                        Toast.makeText(getApplicationContext(),
                                "Height should be minimum 2 characters", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),
                            "One or more fields are empty", Toast.LENGTH_SHORT).show();
                }
            }
            private void NetAsync(View view) {
                new NetCheck().execute();

            }
        });
    }


    private class NetCheck extends AsyncTask<String, String, Boolean> {
        private ProgressDialog nDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            nDialog = new ProgressDialog(Profile.this);
            nDialog.setTitle("Updated");
            nDialog.setIndeterminate(false);
            nDialog.setCancelable(true);
            nDialog.show();
        }

        @Override
        protected Boolean doInBackground(String... args) {

            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()) {
                try {
                    URL url = new URL("http://fitbitsample-40998.onmodulus.net/getStepsForUser/2XXCMB");

                    HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
                    urlc.setConnectTimeout(7000);

                    urlc.connect();
                    if (urlc.getResponseCode() == 200) {
                        return true;
                    }
                } catch (MalformedURLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            return false;

        }
        @Override
        protected void onPostExecute(Boolean th){

            if(th == true){
                nDialog.dismiss();
                new ProfileUpdate().execute();
            }
            else{
                nDialog.dismiss();
                upateerrmsg.setText("Error in Network Connection");
            }
        }
    }





    private class ProfileUpdate extends AsyncTask<String, String, JSONObject> {

        /**
         * Defining Process dialog
         **/
        private ProgressDialog pDialog;

        String height,weight,age;
        public ProfileUpdate() {
            // TODO Auto-generated constructor stub
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            height=inputHeight.getText().toString();
            weight=inputWeight.getText().toString();
            age=inputAge.getText().toString();
            pDialog = new ProgressDialog(Profile.this);

            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... args) {


            UserFunctions userFunction = new UserFunctions();
            JSONObject json = userFunction.ProfileUpdate(height,weight,age);

            return json;


        }
        @Override
        protected void onPostExecute(JSONObject json) {
            /**
             * Checks for success message.
             **/
            try {
                if (json != null) {
                    upateerrmsg.setText("");
                    String res = json.getString("_id");



                    upateerrmsg.setText("Successfully Updated");


                    DatabaseHandler db = new DatabaseHandler(getApplicationContext());

                    UserFunctions logout = new UserFunctions();
                    logout.logoutUser(getApplicationContext());


                    Intent registered = new Intent(getApplicationContext(), Registered.class);

                    /**
                     * Close all views before launching Registered screen
                     **/
                    registered.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    pDialog.dismiss();
                    startActivity(registered);


                    finish();

                }


                else{
                    pDialog.dismiss();

                    upateerrmsg.setText("Error occured in updates");
                }

            } catch (JSONException e) {
                e.printStackTrace();


            }
        }}

}









