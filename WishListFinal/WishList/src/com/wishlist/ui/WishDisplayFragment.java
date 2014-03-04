package com.wishlist.ui;

import java.util.ArrayList;

import com.wishlist.obj.User;
import com.wishlist.obj.WishItem;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.*;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

public class WishDisplayFragment extends Fragment implements WishCreatorDialog.WishCreatorDialogListener
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
    private User u;
    private ArrayList<WishItem> w;
    private boolean isAppUser;
    
    public void onCreate(Bundle savedInstanceState)
    {
    	super.onCreate(savedInstanceState);
    	//Transporter.unpackFromBundle(this.getArguments(), Transporter.USER, u);
    	//w = u.getList();
    	//isAppUser = u.getIsAppUser();
    }
    
    public void onStart()
    {
    	super.onStart();
    }
    
    public void onResume()
    {
    	super.onResume();
    }
    
    public void onStop()
    {
    	super.onStop();
    }
    
    public void onDestroy()
    {
    	super.onDestroy();
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
    	setHasOptionsMenu(true);
        View listView = inflater.inflate(R.layout.fragment_wish_display,
                                         container, false);

        //put add wish in the action bar; delete wishes in the contextual action bar
        //display wishes in a listview
        
        rootView = listView;
        return listView;
    }
	
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		//This displays the right action bar for the fragment
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.wish_list_view, menu);
		//TODO: display a different action bar (without the ability to add items) if the user doesn't own the list
		
	} 
	
    public boolean onOptionsItemSelected(MenuItem item) 
	{
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_add:
	            Toast toast = Toast.makeText(getActivity().getApplicationContext(), "New Wish", Toast.LENGTH_SHORT);
	            toast.show();
	            return addWishItem();
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}   
    
    protected boolean addWishItem()
    { //called when the Add action is activated in the action bar
    	showWishCreatorDialog();
    	return true;
    }
    
    public void showWishCreatorDialog()
    {
    	WishCreatorDialog d = new WishCreatorDialog();
    	d.show(getFragmentManager(), "WishCreatorDialog");
    }
    
	@Override
	public void onDialogPositiveClick(DialogFragment dialog) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDialogNegativeClick(DialogFragment dialog) {
		// TODO Auto-generated method stub
		
	}
    
}

class WishAdapter extends BaseAdapter 
{
    private Context mContext;

    public WishAdapter(Context c) 
    {
        mContext = c;
    }

    public int getCount() 
    {
        return 0; 
    }

    public Object getItem(int position) 
    {
        return null;
    }

    public long getItemId(int position) 
    {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) 
    {

    	return new View(mContext);
    }
  
}