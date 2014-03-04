package com.wishlist.ui;

import java.util.*;

import android.os.Bundle;
import android.app.Activity;
import com.facebook.*;
import com.facebook.model.*;
import com.wishlist.obj.FBUser;

import android.util.Log;
import android.content.Intent;

public final class Login extends Activity
{

    private FBUser currentAppUser= null; //user object for the Facebook user actually using the app.
    private ArrayList<FBUser> friends = null; //ID of friends
    private Bundle FBData = null;
    //private Bundle FBSession;
    private Session s = null;

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
        super.onActivityResult(requestCode, resultCode, data);
        Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
        //Start the main activity
        //pack();
        start();
    }
    
    protected void pack()
    {
    	FBData = new Bundle();
    	Transporter.packIntoBundle(FBData, Transporter.USER, currentAppUser);
        Transporter.packIntoBundle(FBData, Transporter.FRIENDS, friends);
    }
    
    protected void start()
    {
    	 Intent intent = new Intent(this, MainActivity.class);
         if(FBData != null) intent.putExtra(Transporter.FBDATA, FBData);
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
                    Log.i("MyActivity", "MyClass.getView() � get item number ");
                    // make request to the /me API
                    Request.newMeRequest(session, new Request.GraphUserCallback()
                    {

                        // callback after Graph API response with user object
                        @Override
                        public void onCompleted(GraphUser user, Response response)
                        {
                            currentAppUser = new FBUser(user.getId(), user.getName(), true);
                        }
                    });

                    //make request to the /friends-list API
                    Request.newMyFriendsRequest(session, new Request.GraphUserListCallback()
                    {

                        @Override
                        public void onCompleted(List<GraphUser> users, Response response)
                        {
                            friends = new ArrayList<FBUser>();
                            for(GraphUser i : users) friends.add(new FBUser(i.getId(), i.getName(), false));
                        }
                    });
                }
            }
        });
        
        //save the session in pair
        //Session.saveSession(s, FBSession);
    }
    
    protected void stopFBSession()
    {
    	s.close();
    }
    
    protected void test()
    {// sets a dummy user for testing purposes
    	currentAppUser = new FBUser("0", "John Doe", true);
    	friends = new ArrayList<FBUser>();
    	for(int i=1; i<11; i++){
    		friends.add(new FBUser(Integer.toString(i), Integer.toString(i), false));
    	}
    	pack();
    	start();
    }
}
