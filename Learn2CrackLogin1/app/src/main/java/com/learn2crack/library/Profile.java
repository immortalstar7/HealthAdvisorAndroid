package com.learn2crack.library;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.style.UpdateAppearance;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;
//import android.support.v4.app.FragmentActivity;
//import android.support.v4.app.FragmentTabHost;

import com.learn2crack.Login;
import com.learn2crack.Main;
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
    Button cancel;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        inputHeight = (EditText) findViewById(R.id.height);
        inputWeight = (EditText) findViewById(R.id.weight);
        inputAge = (EditText) findViewById(R.id.age);
        btnUpdate = (Button) findViewById(R.id.update);

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
            //nDialog.setMessage("Loading..");
            nDialog.setTitle("Updated");
            nDialog.setIndeterminate(false);
            nDialog.setCancelable(true);
            nDialog.show();
        }

        @Override
        protected Boolean doInBackground(String... args) {


            class ProcessRegister extends AsyncTask<String, String, JSONObject> {

                /**
                 * Defining Process dialog
                 */
                private ProgressDialog pDialog;

                String height, weight,age;

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    inputHeight = (EditText) findViewById(R.id.height);
                    inputWeight = (EditText) findViewById(R.id.weight);
                    inputAge = (EditText) findViewById(R.id.age);
                    pDialog = new ProgressDialog(Profile.this);
                    pDialog.setCancelable(true);
                    pDialog.show();
                }

                @Override
                protected JSONObject doInBackground(String... args) {


                    UserFunctions userFunction = new UserFunctions();
                    JSONObject json = userFunction.registerProfile(height, weight,age);

                    return json;


                }


       /* protected void onPostExecute(JSONObject json) {
            /**
             * Checks for success message.
             **/
           /* try {
                if (json.getString(KEY_SUCCESS) != null) {
                    //registerErrorMsg.setText("");
                    String res = json.getString(KEY_SUCCESS);

                    String red = json.getString(KEY_ERROR);

                    if(Integer.parseInt(res) == 1){
                        pDialog.setTitle("Getting Data");
                        pDialog.setMessage("Loading Info");

                       // registerErrorMsg.setText("Successfully Registered");


                        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                        JSONObject json_user = json.getJSONObject("user");


                        UserFunctions logout = new UserFunctions();
                        logout.logoutUser(getApplicationContext());
                        db.addUser(json_user.getString(KEY_HEIGHT),json_user.getString(KEY_WEIGHT)));


                        Intent registered = new Intent(getApplicationContext(), Registered.class);

                        /**
                         * Close all views before launching Registered screen
                         **/
                       /* registered.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        pDialog.dismiss();
                        startActivity(registered);


                        finish();
                    }

                }


            } catch (JSONException e) {
                e.printStackTrace();


            }
        }*/
            }
            return null;
        }
    }

}









