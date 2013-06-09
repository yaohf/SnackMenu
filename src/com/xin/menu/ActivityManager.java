package com.xin.menu;

import java.util.Stack;

import android.app.Activity;

public class ActivityManager
{
	private Stack<Activity> activityStack;

	private static ActivityManager instance;

	private ActivityManager()
	{
	}

	public synchronized static ActivityManager getScreenManager()
	{
		if (instance == null)
		{
			instance = new ActivityManager();
		}
		return instance;
	}

	public void pushActivity(Activity a)
	{
		if (activityStack == null)
		{
			activityStack = new Stack<Activity>();
		}
		activityStack.add(a);
	}

	public void popActivity(Activity a)
	{
		if (a != null)
		{
			a.finish();
			activityStack.remove(a);
			a = null;
		}
	}

	public Activity currentActivity()
	{

		Activity activity = null;
		if (!activityStack.isEmpty())
		{
			activity = activityStack.lastElement();
		}
		return activity;
	}

	public void popAllActivityExceptOne(Class<?> cls)
	{
		while (true)
		{
			Activity activity = currentActivity();
			if(activity == null)
			{
				break;
			}
			if (activity.getClass().equals(cls))
			{
				break;
			}
			popActivity(activity);
		}
	}

}
