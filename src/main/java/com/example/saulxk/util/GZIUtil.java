package com.example.saulxk.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

public class GZIUtil {
	
public static byte[] GZIOutputToByte(InputStream inputStream,int size) throws IOException{
	GZIPInputStream gis= (GZIPInputStream) GZIOutput(inputStream, size);  //把输入流建立为GZI输入流
    ByteArrayOutputStream os = new ByteArrayOutputStream();  //创建一个字节输出流
    int count=0;  
    byte data[] = new byte[size];  //一组大小为size的字节
    while ((count = gis.read(data)) != -1) {  //循环读取GZI解压流
        os.write(data, 0, count); //将字节输入流输入进字节数组
    }   
    gis.close();  //关闭输入流
    return os.toByteArray();//返回字节组
}
public static InputStream GZIOutput(InputStream inputStream,int size) throws IOException{
    GZIPInputStream gis = new GZIPInputStream(inputStream);  //把输入流建立为GZI输入流
    return gis;//GZIPInput流
}
}
