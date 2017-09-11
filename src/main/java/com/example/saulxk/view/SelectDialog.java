package com.example.saulxk.view;

import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.saulxk.controller.WeatherDisplay;
import com.example.saulxk.util.HttpConnectionUtil;
import com.example.saulxk.weatherforecast.R;
import com.example.saulxk.weatherforecast.ScrollingActivity;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Saulxk on 2016/12/17.
 */

public class SelectDialog extends Dialog implements AdapterView.OnItemSelectedListener,View.OnClickListener{
    public static final  String TAG="saul";
    Document mDocument;
    List<Element> states,citys;
    Spinner mStates,mCitys,mRegions;
    Button mConfirm,mCancle;
    public SelectDialog(Context context) {
        super(context, R.style.customDialog);
    }

    public SelectDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected SelectDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
    /*
    * 查询城市xml文档
    * */
    protected Document queryDocument(){
        InputStream inputStream= this.getContext().getResources().openRawResource(R.raw.local);
        SAXReader saxReader=new SAXReader();
        Document document=null;
        try {
            document= saxReader.read(inputStream);
            inputStream.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return document;
    }
    /*
    * 查询元素集中的城市/省份/县/市名
    * */
    protected List<String> getNameList(List<Element> elements){
       List<String> names=new ArrayList<String>();
        for (Element element:elements){
            names.add(element.attribute("Name").getValue());
        }
        return names;
    }
    /**
     * 获取元素下名为name的元素集合
     * 参数：
     * element:要查询的根元素
     * name:要查询的Name的对于值
     * */
    protected List<Element> getElements(Element element, String name){
        return  element.elements(name);
    }
    /**
     * 获取元素集中子元素属性Name值为name的节点
     * 参数：
     * elements:要查询的元素集合
     * name:要查询的Name的对于值
     * */
    protected Element getElement(List<Element> elements, String name){
        for(Element element:elements){
            if (element.attribute("Name").getValue().equals(name))
                return element;
        }
        return null;
    }
    /*
    * 初始化
    * */
    protected void Init(){
        mStates= (Spinner) findViewById(R.id.s_dialog_spinner1);
        mStates.setPrompt("省/直辖市/特别行政区");
        mCitys=(Spinner) findViewById(R.id.s_dialog_spinner2);
        mCitys.setPrompt("市/区");
        mRegions=(Spinner)findViewById(R.id.s_dialog_spinner3);
        mCitys.setPrompt("县/区");
        mDocument=queryDocument();
        mCancle=(Button)findViewById(R.id.s_dialog_button1);
        mConfirm=(Button)findViewById(R.id.s_dialog_button2);
    }
    /*
    * 参数：
    * root：要设置为的Spinner元素集的所在跟元素节点
    * kind：要设置为的Spinner元素集的所在跟元素节点中元素集的类型
    * spinner:要设置为的Spinner列表
    * */
    protected List<Element> setArrayAdapater(Element root, String kind, Spinner spinner){
        List<Element> elements=getElements(root,kind);//设置最后打开的元素集
        List<String> kinds=getNameList(elements);
        if(spinner.getAdapter()==null){
        final ArrayAdapter<String> adapter=new ArrayAdapter<String>(SelectDialog.this.getContext(), R.layout.support_simple_spinner_dropdown_item,kinds);
            adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
        }else{
            ((ArrayAdapter<String>)spinner.getAdapter()).clear();
            ((ArrayAdapter<String>)spinner.getAdapter()).addAll(kinds);
        }
        return elements;
    }
    /*
    * 重写
    * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_dialog);
        //mCitys,mRegions,mStates,mDocument 参数初始化
        Init();
        setTitle("选择城市");
        states=setArrayAdapater(mDocument.getRootElement(),"State",mStates);
        mStates.setOnItemSelectedListener(this);
        mCitys.setOnItemSelectedListener(this);
        mRegions.setOnItemSelectedListener(this);
        mCancle.setOnClickListener(this);
        mConfirm.setOnClickListener(this);
    }
    /*
    * 接口类
    * */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Element element=null;
        String s=(String) parent.getItemAtPosition(position);
        if (position>=0)
        switch (parent.getId()){
            case R.id.s_dialog_spinner1:
                element=getElement(states,s);
                citys=setArrayAdapater(element,"City",mCitys);
                //设置默认显示，为第0个元素一般是省会，中心区
                mCitys.setPrompt(mCitys.getAdapter().getItem(0).toString());
                //刷新市级地区对应的城镇
                s=(String) mCitys.getItemAtPosition(0);
                element=getElement(citys,s);
                setArrayAdapater(element,"Region",mRegions);
                break;
            case R.id.s_dialog_spinner2:
                element=getElement(citys,s);
                setArrayAdapater(element,"Region",mRegions);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.s_dialog_button1:
                String position=getPosition();
                Log.d(TAG, "onClick: "+"position:"+position);
                ResponseSelection(position);
                break;
            case R.id.s_dialog_button2:
                this.dismiss();
                break;
        }
    }
    protected void ResponseSelection(final String position){
        Log.d(TAG, "ResponseSelection: "+getContext());
        //dialog中的getContext并不是真正的返回Context，而是返回了ThemeContextWrapper所以，不能直接转化为Activity
        final Context context= ((ContextWrapper)this.getContext()).getBaseContext();
        Thread display=new Thread(new Runnable() {
            @Override
            public void run() {
                WeatherDisplay weatherDisplay=new WeatherDisplay((ScrollingActivity) context);
                weatherDisplay.setWeatherResponseByCityName(position);
                weatherDisplay.display();
                SelectDialog.this.dismiss();
                SelectDialog.this.annouce(context,position);
            }
        });
        display.start();
    }
    /*
    * 提示函数，提示用户切换城市是否成功
    * */
    protected void annouce(Context context,String position){
        if(HttpConnectionUtil.isNetworkConnected(context))
            Snackbar.make(((ScrollingActivity)context).findViewById(R.id.fab), "已经切换到城市:"+position, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        else
            Snackbar.make(((ScrollingActivity)context).findViewById(R.id.fab), "网络链接异常无法切换到服务:", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
    }

    protected String getPosition(){
        String province,city,region;
        province=(String) mStates.getSelectedItem();
        city=(String) mCitys.getSelectedItem();
        region=(String) mRegions.getSelectedItem();
       if (province!=null&&city!=null){
             if (province.equals("台湾")){
               return city.substring(0,city.length()-1);
              }
            else if (region==null){//直辖市或者特别行政区
                return province;
            }else{
                String level=region.substring(region.length()-1,region.length());
                if("区".equals(level)||"旗".equals(level)||region.contains("自治")){
                    return city;
                }
                else{
                    region= region.substring(0,region.length()-1).replace(" ","");
                    return region;
                }
            }
        }else{
            Toast.makeText(this.getContext(),"请选择地区",Toast.LENGTH_LONG);
            return null;
        }
    }
}
