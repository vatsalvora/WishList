/*Author: Joon Kim
 *
 * Pair class to link two objects together
 * 
 * EDIT: implementing Parcelable with generics is extremely tricky. Solution here is unfavorable. (Joon)
 * EDIT2: Fuck it. Went into StringPair
 */

package com.wishlist.obj;

public final class IDNamePair{
	public String ID;
	public String name;
	
	public IDNamePair(){
		throw new RuntimeException("Uhhhh...");
	}
	
	public IDNamePair(String ID, String name){
		this.ID=ID;
		this.name=name;
	}
}