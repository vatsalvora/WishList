/*Author: Joon Kim
 * 
 * class to hold image using a filepath, should be subclassed for better functionality
 */

package com.wishlist.obj;

import java.io.File;
import java.io.FileInputStream;

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
