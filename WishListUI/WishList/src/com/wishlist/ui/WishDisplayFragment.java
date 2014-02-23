package com.wishlist.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class WishDisplayFragment extends Fragment
{/*
Used to display wish lists (of the active user and of their friends). 
Different actions are allowed depending on the user.

OWN WISHLIST: 
- can add/delete items
- UI elements: contextual action bar, action bar + icon

FRIEND'S WISHLIST: 
- can claim items 
- UI elements: checkbox

BOTH WISHLISTS: 
- can navigate to the item detail screen by touching a wish item, scroll through the items, etc

So the UI elements for certain actions are hidden based on user. 
*/
	
    private View rootView;

    public WishDisplayFragment()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        View listView = inflater.inflate(R.layout.fragment_wish_display,
                                         container, false);

        //put add wish in the action bar; delete wishes in the contextual action bar
        //display wishes in a listview
        
        
        return listView;
    }
    
    public void addWishItem(){ //called when the Add action is activated in the action bar
    	
    	
    }
    
}
class WishAdapter extends BaseAdapter {
    private Context mContext;

    public WishAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return 0; 
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {

    	return new View(mContext);
    }

    
}