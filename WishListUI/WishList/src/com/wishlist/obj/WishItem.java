package com.wishlist.obj;

import java.io.File;
import java.util.Date;
import java.util.TreeMap;

import android.widget.ImageView;

public class WishItem implements Comparable<WishItem>{
	protected String name = null; //name of item
	protected String description = null; //description of item
	protected Double price = 0.0; //price of item
	protected Date dateAdded = null; //date of the item added to user
	protected TreeMap<String, String> comments; //Map FBID with comment 
	protected byte[] picture; //byte array for picture of item
	protected boolean updateRequest = false;
	
	public WishItem(){
		throw new RuntimeException("Name not specified!");
	}
	
	public WishItem(String name){
		this.name= new String(name);
	}
	
	public WishItem(String name, String description){
		this.name= new String(name);
		this.description= new String(description);
	}
	
	public WishItem(String name, String description, Double price){
		this.name=new String(name);
		this.description=new String(description);
		this.price= Double.valueOf(price);
	}
	
	public WishItem(String name, String description, String price, File pic){
		
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
		this.description= new String(description);
	}
	
	public double getPrice(){
		return price;
	}
	
	public void setPrice(Double price){
		this.price = Double.valueOf(price);
		update();
	}
	
	public byte[] getPicture(){
		return picture;
	}
	
	public ImageView getPictureAndroid(){
		return null;
	}
	
	public void setPicture(byte[] picture){
		this.picture = new byte[picture.length];
		for(int i=0; i<picture.length; ++i) this.picture[i] = picture[i];
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
	
	private void update(){
		updateRequest=true;
	}
}
