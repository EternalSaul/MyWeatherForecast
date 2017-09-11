package com.example.saulxk.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpConnectionUtil {
	public static HttpURLConnection conn;
	/*
	* 设置链接
	* */
	public static void Connection(URL url,String method) throws IOException{
		conn = (HttpURLConnection)url.openConnection();
		conn.setRequestMethod(method);
	}
	/*
	* 获取响应内容流
	* */
	public static InputStream getResponseContent(URL url,String method) throws IOException{
		Connection(url,method);//建立网站链接
		InputStream inputStream = (InputStream)conn.getInputStream();   //通过输入流获得网站数据
		return inputStream;
	}
	/*
	* 检查网络是否可用
	* */
	public static boolean isNetworkConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}
		}
		return false;
	}

}
