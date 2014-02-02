package com.wishlist.obj;

public class WishItem {
	protected String name = null;
	protected String description = "None";
	protected String price = "Not known";
	protected byte[] picture;
	
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
	
	public String getName(){
		return name;
	}
	
	public String getDescription(){
		return description;
	}
	
	public String getPrice(){
		return price;
	}

	public byte[] getPicture(){
		return picture;
	}
}
