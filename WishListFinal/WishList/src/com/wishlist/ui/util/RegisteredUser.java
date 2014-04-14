package com.wishlist.ui.util;

import java.util.ArrayList;

import android.os.AsyncTask;
import android.util.Log;

import com.wishlist.serv.WLServerCom;

public final class RegisteredUser extends AsyncTask<ArrayList<String>, Integer, ArrayList<String>> {
	@Override
	protected ArrayList<String> doInBackground(ArrayList<String>... params) {
		
        ArrayList<String> a = params[0];
        ArrayList<String> ret = new ArrayList<String>();
        try {
			ret = WLServerCom.getWLUsers(a);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e("Registered Users", e.toString());
		} 
        Log.e("Registered Users", ret.size()+"");
        return ret;
	}

    protected void onProgressUpdate(Integer... progress) {
    }

    protected void onPostExecute(ArrayList<String> result) {
        Log.d("Image",result.toString());
    }
}