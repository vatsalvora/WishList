/* Author: Joon Kim
 * 
 * Basically a class to pass data around. No need to instance a Transporter class, just call the constants and static methods.
 * 
 */

package com.wishlist.ui.util;

import java.util.ArrayList;

import android.os.Bundle;
import android.os.Parcelable;

public final class Transporter {
	
	//hashcodes for transporting data around
	
	public static final String USER = "User";
	public static final String APPUSER = "AppUser";
	public static final String CURRENT = "CurrentUser";
	public static final String WISH = "Wish";
	public static final String WISHES = "Wishes";
	public static final String FRIENDS = "Friends";
	public static final String DATA = "Data";
	public static final String HASH = "Hash";
	public static final String FBDATA = "FacebookData";
	public static final String GDATA = "GoogleData";
	public static final String INDEX = "Index";
	
	private Transporter()
	{
		throw new RuntimeException("How did you access this? You are a magician...");
	}
	
	//for all pack_into_bundle methods, put the bundle, the key, and the parcelable object in the methods
	//I'm using method overloading in order to standardize the transport process. (Joon)
	public static void pack(Bundle b, String key, Parcelable p)
	{
		b.putParcelable(key, p);
	}
	
	public static void pack(Bundle b, String key, ArrayList<? extends Parcelable> p)
	{
		b.putParcelableArrayList(key, p);
	}
	
	public static void pack(Bundle b, String key, String p)
	{
		b.putString(key, p);
	}
	
	public static void pack(Bundle b, String key, int num){
		b.putInt(key, num);
	}
	
	public static void pack(Bundle b, String key, boolean val){
		b.putBoolean(key, val);
	}
	
	public static Parcelable unpackObject(Bundle b, String key){
		return b.getParcelable(key);
	}
	
	public static ArrayList<? extends Parcelable> unpackArrayList(Bundle b, String key){
		return b.getParcelableArrayList(key);
	}
	
	public static int unpackInteger(Bundle b, String key){
		return b.getInt(key);
	}
	
	
	public static boolean unpackBoolean(Bundle b, String key){
		return b.getBoolean(key);
	}
	
	protected static void nullError(){
		throw new RuntimeException("The object is null");
	}
	
}
