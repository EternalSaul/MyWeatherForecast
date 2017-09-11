package com.example.saulxk.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

public class DomUtil {
	private static DomUtil instance=new DomUtil();
	private Document document;
	private Element element;
	private DomUtil() {
	}
	//获取单例
	public static DomUtil getInstance() {
		return instance;
	}
	//设置DOM
	public void setDocument(InputStream inputStream) throws DocumentException, IOException {
		SAXReader reader = new SAXReader();
		document=reader.read(inputStream);
		inputStream.close();
	}
	public void setDocument(File file) throws DocumentException{
		SAXReader reader = new SAXReader();
		document=reader.read(file);
	}
	public void setDocument(URL url) throws DocumentException{
		SAXReader reader = new SAXReader();
		document=reader.read(url);
	}
	public void setDocument(Document document) {
		this.document = document;
	}
	//获取当前dom
	public Document getDocument() {
		return document;
	}
	//获取当前的element
	public Element getElement(){
		return element;
	}
	//返回根节点
	public Element getRoot(){
		return document.getRootElement();		
	}
	//通过id来获取节点
	public Element getNodeById(String id){
		return document.elementByID(id);
	}
	public Element getNodeByName(String name){
		return this.getRoot().element(name);
	}
	public List<Element> getNodesByName(String name){
		return this.getRoot().elements(name);
	}
	public void setElementByName(Element e,String name){
		element=e.element(name);
	}
	public Element listNodes(Element node,String name) {
		// 当前节点下面子节点迭代器
		Iterator<Element> it = node.elementIterator();
		Element e = null;
		// 遍历
		while (it.hasNext()) {
			// 获取某个子节点对象
			e= it.next();
			if(name.equals(e.getName()))
				return e;
			else{
				e=listNodes(e,name);
				if(e!=null)
					return e;
			}				
		}
		return null;
	}
}
