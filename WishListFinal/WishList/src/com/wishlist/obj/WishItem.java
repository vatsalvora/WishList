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

import java.io.File;
import java.util.*;
import android.os.Parcel;
import android.os.Parcelable;

public class WishItem implements Comparable<WishItem>, Parcelable
{
	protected Pair<String, String> wish; //wish ID and name pair
	protected Pair<String, String> user; //user ID and name pair
	protected Pair<String, String> buyer; //buyer ID and name pair
    protected String description; //description of item
    protected Date dateAdded; //date of the item added to user
    protected String price; //price of item
    protected Picture picture; //Picture object for image of item. Implement in sprint 2
    protected TreeMap<String, String> comments; //Map ID with comment.  Implement in sprint 2
    protected int status;  //status of item. Open, registered, bought, etc
    protected boolean updateRequest = false; //update changes

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
    
    private void setWish(String ID, String name)
    {
    	if(wish == null) wish = new Pair<String, String>(ID, name);
    	else{
    		wish.first = ID;
    		wish.second = name;
    	}
    }
    
    public void setWishName(String name)
    {
    	wish.second = name;
    }
    
    private void setUser(String ID, String name)
    {
    	if(user == null) user = new Pair<String, String>(ID, name);
    	else
    	{
    		user.first = ID;
    		user.second = name;
    	}
    }
    
    public void setBuyer(String ID, String name)
    {
    	if(buyer == null) buyer = new Pair<String, String>(ID, name);
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

    public void setStatus(int status)
    {
        this.status = status;
        requestUpdate();
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description= description;
        requestUpdate();
    }

    public String getPrice()
    {
        return price;
    }

    public void setPrice(String price)
    {
        this.price = price;
        requestUpdate();
    }

    public Date getDate()
    {
        return dateAdded;
    }

    public void setDate(Date d)
    {
        dateAdded = d;
        requestUpdate();
    }

    @SuppressWarnings("deprecation")
    public void setDate(String in)
    {
        dateAdded = new Date(in);
    }

    public Picture getPicture()
    {
        return picture;
    }

    public void setPicture(File f)
    {
        picture.setPicture(f);
        requestUpdate();
    }

    public String getComment(String ID)
    {
        return comments.get(ID);
    }

    public void setComment(String ID, String comment)
    {
        comments.put(ID, comment);
        requestUpdate();
    }

    public int compareTo(WishItem arg0)
    {
        return this.wish.second.compareTo(arg0.wish.second);
    }

    public boolean getUpdate()
    {
        return updateRequest;
    }

    private void requestUpdate()
    {
        updateRequest=true;
    }

    public void finishUpdate()
    {
        updateRequest=false;
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
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeParcelable(wish, 1);
        dest.writeParcelable(user, 1);
        dest.writeParcelable(buyer, 1);
    	dest.writeString(description);
        dest.writeString(price);
        //dest.writeString(dateAdded.toString());
        //dest.writeString(picture.toString());
        if(updateRequest == false) dest.writeInt(0);
        else dest.writeInt(1);
    }

    @SuppressWarnings("unchecked")
	private WishItem(Parcel in)
    {
        if(in.readInt() == 0) updateRequest=false;
        else updateRequest=true;
        //setDate(temp);
        setPrice(in.readString());
        setDescription(in.readString());
		Pair<String, String> p = (Pair<String, String>) in.readParcelable(Pair.class.getClassLoader());
        setBuyer(p.first, p.second);
        p = (Pair<String, String>) in.readParcelable(Pair.class.getClassLoader());
        setUser(p.first, p.second);
        p = (Pair<String, String>) in.readParcelable(Pair.class.getClassLoader());
        setWish(p.first, p.second);
    }
}
