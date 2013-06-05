package com.xin.menu;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.xin.menu.adapter.ChatAdapter;
import com.xin.menu.adapter.ChatAdapter.ViewHolder;
import com.xin.menu.adapter.ChatAdapter.onChanagedCheckBoxListener;
import com.xin.menu.model.Food;
import com.xin.menu.model.ShoppingCart;
import com.xin.menu.util.L;
import com.xin.menu.view.XListView;

public class ShoppingActivity extends Activity implements OnItemClickListener,onChanagedCheckBoxListener
{

	private XListView chatlistview;
	private TextView chatTitle, commodityCount, chatPriceView;
	private Button editBtn, chatBtn,deleteBtn;
	
	ChatAdapter chatAdapter;
	ArrayList<Food> buyList;
	private ShoppingCart shopping;
	

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.shopping_chat_main);
		shopping = ShoppingCart.getInstance();

		chatlistview = (XListView) findViewById(R.id.chat_list);
		chatlistview.setOnItemClickListener(this);
		chatlistview.setPullLoadEnable(false);
		chatlistview.setPullRefreshEnable(false);
		chatlistview.setItemsCanFocus(false);
		
		deleteBtn = (Button) findViewById(R.id.delete_btn);
		deleteBtn.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View arg0)
			{
				L.v("start");
				for(Food f : flagIds){
					for(Food food : shopping.getCarts()){
						if(food.equals(f)){
							shopping.removeFoodItem(f);
							buyList.remove(food);
							break;
						}
					}
				}
				flagIds.clear();
				chatPriceView.setText(shopping.getSumPrice() + "");
				commodityCount.setText("" + shopping.getCount());
				chatTitle.setText("我的购物车(" + shopping.getCount() + ")");
				chatAdapter.notifyDataSetChanged();
			}
		});
		
		
		chatTitle = (TextView) findViewById(R.id.chat_title);
		commodityCount = (TextView) findViewById(R.id.commodity_count);
		chatPriceView = (TextView) findViewById(R.id.chat_price);

		Bundle bundle = getIntent().getBundleExtra("chat");
		buyList = bundle.getParcelableArrayList("buy_list");
		
		int count = bundle.getInt("chat_count");
		float chatPrice = bundle.getFloat("chat_price");

		chatTitle.setText("我的购物车(" + count + ")");
		commodityCount.setText("共 " + count + " 件商品");
		chatPriceView.setText("总价 " + chatPrice);

		chatAdapter = new ChatAdapter(this, buyList,this);
		chatlistview.setAdapter(chatAdapter);

		editBtn = (Button) findViewById(R.id.chat_edit);

	}

	// 标记
	public ArrayList<Food> flagIds = new ArrayList<Food>();

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
	{
		final Food f = (Food) arg0.getItemAtPosition(arg2);
		ChatAdapter.ViewHolder holder = (ViewHolder) arg1.getTag();
		holder.chatbox.setChecked(!holder.chatbox.isChecked());
		
		L.v(" item click food >>" + f);
		if (holder.chatbox.isChecked())
		{
			if (!flagIds.contains(f))
			{
				L.v("add food>>" + f);
				flagIds.add(f);
			}
		} else
		{
			if (flagIds.size() > 0)
			{
				L.v("remove food>>" + f);
				flagIds.remove(f);
			}
		}
	}

	private void backMain(){
		Intent intent = new Intent(this,MainActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.enter_left_to_right,R.anim.exit_left_to_right);
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0){
			backMain();
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void OnCheckedChanged(Food f,CompoundButton btn, boolean isChecked)
	{
		L.v("arg1>>" + isChecked);
		if (btn.isChecked())
		{
			if (!flagIds.contains(f))
			{
				L.v("add food>>" + f);
				flagIds.add(f);
			}
		} else
		{
			if (flagIds.size() > 0)
			{
				L.v("remove food>>" + f);
				flagIds.remove(f);
			}
		}
		
	}

	@Override
	public void onDeleteItem(Food f, View v)
	{
		buyList.remove(f);
		shopping.removeFoodItem(f);
		
		chatPriceView.setText(shopping.getSumPrice() + "");
		commodityCount.setText("" + shopping.getCount());
		chatTitle.setText("我的购物车(" + shopping.getCount() + ")");
		chatAdapter.notifyDataSetChanged();
	}

}
