package com.xin.menu.model;

import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;

import android.os.Parcel;
import android.os.Parcelable;


public class Food implements Parcelable
{
	/**
	 * id：产品编号
	 * menuName 菜品名称
	 * price 菜品价钱
	 * count 数量
	 * isRacking 是否上架
	 */
	public int id;
	public String name;
	public String content;
	public float price;
	public int count = 1;
	public int bitmapUrl;
	public boolean isRacking;
//	public Bitmap bitmap;
//	
//	private static  byte [] byteBitmap;
//	
//	private byte [] getBytes(Bitmap bitmap){
//		ByteArrayOutputStream baops = new ByteArrayOutputStream();
//		bitmap.compress(CompressFormat.PNG, 0, baops);
//		return baops.toByteArray();
//	}
//	private static Bitmap getBitmap(byte [] bytes){
//		return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//	}
	
	
	@Override
	public String toString()
	{
		return "Food [id=" + id + ", name=" + name + ", price=" + price
				+ ", count=" + count + ", bitmap=" + bitmapUrl + "]";
	}

//	@Override
//	public int hashCode()
//	{
//		final int PRIME = 31;
//		int result = 1;
//		result = PRIME * result + id;
//		return result;
//	}

	@Override
	public boolean equals(Object obj)
	{
		if(obj instanceof Food){
			Food f = (Food) obj;
			return f.name.equals(name);
		}
		// TODO Auto-generated method stub
		return false;
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
		dest.writeInt(bitmapUrl);
//		byteBitmap = getBytes(bitmap);
//		dest.writeByteArray(byteBitmap);
//		dest.writeParcelable(bitmap, flags);
//		bitmap.writeToParcel(dest, 0);
		dest.writeString(content);
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
			item.content = par.readString();
			item.bitmapUrl = par.readInt();
//			par.readByteArray(byteBitmap);
//			item.bitmap = getBitmap(byteBitmap);
//			item.bitmap = (Bitmap)par.readParcelable(Bitmap.class.getClassLoader());
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
