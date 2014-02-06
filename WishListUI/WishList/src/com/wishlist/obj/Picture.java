package com.wishlist.obj;

import java.io.File;
import java.io.FileInputStream;

//class to hold the picture
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
}
