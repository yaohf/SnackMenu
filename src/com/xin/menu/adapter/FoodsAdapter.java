package com.xin.menu.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.xin.menu.R;
import com.xin.menu.model.Food;

public  class FoodsAdapter extends BaseAdapter
{

	private ChatListener chatlistener;
	private Context mContext;
	private ArrayList<Food>lists;
	private LayoutInflater inflater;
	
	public FoodsAdapter(Context c,ArrayList<Food> list,ChatListener listener)
	{
		mContext = c;
		this.chatlistener = listener;
		inflater = LayoutInflater.from(mContext);
		this.lists = list;
		if(list == null){
			lists = new ArrayList<Food>();
		}
	}
	@Override
	public int getCount()
	{
		return lists.size() > 0 ? lists.size() : 0;
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
			view = inflater.inflate(R.layout.list_item, null);
//			holder.chatBtn = (ImageButton) view.findViewById(R.id.chat_btn);
			holder.foodImage = (ImageView) view.findViewById(R.id.food_image);
			holder.foodContent = (TextView) view.findViewById(R.id.food_content);
			holder.food_price = (TextView) view.findViewById(R.id.food_price);
			holder.number = (TextView) view.findViewById(R.id.number);
			holder.foodName = (TextView) view.findViewById(R.id.food_name);
			view.setTag(holder);
		}else {
			holder = (ViewHolder) view.getTag();
		}
		
		final Food food = lists.get(position);
		holder.foodImage.setImageResource(food.bitmapUrl);
		holder.number.setText(food.id + "");
		holder.foodContent.setText(R.string.food_content);
		holder.food_price.setText( mContext.getResources().getString(R.string.price) + food.price);
		holder.foodName.setText(food.name);
		
		//点击购物
//		holder.chatBtn.setOnClickListener(new OnClickListener()
//		{
//			
//			@Override
//			public void onClick(View arg0)
//			{
//				chatlistener.onChatFood(food);
//			}
//		});
		
		return view;
	}

	static final class ViewHolder
	{
		TextView number,food_price,foodName,foodContent;
		ImageButton chatBtn;
		
		ImageView foodImage;
	}
	public interface ChatListener{
		public void onChatFood(Food food);
	}
	
}
