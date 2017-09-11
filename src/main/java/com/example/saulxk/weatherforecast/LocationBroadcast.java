package com.example.saulxk.weatherforecast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.widget.Toast;

import com.example.saulxk.controller.WeatherDisplay;

/**
 * Created by Saulxk on 2016/12/20.
 */
public class LocationBroadcast extends BroadcastReceiver {
    @Override
    /*
    * 广播接受
    * */
    public void onReceive(Context context, Intent intent) {
        /*
        * 判断context是不是动态注册的context，如果不是的话直接不处理
        * 因为这个小程序中静态和动态都注册了该广播，
        * 实际运行时会受到两个，Context分别是ReceiverRestrictedContext（静态），ScrollingActivity（动态）
        * */
        if (context instanceof ScrollingActivity) {
            if (intent.getAction().equals(Common.LOCATION_ACTION)) {
                Bundle bundle = intent.getExtras();
                String city = bundle.getString(Common.LOCATION);
                display((ScrollingActivity) context,city);
            }
        }
    }
    /*
    * 参数：
    * context: 即main activity
    *city: 通过位置服务取得的城市名
    * 本函数用来在接受到广播后重置整个显示页面
    * */
    protected void display(final ScrollingActivity context, final String city){
        if (city != null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    WeatherDisplay weatherDisplay = new WeatherDisplay(context);
                    weatherDisplay.setWeatherResponseByCityName(city);
                    weatherDisplay.display();
                    Snackbar.make(context.findViewById(R.id.fab), "已经定位到:"+city, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                }
            }).start();
        } else {
            Toast.makeText(context, "无法取得位置", Toast.LENGTH_LONG);
        }
    }
}
