/* Author: Joon Kim
 *
 * subclass of User in our system who uses Facebook
 *
 * Edited by: Alex Bryan
 * EDIT: implements Parcelable here, instead of User superclass
 */

package com.wishlist.obj;

import android.os.Parcel;
import android.os.Parcelable;



public class FBUser extends User
{

    public FBUser(String uid, String name, boolean isAppUser)
    {
        super(uid, name, isAppUser);
    }
    
    @Override
    public int describeContents()
    {
        return 0;
    }


    public static final Parcelable.Creator<FBUser> CREATOR
    = new Parcelable.Creator<FBUser>()
    {
        public FBUser createFromParcel(Parcel in)
        {
            return new FBUser(in);
        }
        public FBUser[] newArray(int size)
        {
            throw new UnsupportedOperationException();
        }
    };

    @Override
    public void writeToParcel(Parcel out, int flags)
    {

        if(isAppUser) out.writeInt(1);
        else out.writeInt(0);
        out.writeString(uid);
        out.writeString(name);
        out.writeTypedList(wList);
    }

    private FBUser(Parcel in)
    {
        super();
        if(in.readInt() == 1) isAppUser = true;
        else isAppUser=false;
        setUID(in.readString());
        setName(in.readString());
        in.readTypedList(wList, WishItem.CREATOR);
    }  
}
