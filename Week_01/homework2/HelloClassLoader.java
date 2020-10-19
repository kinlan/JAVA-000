package com.kin_lan.training.jvm;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class HelloClassLoader extends ClassLoader {
	
	private final static String CLASS_NAME = "Hello";
	private final static String METHOD_NAME = "hello";
	
	public static void main(String[] args){
		try {
			
			Class<?> cls=Class.forName(CLASS_NAME,true,new HelloClassLoader());
			Method method = cls.getDeclaredMethod(METHOD_NAME);
			method.invoke(cls.newInstance());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected Class<?> findClass(String name){
		
		String filenameString = "src/main/resource/Hello.xlass";
		
		List<Integer> list = new ArrayList<Integer>(); 
		
		try (InputStream InputStream = new FileInputStream(new File(filenameString))){
			int tempbyte;
            while ((tempbyte = InputStream.read()) != -1) {
            	//x=225-x
                list.add(255 - tempbyte);
            }
		} catch (Exception e) {}
		
		byte[] bytes = new byte[list.size()];
		int i = 0;
		for(Integer integer: list) {
			bytes[i] = toByte(integer);
			i++;
		}
		return defineClass(name, bytes, 0, bytes.length);
	}
	
	public static byte toByte(int number){
        
        return (byte) (number & 0xff);
    }

}
