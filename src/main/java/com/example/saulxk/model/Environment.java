package com.example.saulxk.model;

/***********************************************************************
 * Module:  Environment.java
 * Author:  Saulxk
 * Purpose: Defines the Class Environment
 ***********************************************************************/

import com.example.saulxk.annotation.Clzz;

import org.dom4j.Element;

public class Environment extends BaseDi{
   private String aqi;
   private String pm25;
   private String suggest;
   private String quality;
   private String o3;
   private String pm10;
   private String co;
   private String so2;
   private String no2;
   private String time;
public Environment(Element element) {
	super(element);
}
public Environment() {
	// TODO Auto-generated constructor stub
}
public String getCo() {
	return co;
}
@Clzz
public void setCo(String co) {
	this.co = co;
}
public String getAqi() {
	return aqi;
}
@Clzz
public void setAqi(String aqi) {
	this.aqi = aqi;
}
public String getPm25() {
	return pm25;
}
@Clzz
public void setPm25(String pm25) {
	this.pm25 = pm25;
}
public String getSuggest() {
	return suggest;
}
@Clzz
public void setSuggest(String suggest) {
	this.suggest = suggest;
}
public String getQuality() {
	return quality;
}
@Clzz
public void setQuality(String quality) {
	this.quality = quality;
}
public String getO3() {
	return o3;
}
@Clzz
public void setO3(String o3) {
	this.o3 = o3;
}
public String getPm10() {
	return pm10;
}
@Clzz
public void setPm10(String pm10) {
	this.pm10 = pm10;
}
public String getSo2() {
	return so2;
}
@Clzz
public void setSo2(String so2) {
	this.so2 = so2;
}
public String getNo2() {
	return no2;
}
@Clzz
public void setNo2(String no2) {
	this.no2 = no2;
}
public String getTime() {
	return time;
}
@Clzz
public void setTime(String time) {
	this.time = time;
}
   

}