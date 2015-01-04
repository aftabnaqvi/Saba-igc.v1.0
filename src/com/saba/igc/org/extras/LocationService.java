package com.saba.igc.org.extras;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;

/**
 * @author Syed Aftab Naqvi
 * @create Jan 03, 2015
 * @version 1.0
 */

public class LocationService extends Service implements LocationListener {
    protected Context mContext; 

    private final long MIN_DISTANCE_FOR_UPDATE = 10;
    private final long MIN_TIME_FOR_UPDATE = 1000 * 60 * 2;

    public LocationService(Context context) {
    	mContext = context;
    }

    public Location getLocation(String provider) {
    	LocationManager locationManager = (LocationManager)mContext
                .getSystemService(LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(provider)) {
            locationManager.requestLocationUpdates(provider,
                    MIN_TIME_FOR_UPDATE, MIN_DISTANCE_FOR_UPDATE, this);
            if (locationManager != null) {
                return locationManager.getLastKnownLocation(provider);
            }
        }
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
}