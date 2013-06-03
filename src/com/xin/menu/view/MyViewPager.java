package com.xin.menu.view;

import java.lang.reflect.Field;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Scroller;

public class MyViewPager extends ViewPager
{

	public MyScroller myScroller;
//	private Context mContext; 
	
	public MyViewPager(Context context)
	{
		super(context);
			initView(context);
	}

	private void  initView(Context c){

		LinearInterpolator interpolator = new LinearInterpolator();
			Field scroller ;
			try
			{
				scroller = MyViewPager.class.getDeclaredField("mScroller");
				scroller.setAccessible(true);
				myScroller = new MyScroller(c, interpolator);
				myScroller.setFixedDuration(5000);
				scroller.set(this, myScroller);
			} catch (SecurityException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchFieldException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}
	
	
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0)
	{
		switch(arg0.getAction()){
		case MotionEvent.ACTION_DOWN:
			getParent().requestDisallowInterceptTouchEvent(true);
			return true;
		}
		return super.onInterceptTouchEvent(arg0);
	}

	public MyViewPager(Context c,AttributeSet att){
		super(c, att);
		initView(c);
		
	}
	private class MyScroller extends Scroller{

		private int mDuration = 5000;
		
		protected void setFixedDuration(int duration){
			this.mDuration = duration;
		}
		public MyScroller(Context context){
			super(context);
		}
		
		@Override
		public void startScroll(int startX, int startY, int dx, int dy,
				int duration)
		{
			super.startScroll(startX, startY, dx, dy, mDuration);
		}

		@Override
		public void startScroll(int startX, int startY, int dx, int dy)
		{
			// TODO Auto-generated method stub
//			super.startScroll(startX, startY, dx, dy);
			super.startScroll(startX, startY, dx, dy, mDuration);
		}

		public MyScroller(Context context, Interpolator interpolator)
		{
			super(context, interpolator);
		}
		
		
	}



}
