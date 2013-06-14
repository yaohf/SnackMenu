package com.xin.menu;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.xin.menu.adapter.FoodsAdapter;
import com.xin.menu.model.Food;
import com.xin.menu.model.ShoppingCart;
import com.xin.menu.util.L;

@SuppressLint("NewApi")
public class MainActivity extends BaseActivity
{
	private Context mContext;

	private RadioGroup mRadioGroup;
	FragmentManager fm;

	ArrayList<Food> lists = new ArrayList<Food>();
	FoodsAdapter foodsAdapter;

	ShoppingCart shopping;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);

		mContext = this;
		MainListViewFragment mainFragment = new MainListViewFragment();
		
		fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		
		ButtomFragment buttomFragment = new ButtomFragment();
		ft.replace(R.id.main_buttom_linear, buttomFragment);
		ft.replace(R.id.main_linear, mainFragment, "main_fragment");
		// ft.add(foodContentFragment, "contentFragment");
		// ft.addToBackStack("main_fragment");
		// ft.setCustomAnimations(R.anim.enter_right_to_left,
		// R.anim.exit_right_to_left)
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		ft.commit();
		

	}

	/**
	 * 再按一次退出
	 */
	private static final long WAITTIME = 2000;
	private long touchTime = 0;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{

		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)
		{

			final int backStack = fm.getBackStackEntryCount();
			if (backStack <= 0)
			{
				long currentTime = System.currentTimeMillis();
				if ((currentTime - touchTime) >= WAITTIME)
				{
					Toast.makeText(mContext, "再按一次退出", 0).show();
					touchTime = currentTime;
				} else
				{
					L.v(" backStack == 0 to exit app");
					exitApp();
				}
				return true;
			}
			
		}
		return super.onKeyDown(keyCode, event);
	}

}
