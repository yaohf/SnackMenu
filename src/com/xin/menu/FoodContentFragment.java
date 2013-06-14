package com.xin.menu;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xin.menu.model.Food;
import com.xin.menu.model.ShoppingCart;

@SuppressLint("NewApi")
public class FoodContentFragment extends Fragment
{

	private TextView food_name_dialog;
	private TextView food_content_dialog;
	private TextView food_price_dialog;
	private ImageView chat_food_view;
	private Button addCartBtn, backBtn, continueBtn, goShoppingBtn;
	Food food;
	ShoppingCart shopping;

	private View view;

	@Override
	public View getView()
	{
		// TODO Auto-generated method stub
		return super.getView();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		view = getView();
		
		
		shopping = ShoppingCart.getInstance();
		
		Bundle bundle = getArguments();
		if(bundle != null)
		{
			food = (Food) bundle.get("food");
			food_name_dialog = (TextView) view.findViewById(R.id.food_name);
			food_name_dialog.setText(food.name);

			chat_food_view = (ImageView) view.findViewById(R.id.chat_food_view);
			chat_food_view.setBackgroundResource(food.bitmapUrl);

			food_content_dialog = (TextView) view.findViewById(R.id.food_content);
			food_content_dialog.setText(food.count + "");

			food_price_dialog = (TextView) view.findViewById(R.id.food_price);
			food_price_dialog.setText(food.price + "");

		}
		
		addCartBtn = (Button) view.findViewById(R.id.add_cart_btn);
		addCartBtn.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{
				// TODO Auto-generated method stub
				shopping.addChar(food);
				Toast toast = Toast.makeText(getActivity(),
						getString(R.string.add_cart_ahost), 0);
				toast.setGravity(Gravity.BOTTOM, 0, 0);
				toast.show();
			}
		});

		backBtn = (Button) view.findViewById(R.id.back_btn);
		backBtn.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				backMain();
			}
		});

		continueBtn = (Button) view.findViewById(R.id.continue_back_btn);
		continueBtn.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				backMain();
			}
		});
		
		goShoppingBtn = (Button) view.findViewById(R.id.in_cart_btn);
		goShoppingBtn.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Bundle b = new Bundle();
				b.putInt("chat_count", shopping.getFoodCount());
				b.putFloat("chat_price", shopping.getSumPrice());
				b.putParcelableArrayList("buy_list", shopping.getCarts());
				
				ShoppingFragment shoppingFragment = new ShoppingFragment();
				shoppingFragment.setArguments(b);
				FragmentTransaction ft = getFragmentManager().beginTransaction();
				ft.replace(R.id.main_linear, shoppingFragment);
//				ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
				ft.setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left,R.anim.enter_left_to_right,R.anim.exit_left_to_right);
				ft.show(shoppingFragment);
//				ft.setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left,R.anim.enter_left_to_right,R.anim.exit_left_to_right);
//				ft.setCustomAnimations(R.anim.fragment_slide_left_enter,R.anim.fragment_slide_left_exit,R.anim.fragment_slide_right_enter, R.anim.fragment_slide_right_exit);
				ft.addToBackStack("main_fragment");
				//点击进入shoppingFragment 的时候 pop 当前stack
				getFragmentManager().popBackStack();
				
				ft.commit();
			}
		});

	}

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
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.food_content, null);
	}

	@Override
	public void onDestroy()
	{
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onDestroyView()
	{
		// TODO Auto-generated method stub
		super.onDestroyView();
	}

	@Override
	public void onDetach()
	{
		// TODO Auto-generated method stub
		super.onDetach();
	}

	@Override
	public void onPause()
	{
		// TODO Auto-generated method stub
		super.onPause();
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
	public void onStart()
	{
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	public void onStop()
	{
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		// this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// setContentView(R.layout.food_content);

	}

	private void backMain(){
		FragmentTransaction ft = getFragmentManager().beginTransaction();

		MainListViewFragment mainFragment = new MainListViewFragment();
		
		ft.replace(R.id.main_linear, mainFragment, "main_fragment");//(containerViewId, fragment)(foodContent,"foodContent");
//		ft.setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left,R.anim.enter_left_to_right,R.anim.exit_left_to_right);
		ft.setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left);
//		ft.hide(this);
		ft.show(mainFragment);
//		ft.setCustomAnimations(R.anim.fragment_slide_left_enter,R.anim.fragment_slide_left_exit,R.anim.fragment_slide_right_enter, R.anim.fragment_slide_right_exit);
		 ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		ft.commit();
	
	}

}
