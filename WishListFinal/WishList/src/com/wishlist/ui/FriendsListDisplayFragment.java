package com.wishlist.ui;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wishlist.obj.FBUser;
import com.wishlist.obj.User;

public class FriendsListDisplayFragment extends Fragment
{
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */

    private View rootView;
    private ArrayList<User> friends; 
    public static final String ARG_SECTION_NUMBER = "section_number";

    public FriendsListDisplayFragment()
    {
    }
    
	public void onCreate(Bundle savedInstanceState)
    {	
    	super.onCreate(savedInstanceState);
    	friends = (ArrayList<User>) Transporter.unpackObject(this.getArguments(), Transporter.FRIENDS);
    	if(friends == null){
            Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Friends list unavailable!", Toast.LENGTH_SHORT);
            toast.show();		
            friends = test(); 
    	}
    	 
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        ListView listView = (ListView) inflater.inflate(R.layout.fragment_friends_list_display,
                                         container, false);
            		
        listView.setAdapter(new friendsListAdapter(getActivity(), friends)); 
        
        
        return listView;
    }
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// Inflate the menu; this adds items to the action bar if it is present.
//		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.main, menu);
		
	} 
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_add:
	            Toast toast = Toast.makeText(getActivity().getApplicationContext(), "New Wish", Toast.LENGTH_SHORT);
	            toast.show();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	} 
	public ArrayList<User> test(){ //makes a test friends list
		ArrayList<User> friends = new ArrayList<User>(); 
		FBUser Oberyn = new FBUser("0", "Oberyn Martell", false); 
		FBUser Tyene = new FBUser("1", "Tyene Sand", false);
		FBUser Oleanna = new FBUser("2", "Oleanna Tyrell", false);
		FBUser Margaery = new FBUser("3", "Margaery Tyrell", false);
		
		friends.add(Oberyn); friends.add(Tyene); friends.add(Oleanna); friends.add(Margaery);
		
		return friends;
		
	}
	
}


class friendsListAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<User> displayItems; //The users to be displayed in the friends list

    public friendsListAdapter(Context c, ArrayList<User> displayables) {
        mContext = c;
        if(displayables == null){
        	displayItems = new ArrayList<User>();
        }
        else{
        	displayItems = displayables; 
        }
        
    }

    public int getCount() {
    	   return displayItems.size();
    }

    public Object getItem(int position) {
       return displayItems.get(position);
    }

    public long getItemId(int position) {
    	//see what this is used for; maybe return the string displayItems.get(position).getUID() 
        return 0; 
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
    	View view; 
    	User current = displayItems.get(position);
        if (convertView == null) {  // if it's not recycled, initialize some attributes
        	LayoutInflater inflater = (LayoutInflater) mContext.getSystemService( Context.LAYOUT_INFLATER_SERVICE ); 
            view = inflater.inflate(R.layout.friend_list_row_layout, null);
            
        } else {
            view = convertView;
        }

        Bitmap profilePicture = current.getProfilePicture(); 
        if(profilePicture != null){
            ImageView imageView = (ImageView) view.findViewById(R.id.friend_profile_picture);
        	imageView.setImageBitmap(profilePicture);
        }        
        
        TextView textView = (TextView)view.findViewById(R.id.friend_name);
        textView.setText(current.getName());
        
        return view; 
    }

 
} 