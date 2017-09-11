package com.example.saulxk.model;

import com.example.saulxk.annotation.Clzz;

import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

public class WeatherResponse extends BaseDi{
   private String city;
   private String updatetime;
   private String wendu;
   private String fengli;
   private String shidu;
   private String fengxiang;
   private String sunrise;
   private String sunset;
   private List<Zhishu> zhishus;
   private List<Weather> weathers;
   private Environment environment;
public WeatherResponse(Element element) {
	super(element);
}
public WeatherResponse() {
}
public String getCity() {
	return city;
}
@Clzz
public void setCity(String city) {
	this.city = city;
}
public String getUpdatetime() {
	return updatetime;
}
@Clzz
public void setUpdatetime(String updatetime) {
	this.updatetime = updatetime;
}
public String getWendu() {
	return wendu;
}
@Clzz
public void setWendu(String wendu) {
	this.wendu = wendu;
}
public String getFengli() {
	return fengli;
}
@Clzz
public void setFengli(String fengli) {
	this.fengli = fengli;
}
public String getShidu() {
	return shidu;
}
@Clzz
public void setShidu(String shidu) {
	this.shidu = shidu;
}
public String getFengxiang() {
	return fengxiang;
}
@Clzz
public void setFengxiang(String fengxiang) {
	this.fengxiang = fengxiang;
}
public String getSunrise() {
	return sunrise;
}
@Clzz(value="sunrise_1")
public void setSunrise(String sunrise) {
	this.sunrise = sunrise;
}
public String getSunset() {
	return sunset;
}
@Clzz(value="sunset_1")
public void setSunset(String sunset) {
	this.sunset = sunset;
}
public List<Zhishu> getZhishus() {
	return zhishus;
}
@Clzz(value="list",name="zhishus")
public void setZhishus(Element element) {
	List<Element> list=element.elements("zhishu");
	List<Zhishu> zhishus=new ArrayList<Zhishu>();
	for(Element e:list){
		zhishus.add(new Zhishu(e));
	}
	this.zhishus = zhishus;
}
public List<Weather> getWeathers() {
	return weathers;
}
@Clzz(value="list",name="forecast")
public void setWeathers(Element element) {
	List<Element> list=element.elements("weather");
	List<Weather>  weathers=new ArrayList<Weather>();
	for(Element e:list){
		 weathers.add(new Weather(e));
	}
	this.weathers = weathers;
}
public Environment getEnvironment() {
	return environment;
}
@Clzz(value="list",name="environment")
public void setEnvironments(Element element) {
	this.environment = new Environment(element);
}
   

}