/*Author: Joon Kim
 *
 * Pair class to link two objects together
 * 
 * EDIT: implementing Parcelable with generics is extremely tricky. Solution here is unfavorable. (Joon)
 * EDIT2: Fuck it. Went into StringPair
 */

package com.wishlist.obj;

import android.os.Parcel;
import android.os.Parcelable;

public class StringPair implements Parcelable
{
    public String first;
    public String second;

    public StringPair()
    {
        throw new RuntimeException("Invalid constructor!");
    }

    public StringPair(String a, String b)
    {
        first = a;
        second = b;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    public static final Parcelable.Creator<StringPair> CREATOR
    = new Parcelable.Creator<StringPair>()
    {
        public StringPair createFromParcel(Parcel in)
        {
            return new StringPair(in);
        }

        public StringPair[] newArray(int size)
        {
            throw new UnsupportedOperationException();
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(first);
        dest.writeString(second);
    }

	private StringPair(Parcel in)
    {
		first = in.readString();
		second = in.readString();
    }

}
