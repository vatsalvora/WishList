package com.wishlist.obj;

import java.io.InputStream;
import java.util.TreeMap;


public class User {
	private String FBID;
	private String name;
	private TreeMap<String, WishItem> wList;
	private final boolean isUser;
	
	public User(){
		throw new RuntimeException("Not valid user.");
	}
	
	public User(String FBID, String name, boolean isUser){
		this.FBID = FBID;
		this.name = name;
		this.isUser = isUser;
	}
	
	public String getFBID(){
		return FBID;
	}
	
	public String getName(){
		return name;
	}
	
	public TreeMap<String, WishItem> getList(){
		return wList;
	}
	
	public WishItem getItem(String name){
		return wList.get(name);
	}
	
	public boolean getIsUser(){
		return isUser;
	}
	
	public static void parseXML(InputStream in){
		
	}
}
