package com.xin.menu.model;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class Food implements Parcelable
{
	/**
	 * id：产品编号
	 * menuName 菜品名称
	 * price 菜品价钱
	 * count 数量
	 */
	public int id;
	public String name;
	public float price;
	public int count; 
	public Bitmap bitmap;
	
	
	
	@Override
	public String toString()
	{
		return "Food [id=" + id + ", name=" + name + ", price=" + price
				+ ", count=" + count + ", bitmap=" + bitmap + "]";
	}

	@Override
	public int describeContents()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags)
	{
		dest.writeInt(id);
		dest.writeInt(count);
		dest.writeString(name);
		dest.writeFloat(price);
		dest.writeParcelable(bitmap, 0);
	}
	public static final Parcelable.Creator<Food> CREATOR = new Parcelable.Creator<Food>(){

		@Override
		public Food createFromParcel(Parcel par)
		{
			Food item = new Food();
			item.id = par.readInt();
			item.count = par.readInt();
			item.name = par.readString();
			item.price = par.readFloat();
			return item;
		}

		@Override
		public Food[] newArray(int size)
		{
			// TODO Auto-generated method stub
			return new Food[size];
		}
		
	};

}
