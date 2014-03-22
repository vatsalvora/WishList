package com.wishlist.ui;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.util.*;

import android.os.Bundle;
import android.app.Activity;
import com.facebook.*;
import com.facebook.model.*;
import com.wishlist.obj.FBUser;
import com.wishlist.obj.WishItem;

import android.util.Base64;
import android.util.Log;
import android.widget.TextView;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.pm.PackageManager.NameNotFoundException;

public final class Login extends Activity
{

    private FBUser currentAppUser= null; //user object for the Facebook user actually using the app.
    private ArrayList<FBUser> friends = null; //ID of friends
    private Bundle FBData = null;
    private Session s = null; //current FB Session object

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //startFBSession();
        test();
    }

    protected void onResume()
    {
    	super.onResume();
    }
    
    protected void onStart()
    {
    	super.onStart();
    }
    
    protected void onPause()
    {
    	super.onPause();
    }
    
    protected void onRestart()
    {
    	super.onRestart();
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
        pack();
        start();
    }
    
    protected void pack()
    {
    	FBData = new Bundle();
    	Transporter.pack(FBData, Transporter.USER, currentAppUser);
    	Transporter.pack(FBData, Transporter.FRIENDS, friends);
    }
    
    protected void start()
    {
    	 Intent intent = new Intent(this, MainActivity.class);
         intent.putExtras(FBData);
         startActivity(intent);
    }
    
    protected void startFBSession()
    {
    	 // start Facebook Login
        s = Session.openActiveSession(this, true, new Session.StatusCallback()
        {

            // callback when session changes state
            @Override
            public void call(Session session, SessionState state, Exception exception)
            {
                if (session.isOpened())
                {
                	Request.newMeRequest(session, new Request.GraphUserCallback() {

                        // callback after Graph API response with user object
                        @Override
                        public void onCompleted(GraphUser user, Response response) {
                          if (user != null) {
                            Log.i("Facebook",user.getName());
                            Log.i("Facebook", user.getId());
                            currentAppUser = new FBUser(user.getName(),user.getId(), true);
                          }
                        }
                      }).executeAsync();
                	Request.newMyFriendsRequest(session, new Request.GraphUserListCallback()
                    {

                        public void onCompleted(List<GraphUser> users, Response response)
                        {
                        	friends = new ArrayList<FBUser>();
                            for(GraphUser i : users){ 
                            	Log.i("Facebook", i.getName());
                            	Log.i("Facebook", i.getId());
                            	friends.add(new FBUser(i.getId(),i.getName(),true));
                            }
                            
                        	ArrayList<WishItem> wishes = new ArrayList<WishItem>();

                        	wishes.add(new WishItem("dummyID1", "Red Ryder BB Gun", currentAppUser.getUID(), currentAppUser.getName(), "", "", "The Red Ryder BB Gun is a lever-action, spring piston air gun with a smooth bore barrel, adjustable iron sights, and a gravity feed magazine with a 650 BB capacity", "10", 0, new Date(3,4,2014)));
                        	wishes.add(new WishItem("dummyID2", "A Feast of Ice and Fire", currentAppUser.getUID(), currentAppUser.getName(), "", "", "Fresh out of the series that redefined fantasy, comes the cookbook that may just redefine dinner . . . and lunch, and breakfast. ", "20", 0, new Date(3,4,2014)));
                        	
                        	currentAppUser.setList(wishes);
                        	
                            pack();
                            start();
                        }
                    }).executeAsync();
                }
                else if(session.isClosed()){
                	Log.i("Facebook", session.getState().toString());
                }
            }
        });
    }
    
    protected void stopFBSession()
    {
    	s.close();
    }
    
    @SuppressWarnings("deprecation")
	protected void test()
    {// sets a dummy user for testing purposes
    	currentAppUser = new FBUser("0", "John Doe", true);
    	friends = new ArrayList<FBUser>();
    	for(int i=1; i<11; i++){
    		friends.add(new FBUser(Integer.toString(i), Integer.toString(i), false));
    	}
    	ArrayList<WishItem> wishes = new ArrayList<WishItem>();

    	wishes.add(new WishItem("dummyID1", "Red Ryder BB Gun", currentAppUser.getUID(), currentAppUser.getName(), "", "", "The Red Ryder BB Gun is a lever-action, spring piston air gun with a smooth bore barrel, adjustable iron sights, and a gravity feed magazine with a 650 BB capacity", "10", 0, new Date(3,4,2014)));
    	wishes.add(new WishItem("dummyID2", "A Feast of Ice and Fire", currentAppUser.getUID(), currentAppUser.getName(), "", "", "Fresh out of the series that redefined fantasy, comes the cookbook that may just redefine dinner . . . and lunch, and breakfast. ", "20", 0, new Date(3,4,2014)));
    	
    	currentAppUser.setList(wishes);
    	
    	pack();
    	start();
    }
}
