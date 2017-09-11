package com.example.saulxk.controller;

import android.app.FragmentManager;
import android.content.SharedPreferences;

import com.example.saulxk.model.Weather;
import com.example.saulxk.model.WeatherResponse;
import com.example.saulxk.model.Zhishu;
import com.example.saulxk.util.SharedPreferencesUtil;
import com.example.saulxk.util.WeatherUtil;
import com.example.saulxk.view.IWeatherDispalyFragment;
import com.example.saulxk.weatherforecast.R;
import com.example.saulxk.weatherforecast.ScrollingActivity;

import org.dom4j.Document;

import java.util.List;


/**
 * Created by Saulxk on 2016/12/11.
 */

public class WeatherDisplay implements Runnable{
    WeatherResponse weatherResponse;
    String mCityName;
    List<Weather> weathers;
    List<Zhishu> zhishus;
    ScrollingActivity main;
    public WeatherDisplay(ScrollingActivity main){
        this.main=main;
    }
    /*
    * 线程函数，主要用来将文件和HTTP请求从主线程中分离
    * */
    @Override
    public void run() {
        if (weatherResponse==null)
            this.setWeatherResponseByCityName(null);
        WeatherDisplay.this.display();
    }
    /*
    * 显示函数，用来将整个天气activity里的各个Fragement展示
    * */
    public void display(){
        main.getHandler().post(new Runnable() {
        @Override
        public void run() {
                FragmentManager fragmentManager=main.getFragmentManager();
                Integer frags[]={R.id.todayfrag,R.id.chartfrag,R.id.indexfrag};
                for (Integer i:frags){
                    ((IWeatherDispalyFragment) fragmentManager.findFragmentById(i)).displayCompletely(weatherResponse,main);
                }
            //                IWeatherDispalyFragment iWeatherDispalyFragments[]=new IWeatherDispalyFragment[3];
//                iWeatherDispalyFragments[0]= (IWeatherDispalyFragment) fragmentManager.findFragmentById(R.id.todayfrag);
//            iWeatherDispalyFragments[1]= (IWeatherDispalyFragment) fragmentManager.findFragmentById(R.id.chartfrag);
//            iWeatherDispalyFragments[2]= (IWeatherDispalyFragment) fragmentManager.findFragmentById(R.id.indexfrag);
//                for (IWeatherDispalyFragment i:iWeatherDispalyFragments){
//                    i.displayCompletely(weatherResponse,main);
//                }
            }
        });
    }
    /*
    * 获取配置文件中默认的城市名以显示
    * */
    protected String getDefaultCity(){
        SharedPreferences sharedPreferences=SharedPreferencesUtil.getSharedPreferences(main);
        String city=sharedPreferences.getString("city","北京");
        return city;
    }
    /*
    * 参数：city ：查询XML的城市名
    * 设置WeatherResponse
    * */
    public void setWeatherResponseByCityName(String city){
        Document document;
        if(city!=null){
            SharedPreferences.Editor editor=SharedPreferencesUtil.getEditor(main);
            editor.putString("city",city);
            editor.commit();
        }
        else {
            city=this.getDefaultCity();
        }
        WeatherUtil weatherUtil=new WeatherUtil();
        document=weatherUtil.getWeatherXML(city,main);
        if (document!=null)
        weatherResponse=new WeatherResponse(document.getRootElement());
    }
    public static int selectWeatherPic(String s,String time){
            int i=Integer.parseInt(time.substring(0,2));
            if (i>19||i<5){
                i=-1;
        }
        if(s.contains("晴")){
            if(s.contains("云")){
                if (i<0)
                    return R.mipmap.cloudy3_night;
                return R.mipmap.cloudy3;
            }
            else if(i<0)
                return R.mipmap.sunny_night;
            return R.mipmap.sunny;
        }
        else if(s.contains("雪")){
            if(s.contains("雨")){
                return R.mipmap.sleet;
            }
            else if(s.contains("小雪")){
                if (i<0)
                    return R.mipmap.snow1_night;
                return  R.mipmap.snow1;
            }
            else if(s.contains("中雪")){
                if (i<0)
                    return R.mipmap.snow2_night;
                return  R.mipmap.snow2;
            }
            else if(s.contains("大雪")){
                if (i<0)
                    return R.mipmap.snow3_night;
                return  R.mipmap.snow3;
            }
            else{
                return R.mipmap.snow5;
            }
        }
        else if(s.contains("雨")){
            if(s.contains("小雨")) {
                if (i<0)
                    return R.mipmap.shower1_night;
                return R.mipmap.shower1;
            }
            else if(s.contains("中雨")){
                if (i<0)
                    return R.mipmap.shower2_night;
                return  R.mipmap.shower2;
            }
            else if (s.contains("大雨")){
                return R.mipmap.shower3;
            }
            else{
                return R.mipmap.tstorm3;
            }
        }
        else if(s.contains("雹")){
            return R.mipmap.hail;
        }
        else if(s.contains("云")){
            if (i<0)
                return R.mipmap.cloudy4_night;
            return R.mipmap.cloudy4;
        }
        else if(s.contains("阴")){
            return R.mipmap.cloudy5;
        }
        else if(s.contains("雾")){
            if (i<0)
                return R.mipmap.mist_night;
            return R.mipmap.mist;
        }
        else if(s.contains("霾")){
            if (i<0)
                return R.mipmap.fog_night;
            return R.mipmap.fog;
        }
        return 0;
    }
    public void setWeatherResponse(WeatherResponse weatherResponse) {
        this.weatherResponse = weatherResponse;
    }

    public WeatherResponse getWeatherResponse() {
        return weatherResponse;
    }

    public ScrollingActivity  getMain() {
        return main;
    }

    public void setMain(ScrollingActivity main) {
        this.main = main;
    }
//    public void displayTheBar() throws IOException, DocumentException {
//        WeatherUtil util=new WeatherUtil();
//        Document document=util.getWeatherByCityName("南昌");
//        Log.d("saul", "displayTheBar: "+document.asXML());
//        weatherResponse=new WeatherResponse(document.getRootElement());
//        Log.d("saul", "displayTheBar: "+weatherResponse);
//    }

    //备份函数
//    public String getTodayDetail() {//获取一份本日概要
//        String Detail="  风向:"+weatherResponse.getFengxiang();
//        Detail+="  风力:"+weatherResponse.getFengli()+"\n";
//        Detail+="  湿度:"+weatherResponse.getShidu()+"\n\n";
//        Detail+="  日出时间:"+weatherResponse.getSunrise();
//        Detail+="  日落时间:"+weatherResponse.getSunset()+"\n";
//        Detail+="  更新时间:"+weatherResponse.getUpdatetime()+"\n";
//        return Detail;
//    }
//    public void setChartView()  {
//        weathers=weatherResponse.getWeathers();
//        zhishus=weatherResponse.getZhishus();
//        ArrayList<Integer> highs=new ArrayList<Integer>();
//        ArrayList<Integer> lowest=new ArrayList<Integer>();
//
//        int i=0;//用于反射序号
//        int high=-1000,low=1000;//用于记录最高和最低温，首先置为了一个不可能出现的极值
//        TextView WeaterIndexText=null;//反射出各种指数说明
//        ImageView nightView= null;//反射出晚间天气视图
//        ImageView dayView= null;//反射出白天天气视图
//        TextView NextdaySummaryView= null;//反射出概要视图
//        TextView NextdayView= null;//反射出天气文本视图
//        for (Zhishu zhishu:zhishus){
//            if(i>0) {
//                try {
//                    WeaterIndexText = (TextView) main.findViewById(R.id.class.getField("WeatherIndexText" + String.valueOf(i)).getInt(new R.id()));
//                    WeaterIndexText.setText(zhishu.getName()+"\n"+zhishu.getValue());
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                } catch (NoSuchFieldException e) {
//                    e.printStackTrace();
//                }
//            }
//            i++;
//        }
//        i=1;
//        for (Weather weather:weathers){
//            int h,l;//设定一天的最大最小值
//            h=Integer.parseInt(weather.getHigh().replace("℃","").substring(3));
//            l=Integer.parseInt(weather.getLow().replace("℃","").substring(3));
//            //获取5天中的最高温度和最低温度
//            if(high<h)
//                high=h;
//            if (l<low)
//                low=l;
//            //将当天的温度加入链表以供后续制图
//            Log.d("smile", "setChartView: "+"listAdds");
//            highs.add(h);
//            lowest.add(l);
//            try {
//                nightView = (ImageView) main.findViewById(R.id.class.getField("NextdayView_Night"+String.valueOf(i)).getInt(new R.id()));
//                dayView = (ImageView) main.findViewById(R.id.class.getField("NextdayView"+String.valueOf(i)).getInt(new R.id()));
//                NextdaySummaryView = (TextView) main.findViewById(R.id.class.getField("NextdaySummary"+String.valueOf(i)).getInt(new R.id()));
//                NextdayView = (TextView) main.findViewById(R.id.class.getField("NextdayText"+String.valueOf(i)).getInt(new R.id()));
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            } catch (NoSuchFieldException e) {
//                e.printStackTrace();
//            }
//            //添加当天概要信息
//            NextdaySummaryView.setText(weather.getNight().getType()+"\n"+weather.getDay().getFengxiang()+"\n"+weather.getNight().getFengli());
//            NextdayView.setText(weather.getDay().getType()+"\n"+weather.getDay().getFengxiang()+"\n"+weather.getDay().getFengli());
////            dayView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
//            //添加当天的天气图片，早间，晚间分开
//            dayView.setImageResource(selectWeatherPic(weather.getDay().getType(),"06:00"));
//            nightView.setImageResource(selectWeatherPic(weather.getNight().getType(),"20:00"));
//            i++;
//        }
//        //获取图表视图
//        LineView lineView=(LineView) main.findViewById(R.id.chartView);
//        //将温度数组链表加入到图表视图以制图
//        lineView.setHighsAndLows(highs,lowest);
//        lineView.setHighsest(high,low);//画高温线
//        lineView.setLowsest(high,low);//画低温线
//        lineView.setVisibility(LineView.VISIBLE);
//    }
//    public void run1() {
//        try {
//            displayTheBar();
//            Log.d("saul", "run: handler");
//            main.getHandler().post(new Runnable() {
//                @Override
//                public void run() {
//                    Log.d("saul", "run: handler");
//                    ImageView imageView=(ImageView) main.findViewById(R.id.WeatherImage);
//                    TextView toDayWeather=(TextView) main.findViewById(R.id.TodayWeather) ;
//                    TextView toDaySummary=(TextView) main.findViewById(R.id.TodayWeatherSummary);
//                    TextView toDayWeatherDeatial=(TextView) main.findViewById(R.id.TodayWeatherDetail);
////                    Toolbar toolbar = (Toolbar) main.findViewById(R.id.toolbar);
//                    String time=weatherResponse.getUpdatetime();//获取更新时间
//                    List<Weather> weather=weatherResponse.getWeathers();//获取5天内的天气预报链表
//                    Environment environment=weatherResponse.getEnvironment();//获取环境详情，so,pm2.5等等
//                    List<Zhishu> zhishus=weatherResponse.getZhishus();//获取活动适宜指数
//                    Log.d("saul", "displayTheBar: "+time+"!!"+weather);
//                    int type=selectWeatherPic(weather.get(0).getDay().getType(),time);
//                    Log.d("saul", "displayTheBar: "+type);
//                    imageView.setImageResource(type);
//                    toDayWeather.setText("  "+weatherResponse.getWendu()+"°");
//                    String Detail=getTodayDetail();
//                    toDayWeatherDeatial.setText(Detail);
////                    toolbar.setTitle(weatherResponse.getCity()+","+weather.get(0).getDate());
////                    main.setSupportActionBar(toolbar);
//                    toDaySummary.setText(weather.get(0).getDay().getType());
//                    setChartView();
//                }
//            });
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (DocumentException e) {
//            e.printStackTrace();
//        }
//    }
}
