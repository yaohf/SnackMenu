package com.xin.menu.adapter;

import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;

import com.xin.menu.view.MyViewPager;

public class ViewPagerAdapter extends PagerAdapter
{

	private Context mContext;
	private List<View> list;

	public ViewPagerAdapter(Context c, List<View> l)
	{
		mContext = c;
		list = l;
	}

	@Override
	public int getCount()
	{
		// TODO Auto-generated method stub
		return list.size();
	}
	

	@Override
	public int getItemPosition(Object object)
	{
		Object tag = ((View)object).getTag();
		for(int i = 0; i < list.size(); i++){
			if(tag.equals(list.get(i))){
				return i;
			}
		}
		return POSITION_NONE;
	}

	@Override
	public void destroyItem(View container, int position, Object view)
	{ 
		// TODO Auto-generated method stub
		((MyViewPager)container).removeView((View)view);
	}

	@Override
	public Object instantiateItem(View container, int position)
	{
		View v = list.get(position);
		((MyViewPager)container).addView(v);
		return v;
		// TODO Auto-generated method stub
//		return super.instantiateItem(container, position);
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1)
	{
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}

}
