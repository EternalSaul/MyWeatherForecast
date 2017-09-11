package com.example.saulxk.view;

import android.support.v7.app.AppCompatActivity;

import com.example.saulxk.model.WeatherResponse;

/**
 * Created by Saulxk on 2016/12/18.
 */

public interface IWeatherDispalyFragment {
   void displayCompletely(WeatherResponse weatherResponse, AppCompatActivity main);
}
