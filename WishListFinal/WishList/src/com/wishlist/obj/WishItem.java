/* Author: Joon Kim
 *
 * class for item in the wishlist
 * The item must have a name for creation. It also has description, price, dateAdded, image, commments, and an ID of a user who "took" the item.
 * Whenever the item is modified, the updateRequest flag becomes true and the item is updated in the database.
 *
 * Edited by: Alex Bryan to make class more closely resemble organization of
 * database.
 */

package com.wishlist.obj;

import java.util.*;
import java.io.Serializable;
import java.sql.Date;
import android.graphics.drawable.Drawable;
//import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

@SuppressWarnings("serial")
public class WishItem implements Comparable<WishItem>, Parcelable, Serializable
{
	protected IDNamePair wish = new IDNamePair("", ""); //wish ID and name pair
	protected IDNamePair user = new IDNamePair("", ""); //user ID and name pair
	protected IDNamePair buyer = new IDNamePair("", ""); //buyer ID and name pair
    protected String description=""; //description of item
    protected Date dateAdded = new Date(0); //date of the item added to user
    protected String price=""; //price of item
    protected Drawable picture; //Picture object for image of item. Implement in sprint 2
    protected int status;  //status of item. Open, registered, bought, etc
    // What are the status codes? Assuming 0 = Open, 1 = registered, 2 = bought. 
    protected int update = NONE; //update changes    
    
    //update codes
    public static final int NONE = 0;
    public static final int WISH = 1;
    public static final int USER = 2;
    public static final int BUYER = 3;
    public static final int DESC = 4;
    public static final int DATE = 5;
    public static final int PRICE = 6;
    public static final int PICTURE = 7;
    public static final int STATUS = 8;
    
    public WishItem()
    {
        throw new RuntimeException("Name and ID not specified!");
    }


    /* Constructor must include name, uid, wid as they are specified
     * NOT NULL in database. Status need not be specified as the status
     * field in the DB has a default value
     */
    public WishItem(String wid, String name, String uid, String uName)
    {
       setWish(wid, name);
       setUser(uid, uName);
    }
    
    /* This constructor will not be used to create a new wish, but rather
     * a new wish object based off of data in database. This is why bid,
     * dateAdded, and status are included
     */
     
    public WishItem(String uid, String bid, String name, String descr,
                    String price, int status, String wid, Date dateAdded)
    {
       setWish(wid, name);
       setUser(uid, "");
       setBuyer(bid, "");
       setDescription(descr);
       setPrice(price);
       setStatus(status);
       setDate(dateAdded);
    }
    
    public WishItem(String wid, String name, String uid, String uname, String bid, String bname,
    				String descr, String price, int status, Date dateAdded)
    {
    	setWish(wid, name);
        setUser(uid, uname);
        setBuyer(bid, bname);
        setDescription(descr);
        setPrice(price);
        setStatus(status);
        setDate(dateAdded);
    }
    
    public String getWID()
    {
    	return wish.ID;
    }
    
    public String getName()
    {
    	return wish.name;
    }
    
    public String getUID()
    {
    	return user.ID;
    }
    
    public String getUserName()
    {
    	return user.name;
    }
    
    public String getBID()
    {
    	return buyer.ID;
    }
    
    public String getBuyerName()
    {
    	return buyer.name;
    }
    
    public String getDescription()
    {
        return description;
    }
    
    public String getPrice()
    {
        return price;
    }
    
    public Date getDate()
    {
        return dateAdded;
    }
    
    public int getStatus()
    {
        return status;
    }
    
    public Drawable getDrawable(){
    	return picture;
    }
    
    public int getUpdate()
    {
        return update;
    }
    
    private final void setWish(String ID, String name)
    {
    	if(wish == null) wish = new IDNamePair(ID, name);
    	else{
    		wish.ID = ID;
    		wish.name = name;
    	}
    	update(WISH);
    }
    
    public void setWishName(String name)
    {
    	wish.name = name;
    	update(WISH);
    }
    
    private final void setUser(String ID, String name)
    {
    	if(user == null) user = new IDNamePair(ID, name);
    	else
    	{
    		user.ID = ID;
    		user.name = name;
    	}
    	update(USER);
    }
    
    public final void setBuyer(String ID, String name)
    {
    	if(buyer == null) buyer = new IDNamePair(ID, name);
    	else
    	{
    		buyer.ID = ID;
    		buyer.name = name;
    	}
    	update(BUYER);
    }
    
    public final void setStatus(int status)
    {
        this.status = status;
        update(STATUS);
    }

    public void setDescription(String description)
    {
        this.description= description;
        update(DESC);
    }

    public void setPrice(String price)
    {
        this.price = price;
        update(PRICE);
    }

    public void setDate(Date d)
    {
        dateAdded = d;
        update(DATE);
    }
    
    public void setDate(String s){
    	dateAdded = Date.valueOf(s);
    	update(DATE);
    }

    public int compareTo(WishItem arg0)
    {
        return this.wish.name.compareTo(arg0.wish.name);
    }

    private void update(int code)
    {
        update=code;
    }
    
    public void doneUpdate(){
    	update = NONE;
    }
    
    @Override
    public int describeContents()
    {
        return 0;
    }

    public static final Parcelable.Creator<WishItem> CREATOR
    = new Parcelable.Creator<WishItem>()
    {
        public WishItem createFromParcel(Parcel in)
        {
            return new WishItem(in);
        }

        public WishItem[] newArray(int size)
        {
            return new WishItem[size];
        }
        
    };

    @Override
    public void writeToParcel(Parcel dest, int args)
    {
        dest.writeString(wish.ID);
        dest.writeString(wish.name);
        dest.writeString(user.ID);
        dest.writeString(user.name);
        dest.writeString(buyer.ID);
        dest.writeString(buyer.name);
    	dest.writeString(description);
        dest.writeString(price);
        if(dateAdded == null) dest.writeString(new Date(0).toString());
        else dest.writeString(dateAdded.toString());
        dest.writeInt(update);
    }

	private WishItem(Parcel in)
    {
		String ID, name;
		ID = in.readString();
		name = in.readString();
        setWish(ID, name);
        ID = in.readString();
		name = in.readString();
        setUser(ID, name);
        ID = in.readString();
		name = in.readString();
        setBuyer(ID, name);
        setDescription(in.readString());
        setPrice(in.readString());
        setDate(in.readString());
        update = in.readInt();
    }
}
