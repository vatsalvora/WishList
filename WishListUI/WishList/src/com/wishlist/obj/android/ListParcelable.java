package com.wishlist.obj.android;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class ListParcelable implements Parcelable{
	private List<String> data;
	
	@Override
	public int describeContents() {
		return 0;
	}
	
	public static final Parcelable.Creator<ListParcelable> CREATOR
		= new Parcelable.Creator<ListParcelable>() {
		public ListParcelable createFromParcel(Parcel in) {
			return new ListParcelable(in);
		}	
		public ListParcelable[] newArray(int size) {
			return new ListParcelable[size];
		}
	};
	
	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeList(data);
	}
	
	private ListParcelable(Parcel in) {
		data = new ArrayList<String>();
		in.readList(data, getClass().getClassLoader());
	}
}
