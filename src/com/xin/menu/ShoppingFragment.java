package com.xin.menu;

import java.util.ArrayList;

import org.ais.event.TEvent;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
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

public class ShoppingFragment extends Fragment implements OnItemClickListener,onChanagedCheckBoxListener
{

	private XListView chatlistview;
	private TextView chatTitle, commodityCount, chatPriceView;
	private Button editBtn, chatBtn,deleteBtn,backMainBtn;
	
	ChatAdapter chatAdapter;
	ArrayList<Food> buyList;
	private ShoppingCart shopping;
	
	private View rootView;

	@Override
	public void onAttach(Activity activity)
	{
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		L.v("start");
//		TEvent.trigger("buttom_fragment", new Object[]{R.id.cart_rbtn,true});
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.shopping_chat_main, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		rootView = getView();
		shopping = ShoppingCart.getInstance();

		
		chatlistview = (XListView) rootView.findViewById(R.id.chat_list);
		chatlistview.setOnItemClickListener(this);
		chatlistview.setPullLoadEnable(false);
		chatlistview.setPullRefreshEnable(false);
		chatlistview.setItemsCanFocus(false);
		
		deleteBtn = (Button) rootView.findViewById(R.id.delete_btn);
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
		
		
		chatTitle = (TextView) rootView.findViewById(R.id.chat_title);
		commodityCount = (TextView) rootView.findViewById(R.id.commodity_count);
		chatPriceView = (TextView) rootView.findViewById(R.id.chat_price);

		Bundle bundle = getArguments();
		buyList = bundle.getParcelableArrayList("buy_list");
		
		int count = bundle.getInt("chat_count");
		float chatPrice = bundle.getFloat("chat_price");

		chatTitle.setText("我的购物车(" + count + ")");
		commodityCount.setText("共 " + count + " 件商品");
		chatPriceView.setText("总价 " + chatPrice);

		chatAdapter = new ChatAdapter(getActivity(), buyList,this);
		chatlistview.setAdapter(chatAdapter);

		editBtn = (Button) rootView.findViewById(R.id.chat_edit);
		backMainBtn = (Button) rootView.findViewById(R.id.back_main_btn);
		backMainBtn.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				backMain();
				
			}
		});

	}

	@Override
	public void onStart()
	{
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	public void onResume()
	{
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public void onSaveInstanceState(Bundle outState)
	{
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}

	@Override
	public void onPause()
	{
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public void onStop()
	{
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	public void onDestroy()
	{
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
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
		FragmentTransaction ft = getFragmentManager().beginTransaction();

		MainListViewFragment mainFragment = new MainListViewFragment();
		
		ft.replace(R.id.main_linear, mainFragment, "main_fragment");//(containerViewId, fragment)(foodContent,"foodContent");
//		ft.setCustomAnimations(R.anim.fragment_slide_left_enter,R.anim.fragment_slide_left_exit,R.anim.fragment_slide_right_enter, R.anim.fragment_slide_right_exit);
//		ft.setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left,R.anim.enter_left_to_right,R.anim.exit_left_to_right);
		ft.setCustomAnimations(R.anim.enter_left_to_right,
				R.anim.exit_left_to_right);
//		ft.hide(this);
		ft.show(mainFragment);
//		ft.detach(this);
		 ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		ft.commit();
		
	
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
