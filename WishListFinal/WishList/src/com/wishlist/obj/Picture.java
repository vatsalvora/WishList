/*Author: Joon Kim
 * 
 * class to hold image using a filepath and returns an Android BitMap for ImageView.
 */

package com.wishlist.obj;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Picture {
	protected File picture=null;
	protected static FileInputStream stream;
	
	public Picture(File f){
		setPicture(f);
	}
	
	public File getPicture(){
		return picture;
	}
	
	public void setPicture(File f){
		picture = f;
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
