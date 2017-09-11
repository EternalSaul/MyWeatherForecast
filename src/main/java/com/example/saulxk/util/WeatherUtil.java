package com.example.saulxk.util;

import android.content.Context;
import android.widget.Toast;

import com.example.saulxk.weatherforecast.ScrollingActivity;

import org.dom4j.Document;
import org.dom4j.DocumentException;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class WeatherUtil {
	public WeatherUtil() {
		// TODO Auto-generated constructor stub
	}
	/*
	* 获取XML，有网络则利用HTTP协议获取，无网络则获取历史记录
	* */
	public Document getWeatherXML(String city, final Context context) {
		Document document=null;
		try {
			if(HttpConnectionUtil.isNetworkConnected(context)){
                //取得网络xml
                document=this.requestWeatherByHTTP(city);
				FileOutputStream fileOutputStream=context.openFileOutput("local.xml",Context.MODE_PRIVATE);
				fileOutputStream.write(document.asXML().getBytes());
            }
            else{
				//转到主线程
				((ScrollingActivity)context).getHandler().post(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(context,"网络链接异常，请检查您的网络链接", Toast.LENGTH_LONG).show();
					}
				});
				FileInputStream inputStream=context.openFileInput("local.xml");
				//检查文件是否可用
				if (inputStream==null) {//文件不可用
					return null;
				}
				//获取本地文件xml
				document = this.requestWeatherByLocalFile(inputStream);
				}
            } catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return document;
	}

    /*
    * 参数:城市名：city
    * 通过http请求来获取xml
    * */
	public Document requestWeatherByHTTP(String city) throws MalformedURLException, IOException, DocumentException{
		String api="http://wthrcdn.etouch.cn/WeatherApi?city=";
		InputStream inputStream=HttpConnectionUtil.getResponseContent(new URL(api+city),"GET");
		DomUtil.getInstance().setDocument(inputStream);
		return DomUtil.getInstance().getDocument();
	}
    /*
    * 参数：
    * 本地xml：file
    *
    * 获取本地的天气xml
    * */
	public Document requestWeatherByLocalFile(InputStream inputStream) throws MalformedURLException, IOException, DocumentException, URISyntaxException {
		DomUtil.getInstance().setDocument(inputStream);
		return DomUtil.getInstance().getDocument();
	}

}
