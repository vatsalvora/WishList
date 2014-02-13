/*Author: Joon Kim
 * 
 * Pair class to link two objects together
 */

package com.wishlist.obj;

public class Pair<A, B>{
	public A first;
	public B second;
	
	Pair(){
		throw new RuntimeException("Invalid constructor!");
	}
	
	Pair(A a, B b){
		first = a;
		second = b;
	}

}
