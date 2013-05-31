package com.xin.menu;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.xin.menu.adapter.FoodsAdapter;
import com.xin.menu.adapter.FoodsAdapter.ChatListener;
import com.xin.menu.model.Food;
import com.xin.menu.util.L;
import com.xin.menu.view.XListView;
import com.xin.menu.view.XListView.IXListViewListener;

public class MainActivity extends Activity implements ChatListener,IXListViewListener,OnItemClickListener
{
	
	private XListView snacklist;
	ArrayList<Food> lists = new ArrayList<Food>();
	FoodsAdapter foodsAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		loadlist();
		snacklist = (XListView) findViewById(R.id.custom_list);
		foodsAdapter = new FoodsAdapter(this,lists,this);
		snacklist.setAdapter(foodsAdapter);
		snacklist.setOnItemClickListener(this);
		snacklist.setPullLoadEnable(true);
		snacklist.setXListViewListener(this);
	}
	private int i;
	public void loadlist(){
		Food food = null;
		for(i = 0; i != 20; i++){
			food = new Food();
			food.bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
			food.id = i;
			food.name = "红烧" + i;
			food.price = 5 + i; 
			lists.add(food);
		}
	}

	@Override
	public void onChatFood(Food food)
	{
		L.v(food);
		Toast.makeText(this, food.name, Toast.LENGTH_SHORT).show();
	}
	
	private void onLoad(){
		snacklist.stopRefresh();
		snacklist.stopLoadMore();
		snacklist.setRefreshTime("刚刚");
	}
	
	
	private static final int LISTVIEW_LOAD_MORE = 10;
	private static final int LISTVIEW_REFRESH = 11;
	
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg)
		{
			
			final int what = msg.what;
			L.v("what>>" + what);
			switch(what)
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
		
	}

}
