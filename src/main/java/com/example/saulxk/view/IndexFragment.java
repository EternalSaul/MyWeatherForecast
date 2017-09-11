package com.example.saulxk.view;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.saulxk.model.WeatherResponse;
import com.example.saulxk.model.Zhishu;
import com.example.saulxk.weatherforecast.R;

import java.util.List;

/**
 * Created by Saulxk on 2016/12/14.
 */

public class IndexFragment extends Fragment implements IWeatherDispalyFragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.index_fragment,container,false);
    }
    public void displayCompletely(WeatherResponse weatherResponse, AppCompatActivity main){
        List<Zhishu> Indexes=weatherResponse.getZhishus();
        TextView WeaterIndexText;
        int i=0;
        for (Zhishu zhishu:Indexes){
            if(i>0) {
                try {
                    WeaterIndexText = (TextView) main.findViewById(R.id.class.getField("WeatherIndexText" + String.valueOf(i)).getInt(new R.id()));
                    WeaterIndexText.setText(zhishu.getName()+"\n"+zhishu.getValue());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
            }
            i++;
        }
    }
}
