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
import java.sql.Date;
//import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

public class WishItem implements Comparable<WishItem>, Parcelable
{
	protected StringPair wish; //wish ID and name pair
	protected StringPair user; //user ID and name pair
	protected StringPair buyer; //buyer ID and name pair
    protected String description=""; //description of item
    protected Date dateAdded; //date of the item added to user
    protected String price=""; //price of item
    //protected Drawable picture; //Picture object for image of item. Implement in sprint 2
    protected int status;  //status of item. Open, registered, bought, etc
    // What are the status codes? Assuming 0 = Open, 1 = registered, 2 = bought. 
    protected TreeMap<String, String> comments; //Map ID with comment.  Implement in sprint 2
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
       setUser(uid, null);
       setBuyer(bid, null);
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
    	return wish.first;
    }
    
    public String getName()
    {
    	return wish.second;
    }
    
    public String getUID()
    {
    	return user.first;
    }
    
    public String getUserName()
    {
    	return user.second;
    }
    
    public String getBID()
    {
    	return buyer.first;
    }
    
    public String getBuyerName()
    {
    	return buyer.second;
    }
    
    private final void setWish(String ID, String name)
    {
    	if(wish == null) wish = new StringPair(ID, name);
    	else{
    		wish.first = ID;
    		wish.second = name;
    	}
    }
    
    public void setWishName(String name)
    {
    	wish.second = name;
    }
    
    private final void setUser(String ID, String name)
    {
    	if(user == null) user = new StringPair(ID, name);
    	else
    	{
    		user.first = ID;
    		user.second = name;
    	}
    }
    
    public final void setBuyer(String ID, String name)
    {
    	if(buyer == null) buyer = new StringPair(ID, name);
    	else
    	{
    		buyer.first = ID;
    		buyer.second = name;
    	}
    }
    
    public int getStatus()
    {
        return status;
    }

    public final void setStatus(int status)
    {
        this.status = status;
        update(STATUS);
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description= description;
        update(DESC);
    }

    public String getPrice()
    {
        return price;
    }

    public void setPrice(String price)
    {
        this.price = price;
        update(PRICE);
    }

    public Date getDate()
    {
        return dateAdded;
    }

    public void setDate(Date d)
    {
        dateAdded = d;
        update(DATE);
    }

    public String getComment(String ID)
    {
        return comments.get(ID);
    }

    public void setComment(String ID, String comment)
    {
        comments.put(ID, comment);
    }

    public int compareTo(WishItem arg0)
    {
        return this.wish.second.compareTo(arg0.wish.second);
    }

    public int getUpdate()
    {
        return update;
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
        dest.writeParcelable(wish, args);
        dest.writeParcelable(user, args);
        dest.writeParcelable(buyer, args);
    	dest.writeString(description);
        dest.writeString(price);
        //dest.writeString(dateAdded.toString());
        //dest.writeString(picture.toString());
        dest.writeInt(update);
    }

	private WishItem(Parcel in)
    {
        StringPair p = (StringPair) in.readParcelable(StringPair.class.getClassLoader());
        setWish(p.first, p.second);
        p = (StringPair) in.readParcelable(StringPair.class.getClassLoader());
        setUser(p.first, p.second);
        p = (StringPair) in.readParcelable(StringPair.class.getClassLoader());
        setBuyer(p.first, p.second);
        setDescription(in.readString());
        setPrice(in.readString());
        update = in.readInt();
    }
}
