package com.example.saulxk.view;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.saulxk.model.Weather;
import com.example.saulxk.model.WeatherResponse;
import com.example.saulxk.weatherforecast.R;

import java.util.List;

import static com.example.saulxk.controller.WeatherDisplay.selectWeatherPic;

/**
 * Created by Saulxk on 2016/12/15.
 */

public class TodayFragment extends Fragment implements IWeatherDispalyFragment{
    ImageView mImageView;
    TextView mTodayWeather;
    TextView mTodaySummary;
    TextView mTodayWeatherDeatial;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.today_fragment,container,false);
    }
    public void displayCompletely(WeatherResponse weatherResponse, AppCompatActivity main){
        mImageView=(ImageView) main.findViewById(R.id.WeatherImage);
        mTodayWeather=(TextView) main.findViewById(R.id.TodayWeather) ;
        mTodaySummary=(TextView) main.findViewById(R.id.TodayWeatherSummary);
        mTodayWeatherDeatial=(TextView) main.findViewById(R.id.TodayWeatherDetail);
        Toolbar toolbar = (Toolbar) main.findViewById(R.id.toolbar);
        String time=weatherResponse.getUpdatetime();//获取更新时间
        List<Weather> weather=weatherResponse.getWeathers();//获取5天内的天气预报链表
        int type=selectWeatherPic(weather.get(0).getDay().getType(),time);
        mImageView.setImageResource(type);
        mTodayWeather.setText("  "+weatherResponse.getWendu()+"°");
        String Detail=getTodayDetail(weatherResponse);
        mTodayWeatherDeatial.setText(Detail);
        toolbar.setTitle(weatherResponse.getCity()+","+weather.get(0).getDate());
        CollapsingToolbarLayout collapsingToolbarLayout= (CollapsingToolbarLayout) main.findViewById(R.id.toolbar_layout);
        collapsingToolbarLayout.setTitle(weatherResponse.getCity()+","+weather.get(0).getDate().replace("日","日,"));
        Log.d("saul", "displayCompletely: "+toolbar.getTitle());
        main.setSupportActionBar(toolbar);
        mTodaySummary.setText(weather.get(0).getDay().getType());
    }
    public String getTodayDetail(WeatherResponse weatherResponse) {//获取一份本日概要
        String Detail="  风向:"+weatherResponse.getFengxiang();
        Detail+="  风力:"+weatherResponse.getFengli()+"\n";
        Detail+="  湿度:"+weatherResponse.getShidu()+"\n\n";
        Detail+="  日出时间:"+weatherResponse.getSunrise();
        Detail+="  日落时间:"+weatherResponse.getSunset()+"\n";
        Detail+="  更新时间:"+weatherResponse.getUpdatetime()+"\n";
        return Detail;
    }
}
