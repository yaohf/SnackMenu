package com.xin.menu;

import android.app.Application;	

public class ApplicationManager extends Application
{
	protected ActivityManager activityManager = null;
	
	public void setActivityManager(ActivityManager am)
	{
		this.activityManager = am;
	}
	public ActivityManager getActivityManager(){
		return activityManager;
	}
	@Override
	public void onCreate()
	{
		super.onCreate();
		activityManager = ActivityManager.getScreenManager();
	}
	
	
}
