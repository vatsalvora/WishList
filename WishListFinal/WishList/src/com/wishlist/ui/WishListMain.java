package com.wishlist.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import android.util.Log;
import android.widget.Toast;
import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.wishlist.obj.*;
import com.wishlist.obj.util.IDComparison;
import com.wishlist.serv.*;
import com.wishlist.ui.util.RegisteredUser;
import com.wishlist.ui.util.Transporter;
import com.wishlist.ui.util.WishRetrieval;


/*
 * This class displays two fragments, one for the user's actual wish list and another for their friends list.
 *
 */

public class WishListMain extends FragmentActivity 
	implements ActionBar.TabListener, WishAddDialogFragment.WishAddDialogListener
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
    
    private Bundle userData; //bundle from login
    private User appUser; //the app user
    private User currentUser; //current user to view the data
    private ArrayList<User> friends; //friends of the app user
    
    public static final int ADD = 0;
    public static final int DEL = 1;
    public static final int EDIT = 2;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //startFBSession();
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
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        //start the main activity
    	super.onActivityResult(requestCode, resultCode, data);

        Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);

    }
    
    protected static void initDB(){
    	//set up DB communication
    	new Thread(){ 
    		public void run(){
	    		try{
		    		WLServerCom.init();
		    		//Log.e("User", currentAppUser.toString());
		    		//WLServerCom.addUser(currentAppUser);
		    	}
		    	catch (Exception e){
		    		Log.e("Backend",e.toString());
		    		Log.e("Backend", "Error, couldn't connect to server");
		    	}
	    	}
    	}.start();
    	
    }
    
    public static void DBWishUpdate(int code, WishItem wish){
    	final int c = code;
    	final WishItem w = wish;
    	new Thread(){
    		public void run(){
		    	try
		    	{
		    		switch(c){
		    			case ADD:
		    				WLServerCom.addWish(w);
				    		break;
		    			case DEL:
		    				WLServerCom.rmWish(w);
		    				break;
		    			case EDIT:
		    				WLServerCom.updateWish(w);
		    				break;
		    			default:
		    				break;
		    		}
		    	}
		    	catch (Exception e)
		    	{
		    		Log.e("Backend",e.toString());
		    		Log.e("Backend", "Error, couldn't connect to server");
		    	}
    		}
    	}.start();
    }
    
    protected void initPolixTest(){
    	//set up DB communication
    	new Thread(){ public void run(){
    	try
    	{
    		ArrayList<WishItem> wishes = WLServerCom.listWishes("13","Alex The Great");
    		Log.w("Backend","got wishes");
    		Log.w("Backend", (wishes.get(1)).getWID());
    		Log.w("Backend", (wishes.get(1)).getDate().toString());
    		
    		
    		
    	}
    	catch (Exception e)
    	{
    		Log.e("Backend",e.toString());
    		Log.e("Backend", "Error Occured in Test Code");
    	}
    	}
    	}.start();
    }
    
    @SuppressWarnings("unchecked")
	protected void initData(){
    	//retrieve data from intent
    	userData = getIntent().getExtras();
        appUser = (User) Transporter.unpackObject(userData, Transporter.USER);
        friends = (ArrayList<User>) Transporter.unpackArrayList(userData, Transporter.FRIENDS);
        
        //set current user as app user
        setCurrentUser(appUser);
        //load wishes from database
        //loadDBData(appUser);
    }
    
    protected void setCurrentUser(User u){
    	currentUser = u;
    	//Toast.makeText(this, currentUser.getName(), Toast.LENGTH_SHORT).show();
    	//change the current user in wishDisplayFragment and display the new wishlist appropriately
    	if(mSectionsPagerAdapter != null){
	    	WishListDisplayFragment wishFragment = (WishListDisplayFragment) mSectionsPagerAdapter.getItem(SectionsPagerAdapter.WISH); 
	    	wishFragment.setCurrentUser(currentUser);
    	}
    	
    }
    
    public User getCurrentUser(){
    	return currentUser;
    }
    
    public User getAppUser(){
    	return appUser; 
    }
    
    public ArrayList<User> getFriendList(){
    	return friends;
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
    
    public void showAddDialog(){
    	FragmentManager fragmentManager = getSupportFragmentManager();
    	DialogFragment d = new WishAddDialogFragment();
    	d.show(fragmentManager, "WishAddDialog");
    }
    
	@Override
	public void onDialogPositiveClick(WishAddDialogFragment dialog) {
		mViewPager.setAdapter(mSectionsPagerAdapter);
		Toast.makeText(this.getApplicationContext(), "Item successfully added", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onDialogNegativeClick(WishAddDialogFragment dialog) {}
    
    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    final class SectionsPagerAdapter extends FragmentPagerAdapter
    {
    	private Fragment arr[];
    	
    	public static final int WISH = 0;
        public static final int FRIEND = 1;
        public static final int COUNT = 2;
    	
        public SectionsPagerAdapter(FragmentManager fm)
        {
            super(fm);
            arr = new Fragment[COUNT];
            update();
        }
        
        @Override
        public Fragment getItem(int position)
        {
            // getItem is called to instantiate the fragment for the given page.
            // Return the appropriate fragment
        	switch(position)
            {
	            case WISH:
	            	return arr[WISH];
	            case FRIEND:
	                return arr[FRIEND];
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
        
        public void update(){
        	arr[WISH] = new WishListDisplayFragment();
            arr[FRIEND] = new FriendsListDisplayFragment();
        }
    }
    
    protected void startFBSession()
    {
    	 // start Facebook Login
        Session.openActiveSession(this, true, new Session.StatusCallback()
        {

            // callback when session changes state
            @Override
            public void call(Session session, SessionState state, Exception exception)
            {
                if (session.isOpened())
                {
                	FBUserRequest(session);
                	FBFriendListRequest(session);
                }
                else if(session.isClosed()){
                	Log.i("Facebook", session.getState().toString());
                }
            }
        });
    }
    
	private void FBUserRequest(Session session){
		Request.newMeRequest(session, new Request.GraphUserCallback(){
			public void onCompleted(GraphUser graphuser, Response response) {
				if (graphuser != null) {
					//Log.e("Order","2");
					//Log.i("Facebook",user.getName());
					//Log.i("Facebook", user.getId());
					appUser = new User(graphuser.getId(), graphuser.getName(), true);
					initDB();
				}
			}
		}).executeAsync();
	}
	
	private void FBFriendListRequest(Session session){
		Request.newMyFriendsRequest(session, new Request.GraphUserListCallback(){	
			public void onCompleted(List<GraphUser> users, Response response)
			{
				friends = new ArrayList<User>();
				for(GraphUser i : users){ 
					//Log.i("Facebook", i.getName());
					//Log.i("Facebook", i.getId());
					friends.add(new User(i.getId(),i.getName(),false));
				}
				FBFriendFilter();
            	DBListFetch(appUser);
            	setCurrentUser(appUser);
            	initGraphics();
			}
		}).executeAsync();
	}
	
	@SuppressWarnings("unchecked")
	private void FBFriendFilter(){
		Collections.sort(friends, new IDComparison());
        
		ArrayList<String> ids = new ArrayList<String>();
		for(User i:friends) ids.add(i.getUID());
		
        RegisteredUser dbList = new RegisteredUser();
        try {
        	dbList.execute(ids);
			ids = dbList.get();
			Collections.sort(ids);
			ArrayList<User> regList = new ArrayList<User>();
			int u=0;
			int f=0;
			while(f<friends.size() && u<ids.size()){
				if((friends.get(f).getUID().equals(ids.get(u))))
				{
					regList.add(friends.get(f));
					//Log.e("New List", friends.get(f).getName());
					u++;
				}
				f++;
			}
			//Log.e("Check", regList.size() + "");
			friends = regList;
		} 
        catch (Exception e) {
			e.printStackTrace();
		}
        Collections.sort(friends);
	}
	
	public static void DBListFetch(User u){
		WishRetrieval wishRet = new WishRetrieval();
		wishRet.execute(u.getUID(), u.getName());
		ArrayList<WishItem> wishes = new ArrayList<WishItem>();
		try{
			wishes = wishRet.get();
		}
		catch(Exception e){
			Log.i("Database", "Failed to receive data");
		}
		u.setList(wishes);
	}
}
