package com.lge.pickitup;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Utils {

    static final String LOG_TAG = "Utils";

    static final String KEY_DB_DATE = "date";
    static final String KEY_COURIER_NAME ="courier_name";

    static Location mCurrent;
    static LocationManager mLocationMgr;
    static Context mContext;

    public static String getKeyHash(final Context context) {
        PackageManager pm = context.getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }

        for (Signature signature : packageInfo.signatures) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                return Base64.encodeToString(md.digest(), Base64.NO_WRAP);
            } catch (NoSuchAlgorithmException e) {
                Log.w(LOG_TAG, "Unable to get MessageDigest. signature=" + signature, e);
            }
        }
        return null;
    }

    public static String getTodayDateStr() {
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formattedTime = sdf.format(date);
        return formattedTime;
    }

    @SuppressLint("MissingPermission")
    protected static void initLocation(Context context) {
        mContext = context;

        Log.i(LOG_TAG, "Start search location");
        mLocationMgr = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        mCurrent = mLocationMgr.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (mCurrent != null) {
            Log.i(LOG_TAG, "current (last) : " + mCurrent.getLatitude() + "/" + mCurrent.getLongitude());
            Toast.makeText(context, "current (last)", Toast.LENGTH_SHORT).show();
        } else {
            List<String> providers = mLocationMgr.getProviders(true);
            Location bestLocation = null;
            for (String provider : providers) {
                Location l = mLocationMgr.getLastKnownLocation(provider);
                if (l == null) {
                    continue;
                }
                if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                    bestLocation = l;
                }
            }

            if (null == bestLocation) {
                Log.i(LOG_TAG, "bestLocation is null");
                Toast.makeText(context, "bestLocation is null", Toast.LENGTH_SHORT).show();
                mLocationMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 1f, mGPSLocationListener);
                mLocationMgr.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1, 1f, mNetworkLocationListener);
            } else {
                mCurrent = bestLocation;
                Log.i(LOG_TAG, "current : " + bestLocation.getLatitude() + "/" + bestLocation.getLongitude());
                Toast.makeText(context, "bestLocation", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private static final LocationListener mGPSLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            mLocationMgr.removeUpdates(mGPSLocationListener);
            mLocationMgr.removeUpdates(mNetworkLocationListener);

            mCurrent = location;
            Log.i(LOG_TAG, "current(mGPSLocationListener) : " + mCurrent.getLatitude() + "/" + mCurrent.getLongitude());
            Toast.makeText(mContext, "current(mGPSLocationListener)", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            // Do nothing
        }

        @Override
        public void onProviderEnabled(String provider) {
            // Do nothing
        }

        @Override
        public void onProviderDisabled(String provider) {
            // Do nothing
        }
    };

    private static final LocationListener mNetworkLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            mLocationMgr.removeUpdates(mGPSLocationListener);
            mLocationMgr.removeUpdates(mNetworkLocationListener);

            mCurrent = location;
            Log.i(LOG_TAG, "current(mNetworkLocationListener) : " + mCurrent.getLatitude() + "/" + mCurrent.getLongitude());
            Toast.makeText(mContext, "current(mNetworkLocationListener)", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };
}
