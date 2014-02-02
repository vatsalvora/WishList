package com.wishlist.obj;

import java.io.File;
import java.util.TreeMap;

import android.widget.ImageView;

public class WishItem implements Comparable<WishItem>{
	protected String name = null; //name of item
	protected String description = null; //description of item
	protected String price = null; //price of item
	protected TreeMap<String, String> comments; //Map FBID with comment 
	protected byte[] picture; //byte array for picture of item
	
	public WishItem(){
		throw new RuntimeException("Name not specified!");
	}
	
	public WishItem(String name){
		this.name=name;
	}
	
	public WishItem(String name, String description){
		this.name=name;
		this.description=description;
	}
	
	public WishItem(String name, String description, String price){
		this.name=name;
		this.description=description;
		this.price=price;
	}
	
	public WishItem(String name, String description, String price, File pic){
		
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name=name;
	}
	
	public String getDescription(){
		return description;
	}
	
	public void setDescription(String description){
		this.description=description;
	}
	
	public String getPrice(){
		return price;
	}
	
	public void setPrice(String price){
		this.price = price;
	}
	
	public byte[] getPicture(){
		return picture;
	}
	
	public ImageView getPictureAndroid(){
		return null;
	}
	
	public void setPicture(byte[] picture){
		this.picture = picture;
	}
	
	public String getComment(String ID){
		return comments.get(ID);
	}
	
	public void setComment(String ID, String comment){
		comments.put(ID, comment);
	}
	
	public String toString(){
		return name;
	}

	@Override
	public int compareTo(WishItem arg0) {
		return this.name.compareTo(arg0.name);
	}
}
