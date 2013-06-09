package com.xin.menu.util;

import java.util.Stack;

import android.support.v4.app.Fragment;



public class AppUtils
{
	private static AppUtils instance;
	
	public static synchronized AppUtils getInstance()
	{
		if(null == instance)
		{
			instance = new AppUtils();
		}
		return instance;
	}
	
	private AppUtils(){}
	
	public static Stack<Fragment> fragments = new Stack<Fragment>();
	
	
}
