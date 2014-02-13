/* Author: Joon Kim
 * 
 * class for item in the wishlist
 * The item must have a name for creation. It also has description, price, dateAdded, image, commments, and an ID of a user who "took" the item.
 * Whenever the item is modified, the updateRequest flag becomes true and the item is updated in the database.
 */

package com.wishlist.obj;

import java.io.File;
import java.util.*;
import android.os.Parcel;
import android.os.Parcelable;

public class WishItem implements Comparable<WishItem>, Parcelable{
	protected String name; //name of item
	protected String ID; //ID of the item
	protected String description; //description of item
	protected Pair<String, String> owner; //ID and name of the owner of the item (ID, name)
	protected Pair<String, String> buyer; //ID and name of the user that took the item (ID,name)
	protected Date dateAdded; //date of the item added to user
	protected Double price; //price of item
	protected Picture picture; //Picture object for image of item
	protected TreeMap<String, String> comments; //Map ID with comment 
	protected boolean updateRequest = false; //update changes
	
	public WishItem(){
		throw new RuntimeException("Name and ID not specified!");
	}
	
	public WishItem(String ID, String name){
		setId(ID);
		setName(name);
	}
	
	public WishItem(String ID, String name, String description){
		setId(ID);
		setName(name);
		setDescription(description);
	}
	
	public WishItem(String ID, String name, String description, Double price){
		setId(ID);
		setName(name);
		setDescription(description);
		setPrice(price);
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name=name;
		requestUpdate();
	}
	
	
	public String getId(){
		return ID;
	}
	
	//note this is private
	private void setId(String in){
		ID = in;
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
		requestUpdate();
	}
	
	public Date getDate(){
		return dateAdded;
	}
	
	public void setDate(Date d){
		dateAdded = d;
	}
	
	@SuppressWarnings("deprecation")
	public void setDate(String in){
		dateAdded = new Date(in);
	}
	
	public Pair<String, String> getOwner(){
		return owner;
	}
	
	public void setOwner(Pair<String, String> in){
		owner = in;
	}
	
	public Pair<String, String> getBuyer(){
		return buyer;
	}
	
	public void setBuyer(Pair<String, String> in){
		buyer = in;
	}
	
	public Picture getPicture(){
		return picture;
	}
	
	public void setPicture(File f){
		picture.setPicture(f);
		requestUpdate();
	}
	
	public String getComment(String ID){
		return comments.get(ID);
	}
	
	public void setComment(String ID, String comment){
		comments.put(ID, comment);
		requestUpdate();
	}
	
	//this should be enough?
	public String toString(){
		return getName();
	}

	@Override
	//native comparison is by name of item.
	public int compareTo(WishItem arg0) {
		return this.name.compareTo(arg0.name);
	}
	
	public boolean getUpdate(){
		return updateRequest;
	}
	
	private void requestUpdate(){
		updateRequest=true;
	}

	public void finishUpdate(){
		updateRequest=false;
	}
	
	@Override
	public int describeContents() {
		return 0;
	}

	public static final Parcelable.Creator<WishItem> CREATOR
	= new Parcelable.Creator<WishItem>() {
		public WishItem createFromParcel(Parcel in) {
			return new WishItem(in);
		}

		public WishItem[] newArray(int size) {
			return new WishItem[size];
		}
	};
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(name);
		dest.writeString(description);
		dest.writeDouble(price);
		dest.writeString(dateAdded.toString());
		//dest.writeString(picture.toString());
		//dest.writeString(buyer);
		if(updateRequest == false) dest.writeInt(0);
		else dest.writeInt(1);
	}
	
	private WishItem(Parcel in) {
		if(in.readInt() == 0) updateRequest=false;
		else updateRequest=true;
		String temp;
		Double temp2;
		//temp = in.readString();
		//if(temp != null) setTakenBy(temp);
		temp = in.readString();
		if(temp != null) setDate(temp);
		temp2 = in.readDouble();
		if(temp2 != null) setPrice(temp2);
		temp = in.readString();
		if(temp != null) setDescription(temp);
		temp = in.readString();
		setName(temp);
	}
}
