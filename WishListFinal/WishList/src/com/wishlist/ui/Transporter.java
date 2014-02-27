/* Author: Joon Kim
 * 
 * Basically a class to pass data around. No need to instance a Transporter class, just call the constants and static methods.
 * 
 */

package com.wishlist.ui;

import java.util.ArrayList;

import android.os.Bundle;
import android.os.Parcelable;

public class Transporter {
	
	//hashcodes for transporting data around
	public static final String USER = "User";
	public static final String CURRENT = "CurrentUser";
	public static final String APP = "AppUser";
	public static final String WISH = "Wish";
	public static final String WISHES = "Wishes";
	public static final String FRIENDS = "Friends";
	public static final String DATA = "Data";
	public static final String FBDATA = "FacebookData";
	public static final String GDATA = "GoogleData";
	
	private Transporter()
	{
		throw new RuntimeException("How did you access this? You are a magician...");
	}
	
	//for all pack_into_bundle methods, put the bundle, the key, and the parcelable object in the methods
	//I'm using method overloading in order to standardize the transport process. (Joon)
	public static final void packIntoBundle(Bundle b, String key, Parcelable p)
	{
		b.putParcelable(key, p);
	}
	
	public static final void packIntoBundle(Bundle b, String key, ArrayList<? extends Parcelable> p)
	{
		b.putParcelableArrayList(key, p);
	}
	
	public static final void packIntoBundle(Bundle b, String key, String val)
	{
		b.putString(key, val);
	}
}
