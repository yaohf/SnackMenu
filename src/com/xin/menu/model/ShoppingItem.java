package com.xin.menu.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 购物车项
 * @author jiang
 *
 */
public class ShoppingItem implements Parcelable
{
	public Food item;
	public int count;
	@Override
	public int describeContents()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags)
	{
		// TODO Auto-generated method stub
		dest.writeInt(count);
		dest.writeParcelable(item,flags);
	}
	public static final  Parcelable.Creator<ShoppingItem> CREATOR = new Creator<ShoppingItem>()
	{
		
		@Override
		public ShoppingItem[] newArray(int size)
		{
			// TODO Auto-generated method stub
			return new ShoppingItem[size];
		}
		
		@Override
		public ShoppingItem createFromParcel(Parcel source)
		{
			ShoppingItem shoppingItem = new ShoppingItem();
			shoppingItem.item = source.readParcelable(Food.class.getClassLoader()) ;
			shoppingItem.count = source.readInt();
			return shoppingItem;
		}
	};

}
