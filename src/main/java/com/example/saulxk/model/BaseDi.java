package com.example.saulxk.model;

import com.example.saulxk.annotation.Clzz;

import org.dom4j.Element;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BaseDi {
	public BaseDi() {
		
	}
	public BaseDi(Element element){
		Method[] ms=this.getClass().getDeclaredMethods();
		for(Method m:ms){
			String mn=m.getName().substring(3);
			mn=mn.toLowerCase();//获取属性名
			try {
				if(m.isAnnotationPresent(Clzz.class)){//查看该属性是不是一个类，或者名字需要别名
				//特殊类型
					Clzz clzz=m.getAnnotation(Clzz.class);
					String hint=clzz.value();
					if(hint.equals("list")){//如果属性是一个类
						String name=clzz.name();//获取属性对应的dom元素名
						if(element.element(name)!=null)
						m.invoke(this, element.element(name));//传递该属性的整个xml
					}
					else if(!hint.equals("")){//属性不是类但需要属性别名
						if(element.element(hint)!=null)
						m.invoke(this,element.element(hint).getText());//获取别名中的值
					}
					else{
						if(element.element(mn)!=null)
						m.invoke(this,element.element(mn).getText());//一般属性赋值
					}
				}
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
