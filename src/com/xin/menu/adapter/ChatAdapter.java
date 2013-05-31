package com.xin.menu.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.xin.menu.R;
import com.xin.menu.model.Food;
import com.xin.menu.util.L;

public class ChatAdapter extends BaseAdapter
{

	private Context mContext;
	private ArrayList<Food>lists;
	private LayoutInflater inflater;
	
	public ChatAdapter(Context c,ArrayList<Food> list)
	{
		mContext = c;
		inflater = LayoutInflater.from(mContext);
		this.lists = list;
		if(list == null){
			lists = new ArrayList<Food>();
		}
	}
	@Override
	public int getCount()
	{
		return lists.size();// > 0 ? lists.size() : 0;
	}

	@Override
	public Object getItem(int position)
	{
		return lists.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent)
	{
		ViewHolder holder;
		if(view == null)
		{
			holder = new ViewHolder();
			view = inflater.inflate(R.layout.shopping_chat, null);
			holder.foodImage = (ImageView) view.findViewById(R.id.chat_food_view);
			holder.food_price = (TextView) view.findViewById(R.id.food_price);
			holder.number = (TextView) view.findViewById(R.id.food_count);
			holder.foodName = (TextView) view.findViewById(R.id.food_name);
			holder.chatbox = (CheckBox) view.findViewById(R.id.chat_cbox);
			view.setTag(holder);
		}else {
			holder = (ViewHolder) view.getTag();
		}
		final Food food = lists.get(position);
		holder.foodImage.setImageResource(food.bitmapUrl);
		holder.number.setText("商品数量：" + food.count + " 件");
		holder.food_price.setText( mContext.getResources().getString(R.string.price) + food.price);
		holder.foodName.setText(food.name);
		holder.chatbox.setChecked(food.isRacking);
		return view;
	}

	
	public static final class ViewHolder
	{
		TextView number,food_price,foodName;
		public CheckBox chatbox;
		ImageView foodImage;
	}
}