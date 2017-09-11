package com.example.saulxk.model;

import com.example.saulxk.annotation.Clzz;

import org.dom4j.Element;

/***********************************************************************
 * Module:  Weather.java
 * Author:  Saulxk
 * Purpose: Defines the Class Weather
 ***********************************************************************/
public class Weather extends BaseDi{
   private String date;
   private String high;
   private String low;
   private Detail day;
   private Detail night;
public Weather() {
	// TODO Auto-generated constructor stub
}
public Weather(Element e) {
	super(e);
}
public String getDate() {
	return date;
}
@Clzz
public void setDate(String date) {
	this.date = date;
}
public String getHigh() {
	return high;
}
@Clzz
public void setHigh(String high) {
	this.high = high;
}
public String getLow() {
	return low;
}
@Clzz
public void setLow(String low) {
	this.low = low;
}
public Detail getDay() {
	return day;
}
@Clzz(value="list",name="day")
public void setDay(Element element) {
	this.day = new Detail(element);
}
public Detail getNight() {
	return night;
}
@Clzz (value="list", name="night")
public void setNight(Element element) {
	this.night = new Detail(element);
}
   

}