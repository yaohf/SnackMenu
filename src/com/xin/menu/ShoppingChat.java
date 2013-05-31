package com.xin.menu;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.xin.menu.adapter.ChatAdapter;
import com.xin.menu.model.Food;
import com.xin.menu.view.XListView;

public class ShoppingChat extends Activity
{
	
	private XListView chatlistview;
	private TextView chatTitle,commodityCount,chatPriceView;
	private Button editBtn,chatBtn;
	ChatAdapter chatAdapter;
	ArrayList<Food> buyList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shopping_chat_main);
		chatlistview = (XListView) findViewById(R.id.chat_list);
		
		chatTitle = (TextView) findViewById(R.id.chat_title);
		commodityCount = (TextView) findViewById(R.id.commodity_count);
		chatPriceView = (TextView) findViewById(R.id.chat_price);
		
		Bundle bundle = getIntent().getBundleExtra("chat");
		buyList = bundle.getParcelableArrayList("buy_list");
		String count = bundle.getString("chat_count");
		String chatPrice = bundle.getString("chat_price"); 

		chatTitle.setText("我的购物车(" + count + ")");
		commodityCount.setText("共 " + count + " 件商品");
		chatPriceView.setText("总价 " + chatPrice);
		
		chatAdapter = new ChatAdapter(this, buyList);
		chatlistview.setAdapter(chatAdapter);
		
		editBtn = (Button) findViewById(R.id.chat_edit);
		chatBtn = (Button) findViewById(R.id.chat_btn);
		
		
	}

}
