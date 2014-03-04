/* Author: Joon Kim
 *
 * Class for users of this app.
 * The user has a unique ID, a name, a flag for app user, and a list of items in the database.
 * The app user can add and remove items from his/her own list and sort the list by name, date, or price.
 */

package com.wishlist.obj;

import java.util.ArrayList;
import java.util.Collections;

import android.os.Parcelable;

public abstract class User implements Parcelable
{
    protected boolean isAppUser; //flag for current app user
    protected String uid; //ID of user
    protected String name; //name of user
    protected ArrayList<WishItem> wList; //wishlist for user
    
    public static final int MAXITEMS = 10; //maximum number of items allowed for one user to have
    
    //hashcode for sorting
    public static final char NAME = 'n';
    public static final char DATE = 'd';
    public static final char PRICE = 'p';
    
 
    public User(String uid, String name, boolean isAppUser)
    {
        setName(name);
        setUID(uid);
        setIsAppUser(isAppUser);
    }

    public String getName()
    {
        return name;
    }

    protected final void setName(String name)
    {
        this.name = name;
    }

    public String getUID()
    {
        return uid;
    }

    protected final void setUID(String uid)
    {
        this.uid = uid;
    }

    public ArrayList<WishItem> getList()
    {
        return wList;
    }

    public void setList(ArrayList<WishItem> in)
    {
        wList = in;
    }

    public boolean addItem(WishItem w)
    {
        if(wList.size() == MAXITEMS) return false;
        wList.add(w);
        return true;
    }

    public boolean removeItem(WishItem w)
    {
        return wList.remove(w);
    }
    
    public boolean removeItem(int position){
    	if(position < 0 || position >= wList.size()) return false;
    	wList.remove(position);
    	return true;
    }

    public static void sortList(char code, ArrayList<WishItem> wList)
    {
        switch(code)
        {
        case NAME:
            Collections.sort(wList);
            break;
        case DATE:
            Collections.sort(wList, new DateComparison());
            break;
        case PRICE:
            Collections.sort(wList, new PriceComparison());
            break;
        default:
            throw new RuntimeException("code not found.");
        }
    }

    public boolean getIsAppUser()
    {
        return isAppUser;
    }

    protected final void setIsAppUser(boolean flag)
    {
        isAppUser = flag;
    }    
}
