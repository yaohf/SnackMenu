package com.xin.menu;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class BaseActivity extends Activity
{

	protected ApplicationManager application;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		application = (ApplicationManager) getApplication();
		application.getActivityManager().pushActivity(this);
	}
	
	public void exitApp(){
		application.getActivityManager().popAllActivityExceptOne(Activity.class);
	}

}
