package com.xin.menu.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
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
	private onChanagedCheckBoxListener listener;
	
	public ChatAdapter(Context c,ArrayList<Food> list,onChanagedCheckBoxListener l)
	{
		mContext = c;
		inflater = LayoutInflater.from(mContext);
		this.lists = list;
		if(list == null){
			lists = new ArrayList<Food>();
		}
		listener = l;
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
		holder.chatbox.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1)
			{
				// TODO Auto-generated method stub
				listener.OnCheckedChanged(food,arg0, arg1);
			}
		});
		return view;
	}

	
	public static final class ViewHolder
	{
		TextView number,food_price,foodName;
		public CheckBox chatbox;
		ImageView foodImage;
	}
	
	public interface onChanagedCheckBoxListener{
		public void OnCheckedChanged(Food f,CompoundButton btn,boolean arg1);
	}
}
