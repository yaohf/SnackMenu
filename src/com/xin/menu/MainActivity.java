package com.xin.menu;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xin.menu.adapter.FoodsAdapter;
import com.xin.menu.adapter.FoodsAdapter.ChatListener;
import com.xin.menu.model.Food;
import com.xin.menu.model.ShoppingCart;
import com.xin.menu.util.L;
import com.xin.menu.view.XListView;
import com.xin.menu.view.XListView.IXListViewListener;

public class MainActivity extends Activity implements ChatListener,IXListViewListener,OnItemClickListener
{
	private Context mContext;
	
	private XListView snacklist;
	ArrayList<Food> lists = new ArrayList<Food>();
	FoodsAdapter foodsAdapter;
	
	ShoppingCart shopping;	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		mContext = this;
		
		loadlist();
		snacklist = (XListView) findViewById(R.id.custom_list);
		foodsAdapter = new FoodsAdapter(this,lists,this);
		snacklist.setAdapter(foodsAdapter);
		snacklist.setOnItemClickListener(this);
		snacklist.setPullLoadEnable(true);
		snacklist.setXListViewListener(this);
		
		shopping = ShoppingCart.getInstance();
	}
	private int i;
	public void loadlist(){
		Food food = null;
		for(i = 0; i != 20; i++){
			food = new Food();
			food.bitmapUrl =  R.drawable.ic_launcher;
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
		L.v("start");
		Food f = (Food) arg0.getItemAtPosition(arg2);
		showDialog(f);
		L.v("end");
	}
	
	private TextView food_name_dialog;
	private TextView food_content_dialog;
	private TextView food_price_dialog;
	private ImageView chat_food_view;
	private Button addCartBtn;
	private View view;
	
	private void showDialog(Food f)
	{
		final Food  food = f;
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		view = LayoutInflater.from(this).inflate(R.layout.food_content, null);
		food_name_dialog = (TextView) view.findViewById(R.id.food_name);
		food_name_dialog.setText(food.name);
		
		chat_food_view = (ImageView)view.findViewById(R.id.chat_food_view);
		chat_food_view.setBackgroundResource(food.bitmapUrl);
		
		food_content_dialog = (TextView) view.findViewById(R.id.food_content);
		food_content_dialog.setText(food.count + "");
		
		food_price_dialog = (TextView) view.findViewById(R.id.food_price);
		food_price_dialog.setText(food.price + "");
		
		addCartBtn = (Button) view.findViewById(R.id.add_cart_btn);
		addCartBtn.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View arg0)
			{
				// TODO Auto-generated method stub
				shopping.addChar(food);
				Toast.makeText(mContext,getString(R.string.add_cart_ahost), 0).show();
			}
		});
		dialog.setView(view);
		dialog.setPositiveButton(R.string.go_shopping, new OnClickListener()
		{
			
			@Override
			public void onClick(DialogInterface dialog, int arg1)
			{
				Intent intent = new Intent(MainActivity.this,ShoppingActivity.class);
				Bundle b = new Bundle();
				b.putInt("chat_count", shopping.getFoodCount());
				b.putFloat("chat_price", shopping.getSumPrice());
				for(Food f : shopping.getCarts()){
					L.v("f>>" + f);
				}
				b.putParcelable("aa", food);
				b.putParcelableArrayList("buy_list", shopping.getCarts());
				intent.putExtra("chat", b);
				startActivity(intent);
				dialog.dismiss();
			}
		})
		.setNegativeButton(R.string.continue_shopping, new OnClickListener()
		{
			
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
					dialog.dismiss();
			}
		})
		.setCancelable(true)
		.show();
		
	}
	
}
