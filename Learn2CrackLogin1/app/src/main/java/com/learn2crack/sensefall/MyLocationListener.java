package com.learn2crack.sensefall;

/**
 * Created by Mansi Joshi on 3/15/15.
 */
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;


public class MyLocationListener extends Service implements LocationListener {
	private final Context context;
	boolean isGPSEnabled = false;
	boolean canGetLocation = false;
	boolean isNetworkEnabled = false;
	double latitude;
	double longitude;
	Location location;
	
	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
	private static final long MIN_TIME_BW_UPDATES = 1000*60*1;
	
	protected LocationManager lManager;
	
	public MyLocationListener(Context context) {
		// TODO Auto-generated constructor stub
		this.context  = context;
		getLocation();
	}
	
	public Location getLocation(){
		try {
			lManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
			isGPSEnabled = lManager.isProviderEnabled(lManager.GPS_PROVIDER);
			isNetworkEnabled = lManager.isProviderEnabled(lManager.NETWORK_PROVIDER);
			
			if(!isGPSEnabled && !isNetworkEnabled){
				
			}else{
				this.canGetLocation = true;
				
				if (isNetworkEnabled) {
					
					lManager.requestLocationUpdates(
							LocationManager.NETWORK_PROVIDER,
							MIN_TIME_BW_UPDATES,
							MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

					if (lManager != null) {
						location = lManager
								.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

						if (location != null) {
							
							latitude = location.getLatitude();
							longitude = location.getLongitude();
						}
					}

			}
		}
		}catch (Exception e) {
			// TODO: handle exception
		}
		return location;
	}
    @Override
    public void onLocationChanged(Location loc) {
        
    }

    @Override
    public void onProviderDisabled(String provider) {}

    @Override
    public void onProviderEnabled(String provider) {}

   
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	
}