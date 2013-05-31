package com.xin.menu.util;

import android.util.Log;

public class L {

	public static final String TAG = "LOG_INFO";
	public static final boolean DEBUG = true;
	private static final String TAG_CONTENT_PRINT = "[ %s: %s: %d]";
	
	
	
	
	private static String getContent(String tag,String method,Object msg){
		final String formatContent = String.format(TAG_CONTENT_PRINT,TAG,tag,method,msg);
		return formatContent;
	}
	public static void v (String tag,String method,Object msg){
		if(DEBUG){
			Log.v(tag, getContent(tag,method, msg));
		}
	} 
	
	public static void v(Object msg){
		if(DEBUG){
			Log.v(TAG, getContent() + ">>" + msg);
		}
	}
		
	private static String getContent(){
		StackTraceElement[] stes = Thread.currentThread().getStackTrace();
		if(stes == null){
			return null;
		}
		for(StackTraceElement s : stes){
			if(s.isNativeMethod()){
				continue;
			}
			if(s.getClassName().equals(Thread.class.getName()))
				continue;
			if(s.getClassName().equals(L.class.getName()))
				continue;
			final String str = String.format(TAG_CONTENT_PRINT, s.getClassName(),s.getMethodName(),s.getLineNumber());
			return str;
		}
		return null;
	}
}
