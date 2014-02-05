package com.wishlist.ui;

import android.os.Bundle;
import android.app.Activity;
import com.facebook.*;
import com.facebook.model.*;

import android.util.Log;
import android.widget.TextView;
import android.content.Intent;

public class Login extends Activity {
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
	      @SuppressWarnings("deprecation")
		@Override
	      public void call(Session session, SessionState state, Exception exception) {
	        if (session.isOpened()) {
	        	Log.i("MyActivity", "MyClass.getView() — get item number ");
	          // make request to the /me API
	          Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {
	        	
	            // callback after Graph API response with user object
	            @Override
	            public void onCompleted(GraphUser user, Response response) {
	             /* if (user != null) {
	                	TextView welcome = (TextView) findViewById(R.id.welcome);
	                	welcome.setText("Hello " + user.getName() + "!");
	              } */
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
	      Intent intent = new Intent(Login.this, MainActivity.class);
    	  startActivity(intent);
	  }

	}