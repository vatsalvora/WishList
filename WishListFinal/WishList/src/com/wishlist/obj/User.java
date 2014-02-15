/* Author: Joon Kim
 * 
 * Class for users of this app.
 * The user has a unique ID, a name, a flag for app user, and a list of items in the database.
 * The app user can add and remove items from his/her own list and sort the list by name, date, or price.
 */

package com.wishlist.obj;

import java.util.ArrayList;
import java.util.Collections;

public abstract class User {
	protected boolean isAppUser; //flag for current app user
	protected String ID; //ID of user
	protected String name; //name of user
	protected ArrayList<WishItem> wList; //wishlist for user
	public static final int MAXITEMS = 10; //maximum number of items allowed for one user to have
	
	public User(){
	}
	
	public User(String ID, String name, boolean isAppUser){
		setName(name);
		setId(ID);
		setIsAppUser(isAppUser);
	}
	
	public String getName(){
		return name;
	}
	
	protected void setName(String name){
		this.name = name;
	}
	
	public String getId(){
		return ID;
	}
	
	protected void setId(String ID){
		this.ID = ID;
	}
	
	public ArrayList<WishItem> getList(){
		return wList;
	}
	
	public void setList(ArrayList<WishItem> in){
		wList = in;
	}
	
	public boolean addItem(WishItem w){
		if(wList.size() == MAXITEMS) return false;
		wList.add(w);
		return true;
	}
	
	public boolean removeItem(WishItem w){
		return wList.remove(w);
	}
	
	public static void sortList(char code, ArrayList<WishItem> wList){
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
	
	public boolean getIsAppUser(){
		return isAppUser;
	}
	
	protected void setIsAppUser(boolean flag){
		isAppUser = flag;
	}
	
}
