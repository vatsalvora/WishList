package com.wishlist.ui;

import java.util.ArrayList;
import java.util.Locale;
import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
//import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import com.wishlist.db.*;
import com.wishlist.obj.*;
//import android.widget.TextView;

/*
 * This class displays two fragments, one for the user's actual wish list and another for their friends list.
 *
 */

public class MainActivity extends FragmentActivity implements 
	ActionBar.TabListener
{

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
     * will keep every loaded fragment in memory. If this becomes too memory
     * intensive, it may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter; //returns the appropriate fragment for each tab. Defined below.
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private ActionBar actionBar;
    
    private DBCom db; //DB pointer
    private Bundle userData; //bundle from login
    private User appUser; //the app user
    private User currentUser; //current user to view the data
    private ArrayList<User> friends; //friends of the app user
    
    public static final int WISH = 0;
    public static final int FRIEND = 1;
    public static final int COUNT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDB(); //set up DB communication
        initData(); //load data from login
        initGraphics(); //load graphics
    }

    protected void onStart(){
    	super.onStart();
    }
    
    protected void onRestart()
    {
    	super.onRestart();
    }
    
    protected void onPause()
    {
    	super.onPause();
    }
    
    protected void onResume()
    {
    	super.onResume();
    }
    
    protected void onStop()
    {
    	super.onStop();
    }
   
    protected void onDestroy()
    {
    	super.onDestroy();
    }
    
    protected void initDB(){
    	//set up DB communication
        db = DBCom.instance();
    }
    
    @SuppressWarnings("unchecked")
	protected void initData(){
    	//retrieve data from intent
    	userData = getIntent().getExtras();
        appUser = (FBUser) Transporter.unpackObjectFromBundle(userData, Transporter.USER);
        friends = (ArrayList<User>) Transporter.unpackArrayListFromBundle(userData, Transporter.FRIENDS);
        
        //set current user as app user
        setCurrentUser(appUser);
        //load wishes from database
        //loadDBData(appUser);
    }
    
    protected void setCurrentUser(User u){
    	currentUser = u;
    }
    
    protected void loadDBData(User u){
    	u.setList(db.listWishes(u.getUID()));
    }
    
    protected void initGraphics()
    {
    	// Set up the action bar. (It contains the tabs)
    	actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setTitle(R.string.app_name);

        // Create the adapter that will return a fragment for each of the two tabs of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        /* When swiping between different sections, select the corresponding 
         * tab. We can also use ActionBar.Tab#select() to do this if we have 
         * a reference to the Tab.
         */
        
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener()
        {
            @Override
            public void onPageSelected(int position)
            {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++)
        {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(actionBar.newTab()
                             .setText(mSectionsPagerAdapter.getPageTitle(i))
                             .setTabListener(this));
        }	
    }
    
    /*@Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }*/

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction)
    {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction)
    {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction)
    {
    	onTabSelected(tab, fragmentTransaction);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter
    {

        public SectionsPagerAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public Fragment getItem(int position)
        {
            // getItem is called to instantiate the fragment for the given page.
            // Return the appropriate fragment
            Bundle args = new Bundle();
            Fragment fragment;
            switch(position)
            {
	            case WISH:
	            	Transporter.packIntoBundle(args, Transporter.USER, currentUser);
	            	fragment = new WishDisplayFragment();
	                fragment.setArguments(args);
	                return fragment;
	            case FRIEND:
	            	Transporter.packIntoBundle(args, Transporter.FRIENDS, friends);
	                fragment = new FriendsListDisplayFragment();
	                fragment.setArguments(args);
	                return fragment;
	            default:
	                throw new RuntimeException("Invalid position");
            }
        }

        @Override
        public int getCount()
        {
            // Show COUNT. COUNT may be modified in the future (even though it won't) so let's use a pointer.
            return COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position)
        {
            Locale l = Locale.getDefault();
            switch (position)
            {
	            case WISH:
	                return getString(R.string.title_section1).toUpperCase(l);
	            case FRIEND:
	                return getString(R.string.title_section2).toUpperCase(l);
	            default:
	                throw new RuntimeException("Error Occured.");
            }
        }
    }
    
}
