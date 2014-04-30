package com.wishlist.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ItemViewFragment extends Fragment{
	
	private WishListMain parent;
	private int index;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        parent = (WishListMain) getActivity();
    }
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View v = inflater.inflate(R.layout.activity_item_view, container, false);
		return v;
	}
}
