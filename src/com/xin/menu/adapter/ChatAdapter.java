package com.xin.menu.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
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
	private ArrayList<Food> lists;
	private LayoutInflater inflater;
	private onChanagedCheckBoxListener listener;

	public ChatAdapter(Context c, ArrayList<Food> list,
			onChanagedCheckBoxListener l)
	{
		mContext = c;
		inflater = LayoutInflater.from(mContext);
		this.lists = list;
		if (list == null)
		{
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
		final ViewHolder holder;
		if (view == null)
		{
			holder = new ViewHolder();
			view = inflater.inflate(R.layout.shopping_chat, null);
			holder.foodImage = (ImageView) view
					.findViewById(R.id.chat_food_view);
			holder.food_price = (TextView) view.findViewById(R.id.food_price);
			holder.number = (TextView) view.findViewById(R.id.food_count);
			holder.foodName = (TextView) view.findViewById(R.id.food_name);
			holder.chatbox = (CheckBox) view.findViewById(R.id.chat_cbox);
			holder.delBtn = (Button) view.findViewById(R.id.delete_item_btn);
			view.setTag(holder);
		} else
		{
			holder = (ViewHolder) view.getTag();
		}
		final Food food = lists.get(position);
		holder.foodImage.setImageResource(food.bitmapUrl);
		holder.number.setText("商品数量：" + food.count + " 件");
		holder.food_price.setText(mContext.getResources().getString(
				R.string.price)
				+ food.price);
		holder.foodName.setText(food.name);
		holder.chatbox.setChecked(food.isRacking);

		holder.chatbox.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1)
			{
				listener.OnCheckedChanged(food, arg0, arg1);
			}
		});

		holder.delBtn.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				listener.onDeleteItem(food, v);
			}
		});

		view.setOnTouchListener(new OnTouchListener()
		{
			float x, ux;

			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				final ViewHolder viewHolder = (ViewHolder) v.getTag();
				switch (event.getAction())
				{
				case MotionEvent.ACTION_DOWN:
					x = event.getX();
					v.setBackgroundResource(R.drawable.mm_listitem_pressed);
					viewHolder.delBtn.setVisibility(View.GONE);
					break;
				case MotionEvent.ACTION_UP:
					v.setBackgroundResource(R.drawable.mm_listitem_simple);
					ux = event.getX();
					if(Math.abs(x - ux) > 20){
						viewHolder.delBtn.setVisibility(View.VISIBLE);
					}

					break;
				case MotionEvent.ACTION_MOVE:
					v.setBackgroundResource(R.drawable.mm_listitem_pressed);
					break;
				default:
					v.setBackgroundResource(R.drawable.mm_listitem_simple);
					break;
				}
				return true;
			}
		});
		return view;
	}

	public static final class ViewHolder
	{
		TextView number, food_price, foodName;
		public CheckBox chatbox;
		ImageView foodImage;
		Button delBtn;
	}

	public interface onChanagedCheckBoxListener
	{
		public void OnCheckedChanged(Food f, CompoundButton btn, boolean arg1);

		public void onDeleteItem(Food f, View v);
	}
}
