package com.example.saulxk.view;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.saulxk.controller.WeatherDisplay;
import com.example.saulxk.model.Weather;
import com.example.saulxk.model.WeatherResponse;
import com.example.saulxk.weatherforecast.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Saulxk on 2016/12/14.
 */

public class ChartFragment extends Fragment implements IWeatherDispalyFragment{

    ImageView mNightView= null;//反射出晚间天气视图

    ImageView mDayView= null;//反射出白天天气视图

    TextView mNextdaySummaryView= null;//反射出概要视图

    TextView mNextdayView= null;//反射出天气文本视图

    TextView mDateView=null;//反射出日期文本视图
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.chart_fragment,container,false);

    }
    public void displayCompletely(WeatherResponse weatherResponse, AppCompatActivity main){
        List<Weather> weathers=weatherResponse.getWeathers();
        ArrayList<Integer> highs=new ArrayList<Integer>();
        ArrayList<Integer> lowest=new ArrayList<Integer>();
        int high=-1000,low=1000;//用于记录最高和最低温，首先置为了一个不可能出现的极值
        int i=1;//用于反射序号
        for (Weather weather:weathers){
            int h,l;//设定一天的最大最小值
            h=Integer.parseInt(weather.getHigh().replace("℃","").substring(3));
            l=Integer.parseInt(weather.getLow().replace("℃","").substring(3));
            //获取5天中的最高温度和最低温度
            if(high<h)
                high=h;
            if (l<low)
                low=l;
            //将当天的温度加入链表以供后续制图
            Log.d("smile", "setChartView: "+"listAdds");
            highs.add(h);
            lowest.add(l);
            try {
                mNightView = (ImageView) main.findViewById(R.id.class.getField("NextdayView_Night"+String.valueOf(i)).getInt(new R.id()));
                mDayView = (ImageView) main.findViewById(R.id.class.getField("NextdayView"+String.valueOf(i)).getInt(new R.id()));
                mNextdaySummaryView = (TextView) main.findViewById(R.id.class.getField("NextdaySummary"+String.valueOf(i)).getInt(new R.id()));
                mNextdayView = (TextView) main.findViewById(R.id.class.getField("NextdayText"+String.valueOf(i)).getInt(new R.id()));
                mDateView=(TextView) main.findViewById(R.id.class.getField("DateText"+String.valueOf(i)).getInt(new R.id()));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            //添加当天概要信息
            mNextdaySummaryView.setText(weather.getNight().getType()+"\n"+weather.getDay().getFengxiang()+"\n"+weather.getNight().getFengli());
            mNextdayView.setText(weather.getDay().getType()+"\n"+weather.getDay().getFengxiang()+"\n"+weather.getDay().getFengli());
//            dayView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            //添加当天的天气图片，早间，晚间分开
            mDayView.setImageResource(WeatherDisplay.selectWeatherPic(weather.getDay().getType(),"06:00"));
            mNightView.setImageResource(WeatherDisplay.selectWeatherPic(weather.getNight().getType(),"20:00"));
            mDateView.setText(weather.getDate().substring(weather.getDate().indexOf("日")+1));
            i++;
        }
        //获取图表视图
        LineView lineView=(LineView) main.findViewById(R.id.chartView);
        lineView.setVisibility(LineView.INVISIBLE);
        //将温度数组链表加入到图表视图以制图
        lineView.setHighsAndLows(highs,lowest);
        lineView.setHighsest(high,low);//画高温线
        lineView.setLowsest(high,low);//画低温线
        lineView.setVisibility(LineView.VISIBLE);
    }
}
