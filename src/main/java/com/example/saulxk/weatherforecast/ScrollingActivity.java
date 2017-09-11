package com.example.saulxk.weatherforecast;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.saulxk.controller.WeatherDisplay;
import com.example.saulxk.util.HttpConnectionUtil;
import com.example.saulxk.view.SelectDialog;

import java.lang.reflect.Method;

public class ScrollingActivity extends AppCompatActivity implements View.OnClickListener {
    private Handler handler;
    private LocationBroadcast mLocationBroadcast;
    private IntentFilter mIntentFilter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        mIntentFilter=new IntentFilter(Common.LOCATION_ACTION);
        mLocationBroadcast=new LocationBroadcast();
        registerReceiver(mLocationBroadcast,mIntentFilter);
        handler=new Handler();
        findViewById(R.id.chartView).setVisibility(View.GONE);
        WeatherDisplay display=new WeatherDisplay(this);
        new Thread(display).start();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (menu != null) {
            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {//反射出MenuBuilder内部框架类
                try {
                    Method m = menu.getClass().getDeclaredMethod(
                            "setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            SelectDialog selectDialog=new SelectDialog(this);
            selectDialog.show();
            return true;
        }
        if (id== R.id.action_location){
            Intent intent=new Intent(this,LocationService.class);
            startService(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
            WeatherDisplay flush = new WeatherDisplay(ScrollingActivity.this);
            new Thread(flush).start();
            if (HttpConnectionUtil.isNetworkConnected(ScrollingActivity.this)) {
                Snackbar.make(v, "天气信息已更新", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            } else {
                Snackbar.make(v, "天气信息更新失败，请检查网络", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
                break;
        }
    }
}
