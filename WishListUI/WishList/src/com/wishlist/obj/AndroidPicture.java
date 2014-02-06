package com.wishlist.obj;

import java.io.*;

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
