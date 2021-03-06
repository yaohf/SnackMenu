package com.xin.menu;



import java.util.ArrayList;
import java.util.List;

import org.ais.event.TEvent;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.xin.menu.adapter.FoodsAdapter;
import com.xin.menu.adapter.FoodsAdapter.ChatListener;
import com.xin.menu.adapter.ViewPagerAdapter;
import com.xin.menu.model.Food;
import com.xin.menu.model.ShoppingCart;
import com.xin.menu.util.L;
import com.xin.menu.view.MyViewPager;
import com.xin.menu.view.XListView;
import com.xin.menu.view.XListView.IXListViewListener;

public class MainListViewFragment extends Fragment implements ChatListener,
IXListViewListener, OnItemClickListener
{

	private Activity mActivity;
	public static XListView snacklist;
	private MyViewPager recommendTitle;

	ArrayList<Food> lists = new ArrayList<Food>();
	FoodsAdapter foodsAdapter;

	ShoppingCart shopping;

	private boolean leftToRight = true;
	private boolean dragLeft = false;
	private boolean dragRight = false;
	private boolean isScrolling = false;
	private int listCount = 0;

	private int actuatorPosition;
	private int lastValue = -1;
	/**
	 * Handler
	 */
	private static final int HANDLER_POSTER_MSG = 0;

	private View mainView;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		mActivity = getActivity();
		loadlist();
		mainView = getView();
		snacklist = (XListView) mainView.findViewById(R.id.custom_list);
		snacklist.addHeaderView(initlistHeadView(), null, true);
		foodsAdapter = new FoodsAdapter(mActivity, lists, this);
		snacklist.setAdapter(foodsAdapter);
		snacklist.setOnItemClickListener(this);
		snacklist.setPullLoadEnable(true);
		snacklist.setXListViewListener(this);

		snacklist.setVerticalScrollBarEnabled(true);
		snacklist.setOnScrollListener(new OnScrollListener()
		{

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState)
			{
				if (scrollState == OnScrollListener.SCROLL_STATE_FLING)
				{
					snacklist.setVerticalScrollBarEnabled(false);
				} else if (scrollState == OnScrollListener.SCROLL_STATE_IDLE)
				{
					snacklist.setVerticalScrollBarEnabled(true);
				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount)
			{
				// TODO Auto-generated method stub

			}
		});

		shopping = ShoppingCart.getInstance();
	}

	private int i;

	public void loadlist()
	{
		Food food = null;
		for (i = 0; i != 20; i++)
		{
			food = new Food();
			food.bitmapUrl = R.drawable.meitu_1;
			food.id = i;
			food.name = "红烧" + i;
			food.price = 5 + i;
			lists.add(food);
		}
	}

	public View initlistHeadView()
	{
		View v_ = LayoutInflater.from(mActivity).inflate(R.layout.recommend_title,
				null);
		recommendTitle = (MyViewPager) v_
				.findViewById(R.id.recommed_title_pager);
		// recommendTitle.setOnTouchListener(new OnTouchListener() {
		// @Override
		// public boolean onTouch(View v, MotionEvent event) {
		// switch (event.getAction()) {
		// case MotionEvent.ACTION_UP:
		// setTimerFlag(true);
		// break;
		//
		// default:
		// setTimerFlag(false);
		// break;
		// }
		//
		// return false;
		// }
		// });
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT);

		List<View> list = new ArrayList<View>();
		for (int i = 0; i < 5; i++)
		{
			ImageView image = new ImageView(mActivity);
			image.setImageResource(R.drawable.meitu_1_200);
			image.setScaleType(ScaleType.FIT_XY);
			image.setLayoutParams(params);
			list.add(image);
		}
		ViewPagerAdapter vpAdapter = new ViewPagerAdapter(mActivity, list);
		recommendTitle.setAdapter(vpAdapter);

		recommendTitle.setOnPageChangeListener(new OnPageChangeListener()
		{

			@Override
			public void onPageScrollStateChanged(int arg0)
			{
				if (arg0 == ViewPager.SCROLL_STATE_DRAGGING)
				{
					L.v("arg0 == ViewPager.SCROLL_STATE_DRAGGING");
					isScrolling = true;
				} else
				{
					isScrolling = false;
				}
			}

			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels)
			{
				// recommendTitle.mScroller.setmDuration(500);
				//
				if (isScrolling)
				{
					if (lastValue > positionOffsetPixels)
					{
						dragRight = true;
						dragLeft = false;
					} else if (lastValue < positionOffsetPixels)
					{
						dragRight = false;
						dragLeft = true;
					}
				}
				lastValue = positionOffsetPixels;
			}

			@Override
			public void onPageSelected(int position)
			{
				// position index to 0 时 当时页面加1
				// if (position == 0) {
				// recommendTitle.setCurrentItem(position + 1);
				// }
				// // position 当时位置为结尾时， 将页面位置-1
				// else if (position == listCount - 1) {
				// recommendTitle.setCurrentItem(position - 1);
				// }
				// actuatorPosition = position;
				// if (actuatorPosition == 0) {
				// actuatorPosition = position + 1;
				// } else if (actuatorPosition == listCount - 1) {
				// actuatorPosition = position - 1;
				// }

				// setPosterPosition(actuatorPosition);
			}
		});
		// setTimerFlag(true);
		return v_;

	}

	

	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
		
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{

//		L.v("start");
//		TEvent.trigger("buttom_fragment", new Object[]{R.id.main_rbtn,true});
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.main_listview, null);
	}

	@Override
	public void onDestroy()
	{
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onDestroyView()
	{
		
		super.onDestroyView();
	}

	@Override
	public void onDetach()
	{
		// TODO Auto-generated method stub
		super.onDetach();
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

	@Override
	public void onStop()
	{
		L.v("start");
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	public void onLoadMore()
	{
		L.v("start");
		handler.obtainMessage(LISTVIEW_LOAD_MORE).sendToTarget();
		L.v("end");

	}

	@Override
	public void onChatFood(Food food)
	{
		L.v(food);
		Toast.makeText(mActivity, food.name, Toast.LENGTH_SHORT).show();
	}

	private void onLoad()
	{
		snacklist.stopRefresh();
		snacklist.stopLoadMore();
		snacklist.setRefreshTime("刚刚");
	}

	private static final int LISTVIEW_LOAD_MORE = 10;
	private static final int LISTVIEW_REFRESH = 11;

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler()
	{

		@Override
		public void handleMessage(Message msg)
		{

			final int what = msg.what;
			L.v("what>>" + what);
			switch (what)
			{
			case LISTVIEW_LOAD_MORE:
				loadlist();
				foodsAdapter.notifyDataSetChanged();
				onLoad();
				break;
			case LISTVIEW_REFRESH:
				break;
			}
			L.v("end");
			super.handleMessage(msg);
		}

	};


	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
	{
		Food f = (Food) arg0.getItemAtPosition(arg2);
		Bundle bundle = new Bundle();
		bundle.putParcelable("food", f);
		final FoodContentFragment foodContent = new FoodContentFragment();
		foodContent.setArguments(bundle);
		
		final FragmentManager fm = getActivity().getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
//		ft.setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left);
//		ft.setCustomAnimations(R.anim.fragment_slide_left_enter,R.anim.fragment_slide_left_exit,R.anim.fragment_slide_right_enter, R.anim.fragment_slide_right_exit);
		ft.replace(R.id.main_linear, foodContent, "foodContent");
//		ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
		ft.setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left,R.anim.enter_left_to_right,R.anim.exit_left_to_right);
//		ft.detach(this);
//		 ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		ft.addToBackStack("main_fragment");
//		ft.hide(this);
		ft.show(foodContent);	
		ft.commit();
		L.v("end");
	}

	@Override
	public void onRefresh()
	{
		L.v("start");
		onLoad();
		L.v("end");
		
	}
}
