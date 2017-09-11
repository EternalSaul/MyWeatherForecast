package com.example.saulxk.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Saulxk on 2016/12/16.
 */

public class SharedPreferencesUtil {
    /*
    * 私有模式
    * */
    public static final int MODE= Context.MODE_PRIVATE;
    /*
    * 文件名
    * */
    public static final String PREFERNCES_NAME= "SaveSetting";
    public SharedPreferencesUtil() {
    }
    /*
    * 获取SharedPreferences
    * */
    public static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(PREFERNCES_NAME,MODE);
    }
    /*
    * 获取SharedPreferences.Editor
    * */
    public static SharedPreferences.Editor getEditor(Context context) {
        SharedPreferences sharedPreferences=context.getSharedPreferences(PREFERNCES_NAME,MODE);
        return sharedPreferences.edit();
    }
}
