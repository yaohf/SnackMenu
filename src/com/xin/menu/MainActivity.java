package com.xin.menu;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;
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

public class MainActivity extends Activity implements ChatListener,
		IXListViewListener, OnItemClickListener
{
	private Context mContext;

	private XListView snacklist;
	private MyViewPager recommendTitle;
	private View mTouchTarget;
	

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

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);

		mContext = this;

		loadlist();
		snacklist = (XListView) findViewById(R.id.custom_list);
		snacklist.addHeaderView(initlistHeadView(),null,true);
		foodsAdapter = new FoodsAdapter(this, lists, this);
		snacklist.setAdapter(foodsAdapter);
		snacklist.setOnItemClickListener(this);
		snacklist.setPullLoadEnable(true);
		snacklist.setXListViewListener(this);

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
		View v_ = LayoutInflater.from(this).inflate(R.layout.recommend_title,
				null);
		recommendTitle = (MyViewPager) v_.findViewById(R.id.recommed_title_pager);
//		recommendTitle.setOnTouchListener(new OnTouchListener() {
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//					switch (event.getAction()) {
//					case MotionEvent.ACTION_UP:
//						setTimerFlag(true);
//						break;
//					
//					default:
//						setTimerFlag(false);
//						break;
//					}
//				
//				return false;
//			}
//		});
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT);

		List<View> list = new ArrayList<View>();
		for (int i = 0; i < 5; i++)
		{
			ImageView image = new ImageView(this);
			image.setImageResource(R.drawable.meitu_1_200);
			image.setScaleType(ScaleType.FIT_XY);
			image.setLayoutParams(params);
			list.add(image);
		}
		ViewPagerAdapter vpAdapter = new ViewPagerAdapter(this, list);
		recommendTitle.setAdapter(vpAdapter);
		
		recommendTitle.setOnPageChangeListener(new OnPageChangeListener()
		{
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				if (arg0 == ViewPager.SCROLL_STATE_DRAGGING) {
					isScrolling = true;
					mTouchTarget = recommendTitle;
				} else{
					if(arg0 == ViewPager.SCROLL_STATE_IDLE || arg0 == ViewPager.SCROLL_STATE_SETTLING){
						mTouchTarget = null;
					}
					isScrolling = false;
				}
			}

			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
//				recommendTitle.mScroller.setmDuration(500);
//
				if (isScrolling) {
					if (lastValue > positionOffsetPixels) {
						dragRight = true;
						dragLeft = false;
					} else if (lastValue < positionOffsetPixels) {
						dragRight = false;
						dragLeft = true;
					}
				}
				lastValue = positionOffsetPixels;
			}

			@Override
			public void onPageSelected(int position) {
				// position index to 0 时 当时页面加1
//				if (position == 0) {
//					recommendTitle.setCurrentItem(position + 1);
//				}
//				// position 当时位置为结尾时， 将页面位置-1
//				else if (position == listCount - 1) {
//					recommendTitle.setCurrentItem(position - 1);
//				}
//				actuatorPosition = position;
//				if (actuatorPosition == 0) {
//					actuatorPosition = position + 1;
//				} else if (actuatorPosition == listCount - 1) {
//					actuatorPosition = position - 1;
//				}

//				setPosterPosition(actuatorPosition);
			}
		});
//		setTimerFlag(true);
		return v_;

	}


	@Override
	public boolean dispatchTouchEvent(MotionEvent ev)
	{
		if(mTouchTarget != null){
			boolean wasProcessed = mTouchTarget.onTouchEvent(ev);
			if(!wasProcessed){
				mTouchTarget = null;
			}
			return wasProcessed;
		}
		return super.dispatchTouchEvent(ev);
	}



	@Override
	public void onChatFood(Food food)
	{
		L.v(food);
		Toast.makeText(this, food.name, Toast.LENGTH_SHORT).show();
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
	public void onRefresh()
	{
		L.v("start");
		onLoad();
		L.v("end");
	}

	@Override
	public void onLoadMore()
	{
		L.v("start");
		handler.obtainMessage(LISTVIEW_LOAD_MORE).sendToTarget();
		L.v("end");

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
	{
		Food f = (Food) arg0.getItemAtPosition(arg2);
		Intent intent = new Intent(MainActivity.this,FoodContentActivity.class);
		Bundle bundle = new Bundle();
		bundle.putParcelable("food", f);
		intent.putExtra("food_content", bundle);
		startActivity(intent);
		overridePendingTransition(R.anim.enter_right_to_left, R.anim.exit_right_to_left);
//		showDialog(f);
		L.v("end");
	}

//	private TextView food_name_dialog;
//	private TextView food_content_dialog;
//	private TextView food_price_dialog;
//	private ImageView chat_food_view;
//	private Button addCartBtn;
//	private View view;

//	private void showDialog(Food f)
//	{
//		final Food food = f;
//		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
//		view = LayoutInflater.from(this).inflate(R.layout.food_content, null);
//		food_name_dialog = (TextView) view.findViewById(R.id.food_name);
//		food_name_dialog.setText(food.name);
//		
//		chat_food_view = (ImageView) view.findViewById(R.id.chat_food_view);
//		chat_food_view.setBackgroundResource(food.bitmapUrl);
//
//		food_content_dialog = (TextView) view.findViewById(R.id.food_content);
//		food_content_dialog.setText(food.count + "");
//
//		food_price_dialog = (TextView) view.findViewById(R.id.food_price);
//		food_price_dialog.setText(food.price + "");
//
//		addCartBtn = (Button) view.findViewById(R.id.add_cart_btn);
//		addCartBtn.setOnClickListener(new View.OnClickListener()
//		{
//
//			@Override
//			public void onClick(View arg0)
//			{
//				// TODO Auto-generated method stub
//				shopping.addChar(food);
//				Toast.makeText(mContext, getString(R.string.add_cart_ahost), 0)
//						.show();
//			}
//		});
//		
//		dialog.setPositiveButton(R.string.go_shopping, new OnClickListener()
//		{
//
//			@Override
//			public void onClick(DialogInterface dialog, int arg1)
//			{
//				Intent intent = new Intent(MainActivity.this,
//						ShoppingActivity.class);
//				Bundle b = new Bundle();
//				b.putInt("chat_count", shopping.getFoodCount());
//				b.putFloat("chat_price", shopping.getSumPrice());
//				
//				b.putParcelableArrayList("buy_list", shopping.getCarts());
//				intent.putExtra("chat", b);
//				startActivity(intent);
//				dialog.dismiss();
//			}
//		}).setNegativeButton(R.string.continue_shopping, new OnClickListener()
//		{
//
//			@Override
//			public void onClick(DialogInterface dialog, int which)
//			{
//				dialog.dismiss();
//			}
//		});
//		AlertDialog alert = dialog.create();
//		
//		alert.setView(view, 0, 0, 0, 0);
//		alert.setCancelable(true);
//		alert.show();
//
//	}

}
