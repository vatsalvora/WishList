package com.wishlist.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class FriendsListDisplayFragment extends Fragment
{
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */

    private View rootView;

    public static final String ARG_SECTION_NUMBER = "section_number";

    public FriendsListDisplayFragment()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        View rootView = inflater.inflate(R.layout.fragment_friends_list_display,
                                         container, false);

        return rootView;
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
}