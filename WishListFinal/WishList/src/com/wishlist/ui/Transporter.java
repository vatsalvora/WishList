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
	//Shouldn't we put all of these in strings.xml with the other string resources? (Christa)
	/*
	 * There's nothing wrong with that. Putting stuff in strings.xml means that the XML layouts can see this also.
	 * The question is, do we want the layouts to see the data? My answer is I don't really care, but reference-wise in Java source,
	 * it's clear where it is coming from (i.e. Transporter.USER) (Joon)
	 */
	public static final String USER = "User";
	public static final String CURRENT = "CurrentUser";
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
	public static final void pack(Bundle b, String key, Parcelable p)
	{
		b.putParcelable(key, p);
	}
	
	public static final void pack(Bundle b, String key, ArrayList<? extends Parcelable> p)
	{
		b.putParcelableArrayList(key, p);
	}
	
	public static final void pack(Bundle b, String key, String p)
	{
		b.putString(key, p);
	}
	
	public static final Parcelable unpackObject(Bundle b, String key){
		return b.getParcelable(key);
	}
	
	public static final ArrayList<? extends Parcelable> unpackArrayList(Bundle b, String key){
		return b.getParcelableArrayList(key);
	}
	
	protected static final void nullError(){
		throw new RuntimeException("The object is null");
	}
	
}
