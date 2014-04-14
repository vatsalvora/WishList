package com.wishlist.ui.util;

import java.util.ArrayList;

import android.os.AsyncTask;
import android.util.Log;

import com.wishlist.obj.WishItem;
import com.wishlist.serv.WLServerCom;

public final class WishRetrieval extends AsyncTask<String, Integer, ArrayList<WishItem>> {
	@Override
	protected ArrayList<WishItem> doInBackground(String... params) {
		
        String uid = params[0];
        String uname = params[1];
        ArrayList<WishItem> wish = new ArrayList<WishItem>();
		try {
			wish = WLServerCom.listWishes(uid, uname);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e("Wish Ret", e.toString());
		}
        return wish;
	}

    protected void onProgressUpdate(Integer... progress) {
    }

    protected void onPostExecute(ArrayList<WishItem> result) {
        Log.d("Wish",result.toString());
    }
}