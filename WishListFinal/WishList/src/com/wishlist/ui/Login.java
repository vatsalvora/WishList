package com.wishlist.ui;

import java.util.*;

import android.os.Bundle;
import android.os.Parcelable;
import android.app.Activity;
import com.facebook.*;
import com.facebook.model.*;
import com.wishlist.obj.FBUser;

import android.util.Log;
import android.content.Intent;

public class Login extends Activity
{

    public FBUser currentAppUser; //user object for the Facebook user actually using the app.
    private ArrayList<FBUser> friends; //ID of friends
    private Bundle FBData = null;
    private Bundle FBSession = null;
    private Session s = null;

    //hashcodes passing data around
    public static final String FBDATA = "FBData";
    public static final String USER = "User";
    public static final String FRIENDS = "Friends";
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
//	    setContentView(R.layout.activity_login);
        /* We don't need an actual layout here; if the user is not logged in the
        *  code below automatically brings up the facebook login anyway. If the user is
        * already logged in, it brings up the main activity.
        */
        startFBSession();
       
    }

    protected void onResume()
    {
    	super.onResume();
    }
    
    protected void onStart()
    {
    	super.onStart();
    	startFBSession();
    }
    
    protected void onPause()
    {
    	super.onPause();
    }
    
    protected void onRestart()
    {
    	super.onRestart();
    	startFBSession();
    }
    
    protected void onStop()
    {
    	super.onStop();
    	stopFBSession();
    }
    
    protected void onDestroy()
    {
    	super.onDestroy();
    	stopFBSession();
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
        //Start the main activity
        packIntoBundle(FBData, USER, currentAppUser);
        packIntoBundle(FBData, FRIENDS, friends);
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(FBDATA, FBData);
        startActivity(intent);
    }
    
    private void packIntoBundle(Bundle b, String key, Parcelable p)
    {
    	b.putParcelable(key, p);
    }
    
    private void packIntoBundle(Bundle b, String key, ArrayList<? extends Parcelable> p){
    	b.putParcelableArrayList(key,p);
    }
    
    private void startFBSession(){
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
        Session.saveSession(s, FBSession);
    }
    
    private void stopFBSession(){
    	s.close();
    }
}
