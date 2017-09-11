package com.example.saulxk.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

/**
 * Created by Saulxk on 2016/12/15.
 */

public class LocationUtil {
    private Location mLocation;
    private LocationManager mLocationManager;
    private String mLocationProvider;
    Context mContext;

    public LocationUtil() {
    }

    public LocationUtil(Context context) {
        mContext = context;
        setLocation();
    }

    public void setLocation() {
        //获取地理位置管理器
        mLocationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        //获取所有可用的位置提供器
        mLocationProvider = LocationManager.GPS_PROVIDER;

        if (mLocationProvider != null) {
            //监视地理位置变化
            if (Build.VERSION.SDK_INT>=23)
            if (ActivityCompat.checkSelfPermission(mContext.getApplicationContext(), ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(mContext.getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) mContext.getApplicationContext(),new String[]{ACCESS_COARSE_LOCATION,ACCESS_FINE_LOCATION},1);
            }
            mLocation = mLocationManager.getLastKnownLocation(mLocationProvider);
            mLocationManager.requestLocationUpdates(mLocationProvider, 1, 1, locationListener);
        } else {
            //无法定位：1、提示用户打开定位服务；2、跳转到设置界面
            Toast.makeText(mContext, "无法定位，请打开定位服务", Toast.LENGTH_SHORT).show();
            Intent i = new Intent();
            i.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            mContext.startActivity(i);
        }
    }

    /**
     * LocationListern监听器
     * 参数：地理位置提供器、监听位置变化的时间间隔、位置变化的距离间隔、LocationListener监听器
     */
    LocationListener locationListener = new LocationListener() {

        @Override
        public void onStatusChanged(String provider, int status, Bundle arg2) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }

        @Override
        public void onLocationChanged(Location location) {
            //如果位置发生变化,重新显示
            mLocation=location;
        }
    };
    public String getCityName(){
        if(mLocation==null)
            return null;
        Geocoder gc = new Geocoder(mContext, Locale.getDefault());
        List<Address> locationList = null;
        try {
            locationList = gc.getFromLocation(mLocation.getLatitude(), mLocation.getLongitude(), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (locationList.size() != 0) {
            Address address = locationList.get(0);//得到Address实例
            if (address != null) {
                String locality = address.getLocality();//得到城市名称，比如：北京市
                return locality.substring(0,locality.length()-1);
            }
        }
        return null;
    }
}
