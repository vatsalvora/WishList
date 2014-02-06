package com.wishlist.obj;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;


public class User {
	private String FBID; //facebook ID
	private String name; //name of facebook user
	private ArrayList<WishItem> wList; //wishlist for user
	private static final int MAXITEMS = 10;
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
	
	public boolean addItem(WishItem w){
		if(wList.size() == MAXITEMS) return false;
		wList.add(w);
		return true;
	}
	
	public boolean removeItem(WishItem w){
		return wList.remove(w);
	}
	
	public void sortList(char code){
		switch(code){
			case 'n':
				Collections.sort(wList);
				break;
			case 'd':
				Collections.sort(wList, new DateComparison());
				break;
			case 'p':
				Collections.sort(wList, new PriceComparison());
				break;
			default:
				throw new RuntimeException("code not found.");
		}
	}
}
