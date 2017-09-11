package com.example.saulxk.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;

public class FileUtil {
	 
public static void fileOutput(byte byt[],URI uri) throws IOException{
	File file=new File(uri);
	if(!file.exists()){
		file.createNewFile();
	}
	FileOutputStream fileOutputStream=new FileOutputStream(file);//打开输出流
	fileOutputStream.write(byt);//向文件输出byt
}
public static void fileInput(byte byt[],URI uri) throws IOException{
	File file=new File(uri);
	FileInputStream fileInputStream=new FileInputStream(file);//打开输入流
	fileInputStream.read(byt);//从文件输出到byt
}
}
