package com.learn2crack;


import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.learn2crack.library.DatabaseHandler;
import com.learn2crack.library.UserFunctions;
import com.learn2crack.sensefall.TabBarExample;

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
            @Override
            public void onClick(View view) {
                String email1 = String.valueOf(inputEmail.getText());
                Bundle b = new Bundle();
                b.putString("email",email1);
                Intent myIntent = new Intent(getApplicationContext(), TabBarExample.class);
                myIntent.putExtras(b);
                startActivity(myIntent);
                NetAsync(view);
            }
        });
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

        protected Boolean doInBackground(String... args){



            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()) {
                try {
                    URL url = new URL("http://fitbitsample-40998.onmodulus.net/loginUser");
                    HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
                    urlc.setConnectTimeout(7000);
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



                    loginErrorMsg.setText("Successfully Registered");


                    DatabaseHandler db = new DatabaseHandler(getApplicationContext());

                    UserFunctions logout = new UserFunctions();
                    logout.logoutUser(getApplicationContext());



                    Intent upanel = new Intent(getApplicationContext(),Main.class);


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