package com.example.saulxk.model;

/***********************************************************************
 * Module:  Detail.java
 * Author:  Saulxk
 * Purpose: Defines the Class Detail
 ***********************************************************************/

import com.example.saulxk.annotation.Clzz;

import org.dom4j.Element;

public class Detail extends BaseDi{
   private String type;
   private String fengxiang;
   private String fengli;
public Detail(Element element) {
	super(element);
}
public Detail() {
}
public String getType() {
	return type;
}
@Clzz
public void setType(String type) {
	this.type = type;
}
public String getFengxiang() {
	return fengxiang;
}
@Clzz
public void setFengxiang(String fengxiang) {
	this.fengxiang = fengxiang;
}
public String getFengli() {
	return fengli;
}
@Clzz
public void setFengli(String fengli) {
	this.fengli = fengli;
}
   

}