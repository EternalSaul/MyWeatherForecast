package com.example.saulxk.weatherforecast;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.example.saulxk.util.LocationUtil;

/**
 * Created by Saulxk on 2016/12/20.
 */

public class LocationService extends Service {
    private LocationUtil mLocationUtil;
    private Context mContext;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    /*
    * 得到城市信息后发送广播，并终止Service
    * */
    protected void sendLocationBroad(){
        String city=mLocationUtil.getCityName();
        if(city!=null) {
            if (city != null) {
                Intent intent = new Intent(Common.LOCATION_ACTION);
                intent.putExtra(Common.LOCATION, city);
                sendBroadcast(intent);
            }
            stopSelf();
        }
        else {
            Toast.makeText(mContext, "无法定位，请检查定位服务", Toast.LENGTH_SHORT).show();
            stopSelf();
        }
    }
    @Override
    public void onCreate() {
        super.onCreate();
        mContext=getBaseContext();
        mLocationUtil=new LocationUtil(getBaseContext());
        sendLocationBroad();;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
}
