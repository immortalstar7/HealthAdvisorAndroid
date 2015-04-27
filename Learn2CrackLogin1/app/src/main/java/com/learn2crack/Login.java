package com.learn2crack;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import org.apache.http.client.methods.HttpPost;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;


import com.learn2crack.library.DatabaseHandler;
import com.learn2crack.library.Profile;
import com.learn2crack.library.UserFunctions;
import com.learn2crack.sensefall.TabBarExample;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Login extends Activity{

    Button btnLogin;
    Button Btnregister;
    Button passreset;
    EditText inputEmail;
    EditText inputPassword;
    private TextView loginErrorMsg;
    /**
     * Called when the activity is first created.
     */
    private static String KEY_SUCCESS = "success";
    private static String KEY_UID = "uid";
    private static String KEY_USERNAME = "uname";
    private static String KEY_FIRSTNAME = "fname";
    private static String KEY_LASTNAME = "lname";
    private static String KEY_EMAIL = "email";
    private static String KEY_CREATED_AT = "created_at";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.pword);
        Btnregister = (Button) findViewById(R.id.registerbtn);
        btnLogin = (Button) findViewById(R.id.login);
        passreset = (Button)findViewById(R.id.passres);
        loginErrorMsg = (TextView) findViewById(R.id.loginErrorMsg);



        passreset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), PasswordReset.class);
                startActivityForResult(myIntent, 0);
                finish();
            }});


        Btnregister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), Register.class);
                startActivityForResult(myIntent, 0);
                finish();
            }});

/**
 * Login button click event
 * A Toast is set to alert when the Email and Password field is empty
 **/
        btnLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), TabBarExample.class);
                startActivityForResult(myIntent, 0);
                finish();
                NetAsync(view);
            }});

               /* if (  ( !inputEmail.getText().toString().equals("")) && ( !inputPassword.getText().toString().equals("")) )
                {
                    //NetAsync(view);
                }
                else if ( ( !inputEmail.getText().toString().equals("")) )
                {
                    Toast.makeText(getApplicationContext(),
                            "Password field empty", Toast.LENGTH_SHORT).show();
                }
                else if ( ( !inputPassword.getText().toString().equals("")) )
                {
                    Toast.makeText(getApplicationContext(),
                            "Email field empty", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),
                            "Email and Password field are empty", Toast.LENGTH_SHORT).show();
                }

            }
        });*/

    }


    /**
     * Async Task to check whether internet connection is working.
     **/

    private class NetCheck extends AsyncTask<String,String,Boolean>
    {
        private ProgressDialog nDialog;

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            nDialog = new ProgressDialog(Login.this);
            nDialog.setTitle("Checking Network");
            nDialog.setMessage("Loading..");
            nDialog.setIndeterminate(false);
            nDialog.setCancelable(true);
            nDialog.show();
        }
        /**
         * Gets current device state and checks for working internet connection by trying Google.
         **/
       /* @Override
        protected Boolean doInBackground(String... args){
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()) {
                HttpClient httpClient = new DefaultHttpClient();
                HttpContext localContext = new BasicHttpContext();
                HttpGet httpGet = new HttpGet("http://fitbitsample-40998.onmodulus.net/getStepsForUser/2XXCMB");
                String text = null;
                try {
                    HttpResponse response = httpClient.execute(httpGet, localContext);
                    HttpEntity entity = response.getEntity();
                    Log.d("@@@ entity RESPONSE", " " + entity);
                    URL url = new URL("http://google.com");
                    HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
                    urlc.setConnectTimeout(3000);
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
        }*/
        protected Boolean doInBackground(String... args){



            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()) {
                try {
                    URL url = new URL("http://fitbitsample-40998.onmodulus.net/loginUser");
                    HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
                    urlc.setConnectTimeout(3000);
                    urlc.setRequestMethod("POST");
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
                new ProcessLogin().execute();
            }
            else{
                nDialog.dismiss();
                loginErrorMsg.setText("Error in Network Connection");
            }
        }
    }

    /**
     * Async Task to get and send data to My Sql database through JSON respone.
     **/

    private class ProcessLogin extends AsyncTask<String, String, JSONObject> {


        private ProgressDialog pDialog;

        String email,password;
        public ProcessLogin(){

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            inputEmail = (EditText) findViewById(R.id.email);
            inputPassword = (EditText) findViewById(R.id.pword);
            email = inputEmail.getText().toString();
            password = inputPassword.getText().toString();
            pDialog = new ProgressDialog(Login.this);
            // pDialog.setTitle("Contacting Servers");
            pDialog.setMessage("Logging in ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... args) {

            UserFunctions userFunction = new UserFunctions();
            JSONObject json = userFunction.loginUser(email, password);
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            try {
                if (json != null) {
                    loginErrorMsg.setText("");
                    String res = json.getString("_id");

                    // String red = json.getString(KEY_ERROR);

                    //if(Integer.parseInt(res) == 1){
                    // pDialog.setTitle("Getting Data");
                    //pDialog.setMessage("Loading Info");

                    loginErrorMsg.setText("Successfully Registered");


                    DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                    // JSONObject json_user = json.getJSONObject("user");

                    /**
                     * Removes all the previous data in the SQlite database
                     **/

                    UserFunctions logout = new UserFunctions();
                    logout.logoutUser(getApplicationContext());
                    //db.addUser(json_user.getString(KEY_FIRSTNAME),json_user.getString(KEY_LASTNAME),json_user.getString(KEY_EMAIL),json_user.getString(KEY_USERNAME),json_user.getString(KEY_UID),json_user.getString(KEY_CREATED_AT));
                    /**
                     * Stores registered data in SQlite Database
                     * Launch Registered screen
                     **/

                    Intent upanel = new Intent(getApplicationContext(), Main.class);

                    /**
                     * Close all views before launching Registered screen
                     **/
                    upanel.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    pDialog.dismiss();
                    startActivity(upanel);


                    finish();

                }


                else{
                    pDialog.dismiss();

                    loginErrorMsg.setText("Error occured in registration");
                }

            } catch (JSONException e) {
                e.printStackTrace();


            }
        }}
    public void NetAsync(View view){
        new NetCheck().execute();
    }
}