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
    protected String name; //name of item
    protected int wid; //ID of the wish
    protected String description = ""; //description of item
    protected int bid; //ID of buyer
    protected String bName = "";  //Name of buyer
    protected int uid;  //User ID of user who has this wish
    protected String uName; //Name of user who has this wish
    protected Date dateAdded; //date of the item added to user
    protected Double price; //price of item
    protected int status;  //status of item. Open, registered, bought, etc
    protected Picture picture; //Picture object for image of item. Implement in sprint 2
    protected TreeMap<String, String> comments; //Map ID with comment.  Implement in sprint 2
    protected boolean updateRequest = false; //update changes

    public WishItem()
    {
        throw new RuntimeException("Name and ID not specified!");
    }


    /* Constructor must include name, uid, wid as they are specified
     * NOT NULL in database. Status need not be specified as the status
     * field in the DB hasa default value
     */
    public WishItem(int wid, String name, int uid)
    {
        setWID(wid);
        setName(name);
        setUID(uid);
    }
    public WishItem(int wid, String name, int uid, String description)
    {
        setWID(wid);
        setName(name);
        setUID(uid);
        setDescription(description);
    }
    public WishItem(int wid, String name, int uid, String description,
                    double price)
    {
        setWID(wid);
        setName(name);
        setUID(uid);
        setDescription(description);
        setPrice(price);
    }

    public WishItem(int wid, String name, int uid, double price)
    {
        setWID(wid);
        setName(name);
        setUID(uid);
        setPrice(price);
    }


    /* This constructor will not be used to create a new wish, but rather
     * a new wish object based off of data in database. This is why bid,
     * dateAdded, and status are included
     */
    public WishItem(int uid, int bid, String name, String descr,
                    double price, int status, int wid, Date dateAdded)
    {
        setUID(uid);
        setBID(bid);
        setName(name);
        setDescription(descr);
        setPrice(price);
        setStatus(status);
        setWID(wid);
        setDate(dateAdded);
    }

    public String getName()
    {
        return name;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public void setName(String name)
    {
        this.name=name;
        requestUpdate();
    }


    public int getWID()
    {
        return wid;
    }

    //note this is private
    private void setWID(int in)
    {
        wid = in;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description= description;
    }

    public double getPrice()
    {
        return price;
    }

    public void setPrice(Double price)
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
    }

    @SuppressWarnings("deprecation")
    public void setDate(String in)
    {
        dateAdded = new Date(in);
    }

    public int getUID()
    {
        return uid;
    }

    private void setUID(int uid)
    {
        this.uid = uid;
    }

    public int getBID()
    {
        return bid;
    }

    public void setBID(int bid)
    {
        this.bid = bid;
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

    //this should be enough?
    public String toString()
    {
        return getName();
    }

    @Override
    //native comparison is by name of item.
    public int compareTo(WishItem arg0)
    {
        return this.name.compareTo(arg0.name);
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
        dest.writeString(name);
        dest.writeString(description);
        dest.writeDouble(price);
        dest.writeString(dateAdded.toString());
        //dest.writeString(picture.toString());
        //dest.writeString(buyer);
        if(updateRequest == false) dest.writeInt(0);
        else dest.writeInt(1);
    }

    private WishItem(Parcel in)
    {
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
