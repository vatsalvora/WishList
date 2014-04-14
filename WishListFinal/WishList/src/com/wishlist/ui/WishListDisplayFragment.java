package com.wishlist.ui;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.wishlist.obj.User;
import com.wishlist.obj.WishItem;
import com.wishlist.ui.util.Transporter;
import com.wishlist.ui.util.WishRetrieval;

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
	
	//easy reference for users
	private User currentUser;
	private User appUser;
	
	//have one adapter for a certain list
	private ArrayAdapter<String> userListAdapter;
	
	//list view to show the items in fragment
	private ListView listView; 
	
	public void onCreate(Bundle savedInstanceState)
    {	
    	super.onCreate(savedInstanceState);
    	currentUser = ((WishListMain) getActivity()).getCurrentUser();
    	appUser = ((WishListMain) getActivity()).getAppUser();
    	
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

        resetListView(appUser);
        
        listView.setOnItemClickListener(new OnItemClickListener(){
        	public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
        		Intent i = new Intent(getActivity(), ItemView.class);
        		Bundle b = new Bundle();
        		Transporter.pack(b, Transporter.APPUSER, appUser);
        		Transporter.pack(b, Transporter.USER, currentUser.getIsAppUser());
        		Transporter.pack(b, Transporter.WISH, currentUser.getItem(position));
        		i.putExtras(b);
        		startActivity(i);
            }
        }); 

        return listView;
    } 
	
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		//This displays the correct action bar for the fragment
		super.onCreateOptionsMenu(menu, inflater);
		
		if(currentUser.getIsAppUser()) inflater.inflate(R.menu.wish_list_view, menu);
		else inflater.inflate(R.menu.nonowner_wish_list_view, menu);
		
	} 
	
    public boolean onOptionsItemSelected(MenuItem item) 
	{
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_add:
	            ((WishListMain) getActivity()).showAddDialog();
	            userListAdapter.notifyDataSetChanged(); 
               	return true;
	        case R.id.action_back: 
	        	resetListView(appUser);
	        	currentUser = appUser;
	        	getActivity().invalidateOptionsMenu();
	           	return true; 
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

    public void setCurrentUser(User u){
    	//Toast.makeText(getActivity().getApplicationContext(), u.getName(), Toast.LENGTH_SHORT).show();
    	currentUser = u; 
    	
    	// load the data for the user from the database if it hasn't been loaded already
    	fetchWishes(currentUser);
    	
    	//Make a new adapter to display the current user's wishlist
    	resetListView(currentUser);
    }
    
    protected void fetchWishes(User user){
    	if(user.getList().isEmpty() || user.getList() == null){
    		WishRetrieval wishRet = new WishRetrieval();
         	//Log.i("Current User", currentUser.getName());
            wishRet.execute(user.getUID(), user.getName());
         	ArrayList<WishItem> wishes = new ArrayList<WishItem>();
         	try{
         		wishes = wishRet.get();
         	}
         	catch(Exception e){
         		
         	}
         	user.setList(wishes);
    	}
    }
    
    protected void resetListView(User u){
    	ArrayList<String> list = new ArrayList<String>();
    	for(WishItem i : u.getList()) list.add(i.getName());
    	resetListView(list);
    	if(u.getIsAppUser()) setAppUserPermissions();
    }
    
    protected void resetListView(ArrayList<String> list){
    	userListAdapter = new ArrayAdapter<String>(getActivity(), R.layout.rowlayout, R.id.label, list);
    	listView.setAdapter(userListAdapter);
    }
    
    protected void setAppUserPermissions(){
    	listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
    	listView.setMultiChoiceModeListener(new MultiChoiceModeListener(){
        	//the listener for the contextual action bar
        	ArrayList<WishItem> selectedWishes = new ArrayList<WishItem>(); 
            
        	@Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                // Here you can do something when items are selected/de-selected,
                // such as update the title in the CAB
            	
            	//add selected wishes to selectedwishes
            	
            	selectedWishes.add(currentUser.getItem(position));           	
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                // Respond to clicks on the actions in the CAB
            	if(currentUser.getIsAppUser())
            	{
            	switch (item.getItemId()) {
                    case R.id.action_delete: // DELETING ITEMS HERE 
                    	//for each selected wish, delete it from the server and the user's list
                    	for(WishItem wish : selectedWishes){
                    		WishListMain.DBWishUpdate(WishListMain.DEL, wish);
                    		userListAdapter.remove(wish.getName()); 
                    		currentUser.removeItem(wish); 
                    	}
                    	
                    	//notify the adapter that the data has changed
        	            userListAdapter.notifyDataSetChanged(); 
                    	mode.finish(); // Action picked, so close the CAB
                        return true;
                    default:
                        return false;
                }
            	}
            	return false;
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
    
}

