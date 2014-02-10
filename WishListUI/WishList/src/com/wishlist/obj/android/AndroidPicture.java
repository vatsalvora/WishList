/* Author: Joon Kim
 * 
 * Subclass of Picture for Android
 * An AndroidPicture object can return a BitMap that is to be used with ImageView in an Android activity.
 */

package com.wishlist.obj.android;

import java.io.*;

import com.wishlist.obj.Picture;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class AndroidPicture extends Picture{

	public AndroidPicture(File f) {
		super(f);
	}
	
	public Bitmap getBitmap(){
		Bitmap ret=null;
		try {
			stream = new FileInputStream(picture);
			ret = BitmapFactory.decodeStream(stream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return ret;
	}
}
