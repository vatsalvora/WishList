package com.wishlist.ui;

import java.sql.Date;
import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.wishlist.obj.FBUser;
import com.wishlist.obj.User;
import com.wishlist.obj.WishItem;

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
    private Bundle b;
    private User user;
    private ArrayList<WishItem> wishlist;
    private boolean isAppUser;
    
    public void onCreate(Bundle savedInstanceState)
    {	
    	super.onCreate(savedInstanceState);
    	Transporter.unpackFromBundle(this.getArguments(), Transporter.USER, user);
    	if(user == null){
            Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Who is the user? I don't know", Toast.LENGTH_SHORT);
            toast.show();			
    	}
    	else{
	    	wishlist = user.getList();
	    	isAppUser = user.getIsAppUser();
    	}
    	
    	
    	//test();
    	
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
        ListView listView = (ListView) inflater.inflate(R.layout.fragment_wish_display,
                                         container, false);

            final ArrayList<String> list = new ArrayList<String>();
            for (int i = 0; i < wishlist.size(); ++i) {
              list.add(wishlist.get(i).getName());
            }
            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.rowlayout, R.id.label, list);
            listView.setAdapter(adapter);
        
        return rootView = listView;
    }
	
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		//This displays the correct action bar for the fragment
		super.onCreateOptionsMenu(menu, inflater);
		if(isAppUser){
			inflater.inflate(R.menu.wish_list_view, menu);
		}
		else{
			inflater.inflate(R.menu.main, menu);
		}
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
    
	
	protected void test(){
		// Dummy user
		user = new FBUser("0", "John Doe", true);
    	ArrayList<WishItem> wishes = new ArrayList<WishItem>();
    	
    	wishes.add(new WishItem("dummyID1", "Red Ryder BB Gun", user.getUID(), user.getName(), "", "", "The Red Ryder BB Gun is a lever-action, spring piston air gun with a smooth bore barrel, adjustable iron sights, and a gravity feed magazine with a 650 BB capacity", "10", 0, new Date(3,4,2014)));
    	wishes.add(new WishItem("dummyID2", "A Feast of Ice and Fire", user.getUID(), user.getName(), "", "", "Fresh out of the series that redefined fantasy, comes the cookbook that may just redefine dinner . . . and lunch, and breakfast. ", "20", 0, new Date(3,4,2014)));
    	wishes.add(new WishItem("dummyID3", "Furby Boom", user.getUID(), user.getName(), "", "", "The Furby Boom experience combines real-world interactions and virtual play with a fun app. Furby Boom will respond to your kid, change personalities based on how its treated, dance to your kid's music, speak Furbish, and much more.", "10", 0, new Date(3,4,2014)));
    	wishes.add(new WishItem("dummyID4", "Lincoln Electric Century AC-120 Stick Welder", user.getUID(), user.getName(), "", "", "The smooth arc provides strong welds on up to 14 gauge steel. ", "20", 0, new Date(3,4,2014)));
    	wishes.add(new WishItem("dummyID5", "Akai Professional LPK25 25-Key MIDI Keyboard", user.getUID(), user.getName(), "", "", " 25-key USB MIDI keyboard controller gives you expressive performance with computer-based digital audio workstations, sequencers, and more", "10", 0, new Date(3,4,2014)));
    	wishes.add(new WishItem("dummyID6", "Aurora Plush 12'' Venus Unicorn", user.getUID(), user.getName(), "", "", "It's really soft and cuddly with a delightful yellow rose attached to its purple ribbon collar. The yellow material on its horn, ears and feet is a little bit sparkly.", "20", 0, new Date(3,4,2014)));
     	   	
    	user.setList(wishes);
    	wishlist = wishes; 
    	isAppUser = user.getIsAppUser();
	}
}
