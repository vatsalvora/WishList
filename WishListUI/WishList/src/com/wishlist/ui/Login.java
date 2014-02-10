package com.wishlist.ui;

import java.util.*;

import android.os.Bundle;
import android.app.Activity;
import com.facebook.*;
import com.facebook.model.*;
import com.wishlist.obj.*;

import android.util.Log;
import android.content.Intent;

public class Login extends Activity {
	
	public static User currentAppUser; //user object for the Facebook user actually using the app.
	ArrayList<GraphUser> friends;
	
	@Override
	 public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
//	    setContentView(R.layout.activity_login);
	    /* We don't need an actual layout here; if the user is not logged in the
	    *  code below automatically brings up the facebook login anyway. If the user is
	    * already logged in, it brings up the main activity. 
	    */
	    
	    // start Facebook Login
	    Session.openActiveSession(this, true, new Session.StatusCallback() {

	      // callback when session changes state
		@Override
	      public void call(Session session, SessionState state, Exception exception) {
	        if (session.isOpened()) {
	        	Log.i("MyActivity", "MyClass.getView() — get item number ");
	          // make request to the /me API
	          Request.newMeRequest(session, new Request.GraphUserCallback() {
	        	
	            // callback after Graph API response with user object
	            @Override
	            public void onCompleted(GraphUser user, Response response) {
	            	currentAppUser = new FBUser(user.getId(), user.getName(), true);
	            }
	          });
	          
	          //make request to the /friends-list API
	          Request.newMyFriendsRequest(session, new Request.GraphUserListCallback() {
				
				@Override
				public void onCompleted(List<GraphUser> users, Response response) {
					friends = new ArrayList<GraphUser>(users);
				}
	          });
	        }
	      }
	    });
	  }

	  @Override
	  public void onActivityResult(int requestCode, int resultCode, Intent data) {
	      super.onActivityResult(requestCode, resultCode, data);
	      Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
	      //Start the main activity
	      Intent intent = new Intent(this, MainActivity.class);
    	  startActivity(intent);
	  }
	  
	}
