package com.wishlist.obj;

import java.io.File;
import java.util.Date;
import java.util.TreeMap;

import android.graphics.Bitmap;

public class WishItem implements Comparable<WishItem>{
	protected String name = null; //name of item
	protected String description = null; //description of item
	protected Double price = null; //price of item
	protected Date dateAdded = null; //date of the item added to user
	protected TreeMap<String, String> comments; //Map FBID with comment 
	protected AndroidPicture picture; //byte array for picture of item
	protected boolean updateRequest = false;
	
	public WishItem(){
		throw new RuntimeException("Name not specified!");
	}
	
	public WishItem(String name){
		setName(name);
	}
	
	public WishItem(String name, String description){
		setName(name);
		setDescription(description);
	}
	
	public WishItem(String name, String description, Double price){
		setName(name);
		setDescription(description);
		setPrice(price);
	}
	
	public WishItem(String name, String description, Double price, File pic){
		
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name=name;
		update();
	}
	
	public String getDescription(){
		return description;
	}
	
	public void setDescription(String description){
		this.description= description;
	}
	
	public double getPrice(){
		return price;
	}
	
	public void setPrice(Double price){
		this.price = price;
		update();
	}
	
	public Picture getPicture(){
		return picture;
	}
	
	public Bitmap getBitmap(){
		return picture.getBitmap();
	}
	
	public void setPicture(File f){
		picture.setPicture(f);
		update();
	}
	
	public String getComment(String ID){
		return comments.get(ID);
	}
	
	public void setComment(String ID, String comment){
		comments.put(ID, comment);
		update();
	}
	
	public String toString(){
		return name;
	}

	@Override
	//native comparison is by name of item.
	public int compareTo(WishItem arg0) {
		return this.name.compareTo(arg0.name);
	}
	
	public boolean getUpdate(){
		return updateRequest;
	}
	
	private void update(){
		updateRequest=true;
	}
}
