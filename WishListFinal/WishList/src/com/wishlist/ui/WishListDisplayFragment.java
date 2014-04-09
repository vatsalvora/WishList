package com.wishlist.ui;

import java.sql.Date;
import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnLongClickListener;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.wishlist.obj.User;
import com.wishlist.obj.WishItem;

public class WishListDisplayFragment extends Fragment 
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
	
	private User user;
	private ArrayAdapter<String> appUserAdapter; //adapter for the app owner's wishlist
	private ArrayAdapter<String> currentUserAdapter; //adapter for the currently-selected friend's wishlist (user if user != appuser)
	
	private ListView listView; 
	
	public void onCreate(Bundle savedInstanceState)
    {	
    	super.onCreate(savedInstanceState);
    	WishListMain parent = (WishListMain) getActivity();
    	user = parent.getCurrentUser();
    	if(user == null){
            Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Who is the user? I don't know", Toast.LENGTH_SHORT);
            toast.show();			
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
        listView = (ListView) inflater.inflate(R.layout.fragment_wish_display, container, false);

        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < user.getList().size(); ++i) list.add(user.getList().get(i).getName());
        
        appUserAdapter = new ArrayAdapter<String>(getActivity(), R.layout.rowlayout, R.id.label, list);
    //    this.adapter = adapter; 
        listView.setAdapter(appUserAdapter);
        
        if(user.getIsAppUser()){
	        //Enabling the contextual action mode for deletion IF the current user owns the wishlist
	        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
	
	        listView.setMultiChoiceModeListener(new MultiChoiceModeListener(){
	        	//the listener for the contextual action bar
	        	
 	
	            @Override
	            public void onItemCheckedStateChanged(ActionMode mode, int position,
	                                                  long id, boolean checked) {
	                // Here you can do something when items are selected/de-selected,
	                // such as update the title in the CAB
	            }
	
	            @Override
	            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
	                // Respond to clicks on the actions in the CAB
	            	switch (item.getItemId()) {
	                    case R.id.action_delete:
	                    	//((WishListMain) getActivity()).showDeleteDialog();
	                        ((WishListMain) getActivity()).getCurrentUser().removeItem(0);
	                    	mode.finish(); // Action picked, so close the CAB
	                        return true;
	                    default:
	                        return false;
	                } 
	            }
	            
	            @Override
	            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
	                // Inflate the menu for the CAB
	                MenuInflater inflater = mode.getMenuInflater();
	                inflater.inflate(R.menu.contextual_menu_wishdisplay, menu);
	                return true;
	            }
	
	            @Override
	            public void onDestroyActionMode(ActionMode mode) {
	                // Here you can make any necessary updates to the activity when
	                // the CAB is removed. By default, selected items are deselected/unchecked.
	            }
	
	            @Override
	            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
	                // Here you can perform updates to the CAB due to
	                // an invalidate() request
	                return false;
	            }
	        });
        }
        
       listView.setOnItemClickListener(new OnItemClickListener(){
        	public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
        		Intent i = new Intent(getActivity(), ItemView.class);
        		Bundle b = new Bundle();
        		Transporter.pack(b, Transporter.USER, user.getIsAppUser() ? 1:0);
        		Transporter.pack(b, Transporter.WISH, user.getItem(position));
        		i.putExtras(b);
        		startActivity(i);
            }
        }); 
        
       listView.setOnItemLongClickListener(new OnItemLongClickListener(){
    	    @Override
			public boolean onItemLongClick(AdapterView<?> adapter, View view, int position, long id) {
    	    	((WishListMain) getActivity()).showDeleteDialog(position);
				return true;
			}
		});
       	
        return listView;
    }
	
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		//This displays the correct action bar for the fragment
		super.onCreateOptionsMenu(menu, inflater);
		
		if(user.getIsAppUser()) inflater.inflate(R.menu.wish_list_view, menu);
		else inflater.inflate(R.menu.nonowner_wish_list_view, menu);
		
		//TODO: display a different action bar (without the ability to add items) if the user doesn't own the list
		
	} 
	
    public boolean onOptionsItemSelected(MenuItem item) 
	{
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_add:
	        	Toast.makeText(getActivity().getApplicationContext(), "New Wish", Toast.LENGTH_SHORT).show();
	            ((WishListMain) getActivity()).showAddDialog();
	        	return true;
	        case R.id.action_back: 
	        	user = ((WishListMain)getActivity()).getAppUser(); 
	        	listView.setAdapter(appUserAdapter);
	        	getActivity().invalidateOptionsMenu();
	        	return true; 
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

    public void setCurrentUser(User u){
    	//Toast.makeText(getActivity().getApplicationContext(), u.getName(), Toast.LENGTH_SHORT).show();
    	this.user = u; 
    	// load the data for the user from the database if it hasn't been loaded already
    	if(user.getList().size() == 0 || user.getList() == null){
 
    		WishRetrieval wishRet = new WishRetrieval();
         	Log.i("Current User", user.getName());
            wishRet.execute(user.getUID(), user.getName());
         	ArrayList<WishItem> wishes = new ArrayList<WishItem>();
         	
         	try{
         		wishes = wishRet.get();
         	}
         	catch(Exception e){
         		
         	}
         	
         	user.setList(wishes);
    	}
    	
    	//Make a new adapter to display the current user's wishlist
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < user.getList().size(); ++i) list.add(user.getList().get(i).getName());
        
        currentUserAdapter = new ArrayAdapter<String>(getActivity(), R.layout.rowlayout, R.id.label, list);

        listView.setAdapter(currentUserAdapter);
    }


}

