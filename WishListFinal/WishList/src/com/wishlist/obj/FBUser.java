/* Author: Joon Kim
 * 
 * subclass of User in our system who uses Facebook
 */

package com.wishlist.obj;

import android.os.Parcel;
import android.os.Parcelable;



public class FBUser extends User implements Parcelable{
	
	public FBUser(String ID, String name, boolean isAppUser){
		super(ID, name, isAppUser);
	}

	@Override
	public int describeContents() {
		return 0;
	}


	public static final Parcelable.Creator<FBUser> CREATOR
	= new Parcelable.Creator<FBUser>() {
		public FBUser createFromParcel(Parcel in) {
			return new FBUser(in);
		}	
		public FBUser[] newArray(int size) {
			throw new UnsupportedOperationException();
		}
	};

	@Override
	public void writeToParcel(Parcel out, int flags) {
		if(isAppUser) out.writeInt(1);
		else out.writeInt(0);
		out.writeString(ID);
		out.writeString(name);
		out.writeTypedList(wList);
	}

	private FBUser(Parcel in) {
		super();
		in.readTypedList(wList, WishItem.CREATOR);
		setName(in.readString());
		setId(in.readString());
		if(in.readInt() == 1) isAppUser = true;
		else isAppUser=false;
	}
}