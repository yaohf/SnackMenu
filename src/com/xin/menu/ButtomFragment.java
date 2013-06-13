package com.xin.menu;

import com.xin.menu.model.ShoppingCart;
import com.xin.menu.util.L;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class ButtomFragment extends Fragment
{
	private RadioGroup mRadioGroup;
	
	private ShoppingCart shopping;
	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		mRadioGroup = (RadioGroup) getView().findViewById(R.id.main_radiogroup);
		mRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId)
			{
				
				switch(checkedId){
				case R.id.main_rbtn:
					L.v("main_rbtn");
					MainListViewFragment mainFragment = new MainListViewFragment();
					FragmentTransaction ft = getFragmentManager().beginTransaction();
					ft.replace(R.id.main_linear, mainFragment);
					
					ft.commit();
					break;
				case R.id.cart_rbtn:
					
					L.v("cart_rbtn");
					Bundle b = new Bundle();
					b.putInt("chat_count", shopping.getFoodCount());
					b.putFloat("chat_price", shopping.getSumPrice());
					b.putParcelableArrayList("buy_list", shopping.getCarts());
					
					ShoppingFragment shoppingFragment = new ShoppingFragment();
					shoppingFragment.setArguments(b);
					ft = getFragmentManager().beginTransaction();
					ft.replace(R.id.main_linear, shoppingFragment);
					ft.setCustomAnimations(R.anim.fragment_slide_left_enter,R.anim.fragment_slide_left_exit,R.anim.fragment_slide_right_enter, R.anim.fragment_slide_right_exit);
					ft.addToBackStack("main_fragment");
					ft.commit();
					
					break;
				}
				
			}
		});
	}

	@Override
	public void onAttach(Activity activity)
	{
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		shopping = ShoppingCart.getInstance();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.buttom_include, null);
	}

	@Override
	public void onDestroy()
	{
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onPause()
	{
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public void onResume()
	{
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public void onSaveInstanceState(Bundle outState)
	{
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}

	@Override
	public void onStart()
	{
		// TODO Auto-generated method stub
		super.onStart();
	}

}
