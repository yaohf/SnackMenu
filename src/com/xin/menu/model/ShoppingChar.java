package com.xin.menu.model;

import java.util.Vector;

public class ShoppingChar
{
	public Vector<Food> foods = new Vector<Food>();

	public void addChar(Food food)
	{
		Food item = food;
		//没有商品直接添加
		if (foods.size() < 0)
		{
			foods.add(item);
		} else
		{
			for (Food f : foods)
			{
				//食物名称相同，数量加1
				if(f.id == item.id){
					item.count++;
					break;
				}
			}
			foods.add(item);
		}
	}
	public void removeFoodItem(Food food){
		if(foods.size() < 0){
			return;
		}
		foods.remove(food);
	}
	//删除多个食物项
	public void removeFoodList(Vector<Food> lists){
		if(foods.size() < 0 ) return;
		foods.removeAll(lists);
	}
	
	public void removeAll(){
		if(foods.size() < 0) return ;
		foods.removeAllElements();
	}

}
