package com.xin.menu;

import java.util.ArrayList;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
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
	private CheckBox selectBox;
	
	ChatAdapter chatAdapter;
	ArrayList<Food> buyList;
	private ShoppingCart shopping;
	

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shopping_chat_main);
		shopping = ShoppingCart.getInstance();

		chatlistview = (XListView) findViewById(R.id.chat_list);
		chatlistview.setOnItemClickListener(this);
		chatlistview.setPullLoadEnable(false);
		chatlistview.setPullRefreshEnable(false);
		chatlistview.setItemsCanFocus(false);
		
		selectBox = (CheckBox) findViewById(R.id.select_box);
		selectBox.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1)
			{
				// TODO Auto-generated method stub
				
			}
		});
		deleteBtn = (Button) findViewById(R.id.delete_btn);
		deleteBtn.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View arg0)
			{
				L.v("start");
				for(Food f : flagIds){
					L.v("f forcech>>" + f);
					for(Food food : shopping.getCarts()){
						if(food.equals(f)){
							L.v("food.id>>" + food.id + "\t f.id>>" + f.id);
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

}
