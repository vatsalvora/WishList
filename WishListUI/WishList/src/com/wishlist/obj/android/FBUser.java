/* Author: Joon Kim
 * 
 * subclass of User in our system who uses Facebook
 */

package com.wishlist.obj.android;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

import com.wishlist.obj.User;

public class FBUser extends User implements Parcelable{
	
	public FBUser(String ID, String name, boolean isAppUser){
		super(ID, name, isAppUser);
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
	//out.writeList(data);
}

private FBUser(Parcel in) {
	super();
}

@Override
	public int describeContents() {
	// TODO Auto-generated method stub
	return 0;
	}

}
