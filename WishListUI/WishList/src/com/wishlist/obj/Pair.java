package com.wishlist.obj;

public class Pair<A, B> {
	private A first;
	private B second;
	
	Pair(){
		throw new RuntimeException("Invalid constructor!");
	}
	
	Pair(A a, B b){
		setFirst(a);
		setSecond(b);
	}
	
	public A getFirst(){
		return first;
	}
	
	public void setFirst(A a){
		first = a;
	}
	
	public B getSecond(){
		return second;
	}
	
	public void setSecond(B b){
		second = b;
	}
}
