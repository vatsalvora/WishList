/*Author: Joon Kim
 *
 * Pair class to link two objects together
 * 
 * EDIT: implementing Parcelable with generics is extremely tricky. Solution here is unfavorable. (Joon)
 */

package com.wishlist.obj;

import android.os.Parcel;
import android.os.Parcelable;

public class Pair<A, B> implements Parcelable
{
    public A first;
    public B second;

    public Pair()
    {
        throw new RuntimeException("Invalid constructor!");
    }

    public Pair(A a, B b)
    {
        first = a;
        second = b;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    public static final Parcelable.Creator<Pair<String, String>> CREATOR
    = new Parcelable.Creator<Pair<String, String>>()
    {
        public Pair<String, String> createFromParcel(Parcel in)
        {
            return new Pair<String, String>(in);
        }

        public Pair<String, String>[] newArray(int size)
        {
            throw new UnsupportedOperationException();
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString((String) first);
        dest.writeString((String) second);
    }

    @SuppressWarnings("unchecked")
	private Pair(Parcel in)
    {
    	second = (B) in.readString();
    	first = (A) in.readString();
    }

}
