package com.example.saulxk.model;

/***********************************************************************
 * Module:  Zhishu.java
 * Author:  Saulxk
 * Purpose: Defines the Class Zhishu
 ***********************************************************************/

import com.example.saulxk.annotation.Clzz;

import org.dom4j.Element;

public class Zhishu extends BaseDi{
   private String name;
   private String value;
   private String detail;
public Zhishu() {
}
public Zhishu(Element element) {
	super(element);
}
public String getName() {
	return name;
}
@Clzz
public void setName(String name) {
	this.name = name;
}
public String getValue() {
	return value;
}
@Clzz
public void setValue(String value) {
	this.value = value;
}
public String getDetail() {
	return detail;
}
@Clzz
public void setDetail(String detail) {
	this.detail = detail;
}
   

}