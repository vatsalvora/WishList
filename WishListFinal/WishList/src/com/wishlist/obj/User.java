/* Author: Joon Kim
 *
 * Class for users of this app.
 * The user has a unique ID, a name, a flag for app user, and a list of items in the database.
 * The app user can add and remove items from his/her own list and sort the list by name, date, or price.
 */

package com.wishlist.obj;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.net.URL; 

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

@SuppressWarnings("serial")
public class User implements Parcelable, Comparable<User>, Serializable
{
    protected boolean isAppUser=false; //flag for current app user
    protected IDNamePair userInfo = new IDNamePair("", "");
    protected ArrayList<WishItem> wList = new ArrayList<WishItem>(); //wishlist for user
    protected Bitmap profilePicture; //profile picture of the user 
    public static final int MAXITEMS = 10; //maximum number of items allowed for one user to have
    
    //hashcode for sorting
    public static final char NAME = 'n';
    public static final char DATE = 'd';
    public static final char PRICE = 'p';
    
    public User(){
    	throw new RuntimeException("Invalid user creation.");
    }
    
    public User(String uid, String name, boolean isAppUser)
    {
    	setUserInfo(uid, name);
        setIsAppUser(isAppUser);
        setList(new ArrayList<WishItem>()); 
    }

    private final void setUserInfo(String ID, String name){
    	userInfo = new IDNamePair(ID, name);
    }
    
    public String getName()
    {
        return userInfo.name;
    }

    public String getUID()
    {
        return userInfo.ID;
    }

    public ArrayList<WishItem> getList()
    {
        return wList;
    }

    public void setList(ArrayList<WishItem> in)
    {
        wList = in;
    }
    
    public WishItem getItem(int index){
    	return wList.get(index);
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
    
    public void setProfilePicture(Bitmap profilePicture){
    	this.profilePicture = profilePicture;
    }
    
    public Bitmap getProfilePicture(){
    	if(profilePicture == null){
    	
	    	String urlConstruct = "https://graph.facebook.com/" + userInfo.ID + "/picture?width=90&height=90";
	    	Bitmap imageFromURL = null;
	    	try{
	    		URL url = new URL(urlConstruct);
	    		imageFromURL = BitmapFactory.decodeStream(url.openConnection().getInputStream());
	    	}
	    	catch(Exception e)
	    	{
	    		Log.e("ImageURL",urlConstruct);
	    		Log.e("Image",e.toString());
	    	}
	    	 if(imageFromURL != null){
	    		profilePicture = imageFromURL; 
	    	//	ImageView profilePic = (ImageView) view.findViewById(R.id.friend_profile_picture);
	    	//	profilePic.setImageBitmap(imageFromURL);
	    	}	
    	}
    	return profilePicture; 
    }
    
    public boolean getIsAppUser()
    {
        return isAppUser;
    }

    protected final void setIsAppUser(boolean flag)
    {
        isAppUser = flag;
    }
    
    public int compareTo(User u){
    	return this.getName().compareTo(u.getName());
    }
    
    @Override
    public int describeContents()
    {
        return 0;
    }


    public static final Parcelable.Creator<User> CREATOR
    = new Parcelable.Creator<User>()
    {
        public User createFromParcel(Parcel in)
        {
            return new User(in);
        }
        public User[] newArray(int size)
        {
            throw new UnsupportedOperationException();
        }
    };

    @Override
    public void writeToParcel(Parcel out, int flags)
    {

        if(isAppUser) out.writeInt(1);
        else out.writeInt(0);
        out.writeString(userInfo.ID);
        out.writeString(userInfo.name);
        out.writeTypedList(wList);
    }

    private User(Parcel in)
    {
        if(in.readInt() == 1) isAppUser = true;
        else isAppUser=false;
        String ID, name;
        ID = in.readString();
        name = in.readString();
        setUserInfo(ID, name);
        in.readTypedList(wList, WishItem.CREATOR);
    }  
}
