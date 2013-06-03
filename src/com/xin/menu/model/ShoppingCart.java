package com.xin.menu.model;

import java.util.ArrayList;
import com.xin.menu.util.L;

public class ShoppingCart
{
	private static ShoppingCart instance;

	private float sumPrice;
	private int foodCount;

	public int getFoodCount(){
		return foodCount;
	}
	
	private ShoppingCart()
	{
	}

	public synchronized static ShoppingCart getInstance()
	{
		if (null == instance)
		{
			instance = new ShoppingCart();
		}
		return instance;
	}

	public static ArrayList<Food> foods = new ArrayList<Food>();

	public ArrayList<Food> getCarts()
	{
		return foods;
	}

	public synchronized void addChar(Food food)
	{
		L.v(food);
		Food item = food;
		// 没有商品直接添加
		if (foods.size() <= 0)
		{
			L.v("size < 0");
			sumPrice = item.price;
			foodCount = 1;
			foods.add(item);
		} else
		{
			L.v("size > 0");

			if (foods.contains(item))
			{
				foodCount++;
				// 食物名称相同，数量加1
				item.count++;
				sumPrice += item.price;
//				break;
			} else
			{
				foodCount++;
				sumPrice += item.price;
				foods.add(item);
			}
			

		}

	}

	public int getCount()
	{
		return foods.size();
	}

	/**
	 * 商品总价格
	 * 
	 * @return
	 */
	public float getSumPrice()
	{
		return sumPrice;
	}

	public void removeFoodItem(Food food)
	{
		if (foods.size() < 0)
		{
			return;
		}
		// 重新计算价格
		if (sumPrice > 0)
		{
			sumPrice -= food.price;
		}
		foods.remove(food);
	}

	// 删除多个食物项
	public void removeFoodList(ArrayList<Food> lists)
	{
		if (foods.size() < 0)
			return;
		foods.removeAll(lists);
	}

	public void removeAll()
	{
		if (foods.size() < 0)
			return;
		foods.removeAll(foods);
	}

}
