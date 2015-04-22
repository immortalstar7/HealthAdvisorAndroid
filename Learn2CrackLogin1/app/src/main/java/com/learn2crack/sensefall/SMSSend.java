package com.learn2crack.sensefall;


import java.util.List;
import java.util.UUID;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.format.DateUtils;
import android.util.Log;

//import com.getpebble.android.kit.PebbleKit;
//import com.getpebble.android.kit.PebbleKit.PebbleDataReceiver;
//import com.getpebble.android.kit.util.PebbleDictionary;


//public class SMSSend extends PebbleDataReceiver {
public class SMSSend {
    //private static final UUID PEBBLE_APP_UUID = UUID.fromString("5ed10362-a625-41e6-b35c-e6b10feb71e6");
    private final String PREF_PHONE_NUMBERS = "phone_numbers";
    private String message = "Help me!";
    private LocationManager mLocationManager;
    private Context mContext;
    private String PREF_CONFIG = "configuration";
    private int thresholdLevel;

    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            sendSMS(location, false);
            mLocationManager.removeUpdates(this);
        }

        @Override
        public void onProviderDisabled(String provider) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onStatusChanged(String provider, int status,
                Bundle bundle) {
        }
    };

    /*public SMSSend() {
        super(PEBBLE_APP_UUID);
    }*/
    public SMSSend(Context context) {
    	mContext = context;
	}
    /*public void receiveData(Context context, int transactionId,
            PebbleDictionary data) {
        PebbleKit.sendAckToPebble(context, transactionId);

        mContext = context;
        loadConfig();

        if (data.getInteger(1).intValue() == 42) {
            mLocationManager = (LocationManager) context
                    .getSystemService(Context.LOCATION_SERVICE);

            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_FINE);
            criteria.setCostAllowed(false);

            String mProviderName = mLocationManager.getBestProvider(
                    criteria, false);
            Location lastLocation = mLocationManager
                    .getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

            if (lastLocation == null
                    || (System.currentTimeMillis()
                            - lastLocation.getTime() > DateUtils.MINUTE_IN_MILLIS * 10)) {
                mLocationManager.requestLocationUpdates(mProviderName, 0,
                        0, mLocationListener);
                sendSMS(lastLocation, true);
            } else if ((System.currentTimeMillis()
                    - lastLocation.getTime() < DateUtils.MINUTE_IN_MILLIS * 10)) {
                sendSMS(lastLocation, false);
            }

        } else if (data.getInteger(1).intValue() == 12) {
            sendDataToWatch();
        }

    }

    public void sendDataToWatch() {
        // Build up a Pebble dictionary
        PebbleDictionary data = new PebbleDictionary();

        data.addUint8(0, (byte) tresholdLevel);

        // Send the assembled dictionary to the weather watch-app;
        //PebbleKit.sendDataToPebble(mContext,UUID.fromString("5ed10362-a625-41e6-b35c-e6b10feb71e6"),data);
    }*/

    protected void loadConfig() {
        SharedPreferences config = mContext.getSharedPreferences(
                PREF_CONFIG, 0);
        thresholdLevel = Integer.parseInt(config.getString("thresholdValue",
                "50"));
        message = config.getString("msg", "Help Me!");
    }

    protected void sendSMS(Location location, boolean last) {
        Geocoder geocoder = new Geocoder(mContext);
        String addressText = "";

        try {
            List<Address> addresses = geocoder.getFromLocation(
                    location.getLatitude(), location.getLongitude(), 1);

            if (addresses.size() > 0) {
                Address a = addresses.get(0);
                addressText = "Address: ";
                for (int i = 0; i <= a.getMaxAddressLineIndex(); i++) {
                    addressText += a.getAddressLine(i) + " ";
                    Log.d("@@@@@@ location", addressText);
                }
            }
        } catch (Exception e) {
            // unable to geocode
            addressText = "";
        }

        String locationString;
        if (location != null && !last) {
            locationString = " New location: " + location.getLatitude()
                    + ", " + location.getLongitude() + " ";
        } else if (last) {
            locationString = " Last known location: "
                    + location.getLatitude() + ", "
                    + location.getLongitude() + " ";
        } else {
            locationString = "Waiting for location";
        }

        SharedPreferences phoneNumbers = mContext.getSharedPreferences(
                PREF_PHONE_NUMBERS, 0);
        String currPhoneNumber;

        for (int i = 0; !((currPhoneNumber = phoneNumbers.getString(
                Integer.toString(i), "")).equals("")); i++) {
            try {
                SmsManager smsManager = SmsManager.getDefault();
                Log.d("@@@@@@ SmsManager.getDefault()", SmsManager.getDefault().toString());
                smsManager.sendMultipartTextMessage(
                        currPhoneNumber,
                        null,
                        smsManager.divideMessage(message + locationString
                                + addressText), null, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
    public void sendSMS() {
        

        
       

        SharedPreferences phoneNumbers = mContext.getSharedPreferences(
                PREF_PHONE_NUMBERS, 0);
        String currPhoneNumber;
        Log.d("phoneNumbers",""+phoneNumbers);
        Log.d("currPhoneNumber",""+phoneNumbers.getString(Integer.toString(0),""));
        for (int i = 0; !((currPhoneNumber = phoneNumbers.getString(
                Integer.toString(i), "")).equals("")); i++) {
            try {
                SmsManager smsManager = SmsManager.getDefault();
                //Log.d("@@@@@@ SmsManager.getDefault()", ""+SmsManager.getDefault());
                smsManager.sendMultipartTextMessage(
                        currPhoneNumber,
                        null,
                        smsManager.divideMessage("THIS IS TEST" 
                               ), null, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
