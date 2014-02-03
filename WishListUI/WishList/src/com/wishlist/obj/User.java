package com.wishlist.obj;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;


public class User {
	private String FBID; //facebook ID
	private String name; //name of facebook user
	private ArrayList<WishItem> wList; //wishlist for user
	private final boolean isAppUser; //flag for current app user
	
	public User(){
		throw new RuntimeException("Not valid user.");
	}
	
	public User(String FBID, String name, boolean isUser){
		this.FBID = FBID;
		this.name = name;
		this.isAppUser = isUser;
	}
	
	public String getFBID(){
		return FBID;
	}
	
	public String getName(){
		return name;
	}
	
	public ArrayList<WishItem> getList(){
		return wList;
	}
	
	public boolean getIsUser(){
		return isAppUser;
	}
	
	public static void parseXML(InputStream in){
		
	}
	
	private void sort(){
		Collections.sort(wList);
	}
}
