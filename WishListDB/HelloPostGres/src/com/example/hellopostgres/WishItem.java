/* Author: Joon Kim
 *
 * class for item in the wishlist
 * The item must have a name for creation. It also has description, price, dateAdded, image, and comments.
 * Whenever the item is modified, the updateRequest flag becomes true and the item is updated in the database.
 *
 * Edited by: Alex Bryan
 */

/*
 * TODO:
 *
 * Sync object with database
 */
package com.example.hellopostgres;

//import java.io.File;
import java.util.Date;
import java.util.TreeMap; // Needed for comments. Comments in Sprint 2

public class WishItem implements Comparable<WishItem>
{
    protected String name = null; //name of item
    protected String description = null; //description of item
    protected Double price = null; //price of item
    protected Date dateAdded = null; //date of the item added to user
    protected TreeMap<String, String> comments; //Map FBID with comment. Sprint 2
    protected boolean updateRequest = false;
    protected int status = 0; //Current status of item (registered, bought)
    protected int wid; //wish id
    protected int bid; //user id of buyer of item
    protected int uid; //user id of person with wish

    public WishItem()
    {
        throw new RuntimeException("Name not specified!");
    }

    /* Will we ever use all of these constructors? */
    public WishItem(String name)
    {
        setName(name);
    }

    public WishItem(String name, String description)
    {
        setName(name);
        setDescription(description);
    }

    public WishItem(String name, String description, Double price)
    {
        setName(name);
        setDescription(description);
        setPrice(price);
    }

    public WishItem(int uid, int bid, String name, String description,
                    double price, int status, int wid)
    {
        setUID(uid);
        setBID(bid);
        setName(name);
        setDescription(description);
        setPrice(price);
        setStatus(status);
        setWID(wid);
    }

    public int getUID()
    {
        return uid;
    }

    private void setUID(int uid)
    {
        //private because UID should never change and thus should never be
        //called publicly. It should only be called by constructors

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

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public int getWID()
    {
        return wid;
    }
    public void setWID(int wid)
    {
        this.wid = wid;
    }


    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name=name;
        update();
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
        update();
    }


    public String getComment(String ID)
    {
        return comments.get(ID);
    }

    public void setComment(String ID, String comment)
    {
        comments.put(ID, comment);
        update();
    }

    public String toString()
    {
        /* Is name all we want to return? */
        return name;
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

    private void update()
    {
        updateRequest=true;
    }
}
