package com.wishlist.ui;

import java.util.ArrayList;
//import java.util.concurrent.ExecutionException;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wishlist.obj.User;

public class FriendsListDisplayFragment extends Fragment
{
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */

    private ArrayList<User> friends;
    private WishListMain parent;
    public static final String ARG_SECTION_NUMBER = "section_number";
    private friendsListAdapter adapter; 

    public FriendsListDisplayFragment()
    {
    }
    
	public void onCreate(Bundle savedInstanceState)
    {	
    	super.onCreate(savedInstanceState);
    	parent = (WishListMain) getActivity();
    	friends = parent.getFriendList();

    	if(friends == null){
            Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Friends list unavailable!", Toast.LENGTH_SHORT);
            toast.show();		
            //friends = test(); 
    	}
    	 
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        ListView listView = (ListView) inflater.inflate(R.layout.fragment_friends_list_display,
                                         container, false);
        
        adapter = new friendsListAdapter(getActivity(), friends); 
        listView.setAdapter(adapter); 
        listView.setOnItemClickListener(new OnItemClickListener() {
        	  @Override
        	  public void onItemClick(AdapterView<?> parent, View view,
        	    int position, long id) {
        	/*    Toast.makeText(getActivity(),
        	      "Click ListItem Number " + position, Toast.LENGTH_LONG)
        	      .show(); */
        	    ActionBar bar = getActivity().getActionBar();
        	    
        	    ((WishListMain) getActivity()).setCurrentUser((User) adapter.getItem(position)); //switch the wish list being displayed in the wishlist tab
        	    
        	    bar.setSelectedNavigationItem(0);

        	  }
        	});
        
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
		User Oberyn = new User("0", "Oberyn Martell", false); 
		User Tyene = new User("1", "Tyene Sand", false);
		User Olenna = new User("2", "Olenna Tyrell", false);
		User Margaery = new User("3", "Margaery Tyrell", false);
		
		friends.add(Oberyn); friends.add(Tyene); friends.add(Olenna); friends.add(Margaery);
		
		return friends;
		
	}
	
}


class friendsListAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<User> displayItems; //The users to be displayed in the friends list

    public friendsListAdapter(Context c, ArrayList<User> friends) {
        mContext = c;
        if(friends == null){
        	displayItems = new ArrayList<User>();
        }
        else{
        	displayItems = friends; 
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

        DownloadProfilePicture pic = new DownloadProfilePicture(view);
        pic.execute(current);
            
        
        TextView textView = (TextView)view.findViewById(R.id.friend_name);
        textView.setText(current.getName());
        
        return view; 
    }
 
}
class DownloadProfilePicture extends AsyncTask<User, Integer, Bitmap> {
	
	private View view;
	protected DownloadProfilePicture(View view){
		this.view = view;
	}
	@Override
	protected Bitmap doInBackground(User... params) {
		User current = params[0];
        return current.getProfilePicture(); 
	}

    protected void onProgressUpdate(Integer... progress) {
    }

    protected void onPostExecute(Bitmap result) {
        Log.d("Image",result.toString());
        ImageView imageView = (ImageView) view.findViewById(R.id.friend_profile_picture);
    	imageView.setImageBitmap(result);
    }


}
