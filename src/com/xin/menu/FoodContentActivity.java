package com.xin.menu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xin.menu.model.Food;
import com.xin.menu.model.ShoppingCart;

public class FoodContentActivity extends Activity
{

	private TextView food_name_dialog;
	private TextView food_content_dialog;
	private TextView food_price_dialog;
	private ImageView chat_food_view;
	private Button addCartBtn,backBtn,continueBtn,goShoppingBtn;
	
	
	ShoppingCart shopping;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.food_content);
		
		shopping =  ShoppingCart.getInstance();
		
		Bundle bundle = getIntent().getBundleExtra("food_content");
		final Food food = (Food) bundle.get("food");
		food_name_dialog = (TextView) findViewById(R.id.food_name);
		food_name_dialog.setText(food.name);
		
		chat_food_view = (ImageView) findViewById(R.id.chat_food_view);
		chat_food_view.setBackgroundResource(food.bitmapUrl);

		food_content_dialog = (TextView) findViewById(R.id.food_content);
		food_content_dialog.setText(food.count + "");

		food_price_dialog = (TextView)findViewById(R.id.food_price);
		food_price_dialog.setText(food.price + "");

		addCartBtn = (Button) findViewById(R.id.add_cart_btn);
		addCartBtn.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{
				// TODO Auto-generated method stub
				shopping.addChar(food);
				Toast toast = Toast.makeText(FoodContentActivity.this,getString(R.string.add_cart_ahost),0);
				toast.setGravity(Gravity.BOTTOM, 0, 0);
				toast.show();
			}
		});

		backBtn = (Button) findViewById(R.id.back_btn);
		backBtn.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				backUp();
			}
		});
		
		continueBtn = (Button) findViewById(R.id.continue_back_btn);
		continueBtn.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				backUp();				
			}
		});
		goShoppingBtn = (Button) findViewById(R.id.in_cart_btn);
		goShoppingBtn.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
			
				Intent intent = new Intent(FoodContentActivity.this,
						ShoppingActivity.class);
				Bundle b = new Bundle();
				b.putInt("chat_count", shopping.getFoodCount());
				b.putFloat("chat_price", shopping.getSumPrice());
				
				b.putParcelableArrayList("buy_list", shopping.getCarts());
				intent.putExtra("chat", b);
				startActivity(intent);
				overridePendingTransition(R.anim.enter_right_to_left, R.anim.exit_right_to_left);
			}
		});
	}

	private void backUp(){
		Intent intent = new Intent(FoodContentActivity.this,MainActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.enter_left_to_right,R.anim.exit_left_to_right);				

	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0){
			backUp();
		}
		return super.onKeyDown(keyCode, event);
	}

	
}
